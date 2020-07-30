package tms.congestion;

import org.junit.Before;
import org.junit.Test;
import tms.sensors.DemoPressurePad;
import tms.sensors.DemoSpeedCamera;
import tms.sensors.DemoVehicleCount;
import tms.sensors.Sensor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class AveragingCongestionCalculatorTest {

    /** A pressure pad sensor */
    private DemoPressurePad pp;
    /** A speed camera sensor */
    private DemoSpeedCamera sc;
    /** A vehicle count sensor */
    private DemoVehicleCount vc;
    /** An averaging congestion calculator */
    private AveragingCongestionCalculator acc;

    /**
     * Checks the calculateCongestion function with empty sensor list.
     * <p>
     * Test fails if the result is not 0.
     *
     * @ass2
     */
    @Test
    public void calculateCongestionEmpty() {
        List<Sensor> sensors = Collections.emptyList();
        acc = new AveragingCongestionCalculator(sensors);
        assertEquals(0, acc.calculateCongestion());
    }

    /**
     * Checks the calculateCongestion function correctly round down.
     * <p>
     * Test fails if the result is not 16.
     *
     * @ass2
     */
    @Test
    public void calculateCongestionRoundDownTest() {
        pp = new DemoPressurePad(new int[]{3,6,8,5,11}, 10);
        sc = new DemoSpeedCamera(new int[]{39,40,40,40,36}, 40);
        vc = new DemoVehicleCount(new int[]{42,40,37,51,35}, 50);
        List<Sensor> sensors = Arrays.asList(new Sensor[]{pp,sc,vc});
        acc = new AveragingCongestionCalculator(sensors);
        assertEquals(16, acc.calculateCongestion());
    }

    /**
     * Checks the calculateCongestion function correctly round up.
     * <p>
     * Test fails if the result is not 20.
     *
     * @ass2
     */
    @Test
    public void calculateCongestionRoundUpTest() {
        pp = new DemoPressurePad(new int[]{4,6,8,5,11}, 10);
        sc = new DemoSpeedCamera(new int[]{39,40,40,40,36}, 40);
        vc = new DemoVehicleCount(new int[]{42,40,37,51,35}, 50);
        List<Sensor> sensors = Arrays.asList(new Sensor[]{pp,sc,vc});
        acc = new AveragingCongestionCalculator(sensors);
        assertEquals(20, acc.calculateCongestion());
    }
}
