package com.intuit.movie.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.intuit.movie.model.Movie_Details;
import com.intuit.movie.model.Seat_Count;

@Component
public class CheckSeatsAvailable {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@SuppressWarnings("resource")
	public void showMovies() {
		List<Movie_Details> movies = jdbcTemplate.query("SELECT * FROM MOVIES", new RowMapper<Movie_Details>() {
			@Override
			public Movie_Details mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Movie_Details(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
			}
		});
		List<Integer> movie_ids = new ArrayList<Integer>();
		for (Movie_Details md : movies) {
			movie_ids.add(md.getMovie_id());
			System.out.println(md.toString());
		}
		System.out.println("Enter 100 to exit!!");
		int movie_id = 0;
		boolean flag = true;

		while (flag) {
			Scanner s = new Scanner(System.in);
			try {
				System.out.println("Select Movie_ID: ");

				movie_id = s.nextInt();

				if (movie_ids.contains(movie_id)) {
					showTheatres(movie_id);
					flag = false;
					continue;
				} else if (movie_id == 100) {
					flag = false;
					continue;
				} else
					System.out.println("Invalid Choice!!!");
			} catch (InputMismatchException e) {
				System.out.println("Movie_Id must be a number.");

			}
		}
		// s.close();

	}

	public void showTheatres(int movie_id) {
		String sql = "SELECT A.show_id, A.theatre_id, B.theatre_name, B.location, A.show_time, count(*) as seat_cnt from movie_show A NATURAL JOIN theatre B where A.movie_id = ? and A.status = 1 GROUP BY A.show_id, A.THEATRE_ID, B.theatre_name, B.location, A.show_time";
		Object movie[] = { movie_id };
		List<Seat_Count> movies = jdbcTemplate.query(sql, movie, new RowMapper<Seat_Count>() {
			@Override
			public Seat_Count mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Seat_Count(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getInt(6));
			}
		});
		for (Seat_Count sc : movies)
			System.out.println(sc.toString());
	}
}
