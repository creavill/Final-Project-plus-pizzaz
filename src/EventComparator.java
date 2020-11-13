import java.util.Comparator;

final class EventComparator implements Comparator<Event> {
    EventComparator() {
    }

    public int compare(Event lft, Event rht) {
        return (int)(lft.time - rht.time);
    }
}