## First Spring Boot Project (with REST and JDBC)
Being my first foray into Spring Boot, this project is based off on this tutorial [here](https://www.springboottutorial.com/creating-rest-service-with-spring-boot).

However, it's not a straight adoption but replaces the hard-coded data retrieval/manipulation via arrays with a JDBC-based data storage approach instead.
We also use two different application property settings: `dev` and `prod`. The `dev` setting uses an embedded H2 in-memory database and scripts for populating that
database are provided. The `prod` setting uses an equivalent local MySQL database, so if you want to use that, you'll either have to set it up on your own first or try setting `spring.sql.init.mode` to `always` (I did not test if these initialization scripts work with the MySQL dialect though).

### REST interface
Following there is an overview over the functionality provided by the project's REST interface. All below functionality uses JSON as exchange data format: 
- `GET: localhost:8080/students` - retrieves a list of all student data and all the associated courses data
- `GET: localhost:8080/students/{studentId}`  - returns the same data for a particular student only
- `GET: localhost:8080/students/{studentId}/courses` - returns the data for all courses the given student is registered for
- `GET: localhost:8080/students/{studentId}/courses/{courseId}` - returns the data for the given course, provided the given student is registered for it
- `POST: /students/{studentId}/courses` - registers the given student to the new course passed via JSON in the POST request body; the name of the new course must be unique to the course list. Returns the new course data, including the auto-assigned new course ID.

## License
The project is available under the **[EPL 2.0 License]**


[EPL 2.0 License]: https://www.eclipse.org/legal/epl-2.0/