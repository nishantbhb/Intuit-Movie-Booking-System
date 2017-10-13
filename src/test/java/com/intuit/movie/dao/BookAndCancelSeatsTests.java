package com.intuit.movie.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { BookSeat.class, InstantiateDB.class, CancelTicket.class })
public class BookAndCancelSeatsTests {

	private UUID booking_id;
	int show_id;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	BookSeat bs;

	@Autowired
	CancelTicket ct;

	@Test
	public void testBlockSeats() {
		List<Integer> seats = new ArrayList<>();
		seats.add(1);
		seats.add(2);
		show_id = 1;
		bs.blockSeats(show_id, seats);

		String sql = "SELECT count(*) from movie_show where status = 1 and show_id = ?";

		int seatsLeft = jdbcTemplate.queryForObject(sql, new Object[] { show_id }, Integer.class);
		Assert.assertEquals(14, seatsLeft);
	}

	@Test
	public void testCreateBookingId() {
		booking_id = UUID.randomUUID();
		show_id = 1;
		List<Integer> bookSeats = new ArrayList<>();
		bookSeats.add(1);
		bookSeats.add(2);

		bs.createBookingId(booking_id, show_id, bookSeats);

		String sql = "SELECT distinct (show_id) from booking where booking_id = ?";

		int db_show_id = jdbcTemplate.queryForObject(sql, new Object[] { booking_id }, Integer.class);
		Assert.assertEquals(show_id, db_show_id);

	}

	@Test
	public void testFreeSeats() {
		List<Integer> seats = new ArrayList<>();
		seats.add(1);
		seats.add(2);
		show_id = 1;
		ct.freeSeats(show_id, seats);
		String sql = "SELECT count(*) from movie_show where status = 1 and show_id = ?";
		int seatsLeft = jdbcTemplate.queryForObject(sql, new Object[] { show_id }, Integer.class);
		Assert.assertEquals(16, seatsLeft);

	}

	@Test
	public void testDeleteBooking() {
		ct.deleteBooking(booking_id);
		int rows = jdbcTemplate.update("DELETE FROM BOOKING WHERE BOOKING_ID = ?", new Object[] { booking_id });
		Assert.assertEquals(0, rows);

	}
}
