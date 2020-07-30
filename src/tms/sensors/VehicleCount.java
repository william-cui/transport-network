package tms.sensors;

public interface VehicleCount extends Sensor {

    /**
     * Returns the observed rate of vehicles travelling past this sensor in
     * vehicles per minute.
     *
     * @return the current traffic count reported by the vehicle count sensor
     * @ass2
     */
    int countTraffic();
}
