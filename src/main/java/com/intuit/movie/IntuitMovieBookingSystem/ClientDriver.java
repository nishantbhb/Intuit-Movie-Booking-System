package com.intuit.movie.IntuitMovieBookingSystem;

import java.util.InputMismatchException;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.intuit.movie.dao.BookSeat;
import com.intuit.movie.dao.CancelTicket;
import com.intuit.movie.dao.CheckSeatsAvailable;

@Component
public class ClientDriver {

	@Autowired
	CheckSeatsAvailable csa;
	@Autowired
	CancelTicket ct;
	@Autowired
	BookSeat bs;

	public void PrintChoice() {

		int choice = 0;
		do {

			Scanner s = new Scanner(System.in);
			System.out.println("------------------------------------------");
			System.out.println("Hello There!!! Select choice!!!");
			System.out.println("1. Check Seats Available");
			System.out.println("2. Book Seats");
			System.out.println("3. Cancel Booking");
			System.out.println("4. Exit");
			System.out.print("Enter Choice: ");
			try {
				choice = s.nextInt();
				switch (choice) {
				case 1:
					csa.showMovies();
					break;
				case 2:
					bs.selectSeat();
					break;
				case 3:
					ct.freeTicket();
					break;

				case 4:
					System.out.println("Thank You for using our service. Bye!");
					s.close();
					break;
				default:
					System.out.println("Enter correct choice!!");
					break;
				}
			} catch (InputMismatchException e) {
				System.out.println("Enter a valid number.");

			}

		} while (choice != 4);

	}

}
