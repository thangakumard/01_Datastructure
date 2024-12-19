package myInterviews;

import org.testng.annotations.Test;

import java.util.*;
import java.util.stream.Collectors;

public class Microsoft04_MediaCollection {

    @Test
    public  void MedicaCollectionTest() {
        Library library = new Library();

        // Example dates
        Calendar cal = Calendar.getInstance();
        cal.set(1977, Calendar.MAY, 25);
        Date date1 = cal.getTime();
        cal.set(2010, Calendar.JULY, 16);
        Date date2 = cal.getTime();
        cal.set(1975, Calendar.OCTOBER, 31);
        Date date3 = cal.getTime();

        // Add Media Items
        library.addMedia(new Video(1, "Star Wars", "George Lucas", date1));
        library.addMedia(new Video(2, "Inception", "Christopher Nolan", date2));
        library.addMedia(new Book(3, "The Great Gatsby", "F. Scott Fitzgerald"));
        library.addMedia(new Song(4, "Bohemian Rhapsody", "Freddie Mercury", "Freddie Mercury", date3));

        // Test Get by ID and Priority Increase
        Media video = library.getMediaById(1);
        System.out.println("Retrieved: " + video.getTitle() + ", Priority: " + video.getPriority());

        // Test Update
        library.updateMedia(new Video(1, "Star Wars - Updated", "George Lucas", date1));
        System.out.println("Updated Title: " + library.getMediaById(1).getTitle());

        // Test Retrieve Top Priority Videos by Year
        List<Video> topVideos = library.getTopPriorityVideosByYear(1977);
        System.out.println("Top Priority Videos Released in 1977:");
        for (Video v : topVideos) {
            System.out.println(v.getTitle() + " - Priority: " + v.getPriority());
        }

        // Test Search by Title
        List<Media> searchResults = library.searchByTitle("Star");
        System.out.println("Search Results for 'Star':");
        for (Media m : searchResults) {
            System.out.println(m.getTitle() + " - Priority: " + m.getPriority());
        }
    }

    // Define Base Media Class
    abstract class Media {
        protected int id;
        protected String title;
        protected int priority = 0;  // Priority for recently viewed items

        public Media(int id, String title) {
            this.id = id;
            this.title = title;
        }

        public int getId() { return id; }
        public String getTitle() { return title; }
        public int getPriority() { return priority; }
        public void increasePriority() { this.priority++; }
    }

    // Video Class
    class Video extends Media {
        private String director;
        private Date releaseDate;

        public Video(int id, String title, String director, Date releaseDate) {
            super(id, title);
            this.director = director;
            this.releaseDate = releaseDate;
        }

        public String getDirector() { return director; }
        public Date getReleaseDate() { return releaseDate; }
    }

    // Book Class
    class Book extends Media {
        private String author;

        public Book(int id, String title, String author) {
            super(id, title);
            this.author = author;
        }

        public String getAuthor() { return author; }
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

        public String getSinger() { return singer; }
        public String getComposer() { return composer; }
        public Date getReleaseDate() { return releaseDate; }
    }

    // Library Class
    class Library {
        private List<Media> mediaCollection = new ArrayList<>();

        // CRUD Operations

        // Create
        public void addMedia(Media media) {
            mediaCollection.add(media);
        }

        // Read
        public Media getMediaById(int id) {
            for (Media media : mediaCollection) {
                if (media.getId() == id) {
                    media.increasePriority();  // Increase priority when accessed
                    return media;
                }
            }
            return null;
        }

        // Update
        public boolean updateMedia(Media updatedMedia) {
            for (int i = 0; i < mediaCollection.size(); i++) {
                Media media = mediaCollection.get(i);
                if (media.getId() == updatedMedia.getId()) {
                    mediaCollection.set(i, updatedMedia);
                    updatedMedia.increasePriority();
                    return true;
                }
            }
            return false;
        }

        // Delete
        public boolean deleteMediaById(int id) {
            return mediaCollection.removeIf(media -> media.getId() == id);
        }

        // Retrieve top-priority videos by year
        public List<Video> getTopPriorityVideosByYear(int year) {
            return mediaCollection.stream()
                    .filter(media -> media instanceof Video)
                    .map(media -> (Video) media)
                    .filter(video -> {
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(video.getReleaseDate());
                        return cal.get(Calendar.YEAR) == year;
                    })
                    .sorted(Comparator.comparingInt(Video::getPriority).reversed())
                    .collect(Collectors.toList());
        }

        // Search by title (case-insensitive)
        public List<Media> searchByTitle(String titleFragment) {
            return mediaCollection.stream()
                    .filter(media -> media.getTitle() != null && media.getTitle().toLowerCase().contains(titleFragment.toLowerCase()))
                    .sorted(Comparator.comparingInt(Media::getPriority).reversed())
                    .collect(Collectors.toList());
        }
    }
}
