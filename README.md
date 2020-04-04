# la restaurante

>“Ask not what you can do for your country. Ask what’s for lunch.”
>― Orson Welles

la restaurante is a simple application to manage restaurant business by helping to keep tracking of it's resources, customers and
reservations and provide the needed information for it's customers and employees with a fancy user interface. 

## Screenshots

![Image of loginPage](https://i.imgur.com/0KTrKXx.png)
![Image of orderPage](https://i.imgur.com/LtnnAs3.png)
![Image of chefPage](https://i.imgur.com/EBgKGgj.png)

## Features

- Allows the customers to make reservations which is suitable with the restaurant schedule and available tables and order dishes from a menu and know the price for each dish
- Allows the waiters to see the reservations for today to be able to set the tables ready for them
- Allows the chefs to see ordered dishes for today with the time of reservation to be able to cook the dishes on time
- Allows the manager to see all the reservations for today and to see the total income of the restaurant for today and the check for each customer

## User Manual

### New Users

New customers are allowed to signup to the program by clicking on the signup button and adding their data
- They should provide their full name ex 'John Smith'
- Username should not contain spaces
- Password should be 6 chars or more

Employees are registered only through the manager's dashboard to assure that valid employees

### Customers

Customers should be able to access their dashboard through logging in from the login page using their username and password
*They are allowed to make one reservation* and pick their prefrences for a table with suitable number of seats and whether if it's in the smoking area or not

- They should not leave any empty field
- They should provide a valid reservation date (A Date and time which are yet to come not have already passed)
- They must specify whether the table is in the smoking area or not
- They must pick the number of persons to make the system be able to reserve a table with the same amount of seats or more which fits the customer

**After creating a successful reservation they are promoted to the order page where they can make their order**.

A Customer should select an dish from any of the lists either a Main dish , Appetizer or dessert and then select the amount of dishes he would like to order and then 
click add to the cart to add it to the list of dishes he would like to order

The price for each dish should be visibile to the user and total amount money to be paid before and after the taxes

- Customer should not order with an empty cart, he must at least add one dish
- Customer should not click add to cart without selecting a dish

**After creating a sucessful order customer is promoted to a page where it contains the details of his order and date of the reservation**

Everytime the user tries to login while having an upcomming reservation this page will appear to him.
Customer will be allowed to create a new Reservation and be navigated to the page where he can make a reservation when the time of his reservation is already over

### Waiters

Waiters should be able to access their dashboard through logging in from the login page using their username and password

Waiters are allowed to see the reservations for today with the customer name and their reserved table and the time of their reservation

### Chefs

Chefs should be able to access their dashboard through logging in from the login page using their username and password

Chefs are allowed to see the ordered meals for today and it's quantity, tables ordering it and the time the meal should be ready at

### Manager

Manager should be able to access their dashboard through logging in from the login page using their username and password

Manager is allowed to see all reservations for today, the customer which are reserving the tables today, their ordered meals and the amount of money they paid

A List of reservations should be available to him. When he clicks an element of it the amount the customer paid be and the meals he ordered should be visible

He is also allowed to see the total income of the restaurant today.

The Manager is also **the only one allowed to navigate to the employee signup page** where he will be allowed to register his new employees and select their roles
by clicking on the employee sign up button


And the proccess of signing up the employees is the exact same as signing up new customers but it will vary only in adding the role of the employee


## Technologies Used

- Java 11
- JUnit Test 5.4
- Dom Parser (To gain complete detailed control over the xml file objects and properties)
- JavaFX (fxml files with css for styling the application)

## Project Structure

### GUI Layer

The Application GUI is located at **restaurant/gui** package with it's dependancies

- **gui/pages** package contains the Pages of the application with a controller provided for each page
- **gui/guiUtils** package contains the needed dependancies for the gui and the navigation between the pages
- **gui/StyleSheets** package contains the style sheets (.css files) for the fxml pages
- **gui/Images** package contains the needed images for the application

### Data Access Layer

We used the Service-Repoistory Hireachy which allows extenisbility and mobility for the data access layer. Also makes it easier for Unit Testing
and splitting the file access and IO logic from the actual project data and objects

- **data/services** contains the services for accessing the data.xml file and performing a specific R/W operations
which are designed to easily be extendable for more data types in the application in the future
- **data/repositories** contains the repoistories which is the middle logic between the services and the application gui 
to reduce the amount of logic code in the gui pages and make the logic layer seperated from other packages and logic
- **data/files** contains the files needed for the program to run

### Models

The **models** package contains all the required models and data types for the application to function well

the model package contains different packages which group each models with things in common or related to each other

**Factory Design Pattern** was implemented in the creation of the User model to offer more reuseability and much more clean code

### Utilities

The application had two utilities packages.
* Utility package for GUI Only which existed in the **gui/utils** package *which was mainly focused on navigation*
* Utility package for the rest of the application **a General one** which existed in **gui/utils** *which was mainly focused on calendar and date manpilation*

### Testing

The application some unit test cases for the repoistories layer of the application which ensures we are extracting the correct data for the user

The tests exists in the **restuarantTest** Package

**Note**: it's taken into note that in more compilcated applications we should implement the tests on a mocked file or database not to ruin the restaurant main data
but for the sake of simplicity we tested on the same file


## The Process of Creating the Application

### Gitlab

File transfers between the team was mainly based on gitlab by pushing and pulling commits from eachother
which allowed them to handle the changes in a professional way and allowed them to handle and overcome the conflicts and challenges
that resulted from a problems from dealing with the version control system

### Labor Division

We would create a list of tasks and divide the tasks by eachother and brief eachother when completing a task about the
way it was implemented and we try to circulate the similiar tasks between us to learn more and to fully understand
how each single small component of the system works and how it is put together

## Conclusion

la restaurante is a collaboration of hard work of the team of the project who spent much time to make sure it fits
the needs of a user and meet the requirements and standards of any modern application

it is simple application which depends on files in ing its data , but the way it's designed allows it to be extended
and have much more features without much dependancies between classes and can easily be converted to *Database-based application*

Thank you.

Omar Alaa & Yosra Emad














