package tms.network;

import org.junit.Before;
import org.junit.Test;
import tms.util.InvalidNetworkException;

import java.io.File;
import java.io.IOException;

public class NetworkInitialiserTest {

    /**
     * Checks loadNetwork function on demo.txt
     * <p>
     * Test fails if there is an exception thrown
     *
     * @ass2
     */
    @Test
    public void testLoadNetwork() throws InvalidNetworkException, IOException {
        NetworkInitialiser.loadNetwork("networks" + File.separator + "demo.txt");
    }

    /**
     * Checks loadNetwork function with IOException
     * <p>
     * Test fails if there is no {@link IOException} thrown
     *
     * @ass2
     */
    @Test (expected = IOException.class)
    public void testIOException() throws IOException, InvalidNetworkException {
        NetworkInitialiser.loadNetwork("networks" + File.separator + "ioException.txt");
    }

    /**
     * Checks loadNetwork function throwing exception with incorrect number of
     * intersections
     * <p>
     * Test fails if there is no {@link InvalidNetworkException} thrown
     *
     * @ass2
     */
    @Test (expected = InvalidNetworkException.class)
    public void testNumIntersection() throws InvalidNetworkException, IOException {
        NetworkInitialiser.loadNetwork("networks" + File.separator + "numIntersection.txt");
    }

    /**
     * Checks loadNetwork function throwing exception with incorrect number of
     * routes
     * <p>
     * Test fails if there is no {@link InvalidNetworkException} thrown
     *
     * @ass2
     */
    @Test (expected = InvalidNetworkException.class)
    public void testNumRoute() throws InvalidNetworkException, IOException {
        NetworkInitialiser.loadNetwork("networks" + File.separator + "numRoute.txt");
    }

    /**
     * Checks loadNetwork function throwing exception with incorrect number of
     * sensors
     * <p>
     * Test fails if there is no {@link InvalidNetworkException} thrown
     *
     * @ass2
     */
    @Test (expected = InvalidNetworkException.class)
    public void testNumSensor() throws InvalidNetworkException, IOException {
        NetworkInitialiser.loadNetwork("networks" + File.separator + "numSensor.txt");
    }

    /**
     * Checks loadNetwork function throwing exception with intersection not
     * matched
     * <p>
     * Test fails if there is no {@link InvalidNetworkException} thrown
     *
     * @ass2
     */
    @Test (expected = InvalidNetworkException.class)
    public void testIntersectionNotMatch() throws InvalidNetworkException, IOException {
        NetworkInitialiser.loadNetwork("networks" + File.separator + "intersectionNotMatch.txt");
    }

    /**
     * Checks loadNetwork function throwing exception with invalid intersection
     * id
     * <p>
     * Test fails if there is no {@link InvalidNetworkException} thrown
     *
     * @ass2
     */
    @Test (expected = InvalidNetworkException.class)
    public void testIntersectionInvalid() throws InvalidNetworkException, IOException {
        NetworkInitialiser.loadNetwork("networks" + File.separator + "intersectionInvalid.txt");
    }

    /**
     * Checks loadNetwork function throwing exception with duplicate
     * intersections
     * <p>
     * Test fails if there is no {@link InvalidNetworkException} thrown
     *
     * @ass2
     */
    @Test (expected = InvalidNetworkException.class)
    public void testDuplicateIntersection() throws InvalidNetworkException, IOException {
        NetworkInitialiser.loadNetwork("networks" + File.separator + "duplicateIntersection.txt");
    }

    /**
     * Checks loadNetwork function throwing exception with duplicate routes
     * <p>
     * Test fails if there is no {@link InvalidNetworkException} thrown
     *
     * @ass2
     */
    @Test (expected = InvalidNetworkException.class)
    public void testDuplicateRoute() throws InvalidNetworkException, IOException {
        NetworkInitialiser.loadNetwork("networks" + File.separator + "duplicateRoute.txt");
    }

    /**
     * Checks loadNetwork function throwing exception with invalid sensor type
     * <p>
     * Test fails if there is no {@link InvalidNetworkException} thrown
     *
     * @ass2
     */
    @Test (expected = InvalidNetworkException.class)
    public void testInvalidSensorType() throws InvalidNetworkException, IOException {
        NetworkInitialiser.loadNetwork("networks" + File.separator + "invalidSensorType.txt");
    }

    /**
     * Checks loadNetwork function throwing exception with duplicate sensors
     * <p>
     * Test fails if there is no {@link InvalidNetworkException} thrown
     *
     * @ass2
     */
    @Test (expected = InvalidNetworkException.class)
    public void testDuplicateSensor() throws InvalidNetworkException, IOException {
        NetworkInitialiser.loadNetwork("networks" + File.separator + "duplicateSensor.txt");
    }

    /**
     * Checks loadNetwork function throwing exception with invalid yellow time
     * <p>
     * Test fails if there is no {@link InvalidNetworkException} thrown
     *
     * @ass2
     */
    @Test (expected = InvalidNetworkException.class)
    public void testYellowTime() throws InvalidNetworkException, IOException {
        NetworkInitialiser.loadNetwork("networks" + File.separator + "yellowTime.txt");
    }

    /**
     * Checks loadNetwork function throwing exception with invalid duration
     * <p>
     * Test fails if there is no {@link InvalidNetworkException} thrown
     *
     * @ass2
     */
    @Test (expected = InvalidNetworkException.class)
    public void testDuration() throws InvalidNetworkException, IOException {
        NetworkInitialiser.loadNetwork("networks" + File.separator + "duration.txt");
    }

    /**
     * Checks loadNetwork function throwing exception with empty intersection
     * order
     * <p>
     * Test fails if there is no {@link InvalidNetworkException} thrown
     *
     * @ass2
     */
    @Test (expected = InvalidNetworkException.class)
    public void testEmptyOrder() throws InvalidNetworkException, IOException {
        NetworkInitialiser.loadNetwork("networks" + File.separator + "emptyOrder.txt");
    }

    /**
     * Checks loadNetwork function throwing exception with invalid intersection
     * order
     * <p>
     * Test fails if there is no {@link InvalidNetworkException} thrown
     *
     * @ass2
     */
    @Test (expected = InvalidNetworkException.class)
    public void testInvalidOrder() throws InvalidNetworkException, IOException {
        NetworkInitialiser.loadNetwork("networks" + File.separator + "invalidOrder.txt");
    }

    /**
     * Checks loadNetwork function throwing exception with invalid route speed
     * <p>
     * Test fails if there is no {@link InvalidNetworkException} thrown
     *
     * @ass2
     */
    @Test (expected = InvalidNetworkException.class)
    public void testRouteSpeed() throws InvalidNetworkException, IOException {
        NetworkInitialiser.loadNetwork("networks" + File.separator + "routeSpeed.txt");
    }

    /**
     * Checks loadNetwork function throwing exception with invalid speed sign
     * speed
     * <p>
     * Test fails if there is no {@link InvalidNetworkException} thrown
     *
     * @ass2
     */
    @Test (expected = InvalidNetworkException.class)
    public void testSpeedSignSpeed() throws InvalidNetworkException, IOException {
        NetworkInitialiser.loadNetwork("networks" + File.separator + "speedSignSpeed.txt");
    }

    /**
     * Checks loadNetwork function throwing exception with invalid threshold
     * <p>
     * Test fails if there is no {@link InvalidNetworkException} thrown
     *
     * @ass2
     */
    @Test (expected = InvalidNetworkException.class)
    public void testSensorThreshold() throws InvalidNetworkException, IOException {
        NetworkInitialiser.loadNetwork("networks" + File.separator + "sensorThreshold.txt");
    }

    /**
     * Checks loadNetwork function throwing exception with invalid sensor data
     * <p>
     * Test fails if there is no {@link InvalidNetworkException} thrown
     *
     * @ass2
     */
    @Test (expected = InvalidNetworkException.class)
    public void testSensorData() throws InvalidNetworkException, IOException {
        NetworkInitialiser.loadNetwork("networks" + File.separator + "sensorData.txt");
    }

    /**
     * Checks loadNetwork function throwing exception with violated
     * colon-delimiter format
     * <p>
     * Test fails if there is no {@link InvalidNetworkException} thrown
     *
     * @ass2
     */
    @Test (expected = InvalidNetworkException.class)
    public void testColonDelimiterViolated() throws InvalidNetworkException, IOException {
        NetworkInitialiser.loadNetwork("networks" + File.separator + "colonDelimiterViolated.txt");
    }

    /**
     * Checks loadNetwork function throwing exception with parse integer
     * <p>
     * Test fails if there is no {@link InvalidNetworkException} thrown
     *
     * @ass2
     */
    @Test (expected = InvalidNetworkException.class)
    public void testParseNumericValue() throws InvalidNetworkException, IOException {
        NetworkInitialiser.loadNetwork("networks" + File.separator + "parseNumericValue.txt");
    }

    /**
     * Checks loadNetwork function throwing exception with invalid empty line
     * <p>
     * Test fails if there is no {@link InvalidNetworkException} thrown
     *
     * @ass2
     */
    @Test (expected = InvalidNetworkException.class)
    public void testEmptyLine() throws InvalidNetworkException, IOException {
        NetworkInitialiser.loadNetwork("networks" + File.separator + "emptyLine.txt");
    }

    /**
     * Checks loadNetwork function throwing exception with more than two new
     * line characters
     * <p>
     * Test fails if there is no {@link InvalidNetworkException} thrown
     *
     * @ass2
     */
    @Test (expected = InvalidNetworkException.class)
    public void testMoreThanTwoNewlineCharacters() throws InvalidNetworkException, IOException {
        NetworkInitialiser.loadNetwork("networks" + File.separator + "moreThanTwoNewlineCharacters.txt");
    }
}
