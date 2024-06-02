package de.buw.se;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

public class DataStoreCsv {
  private static final String FILE_NAME = "src/main/resources/book.csv";

  /**
   * Read authors from the CSV file
   * 
   * @return list of authors
   */
  public static List<String> readAuthors() {
    List<String> authors = new ArrayList<>();
    // open CSV file and read authors of each entry
		try (Reader reader = Files.newBufferedReader(Paths.get(FILE_NAME));
				@SuppressWarnings("deprecation")
				CSVParser csvParser = new CSVParser(reader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
			for (CSVRecord csvRecord : csvParser) {
				String name = csvRecord.get("author");
				authors.add(name);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    return authors;
  }

  /**
   * Add a new book to the CSV file
   * 
   * @param author author of the book
   * @param title title of the book to add
   */
  public static void addBook(String author, String title) {
    // add a new book to the CSV file
    try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(FILE_NAME), 
        StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);) {
      csvPrinter.printRecord(author, title);
      csvPrinter.flush();
    } catch (IOException e) {
      e.printStackTrace();      
    }
  }
  
}  
