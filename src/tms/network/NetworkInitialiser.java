package tms.network;

import tms.sensors.*;
import tms.util.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class NetworkInitialiser {

    /** String delimiter represents the colon */
    public static final String LINE_INFO_SEPARATOR = ":";
    /** String delimiter represents the comma */
    public static final String LINE_LIST_SEPARATOR = ",";
    /** String id represents the intersection which the route is from */
    private static String routeFromID = null;
    /** String id represents the intersection which the route is to */
    private static String routeToID = null;
    /** The number of sensor which the route has */
    private static int numSensor = 0;

    /**
     * Creates a new network initialiser
     */
    public NetworkInitialiser() {}

    /**
     * Loads a saved Network from the file with the given filename.
     *
     * @param filename name of the file from which to load a network
     * @return the Network loaded from file
     * @throws IOException any IOExceptions encountered when reading the file
     * are bubbled up
     * @throws InvalidNetworkException if the file format of the given file is
     * invalid
     * @ass2
     */
    public static Network loadNetwork(String filename)
            throws IOException,
                   InvalidNetworkException
    {
        Network network = new Network();
        int numIntersectionsSpecified;
        int numRoutesSpecified;
        int numIntersections = 0;
        int numRoutes = 0;
        int yellowTime;

        File demoFile = new File(filename);
        Scanner myScanner1 = new Scanner(demoFile);
        Scanner myScanner2 = new Scanner(demoFile);
        String line = null;

        try {
            while (myScanner1.hasNextLine()) {
                line = myScanner1.nextLine();
                if (!line.startsWith(";")) {
                    break;
                }
            }

            assert line != null;
            numIntersectionsSpecified = Integer.parseInt(line);
            numRoutesSpecified = Integer.parseInt(myScanner1.nextLine());
            yellowTime = Integer.parseInt(myScanner1.nextLine());
            network.setYellowTime(yellowTime);

            /* Adds intersections, route, sensors. */
            while (myScanner1.hasNextLine()) {
                line = myScanner1.nextLine();

                if (line.endsWith(":")) {
                    throw new Exception("The colon-delimited format is " +
                            "violated");
                }

                if (line.strip().equals("")) {
                    // Empty line
                    if (myScanner1.hasNextLine()) {
                        if (!myScanner1.nextLine().equals("")) {
                            // Empty line occurs unexpectedly
                            throw new Exception("Invalid Empty line");
                        }
                        else {
                            // There can only be one empty line at the end.
                            throw new Exception("More than two (2) newline " +
                                    "characters at the end of the file");
                        }
                    }
                }
                else if (line.split(LINE_INFO_SEPARATOR).length < 4) {
                    // Intersection line
                    numIntersections ++ ;
                    network.createIntersection(line
                            .split(LINE_INFO_SEPARATOR)[0]);
                }
                else if (line.split(LINE_INFO_SEPARATOR).length > 5 ||
                        line.split(LINE_INFO_SEPARATOR).length == 2) {
                    throw new InvalidNetworkException("The colon-delimited " +
                            "format is violated");
                }
                else {
                    // Route line
                    numRoutes ++ ;
                    addRoute(line, network);
                    for (int i = 0; i < numSensor; i++) {
                        // Sensor line
                        line = myScanner1.nextLine();
                        addSensor(line, network);
                    }
                }
            }

            /* Adds traffic lights to intersection. */
            while (myScanner2.hasNextLine()) {
                line = myScanner2.nextLine();
                if (line.startsWith(";")) {
                    // Comment line
                    continue;
                }
                else if (line.split(LINE_INFO_SEPARATOR).length == 3) {
                    if (line.split(LINE_INFO_SEPARATOR)[2].trim().equals("")) {
                        throw new Exception("Intersection order is empty");
                    }
                    addLights(line, network);
                }
                else if (line.split(LINE_INFO_SEPARATOR).length == 4) {
                    break;
                }
            }
        } catch (Exception e) {
            throw new InvalidNetworkException(e.getClass() + ": "
                    + e.getMessage());
        }

        if (numIntersectionsSpecified != numIntersections) {
            throw new InvalidNetworkException("The number of intersections " +
                    "specified is not equal to the number of intersections" +
                    " read from the file");
        }
        else if (numRoutesSpecified != numRoutes) {
            throw new InvalidNetworkException("The number of routes " +
                    "specified does not match the number read from the file");
        }
        return network;
    }

    /**
     * Adds a route to the given network using the given string as
     * "from:to:defaultSpeed:numSensors:speedSignSpeed"
     *
     * @param routeLine the string representation of the route
     * @param network the network which the route should be added into
     * @throws IntersectionNotFoundException if no intersection exists with an
     * ID of 'from' or 'to'
     * @throws RouteNotFoundException if no route exists between the two given
     * intersections
     * @ass2
     */
    private static void addRoute(String routeLine,
                                 Network network)
            throws IntersectionNotFoundException,
                   RouteNotFoundException {
        String[] routeInfo = routeLine.split(LINE_INFO_SEPARATOR);
        routeFromID = routeInfo[0];
        routeToID = routeInfo[1];
        int defaultSpeed = Integer.parseInt(routeInfo[2]);
        numSensor = Integer.parseInt(routeInfo[3]);
        network.connectIntersections(routeFromID, routeToID,
                defaultSpeed);
        if (routeInfo.length > 4) {
            network.addSpeedSign(routeFromID, routeToID,
                    Integer.parseInt(routeInfo[4]));
        }
    }

    /**
     * Adds a sensor to the given network using the given string as
     * "type:threshold:list,of,data,value"
     *
     * @param sensorLine the string representation of the sensor
     * @param network the network which the sensor should be added into
     * @throws IntersectionNotFoundException if no intersection exists with an
     * ID of 'from' or 'to'
     * @throws RouteNotFoundException if no route exists between the two given
     * intersections
     * @throws DuplicateSensorException if a sensor already exists on the route
     * with the same type
     * @ass2
     */
    private static void addSensor(String sensorLine,
                                  Network network)
            throws IntersectionNotFoundException,
                   DuplicateSensorException,
                   RouteNotFoundException
    {
        String[] sensorInfo = sensorLine.split(LINE_INFO_SEPARATOR);
        String sensorType = sensorInfo[0];
        int threshold = Integer.parseInt(sensorInfo[1]);
        if (threshold <= 0) {
            throw new IllegalArgumentException("Sensor " +
                    "threshold is less than or equal to 0");
        }
        String[] dataInfo = sensorInfo[2]
                .split(LINE_LIST_SEPARATOR);
        int[] data = new int[dataInfo.length];
        for (int i = 0; i < dataInfo.length ; i++) {
            int intData = Integer.parseInt(dataInfo[i]);
            if (intData < 0) {
                throw new IllegalArgumentException("Sensor data " +
                        "value is less than 0");
            }
            data[i] = intData;
        }
        Sensor sensor;
        switch (sensorType) {
            case "PP":
                sensor = new DemoPressurePad(data, threshold);
                break;
            case "VC":
                sensor = new DemoVehicleCount(data, threshold);
                break;
            case "SC":
                sensor = new DemoSpeedCamera(data, threshold);
                break;
            default:
                throw new IllegalStateException("The sensor " +
                        "type cannot be resolved: " + sensorType);
        }
        network.addSensor(routeFromID,routeToID,sensor);
    }

    /**
     * Adds a set of lights to the given network using the given string as
     * "id:duration:list,of,intersection,order"
     * @param intersectionLine the string representation of the intersection
     *                         with traffic lights
     * @param network the network which the traffic lights should be added into
     * @throws IntersectionNotFoundException  if no intersection with the given
     * ID exists
     * @throws InvalidOrderException if the order specified is not a
     * permutation of the intersection's incoming routes; or if order is empty
     * @ass2
     */
    private static void addLights(String intersectionLine,
                                  Network network)
            throws IntersectionNotFoundException,
            InvalidOrderException {
        String[] intersectionInfo = intersectionLine.
                split(LINE_INFO_SEPARATOR);
        String intersectionID = intersectionInfo[0];
        int lightsDuration = Integer.parseInt(intersectionInfo[1]);
        List<String> intersectionOrder =  Arrays.asList(
                intersectionInfo[2].split(LINE_LIST_SEPARATOR));
        network.addLights(intersectionID, lightsDuration,
                intersectionOrder);
    }
}