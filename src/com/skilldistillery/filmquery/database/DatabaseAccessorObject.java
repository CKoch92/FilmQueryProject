package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";
	private String user = "student";
	private String pass = "student";

	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
/////////////////Method #1: Finds Film by fixed ID
	@Override
	public Film findFilmById(int filmId) throws SQLException {
		Film film = null;
		Connection conn = DriverManager.getConnection(URL, user, pass);

		String sql = "SELECT * FROM film WHERE film.id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, filmId);
		ResultSet filmResult = stmt.executeQuery();
		if (filmResult.next()) {
			film = new Film(filmResult.getInt("id"), filmResult.getString("title"), filmResult.getString("description"),
					filmResult.getInt("release_year"), filmResult.getInt("language_id"),
					filmResult.getInt("rental_duration"), filmResult.getDouble("rental_rate"),
					filmResult.getInt("length"), filmResult.getDouble("rental_rate"), filmResult.getString("rating"),
					filmResult.getString("special_features"), findActorsByFilmId(filmId));
		}
		return film;

	}

/////////////////Method #2: Finds Actor by fixed ID
	@Override
	public Actor findActorById(int actorId) throws SQLException {
		Actor actor = null;
		Connection conn = DriverManager.getConnection(URL, user, pass);

		String sql = "SELECT * FROM actor WHERE actor.id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, actorId);
		ResultSet actorResult = stmt.executeQuery();
		if (actorResult.next()) {
			actor = new Actor(actorResult.getInt("id"), actorResult.getString("first_name"),
					actorResult.getString("last_name"));
		}
		return actor;
	}

	/////////////////Method #3: Finds Actor by fixed film ID
	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		List<Actor> actors = new ArrayList<Actor>();
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT actor.* FROM film JOIN film_actor ON film.id = film_actor.film_id JOIN actor ON film_actor.actor_id =actor.id WHERE film.id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int actorId = rs.getInt("id");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				Actor actor = new Actor(actorId, firstName, lastName);
				actors.add(actor);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actors;
	}

/////////////////Method #4: Finds film by user input film ID	
	@Override
	public void userFilmByID(int filmId) {
		if (filmId < 1 || filmId > 1000) {
			System.out.println("Film not found.");
		} else {
			try {
				Connection conn = DriverManager.getConnection(URL, user, pass);
				String sql = "SELECT film.*, language.* FROM film JOIN language ON film.language_id = language.id WHERE film.id = ?";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setInt(1, filmId);
				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					int id =rs.getInt("id");
					String title = rs.getString("title");
					String year = rs.getString("release_year");
					String rating = rs.getString("rating");
					String description = rs.getString("description");
					String language = rs.getString("name");
					System.out.println();
					System.out.println("Film ID: " + filmId);
					System.out.println("Title: " + title);
					System.out.println("Date: " + year);
					System.out.println("Rating: " + rating);
					System.out.println("Language: " + language);
					System.out.println("Description: " + description);
					System.out.println();
					System.out.println("Actors listed below: ");
					System.out.println();
					System.out.println(findActorsByFilmId(id));
				}
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}
	
/////////////////Method #5: Finds film by user input keyword
	@Override
	public void userKeywordSearch(String keyword) {

		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT language.*, film.* FROM film JOIN language ON film.language_id = language.id WHERE film.title LIKE ? OR film.description LIKE ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%" + keyword + "%");
			stmt.setString(2, "%" + keyword + "%");
			
			System.out.println(stmt);
			
			ResultSet rs = stmt.executeQuery();
			if (!rs.next()) {
				System.out.println("No films found with the keyword: " + keyword);
			}
			while (rs.next()) {
				int id =rs.getInt("film.id");
				String title = rs.getString("title");
				String year = rs.getString("release_year");
				String rating = rs.getString("rating");
				String description = rs.getString("description");
				String language = rs.getString("name");
				System.out.println();
				System.out.println("Keyword: " + keyword);
				System.out.println("Title: " + title);
				System.out.println("Date: " + year);
				System.out.println("Rating: " + rating);
				System.out.println("Language: " + language);
				System.out.println("Description: " + description);
				System.out.println();
				System.out.println("Actors: " + findActorsByFilmId(id));
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
