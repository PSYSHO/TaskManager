package AlertClass;
import java.util.Date;

public class Task {
    public String title;
    public String description;
    public Date date;
    public String contacts;
    public Task(String title, String description, Date date, String contacts){
        this.title = title;
        this.description = description;
        this.date= date;
        this.contacts = contacts;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
