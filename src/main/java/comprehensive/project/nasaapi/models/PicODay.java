package comprehensive.project.nasaapi.models;

public class PicODay
{
    private String day;
    private String title;
    private String url;
    private String credits;

    public PicODay(String day, String title, String url, String credits) {
        this.day = day;
        this.title = title;
        this.url = url;
        this.credits = credits;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }
}

