package tms.intersection;

import org.junit.Before;
import org.junit.Test;
import tms.route.Route;
import tms.route.TrafficSignal;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class IntersectionLightsTest {

    /** Yellow time which is one second */
    final int YELLOWTIME = 1;
    /** Initialised duration time which is two seconds */
    final int DURATION = 2;
    /** An instance of IntersectionLights */
    private IntersectionLights it;
    /** A list of routes */
    private List<Route> routes;

    /**
     * Set up a intersection lights before running a test.
     * @ass2
     */
    @Before
    public void setUp() {
        Intersection i1 = new Intersection("X");
        Intersection i2 = new Intersection("Y");
        Intersection i3 = new Intersection("Z");
        i1.addConnection(i3,60);
        i1.addConnection(i2,80);
        routes = i1.getConnections();
        it = new IntersectionLights(routes, YELLOWTIME, DURATION);
    }

    /**
     * Checks getYellowTime method.
     * <p>
     * Test fails if the result is not 1.
     *
     * @ass2
     */
    @Test
    public void getYellowTimeTest() {
        assertEquals(1, it.getYellowTime());
    }

    /**
     * Checks setDuration method.
     * <p>
     * Test fails if:
     * <ul>
     *     <li>The current light is not reset to green</li>
     *     <li>The current light is not yellow after two seconds</li>
     * </ul>
     */
    @Test
    public void setDurationTest() {
        it.oneSecond();
        it.setDuration(3);
        assertEquals(TrafficSignal.GREEN, routes.get(0).getTrafficLight().getSignal());
        it.oneSecond();
        it.oneSecond();
        assertEquals(TrafficSignal.YELLOW, routes.get(0).getTrafficLight().getSignal());
    }

    /**
     * Checks oneSecond function
     * <p>
     * Test fails if the current light is not yellow after one second
     *
     * @ass2
     */
    @Test
    public void oneSecondSetYellowTest() {
        it.oneSecond();
        assertEquals(TrafficSignal.YELLOW, routes.get(0).getTrafficLight().getSignal());
    }

    /**
     * Checks oneSecond function turn to the next light
     * <p>
     * Test fails if:
     * <ul>
     *     <li>The current light is not green</li>
     *     <li>The previous light is not red</li>
     * </ul>
     *
     * @ass2
     */
    @Test
    public void oneSecondSetRedAndGreenTest() {
        for (int i = 0; i < DURATION; i++) {
            it.oneSecond();
        }
        assertEquals(TrafficSignal.RED, routes.get(0).getTrafficLight().getSignal());
        assertEquals(TrafficSignal.GREEN, routes.get(1).getTrafficLight().getSignal());
    }

    /**
     * Checks oneSecond function after one cycle
     * <p>
     * Test fails if:
     * <ul>
     *     <li>The current light is not green</li>
     *     <li>The previous light is not red</li>
     * </ul>
     *
     * @ass2
     */
    @Test
    public void oneSecondCycleTest() {
        for (int i = 0; i < DURATION * routes.size(); i++) {
            it.oneSecond();
        }
        assertEquals(TrafficSignal.GREEN, routes.get(0).getTrafficLight().getSignal());
        assertEquals(TrafficSignal.RED, routes.get(1).getTrafficLight().getSignal());
    }

    /**
     * Checks toString function
     * <p>
     * Test fails if the result is not "2:Z,Y"
     *
     * @ass2
     */
    @Test
    public void toStringTest() {
        assertEquals("2:Z,Y", it.toString());
    }
}
