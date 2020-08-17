# Safe-Seat-App
![App Logo](/logo/safe-seat-logo.png)
<br/>
Pick your safe seat, and enjoy life, stay away from COVID-19 :)

---



## Spring-Boot / Java 14 API :
This API, provide the necessary endpoints to reserve your seat.
This API, helps you reserve your seat at work, in a way that respects the distancing rules of COVID-19. And provide some other useful information.



## The rules of a valid (safe) seat are :
1. The right, left, front, and behind seats must be empty.
2. In a floor X the available seats for reservation are 50% to the floor's total capacity.

## Data example :
Here is the default data,loaded on server startup : <br/>
> This image demonstrate, the available safe seats
![Seats example](/logo/seats.png)



## The exposed endpoints :
### https://safe-seat-app.herokuapp.com/api/v1/valid/seats
> [GET_REQUEST] => Get all valid seats, that we can book.

### https://safe-seat-app.herokuapp.com/api/v1/new/reservation
> [POST_REQUEST] => Book new valid seat, We can't reserve multiple seats within the same period.

### https://safe-seat-app.herokuapp.com/api/v1/reservations/details
> [GET_REQUEST] => Get all reservations details for all employees/users.

### https://safe-seat-app.herokuapp.com/api/v1/team/{teamId}/valid/seats
> [GET_REQUEST],[TEAM_ID] => Get the nearest valid seats to your teammates.

### https://safe-seat-app.herokuapp.com/api/v1/user/{employeeId}/reservations
> [GET_REQUEST],[EMPLOYEE_ID] => Get reservations details for one employee/user.

### https://safe-seat-app.herokuapp.com/api/v1/all/seats
> [GET_REQUEST] => Get all seats

### https://safe-seat-app.herokuapp.com/api/v1/all/sites
> [GET_REQUEST] => Get all sites

### https://safe-seat-app.herokuapp.com/api/v1/all/floors
> [GET_REQUEST] => Get all floors
