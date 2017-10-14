package com.intuit.movie.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CancelTicket {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@SuppressWarnings("resource")
	public void freeTicket() {
		boolean flag = true;
		while (flag) {
			System.out.println("------------------");
			System.out.println("Enter 100 to exit!!!");
			System.out.println("Enter booking id: ");
			Scanner s = new Scanner(System.in);
			try {
				String choice = s.nextLine();

				try {
					if (Integer.parseInt(choice) == 100) {
						flag = false;
						continue;
					}
				} catch (IllegalArgumentException e) {
					System.out.println("Checking in DB....!!!");
				}
				UUID booking_id = UUID.fromString(choice);

				int show_id = jdbcTemplate.queryForObject("SELECT distinct (SHOW_ID) FROM BOOKING WHERE BOOKING_ID = ?",
						new Object[] { booking_id }, Integer.class);

				String sql = "Select seat from booking where booking_id = ?";
				List<Integer> seats = jdbcTemplate.queryForList(sql, new Object[] { booking_id }, Integer.class);

				freeSeats(show_id, seats);
				deleteBooking(booking_id);
				flag = false;

			} catch (EmptyResultDataAccessException e) {
				System.out.println("Invalid Booking_Id.");
			} catch (IllegalArgumentException e) {
				System.out.println("Enter booking_id in valid format");
			}
		}

	}

	public void freeSeats(int show_id, List<Integer> seats) {
		String sql = "update movie_show set status = 1 where show_id = ? and seat = ?";

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

	public void deleteBooking(UUID booking_id) {
		jdbcTemplate.update("DELETE FROM BOOKING WHERE BOOKING_ID = ?", new Object[] { booking_id });
		System.out.println("Booking Cancelled!!!");

	}
}
