package de.buw.se;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class DataStoreSql {
  private static final String FILE_NAME = "src/main/resources/books";

  private static Statement getSqlStatement() {
    try {
      
      Class.forName("org.h2.Driver");
      Connection conn = DriverManager.getConnection("jdbc:h2:./" + FILE_NAME, "", "");
  
      Statement stmt = conn.createStatement();
  
      String createQ = "CREATE TABLE IF NOT EXISTS Books"
          + "(ID INT PRIMARY KEY AUTO_INCREMENT(1,1) NOT NULL, AUTHOR VARCHAR(255), TITLE VARCHAR(255))";
      stmt.executeUpdate(createQ);
      return stmt;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Read authors from the DB
   * 
   * @return list of authors
   */
  public static List<String> readAuthors() {
    List<String> authors = new ArrayList<>();
    try {
      Statement stmt = getSqlStatement();
      ResultSet selectRS = stmt.executeQuery("SELECT * FROM Books");
      while (selectRS.next()) {
        authors.add(selectRS.getString("AUTHOR"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return authors;
  }

  /**
   * Add a new book to the DB
   * 
   * @param author author of the book
   * @param title title of the book to add
   */
  public static void addBook(String author, String title) {
    try {
      Statement stmt = getSqlStatement();
      stmt.executeUpdate("INSERT INTO Books (AUTHOR, TITLE) VALUES('" + author + "', '" + title + "')");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  
}  
