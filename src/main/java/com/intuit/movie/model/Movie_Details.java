package com.intuit.movie.model;

public class Movie_Details {
	int movie_id;
	String movie_name;
	String langu;
	String genre;

	public Movie_Details() {
		super();
	}

	public Movie_Details(int movie_id, String movie_name, String langu, String genre) {
		super();
		this.movie_id = movie_id;
		this.movie_name = movie_name;
		this.langu = langu;
		this.genre = genre;
	}

	public int getMovie_id() {
		return movie_id;
	}

	public void setMovie_id(int movie_id) {
		this.movie_id = movie_id;
	}

	public String getMovie_name() {
		return movie_name;
	}

	public void setMovie_name(String movie_name) {
		this.movie_name = movie_name;
	}

	public String getLangu() {
		return langu;
	}

	public void setLangu(String langu) {
		this.langu = langu;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	@Override
	public String toString() {
		return "Movie_Details [movie_id=" + movie_id + ", movie_name=" + movie_name + ", langu=" + langu + ", genre="
				+ genre + "]";
	}

}
