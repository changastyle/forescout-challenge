import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleInfectationTest {

  @Test
  public void testCalculateInfectedDevices() {
    long[] initialStates = {
        1, 1, 3, 1, 3, 2, 1, 3, 1, 1, 3, 1, 1, 2, 1, 3, 1, 1, 3, 5, 1, 1, 1, 3, 1, 2, 1, 1, 1, 1, 4, 4,
        1, 2, 1, 2, 1, 1, 1, 5, 3, 2, 1, 5, 2, 5, 3, 3, 2, 2, 5, 4, 1, 1, 4, 4, 1, 1, 1, 1, 1, 1, 5, 1,
        2, 4, 3, 2, 2, 2, 2, 1, 4, 1, 1, 5, 1, 3, 4, 4, 1, 1, 3, 3, 5, 5, 3, 1, 3, 3, 3, 1, 4, 2, 2, 1,
        3, 4, 1, 4, 3, 3, 2, 3, 1, 1, 1, 5, 3, 1, 4, 2, 2, 3, 1, 3, 1, 2, 3, 3, 1, 4, 2, 2, 4, 1, 3, 1,
        1, 1, 1, 1, 2, 1, 3, 3, 1, 2, 1, 1, 3, 4, 1, 1, 1, 1, 5, 1, 1, 5, 1, 1, 1, 4, 1, 5, 3, 1, 1, 3,
        2, 1, 1, 3, 1, 1, 1, 5, 4, 3, 3, 5, 1, 3, 4, 3, 3, 1, 4, 4, 1, 2, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1,
        1, 1, 5, 1, 1, 2, 1, 5, 2, 1, 1, 2, 3, 2, 3, 1, 3, 1, 1, 1, 5, 1, 1, 2, 1, 1, 1, 1, 3, 4, 5, 3,
        1, 4, 1, 1, 4, 1, 4, 1, 1, 1, 4, 5, 1, 1, 1, 4, 1, 3, 2, 2, 1, 1, 2, 3, 1, 4, 3, 5, 1, 5, 1, 1,
        4, 5, 5, 1, 1, 3, 3, 1, 1, 1, 1, 5, 5, 3, 3, 2, 4, 1, 1, 1, 1, 1, 5, 1, 1, 2, 5, 5, 4, 2, 4, 4,
        1, 1, 3, 3, 1, 5, 1, 1, 1, 1, 1, 1
    };

    int minutes = 2;

    long result = SimpleInfectation.calculateInfectedDevices(initialStates, minutes);

    long expected = 445;
    assertEquals(expected, result, "The number of infected devices should be correct after 2 minutes.");
  }
  @Test
  public void testOneSimpleDeviceInfectedStatus0() {

    long[] initialDevicesStates = {0};
    long expected = 2;

    long result = SimpleInfectation.calculateInfectedDevices(initialDevicesStates,1);

    assertEquals(expected, result);
  }
  @Test
  public void testDobleSimpleDeviceInfectedStatus0() {

    long[] initialDevicesStates = {0,0};
    long expected = 4;

    long result = SimpleInfectation.calculateInfectedDevices(initialDevicesStates,1);

    assertEquals(expected, result);
  }
  @Test
  public void testOneSimpleDeviceInfectedStatus0After8Minutes() {

    long[] initialDevicesStates = {0};
    long expected = 3;

    long result = SimpleInfectation.calculateInfectedDevices(initialDevicesStates,8);

    assertEquals(expected, result);
  }
  @Test
  public void testOneSimpleDeviceInfectedStatus0After16Minutes() {

    long[] initialDevicesStates = {0};
    long expected = 5;

    long result = SimpleInfectation.calculateInfectedDevices(initialDevicesStates,16);

    assertEquals(expected, result);
  }
  @Test
  public void testDobleDeviceInfectedStatus0After8Minutes() {

    long[] initialDevicesStates = {0,0};
    long expected = 6;

    long result = SimpleInfectation.calculateInfectedDevices(initialDevicesStates,8);

    assertEquals(expected, result);
  }
  @Test
  public void testDobleSimpleDeviceInfectedStatus0After16Minutes() {

    long[] initialDevicesStates = {0,0};
    long expected = 10;

    long result = SimpleInfectation.calculateInfectedDevices(initialDevicesStates,16);

    assertEquals(expected, result);
  }
  @Test
  public void testSortStatesIntoArray() {

    long[] initialDevicesStates = {5, 1, 2, 4, 1};
    long[] expected = {0,2,1,0,1,1,0,0,0,0};

    long[] result = SimpleInfectation.sortStatesIntoArray(initialDevicesStates);

    assertArrayEquals(expected, result);
  }
  @Test
  public void testCalculateExample18Min() {

    long[] initialDevicesStates = {5, 1, 2, 4, 1};
    int minutesElapsed = 18;
    long expected = 27;

    long result = SimpleInfectation.calculateInfectedDevices(initialDevicesStates, minutesElapsed);
    assertEquals(expected, result);
  }
  @Test
  public void testCalculateExample80Min() {

    long[] initialDevicesStates = {5, 1, 2, 4, 1};
    int minutesElapsed = 80;
    long expected = 5977;

    long result = SimpleInfectation.calculateInfectedDevices(initialDevicesStates, minutesElapsed);
    assertEquals(expected, result);
  }
  @Test
  public void testCalculateExample256Min() {

    long[] initialDevicesStates = {5, 1, 2, 4, 1};
    int minutesElapsed = 256;
    long expected = Long.parseLong("27125064097");

    long result = SimpleInfectation.calculateInfectedDevices(initialDevicesStates, minutesElapsed);
    assertEquals(expected, result);
  }
}