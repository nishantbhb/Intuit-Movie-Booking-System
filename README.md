# Intuit-Movie-Booking-System
A console based movie booking system in JAVA using Spring Boot and HSQLDB.<br>
This is a standalone JAVA application which will give the user 4 choices:<br>
1. Check Seats Available
2. Book Seats
3. Cancel Booking
4. Exit
<UL>
  <LI>User can select any of the 4 options
  <LI>There are multiplse screens and multiple shows in each theatre.
  <LI>During Booking, user has to check seats available and take note of the show_id to proceed with seat booking.
  <LI>When user selects choice 1, he gets the list of movies and its details.
  <LI>The next selection user does for movie and gets to see all the show details along with number of seats available.
  <LI>User gets to see the seat_map only after selecting the show and number of tickets.
  <LI>Once the right show, seat(s) is/are selected a new booking_id (guid) will be generated and a new record will be created.
    Also, those seats will be marked occupied in the matrix represented by 0.
  <LI>After successful booking, details susch as booking_id, movie name, theatre name , location, show time and seat nos. will be displayed.
    <LI>For cancellation, the user has to enter valid booking_id. Using this the booking will be cancelled and the seats will be freed.    
</UL>
<B>All the checks for input and exception handling is done.<br><br>

<B>Assumption:
> There are only 16 seats in each screen.
