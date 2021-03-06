import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

final class EventScheduler {
    public static final int QUAKE_ANIMATION_REPEAT_COUNT = 10;
    public static final int ATLANTIS_ANIMATION_REPEAT_COUNT = 7;
    public PriorityQueue<Event> eventQueue = new PriorityQueue(new EventComparator());
    public Map<Entity, List<Event>> pendingEvents = new HashMap();
    public double timeScale;

    public EventScheduler(double timeScale) {
        this.timeScale = timeScale;
    }

    public void unscheduleAllEvents(Entity entity) {
        List<Event> pending = (List)this.pendingEvents.remove(entity);
        if (pending != null) {
            Iterator var3 = pending.iterator();

            while(var3.hasNext()) {
                Event event = (Event)var3.next();
                this.eventQueue.remove(event);
            }
        }

    }

    public  void scheduleEvent(Entity entity, Action action, long afterPeriod) {
        long time = System.currentTimeMillis() + (long)((double)afterPeriod * this.timeScale);
        Event event = new Event(action, time, entity);
        this.eventQueue.add(event);
        List<Event> pending = (List)this.pendingEvents.getOrDefault(entity, new LinkedList());
        pending.add(event);
        this.pendingEvents.put(entity, pending);
    }

    public void removePendingEvent(Event event) {
        List<Event> pending = (List)this.pendingEvents.get(event.entity);
        if (pending != null) {
            pending.remove(event);
        }

    }

    public void updateOnTime(long time) {
        while(!this.eventQueue.isEmpty() && ((Event)this.eventQueue.peek()).time < time) {
            Event next = (Event)this.eventQueue.poll();
            this.removePendingEvent(next);
            next.action.executeAction(this);
        }

    }
}
