package tms.network;

import tms.intersection.Intersection;
import tms.route.Route;
import tms.sensors.Sensor;
import tms.util.DuplicateSensorException;
import tms.util.IntersectionNotFoundException;
import tms.util.InvalidOrderException;
import tms.util.RouteNotFoundException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Network {

    /** Yellow time of the network */
    private int yellowTime;
    /** A list of intersections, null if none exists */
    private List<Intersection> intersectionList;

    /**
     * Creates a new empty network with no intersections.
     *
     * @ass2
     */
    public Network() {
        this.yellowTime = 0;
        this.intersectionList = new ArrayList<>();
    }

    /**
     * Returns the yellow time for all traffic lights in this network.
     *
     * @return traffic light yellow time (in seconds)
     * @ass2
     */
    public int getYellowTime() {
        return yellowTime;
    }

    /**
     * Sets the time that lights appear yellow between turning from green to
     * red (in seconds) for all new traffic lights added to this network.
     * <p>
     * Existing traffic lights should not have their yellow time changed after
     * this method is called.
     * <p>
     * The yellow time must be at least one (1) second. If the argument
     * provided is below 1, throw an exception and do not set the yellow time.
     * @param yellowTime new yellow time for all new traffic lights in network
     * @throws IllegalArgumentException  if yellowTime < 1
     * @ass2
     */
    public void setYellowTime(int yellowTime) throws IllegalArgumentException {
        if (yellowTime < 1) {
            throw new IllegalArgumentException("Yellow time is less than " +
                    "one (1) second");
        }
        this.yellowTime = yellowTime;
    }

    /**
     * Returns a new list containing all the intersections in this network.
     * <p>
     * Adding/removing intersections from this list should not affect the
     * network's internal list of intersections.
     *
     * @return list of all intersections in this network
     * @ass2
     */
    public List<Intersection> getIntersections() {
        return new ArrayList<>(this.intersectionList);
    }

    /**
     * Creates a new intersection with the given ID and adds it to this network.
     *
     * @param id identifier of the intersection to be created
     * @throws IllegalArgumentException  if an intersection already exists with
     * the given ID, or if the given ID contains the colon character (:), or if
     * the id contains only whitespace (space, newline, tab, etc.) characters
     * @ass2
     */
    public void createIntersection(String id)
            throws IllegalArgumentException
    {
        if (id.contains(":")) {
            throw new IllegalArgumentException("Intersection id cannot " +
                    "contain \":\"");
        }
        else if (id.strip().equals("")) {
            throw new IllegalArgumentException("Intersection id cannot " +
                    "contain only whitespace characters");
        }
        else {
            for (Intersection intersection : intersectionList) {
                if (intersection.getId().equals(id)) {
                    throw new IllegalArgumentException("Intersection already" +
                            " exists with the given ID: \"" + id + "\"");
                }
            }
            Intersection inter = new Intersection(id);
            intersectionList.add(inter);
        }
    }

    /**
     * Creates a connecting route between the two intersections with the given
     * IDs.
     * <p>
     * The new route should start at 'from' and end at 'to', and have a default
     * speed of 'defaultSpeed'.
     *
     * @param from ID of origin intersection
     * @param to ID of destination intersection
     * @param defaultSpeed speed limit of the route to create
     * @throws IntersectionNotFoundException if no intersection exists with an
     * ID of 'from' or 'to'
     * @throws IllegalStateException if a route already exists between the
     * given two intersections
     * @throws IllegalArgumentException if defaultSpeed is negative
     * @ass2
     */
    public void connectIntersections(String from,
                                     String to,
                                     int defaultSpeed)
            throws IntersectionNotFoundException,
                   IllegalStateException,
                   IllegalArgumentException
    {
        boolean fromExists = false;
        boolean toExists = false;
        Intersection routeFrom = null;
        Intersection routeTo = null;

        for (Intersection intersection : intersectionList) {
            if (intersection.getId().equals(from)) {
                fromExists = true;
                routeFrom = intersection;
            }
            else if (intersection.getId().equals(to)) {
                toExists = true;
                routeTo = intersection;
            }
        }

        if (!fromExists) {
            throw new IntersectionNotFoundException("No such intersection: " +
                    "\"" + from + "\"");
        }
        else if (!toExists) {
            throw new IntersectionNotFoundException("No such intersection: " +
                    "\"" + to + "\"");
        }
        else {
            try {
                routeTo.addConnection(routeFrom, defaultSpeed);
            } catch (IllegalStateException e) {
                throw new IllegalStateException(e.getMessage() + " to \""
                        + to + "\"");
            }

        }
    }

    /**
     * Adds traffic lights to the intersection with the given ID.
     * <p>
     * The traffic lights will change every duration seconds and will cycle in
     * the order given by intersectionOrder, whereby each element in the list
     * represents the intersection from which each incoming route originates.
     * The yellow time will be the network's yellow time value.
     * <p>
     * If the intersection already has traffic lights, the existing lights
     * should be completely overwritten and reset, and the new duration and
     * order should be set.
     *
     * @param intersectionId ID of intersection to add traffic lights to
     * @param duration number of seconds between traffic light cycles
     * @param intersectionOrder list of origin intersection IDs, traffic lights
     *                         will go green in this order
     * @throws IntersectionNotFoundException if no intersection with the given
     * ID exists
     * @throws InvalidOrderException if the order specified is not a
     * permutation of the intersection's incoming routes; or if order is empty
     * @throws IllegalArgumentException if the given duration is less than the
     * network's yellow time plus one
     * @see Intersection#addTrafficLights(List, int, int)
     * @ass2
     */
    public void addLights(String intersectionId,
                          int duration,
                          List<String> intersectionOrder)
            throws IntersectionNotFoundException,
                   InvalidOrderException,
                   IllegalArgumentException
    {
        Intersection targetIntersection = findIntersection(intersectionId);
        List<Route> order = new ArrayList<>();
        for (String id : intersectionOrder) {
            for (Route route : targetIntersection.getConnections()) {
                if (route.getFrom().getId().equals(id)) {
                    order.add(route);
                    break;
                }
            }
        }
        targetIntersection.addTrafficLights(order, yellowTime, duration);
    }

    /**
     * Adds an electronic speed sign on the route between the two given
     * intersections.
     * <p>
     * The new speed sign should have an initial displayed speed of
     * 'initialSpeed'.
     *
     * @param from ID of origin intersection
     * @param to ID of destination intersection
     * @param initialSpeed initial speed to be displayed on speed sign
     * @throws IntersectionNotFoundException if no intersection exists with an
     * ID given by 'from' or 'to'
     * @throws RouteNotFoundException if no route exists between the two given
     * intersections
     * @ass2
     */
    public void addSpeedSign(String from,
                             String to,
                             int initialSpeed)
            throws IntersectionNotFoundException,
                   RouteNotFoundException
    {
        Route targetRoute = getConnection(from, to);
        targetRoute.addSpeedSign(initialSpeed);
    }

    /**
     * Sets the speed limit on the route between the two given intersections.
     * <p>
     * Speed limits can only be changed on routes with an electronic speed sign.
     * Calling this method on a route without an electronic speed sign should
     * result in an exception.
     *
     * @param from ID of origin intersection
     * @param to ID of destination intersection
     * @param newLimit new speed limit
     * @throws IntersectionNotFoundException if no intersection exists with an
     * ID given by 'from' or 'to'
     * @throws RouteNotFoundException if no route exists between the two given
     * intersections
     * @throws IllegalStateException if the specified route does not have an
     * electronic speed sign
     * @throws IllegalArgumentException if the given speed limit is negative
     * @ass2
     */
    public void setSpeedLimit(String from,
                              String to,
                              int newLimit)
            throws IntersectionNotFoundException,
                   RouteNotFoundException
    {
        Route targetRoute = getConnection(from, to);
        targetRoute.setSpeedLimit(newLimit);
    }

    /**
     * Sets the duration of each green-yellow cycle for the given
     * intersection's traffic lights.
     *
     * @param intersectionId ID of target intersection
     * @param duration new duration of traffic lights
     * @throws IntersectionNotFoundException if no intersection exists with an
     * ID given by 'intersectionId'
     * @throws IllegalStateException if the given intersection has no traffic
     * lights
     * @throws IllegalArgumentException if the given duration is less than the
     * network's yellow time plus one
     * @see Intersection#setLightDuration(int)
     * @ass2
     */
    public void changeLightDuration(String intersectionId,
                                    int duration)
            throws IntersectionNotFoundException
    {
        Intersection targetIntersection = findIntersection(intersectionId);
        targetIntersection.setLightDuration(duration);
    }

    /**
     * Returns the route that connects the two given intersections.
     *
     * @param from origin intersection
     * @param to ID of destination intersection
     * @return Route that connects these intersections
     * @throws IntersectionNotFoundException if no intersection exists with an
     * ID given by 'to'
     * @throws RouteNotFoundException if no route exists between the two given
     * intersections
     * @ass2
     */
    public Route getConnection(String from,
                               String to)
            throws IntersectionNotFoundException,
                   RouteNotFoundException
    {
        Intersection routeFrom = null;
        Intersection routeTo = null;

        for (Intersection intersection : intersectionList) {
            if (intersection.getId().equals(from)) {
                routeFrom = intersection;
            }
            else if (intersection.getId().equals(to)) {
                routeTo = intersection;
            }
        }

        if (routeFrom == null) {
            throw new IntersectionNotFoundException("No such intersection: " +
                    "\"" + from + "\"");
        }
        else if (routeTo == null) {
            throw new IntersectionNotFoundException("No such intersection: " +
                    "\"" + to + "\"");
        }
        else {
            return routeTo.getConnection(routeFrom);
        }
    }

    /**
     * Attempts to find an Intersection instance in this network with the same
     * identifier as the given 'id' string.
     *
     * @param id intersection identifier to search for
     * @return the intersection that was found (if one was found)
     * @throws IntersectionNotFoundException if no intersection could be found
     * with the given identifier
     * @ass2
     */
    public Intersection findIntersection(String id)
            throws IntersectionNotFoundException
    {
        Intersection targetIntersection = null;

        for (Intersection intersection : intersectionList) {
            if (intersection.getId().equals(id)) {
                targetIntersection = intersection;
                break;
            }
        }
        if (targetIntersection == null) {
            throw new IntersectionNotFoundException("No such intersection: " +
                    "\"" + id + "\"");
        }
        else {
            return targetIntersection;
        }
    }

    /**
     * Adds a sensor to the route between the two intersections with the given
     * IDs.
     *
     * @param from ID of intersection at which the route originates
     * @param to ID of intersection at which the route ends
     * @param sensor sensor instance to add to the route
     * @throws DuplicateSensorException if a sensor already exists on the route
     * with the same type
     * @throws IntersectionNotFoundException if no intersection exists with an
     * ID given by 'from' or 'to'
     * @throws RouteNotFoundException if no route exists between the given
     * to/from intersections
     * @ass2
     */
    public void addSensor(String from,
                          String to,
                          Sensor sensor)
            throws DuplicateSensorException,
                   IntersectionNotFoundException,
                   RouteNotFoundException
    {
        Route targetRoute = getConnection(from, to);
        targetRoute.addSensor(sensor);
    }

    /**
     * Returns the congestion level on the route between the two given
     * intersections.
     *
     * @param from ID of origin intersection
     * @param to ID of destination intersection
     * @return congestion level (integer between 0 and 100) of connecting route
     * @throws IntersectionNotFoundException if no intersection exists with an
     * ID given by 'from' or 'to'
     * @throws RouteNotFoundException if no connecting route exists between the
     * given two intersections
     * @see Route#getCongestion()
     * @ass2
     */
    public int getCongestion(String from,
                             String to)
            throws IntersectionNotFoundException,
                   RouteNotFoundException
    {
        Route targetRoute = getConnection(from, to);
        return targetRoute.getCongestion();
    }

    /**
     * Creates a new connecting route in the opposite direction to an existing
     * route.
     * <p>
     * The newly created route should start at the intersection with the ID
     * given by 'to' and end at the intersection with the ID given by 'from'.
     * It should have the same default speed limit as the current speed limit
     * of the existing route, as returned by {@link Route#getSpeed()}.
     * <p>
     * If the existing route has an electronic speed sign, then a new
     * electronic speed sign should be added to the new route with the same
     * displayed speed as the existing speed sign.
     *
     * @param from ID of intersection that the existing route starts at
     * @param to ID of intersection that the existing route ends at
     * @throws IntersectionNotFoundException if no intersection exists with the
     * ID given by 'from' or 'to'
     * @throws RouteNotFoundException if no route currently exists between
     * given two intersections
     * @throws IllegalStateException if a route already exists in the opposite
     * direction to the existing route
     * @ass2
     */
    public void makeTwoWay(String from,
                           String to)
            throws IntersectionNotFoundException,
                   RouteNotFoundException
    {
        Route targetRoute = getConnection(from, to);
        int defaultSpeed = targetRoute.getSpeed();
        connectIntersections(to, from, defaultSpeed);
        if (targetRoute.hasSpeedSign()) {
            getConnection(to, from).addSpeedSign(defaultSpeed);
        }
    }

    /**
     * Returns true if and only if this network is equal to the other given
     * network.
     * <p>
     * For two networks to be equal, they must have the same number of
     * intersections, and all intersections in the first network must be
     * contained in the second network, and vice versa.
     *
     * @param obj other object to compare equality
     * @return true if equal, false otherwise.
     * @ass2
     */
    @Override
    public boolean equals(Object obj) {
        if (obj.getClass().equals(this.getClass())) {
            return false;
        }
        else {
            Network network = (Network) obj;
            return intersectionList.equals(network.intersectionList);
        }
    }

    /**
     * Returns the hash code of this network.
     * <p>
     * Two networks that are equal must have the same hash code.
     *
     * @return hash code of the network
     * @ass2
     */
    @Override
    public int hashCode() {
        return intersectionList.hashCode();
    }

    /**
     * Returns the string representation of this network.
     * <p>
     * The format of the string to return is identical to that described in
     * {@link NetworkInitialiser#loadNetwork(String)}. All intersections in the
     * network, including all connecting routes with their respective sensors,
     * should be included in the returned string.
     * <p>
     * Intersections and routes should be listed in alphabetical order, similar
     * to the way in which sensors are sorted alphabetically in
     * {@link Route#toString()}.
     * <p>
     * Comments (lines beginning with a semicolon character ";") are not added
     * to the string representation of a network.
     *
     * @return string representation of this network
     * @see NetworkInitialiser#loadNetwork(String)
     * @ass2
     */
    @Override
    public String toString() {
        List<Route> routeList = new ArrayList<>();
        for (Intersection intersection : intersectionList) {
            routeList.addAll(intersection.getConnections());
        }
        routeList.sort(Comparator.comparing(Object::toString));

        StringBuilder str = new StringBuilder(String.format("%d%s%d%s%d",
                intersectionList.size(), System.lineSeparator(),
                routeList.size(), System.lineSeparator(),
                yellowTime));

        String[] intersectionLines = intersectionList.stream()
                .map(Object::toString).sorted().toArray(String[]::new);
        for (String intersectionLine : intersectionLines) {
            str.append(System.lineSeparator()).append(intersectionLine);
        }

        String[] routeLines = routeList.stream()
                .map(Object::toString).sorted().toArray(String[]::new);
        for (String routeLine : routeLines) {
            str.append(System.lineSeparator()).append(routeLine);
        }
        return str.toString();
    }
}
