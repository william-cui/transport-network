package tms.congestion;

public interface CongestionCalculator {

    /**
     * Returns a congestion level, between 0 and 100, indicating the calculated
     * congestion of a route based on the reported congestion levels of the
     * sensors on that route.
     *
     * @return calculated congestion level, 0 to 100 inclusive
     * @ass2
     */
    int calculateCongestion();
}
