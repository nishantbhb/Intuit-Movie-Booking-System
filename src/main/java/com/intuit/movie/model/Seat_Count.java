package com.intuit.movie.model;

public class Seat_Count {
	int show_id;
	int theatre_id;
	String theatre_name;
	String location;
	String time;
	int seats_left;

	public Seat_Count() {
		super();
	}

	public Seat_Count(int show_id, int theatre_id, String theatre_name, String location, String time, int seats_left) {
		super();
		this.show_id = show_id;
		this.theatre_id = theatre_id;
		this.theatre_name = theatre_name;
		this.location = location;
		this.time = time;
		this.seats_left = seats_left;
	}

	public int getShow_id() {
		return show_id;
	}

	public void setShow_id(int show_id) {
		this.show_id = show_id;
	}

	public int getTheatre_id() {
		return theatre_id;
	}

	public void setTheatre_id(int theatre_id) {
		this.theatre_id = theatre_id;
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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getSeats_left() {
		return seats_left;
	}

	public void setSeats_left(int seats_left) {
		this.seats_left = seats_left;
	}

	@Override
	public String toString() {
		return "Seat_Count [show_id=" + show_id + ", theatre_id=" + theatre_id + ", theatre_name=" + theatre_name
				+ ", location=" + location + ", time=" + time + ", seats_left=" + seats_left + "]";
	}

}
