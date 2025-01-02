package myInterviews.Microsoft.Media;

import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * Design and implement a library that manages a collection if media items for the user. The library should support CURD (Create, Read, Update and Delete) operations for the different types of media including videos, books, songs and games. Each media type has specific attributes as descriped below. The Library should also support priority functionality and provide methods for querying media collection by priority.
 *
 * Media Types and Attributes:
 * Video
 * {
 * 	id,
 * 	title,
 * 	director,
 * 	releaseDate
 * }
 * Book
 * {
 * 	id,
 * 	title,
 * 	author
 * }
 * Song
 * {
 * 	id,
 * 	title,
 * 	Singer,
 * 	Composor,
 * 	releaseDate
 * }
 *
 * Task1: Implement CURD operations for each media type
 * - Create a new media item with the given attributes
 * - Read an existing media item by its Id
 * - Update an existing media item's attributes
 * - Delegte an existing media item by its id
 * Task2: Keep track of recently viewed items by weighting them higher in priority
 * Task 3 : Implement a method to retrive the top-priority videos by year
 *  -The method should accept a year as input and return a list if videos released in that year, sorted by their priority in decending order
 * Task4: Implemant a method to search media by title. The method should accept a string as input and retunr a list of all media items that contain the given string in their title. "star" should return "Star wars" and "Night star in the sky"
 */

public class MediaTest {
    @Test
    public void testMedia() {
        MediaLibrary library = new MediaLibrary();

        // Adding media items
        library.createMedia(new Video(1, "Star Wars", "George Lucas", new Date(77, Calendar.MAY, 25)));
        library.createMedia(new Book(2, "The Hobbit", "J.R.R. Tolkien"));
        library.createMedia(new Song(3, "Bohemian Rhapsody", "Freddie Mercury", "Queen", new Date(75, Calendar.OCTOBER, 31)));

        // Marking items as viewed
        library.markAsViewed(1);
        library.markAsViewed(1);
        library.markAsViewed(3);

        // Top-priority videos by year
        System.out.println("Top-priority videos for 1977:");
        System.out.println(library.getTopPriorityVideosByYear(1977));

        // Searching media by title
        System.out.println("Search results for 'star':");
        System.out.println(library.searchMediaByTitle("star"));
    }
}
