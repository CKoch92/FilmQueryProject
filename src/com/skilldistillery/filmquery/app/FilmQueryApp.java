package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {
  
  DatabaseAccessor db = new DatabaseAccessorObject();

  public static void main(String[] args) throws Exception {
    FilmQueryApp app = new FilmQueryApp();
//    app.test();
    app.launch();
  }

  private void test() throws SQLException {

//	  db.userFilmByID(1);
//	  db.userKeywordSearch("dragonfly");
	  
	  //    Film film = db.findFilmById(1);
//    System.out.println(film);
//    
//    Actor actor = db.findActorById(1);
//    System.out.println(actor);
    
//    List<Actor> actors = db.findActorsByFilmId(1);
//    System.out.println(actors);
  }

  private void launch() {
    Scanner input = new Scanner(System.in);
    
    startUserInterface(input);
    
    input.close();
  }

  private void startUserInterface(Scanner input) {
	  boolean menuLoop = true;
		
		  while(menuLoop) {
	  	System.out.println("------------------------------------------------");
		System.out.println("************************************************");
		System.out.println("------------------------------------------------");
		System.out.println();
		System.out.println("  Please select an option from the menu below:  ");
		System.out.println();
		System.out.println();
		System.out.println("1. Look up a film by its id");
		System.out.println("2. Look up a film by a keyword search");
		System.out.println("3. Quit");
		System.out.println();
		System.out.println("------------------------------------------------");
		System.out.println("************************************************");    
		System.out.println("------------------------------------------------");
  
		String userSelect = input.nextLine();
  
		switch (userSelect) {
		case "1":
			System.out.println();
			System.out.println("Enter a Film ID between 1 and 1000");
			int userID = input.nextInt();
			db.userFilmByID(userID);
			menuLoop = true;
			break;
		case "2":
			System.out.println();
			System.out.println("Enter a keyword to search for a film");
			String userWord = input.next();
			db.userKeywordSearch(userWord);
			menuLoop = true;
			break;
		case "3":
			menuLoop = false;
			break;
		}
		
  }
  }
  


}
