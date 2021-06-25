package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {
	  private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";
	  private String user = "student";
	  private String pass = "student";

	 
	  
//	  Static {
//		  Class.forName("com.mysql.jdbc.Driver");
//	  }
	  
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
	
		public DatabaseAccessorObject() throws ClassNotFoundException {
			  Class.forName("com.mysql.jdbc.Driver");
			}
	  @Override
  public Film findFilmById(int filmId) throws SQLException {
		  Film film =null;
		   Connection conn = DriverManager.getConnection(URL, user, pass);

		  String sql = "SELECT * FROM film WHERE film.id = ?";
		  PreparedStatement stmt = conn.prepareStatement(sql);
		  stmt.setInt(1,filmId);
		  ResultSet actorResult = stmt.executeQuery();
		  if (actorResult.next()) {
			  film = new Film();
		    film.setId(actorResult.getInt(1));
		    film.setTitle(actorResult.getString(2));
		    film.setDescription(actorResult.getString(3));
		    film.setReleaseYear(actorResult.getString(4));
		    film.setLanguageId(actorResult.getInt(5));
		    film.setRentalDuration(actorResult.getInt(6));
		    film.setRentalRate(actorResult.getDouble(7));
		    film.setLength(actorResult.getInt(8));
		    film.setReplacementCost(actorResult.getDouble(9));
		    film.setRating(actorResult.getString(10));
		    film.setSpecialFeatures(actorResult.getString(11));
		    
		 
		  }
		  //...
		  
		  System.out.println(film);
		  return film;
		  
		  
		  
  }



//
//@Override
//public Actor findActorById(int actorId) {
//	// TODO Auto-generated method stub
//	return null;
//}
//
//
//
//@Override
//public List<Actor> findActorsByFilmId(int filmId) {
//	// TODO Auto-generated method stub
//	return null;
//}

}
