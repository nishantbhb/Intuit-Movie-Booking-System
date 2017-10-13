package com.intuit.movie.model;

import java.util.List;
import java.util.UUID;

public class Booking_Details {
	UUID booking_id;
	String movie_name;
	String theatre_name;
	String location;
	String screen_id;
	String show_time;
	List<Integer> seats;

	public Booking_Details() {
		super();
	}

	public Booking_Details(UUID booking_id, String movie_name, String theatre_name, String location, String screen_id,
			String show_time, List<Integer> seats) {
		super();
		this.booking_id = booking_id;
		this.movie_name = movie_name;
		this.theatre_name = theatre_name;
		this.location = location;
		this.screen_id = screen_id;
		this.show_time = show_time;
		this.seats = seats;
	}

	public UUID getBooking_id() {
		return booking_id;
	}

	public void setBooking_id(UUID booking_id) {
		this.booking_id = booking_id;
	}

	public String getMovie_name() {
		return movie_name;
	}

	public void setMovie_name(String movie_name) {
		this.movie_name = movie_name;
	}

	public String getTheatre_name() {
		return theatre_name;
	}

	public void setTheatre_name(String theatre_name) {
		this.theatre_name = theatre_name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getScreen_id() {
		return screen_id;
	}

	public void setScreen_id(String screen_id) {
		this.screen_id = screen_id;
	}

	public String getShow_time() {
		return show_time;
	}

	public void setShow_time(String show_time) {
		this.show_time = show_time;
	}

	public List<Integer> getSeats() {
		return seats;
	}

	public void setSeats(List<Integer> seats) {
		this.seats = seats;
	}

	@Override
	public String toString() {
		return "Booking_Details [booking_id=" + booking_id + ", movie_name=" + movie_name + ", theatre_name="
				+ theatre_name + ", location=" + location + ", screen_id=" + screen_id + ", show_time=" + show_time
				+ ", seats=" + seats + "]";
	}

}
