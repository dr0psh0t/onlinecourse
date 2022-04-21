# Online Course Mini Project

### Project Setup

* After cloning this project, go to the database folder under src/main/resources.
* Execute first the create-user.sql then create-db.sql.
* After that you can start working with the project.

### One time application run

* Open application.properties and uncomment spring.jpa.hibernate.ddl-auto=create.
* Open OnlinecourseApplication.java and uncomment lines 34-44.
* Run OnlinecourseApplication.java as Java application.

### After one time application run

* Open application.properties and comment spring.jpa.hibernate.ddl-auto=create.
* Open OnlinecourseApplication.java and comment lines 34-44.
* So that every time you rerun, the values in the database will not be erased.


* Go to http://localhost:8080/onlinecourse/Login