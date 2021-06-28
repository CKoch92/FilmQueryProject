# Film Query Project

## This project is designed to access to retrieve information from a database of 1000 films. Users are presented with a menu where they can 1) search for a movie by its id, 2) search for a movie by a keyword, or 3) quit the menu. For a movie search, the user inputs an id or keyword and a matching movie's id, title, release date, rating, language, description, and list of actors are printed, followed by the original menu.

##This project uses java, mysql, and maven technologies. Java is used for the application code. Mysql is used to store and run the database. Maven is used to allow the java project to access the database.

##The user menu is stored in the FilmQueryApp class. A class object is created and used to run a launch method that opens a scanner, implements the StartUserInterface method, and closes the scanner. The StartUserInterface method prints a menu and asks the user to select one of three options with inputs of 1, 2, or 3. A switch is used to run code dependent on the user's input. If "1" is input, the user is asked to input a film id, which is used in calling the DatabaseAccessorObject's userFilmByID method. If "2" is input, the user is asked to input a keyword that could be included in a film's title or description, which is used to call the DatabaseAccessorObject's userKeywordSearch method. If "3" selected, the menuLoop boolean is set to false and the user exits out of the menu's while loop.

##The java class "DatabaseAccessorObject" is where the code to retrieve database information is kept. To start, it provides the url, username, and password to access the sdvid database, where the film and actor tables are kept. It also includes methods that override its DatabaseAccessor interface.

##The method call for user selection 1, userFilmByID, takes the users requested film ID as an int. That int is then inserted into a Prepared Statement that will be used to request information from the sdvid mysql database. The statement (line 100) collects all film info and all language info from the film and language tables where the film id equals the id input by the user. Accessing the Actors and Film classes' get methods, the method prints the film's id, title, release date, rating, language, and description. In addition, an ArrayList of actors in the film are printed, by calling on the findActorsByFilmID method.

##The findActorsByFilmID method takes in a film id and puts it into a prepared statement that collects all actor information from a film. Since actor info is not all listed in the film table, the film table is joined by the actor table, via the film_actor table. From there an actor's info can be pulled for any film that where the film ID is equal to the film id provided by the user. From there, the method accesses the getter methods of the Actor class, uses them to construct a new actor object and adds them to an ArrayList of Actor objects, which is returned by the method, when called upon.

##The method call for user selection 2, userKeywordSearch, takes in the user's keyword as String. The methodology is very similar to userFilmByID. The main difference is in the phrasing of the prepared statement sent into mysql. The statement will use LIKE to see if the title or description contains the String that was sent in through the method. From there, all info is collected from matching films, which the method accesses via Film's getter methods and prints the information. Since the film ID can be found, it is used to call the findActorsByFilmID method and return an ArrayList of actors for each film.

##It is important to note that all of these methods access the database by 1) creating a connection object that takes in the url, username, and password, 2) creating a sql command statement with a variable ?, 3)generating a PreparedStatement using the connection object and sql String, 4) inserting the user's input into the "?" variables of the sql String, and 5)Generating a ResultSet with all information called from the PreparedStatement. These steps are all done in a try block that ends with closing the ResultSet, Connection, and PreparedStatement objects.
