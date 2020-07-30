package tms.intersection;

import tms.route.Route;
import tms.route.TrafficSignal;
import tms.util.TimedItem;
import tms.util.TimedItemManager;

import java.util.List;

public class IntersectionLights implements TimedItem {

    /** A list of routes, null of none exists. */
    private List<Route> connections;
    /** Yellow time of the traffic lights */
    private int yellowTime;
    /** Green-yellow cycle duration time in this set of intersection lights */
    private int duration;
    /** Time passed by */
    private int time = 0;
    /** Index of the current route in the list of routes */
    private int index = 0;

    /**
     * Creates a new set of traffic lights at an intersection.
     * <p>
     * The first route in the given list of incoming routes should have its
     * TrafficLight signal set to {@code TrafficSignal.GREEN}.
     *
     * @param connections a list of incoming routes, the list cannot be empty
     * @param yellowTime time in seconds for which lights will appear yellow
     * @param duration time in seconds for which lights will appear yellow
     *                 and green
     * @ass2
     */
    public IntersectionLights(List<Route> connections,
                              int yellowTime,
                              int duration)
    {
        this.connections = connections;
        this.yellowTime = yellowTime;
        this.duration = duration;
        this.connections.get(0).setSignal(TrafficSignal.GREEN);
        TimedItemManager.getTimedItemManager().registerTimedItem(this);
    }

    /**
     * Returns the time in seconds for which a traffic light will appear
     * yellow when transitioning from green to red.
     *
     * @return yellow time in seconds for this set of traffic lights
     * @ass2
     */
    public int getYellowTime() {
        return yellowTime;
    }

    /**
     * Sets a new duration of each green-yellow cycle.
     * <p>
     * The current progress of the lights cycle should be reset, such that on
     * the next call to {@code oneSecond()}, only one second of the new
     * duration has been elapsed for the incoming route that currently has a
     * green light.
     *
     * @param duration the new light signal duration
     * @ass2
     */
    public void setDuration(int duration) {
        this.connections.get(index).setSignal(TrafficSignal.GREEN);
        this.duration = duration;
        this.time = 0;
    }

    /**
     * Simulates one second passing and updates the state of this set of
     * traffic lights.
     * <p>
     * If enough time has passed such that a full green-yellow duration has
     * elapsed, or such that the current green light should now be yellow,
     * the appropriate light signals should be changed:
     * <ul>
     * <li>When a traffic light signal has been green for 'duration - yellowTime'
     * seconds, it should be changed from green to yellow.</li>
     * <li>When a traffic light signal has been yellow for 'yellowTime' seconds,
     * it should be changed from yellow to red, and the next incoming route in
     * the order passed to IntersectionLights(List, int, int) should be given
     * a green light. If the end of the list of routes has been reached, simply
     * wrap around to the start of the list and repeat.</li>
     * </ul>
     * <p>
     * If no routes are connected to the intersection, the duration shall not
     * elapse and the call should simply return without changing anything.
     *
     * @ass2
     */
    public void oneSecond() {
        if(connections.isEmpty()) {
            return;
        }

        time ++ ;
        if (time == duration - yellowTime) {
            connections.get(index).setSignal(TrafficSignal.YELLOW);
        }
        else if (time == duration) {
            connections.get(index).setSignal(TrafficSignal.RED);
            index ++ ;
            index = index % connections.size();
            time = 0;
            connections.get(index).setSignal(TrafficSignal.GREEN);
        }
    }

    /**
     * Returns the string representation of this set of IntersectionLights.
     * <p>
     * The format to return is "duration:list,of,intersection,ids" where
     * 'duration' is our current duration and 'list,of,intersection,ids' is a
     * comma-separated list of the IDs of all intersections that have an
     * incoming route to this set of traffic lights, in order given to
     * IntersectionLights' constructor.
     * <p>
     * For example, for a set of traffic lights with inbound routes from three
     * intersections - A, C and B - in that order, and a duration of 8 seconds,
     * return the string "8:A,C,B".
     *
     * @return formatted string representation
     * @ass2
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(duration).append(":");
        for (Route route : connections) {
            s.append(route.getFrom().getId()).append(",");
        }
        return s.substring(0, s.length()-1);
    }
}
