package com.intuit.movie.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.intuit.movie.model.Booking_Details;

@Component
public class BookSeat {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	CheckSeatsAvailable csa;

	@SuppressWarnings("resource")
	public void selectSeat() {
		String sql = "Select show_id, count(*) from movie_show where status = 1 group by show_id";
		Map<Integer, Integer> show_ids = jdbcTemplate.query(sql, (ResultSet rs) -> {
			HashMap<Integer, Integer> results = new HashMap<>();
			while (rs.next()) {
				results.put(rs.getInt(1), rs.getInt(2));
			}
			return results;
		});
		sql = "Select show_id, seat from movie_show where status = 1 order by show_id, seat asc";
		Map<Integer, List<Integer>> seat_Map = jdbcTemplate.query(sql, (ResultSet rs) -> {
			HashMap<Integer, List<Integer>> results = new HashMap<>();
			List<Integer> seats = new ArrayList<>();
			while (rs.next()) {
				if (results.containsKey(rs.getInt(1))) {
					seats.add(rs.getInt(2));
					results.put(rs.getInt(1), seats);
				} else {
					seats = new ArrayList<>();
					seats.add(rs.getInt(2));
					results.put(rs.getInt(1), seats);
				}
			}
			return results;
		});

		boolean flag = true;
		while (flag) {
			Scanner s = new Scanner(System.in);
			try {
				System.out.println("---------------------------");
				System.out.println("Press 1 if you know show_id");
				System.out.println("Press 2 to check shows");
				System.out.println("Press 100 to go to main menu");
				System.out.println("---------------------------");
				System.out.println("Enter choice:");
				int choice = s.nextInt();
				if (choice == 2) {
					csa.showMovies();
					flag = false;
				} else if (choice == 1) {
					System.out.println("Press 100 to Exit!!!");
					System.out.println("Enter Show_Id: ");
					int show_id = s.nextInt();
					if (show_id == 100)
						flag = false;
					else if (show_ids.containsKey(show_id)) {
						System.out.println("Press 100 to Exit!!!");
						System.out.println("Enter no. of tickets: ");
						int ticketCnt = s.nextInt();
						if (ticketCnt == 100)
							flag = false;
						else if (show_ids.get(show_id) >= ticketCnt) {
							flag = false;
							System.out.println("Select Seats:");
							System.out.println("---------------------");
							System.out.println("---Screen this way---");
							int seats[] = new int[16];
							for (int i : seat_Map.get(show_id))
								seats[i - 1] = i;

							for (int i = 0; i < 16; i++) {
								System.out.print(seats[i] + " ");
								if ((i + 1) % 4 == 0)
									System.out.println();
							}
							List<Integer> bookSeats = new ArrayList<>();
							for (int i = 1; i <= ticketCnt; i++) {
								boolean flg = true;
								while (flg) {
									System.out.print("Enter seat no. " + i + ": ");
									try {
										s = new Scanner(System.in);
										int seatNo = s.nextInt();
										if (seat_Map.get(show_id).contains(seatNo)) {
											if (bookSeats.contains(seatNo))
												System.out.println(
														"You already selected this seat. Try again a new seat.");
											else {
												flg = false;
												bookSeats.add(seatNo);
											}
										} else {
											System.out.println("Enter a valid seat no.");
										}
									} catch (InputMismatchException e) {
										System.out.println("Seat Number must be a number.");

									}
								}
							}
							UUID booking_id = UUID.randomUUID();
							blockSeats(show_id, bookSeats);
							createBookingId(booking_id, show_id, bookSeats);
							sql = "SELECT c.movie_name, b.theatre_name, b.location, a.screen_id, a.show_time from movies c natural join movie_show A NATURAL JOIN theatre B where A.show_id = ? GROUP BY c.movie_name, b.theatre_name, b.location, a.screen_id, a.show_time";
							Object show[] = { show_id };
							Booking_Details bd = jdbcTemplate.queryForObject(sql, show,
									new RowMapper<Booking_Details>() {
										@Override
										public Booking_Details mapRow(ResultSet rs, int rowNum) throws SQLException {
											return new Booking_Details(booking_id, rs.getString(1), rs.getString(2),
													rs.getString(3), rs.getString(4), rs.getString(5), bookSeats);
										}
									});
							System.out.println("Ticket Booked!!!");
							System.out.println("Please make payment at the theatre.");
							System.out.println(bd.toString());

						} else {
							System.out.println(
									"This show has only " + show_ids.get(show_id) + " seats available. Try Again!");
						}
					} else {
						System.out.println("Enter a valid show_id. Try Again!");
					}
				} else if (choice == 100) {
					flag = false;
				} else {
					System.out.println("Enter a valid option");
				}
			} catch (InputMismatchException e) {
				System.out.println("Input must be a number.");

			}
		}
	}

	public void blockSeats(int show_id, List<Integer> seats) {
		String sql = "update movie_show set status = 0 where show_id = ? and seat = ?";

		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				int seatNo = seats.get(i);
				ps.setInt(1, show_id);
				ps.setInt(2, seatNo);

			}

			@Override
			public int getBatchSize() {
				return seats.size();
			}
		});
	}

	public void createBookingId(UUID booking_id, int show_id, List<Integer> bookSeats) {
		String sql = "insert into booking values( ?, ?, ? )";

		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				int seatNo = bookSeats.get(i);
				ps.setObject(1, booking_id);
				ps.setInt(2, show_id);
				ps.setInt(3, seatNo);

			}

			@Override
			public int getBatchSize() {
				return bookSeats.size();
			}
		});

	}
}
