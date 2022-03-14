## First Spring Boot Project (with REST, JDBC and Reactive Kafka)

#### IMPORTANT: for now only the profile 'dev-kafka' works. This will get fixed in the next iteration of this project.

Being my first foray into Spring Boot, this project is based off on this tutorial [here](https://www.springboottutorial.com/creating-rest-service-with-spring-boot).

However, it's not a straight adoption but replaces the hard-coded data retrieval/manipulation via arrays with a JDBC-based data storage approach instead.
~~We also use two different application property settings: `dev` and `prod`. The `dev` setting uses an embedded H2 in-memory database and scripts for populating that
database are provided. The `prod` setting uses an equivalent local MySQL database, so if you want to use that, you'll either have to set it up on your own first or try setting `spring.sql.init.mode` to `always` (I did not test if these initialization scripts work with the MySQL dialect though).~~

The project now also uses Reactive Kafka to communicate with [my other project's reservation service](https://github.com/ak-73/reservation-service) (see `POST: /guests/{guestId}/courses` endpoint below). 

#### In order to facilitate communication with reservation-service, you will therefore need Zookeeper running on Port 2181 and Apache Kafka on Port 9092!

### REST interface
Following there is an overview over the functionality provided by the project's REST interface. All below functionality uses JSON as exchange data format: 
- `GET: localhost:8080/students` - retrieves a list of all student data and all the associated courses data
- `GET: localhost:8080/students/{studentId}`  - returns the same data for a particular student only
- `GET: localhost:8080/students/{studentId}/courses` - returns the data for all courses the given student is registered for
- `GET: localhost:8080/students/{studentId}/courses/{courseId}` - returns the data for the given course, provided the given student is registered for it
- `POST: /students/{studentId}/courses` - registers the given student to the new course passed via JSON in the POST request body; the name of the new course must be unique to the course list. Returns the new course data, including the auto-assigned new course ID.
*NEW:*
- `GET: localhost:8080/guests` - retrieves a list of all guest lecturers data and all the associated courses data
- `POST: /guests/{guestId}/courses` - registers the given guest lecturer for conducting the new course passed via JSON in the POST request body; as above, the name of the new course must be unique to the course list. Returns the new course data, including the auto-assigned new course ID. Most notably this also informs [my other project's reservation service](https://github.com/ak-73/reservation-service) so that it can book suitable lodgings for the guest lecturer for the duration of the stay (yes, it's a contrived example, tailored to tie both tutorial projects together).

## License
The project is available under the **[EPL 2.0 License]**


[EPL 2.0 License]: https://www.eclipse.org/legal/epl-2.0/
