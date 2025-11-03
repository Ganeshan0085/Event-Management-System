public class Event {
    private int id;
    private String name;
    private String date;
    private String venue;

    public Event(String name, String date, String venue) {
        this.name = name;
        this.date = date;
        this.venue = venue;
    }

    public String getName() { return name; }
    public String getDate() { return date; }
    public String getVenue() { return venue; }
}
