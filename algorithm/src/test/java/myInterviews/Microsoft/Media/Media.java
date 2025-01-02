package myInterviews.Microsoft.Media;

public abstract class Media {
    protected int id;
    protected String title;
    private int priority; // To keep track of recently viewed items' priority

    public Media(int id, String title) {
        this.id = id;
        this.title = title;
        this.priority = 0;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getPriority() {
        return priority;
    }

    public void increasePriority() {
        this.priority++;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
