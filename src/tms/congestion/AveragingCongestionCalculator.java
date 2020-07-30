package tms.congestion;

import tms.sensors.Sensor;

import java.util.List;

public class AveragingCongestionCalculator implements CongestionCalculator {

    /** A list of sensors, null if none exists. */
    private List<Sensor> sensors;

    /**
     * Creates a new congestion calculator with the given list of sensors.
     *
     * @param sensors a list of sensors
     * @ass2
     */
    public AveragingCongestionCalculator(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    /**
     * Calculates the average congestion level, as returned by
     * {@link Sensor#getCongestion()}, of all the sensors stored by this
     * calculator.
     * <p>
     * If there are no sensors stored, return 0.
     * <p>
     * If the computed average is not an integer, it should be rounded to the
     * nearest integer before being returned.
     *
     * @return the calculated congestion rate as an integer between 0 and 100
     * inclusive
     * @ass2
     */
    @Override
    public int calculateCongestion() {
        if (sensors.isEmpty()) {
            return 0;
        }
        else {
            float totalCongestion = 0;
            for (Sensor sensor : sensors) {
                totalCongestion += sensor.getCongestion();
            }
            return Math.round(totalCongestion / sensors.size());
        }
    }
}
