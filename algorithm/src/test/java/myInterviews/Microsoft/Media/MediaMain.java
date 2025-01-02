package myInterviews.Microsoft.Media;

import java.util.*;
import java.util.stream.Collectors;

// Video Class
class Video extends Media {
    private String director;
    private Date releaseDate;

    public Video(int id, String title, String director, Date releaseDate) {
        super(id, title);
        this.director = director;
        this.releaseDate = releaseDate;
    }

    public String getDirector() {
        return director;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "Video [id=" + id + ", title=" + title + ", director=" + director + ", releaseDate=" + releaseDate + "]";
    }
}

// Book Class
class Book extends Media {
    private String author;

    public Book(int id, String title, String author) {
        super(id, title);
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book [id=" + id + ", title=" + title + ", author=" + author + "]";
    }
}

// Song Class
class Song extends Media {
    private String singer;
    private String composer;
    private Date releaseDate;

    public Song(int id, String title, String singer, String composer, Date releaseDate) {
        super(id, title);
        this.singer = singer;
        this.composer = composer;
        this.releaseDate = releaseDate;
    }

    public String getSinger() {
        return singer;
    }

    public String getComposer() {
        return composer;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "Song [id=" + id + ", title=" + title + ", singer=" + singer + ", composer=" + composer + ", releaseDate="
                + releaseDate + "]";
    }
}

// MediaLibrary Class
class MediaLibrary {
    private Map<Integer, Media> mediaCollection = new HashMap<>();

    // Task 1: CRUD Operations
    public void createMedia(Media media) {
        mediaCollection.put(media.getId(), media);
    }

    public Media readMedia(int id) {
        return mediaCollection.get(id);
    }

    public void updateMedia(Media media) {
        mediaCollection.put(media.getId(), media);
    }

    public void deleteMedia(int id) {
        mediaCollection.remove(id);
    }

    // Task 2: Keep track of recently viewed items
    public void markAsViewed(int id) {
        Media media = mediaCollection.get(id);
        if (media != null) {
            media.increasePriority();
        }
    }

    // Task 3: Retrieve top-priority videos by year
    public List<Video> getTopPriorityVideosByYear(int year) {
        return mediaCollection.values().stream()
                .filter(media -> media instanceof Video)
                .map(media -> (Video) media)
                .filter(video -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(video.getReleaseDate());
                    return calendar.get(Calendar.YEAR) == year;
                })
                .sorted(Comparator.comparingInt(Video::getPriority).reversed())
                .collect(Collectors.toList());
    }

    // Task 4: Search media by title
    public List<Media> searchMediaByTitle(String query) {
        return mediaCollection.values().stream()
                .filter(media -> media.getTitle().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }
}

// Main Class for Testing
public class MediaMain {
    public static void main(String[] args) {
        MediaLibrary library = new MediaLibrary();

        //Adding media items
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





