import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SimpleInfectation {

  public static long calculateInfectedDevices(long[] initialStates, int minutesRecibed) {

    // 1 - ARRAY WITH THE AMOUNT OF DEVICES IN EACH STATUS:
    long[] arrAmountOfDevicesSortByState = new long[9];
    arrAmountOfDevicesSortByState = sortStatesIntoArray(initialStates); // SORT THE ENTRY VALUES:

    // 0 - STATUS: READY TO INFECT A NEW DEVICE.
    // 1-6 - COLLECTING DATA TO INFECT MORE DEVICES.
    // 7-8 - JUST INFECTED BY ANOTHER DEVICE, INITIAL STATE

    // 2 - SIMULATION OF ELAPSED MINUTES;
    for (int minuteLoop = 0; minuteLoop < minutesRecibed; minuteLoop++) {

      // 3 - THE DEVICES IN STATUS 0 WILL GENERATE NEW INFECTION DEVICES
      long arrNewInfections = arrAmountOfDevicesSortByState[0];

      // 4 - ROTATE THE ARRAY TO MOVE DEVICE TOWARDS THE NEXT STATE:
      long amountOfDevicesReadyToInfect = arrAmountOfDevicesSortByState[0];
      System.arraycopy(arrAmountOfDevicesSortByState, 1, arrAmountOfDevicesSortByState, 0, 8);// THIS METHOD IS FASTER TO ROTATE THIS METHOD
      //arrAmountOfDevicesSortByState = rotateArray(arrAmountOfDevicesSortByState); // OLD METHOD TO ROTATE THE ARRAY ALREADY PERFORMED..

      // 5 - DEVICES IN STATUS 0 ARE RESETED TO STATUS 6 AND NEW DEVICES ARE GENERATED IN STATUS 8:
      arrAmountOfDevicesSortByState[6] += amountOfDevicesReadyToInfect;
      arrAmountOfDevicesSortByState[8] = arrNewInfections;
    }

    // 99 - SUM ALL THE DEVICE AT ANY STATE:
    return Arrays.stream(arrAmountOfDevicesSortByState).sum();
  }

  public static long[] sortStatesIntoArray(long[] initialStates) {
    // Inicializa el arreglo con 10 posiciones (0 a 9)
    long[] sortedStates = new long[10];

    // Recorre initialStates y acumula en sortedStates usando la posición de los valores
    Arrays.stream(initialStates)
        .forEach(state -> {
          if (state >= 0 && state < 10) { // Asegura que el valor esté en el rango de 0 a 9
            sortedStates[(int) state]++; // Incrementa el contador en la posición correspondiente
          }
        });

    return sortedStates;
  }
  public static void main(String[] args) {

    Runtime runtime = Runtime.getRuntime();
    long memoryBefore = runtime.totalMemory() - runtime.freeMemory();
    long start = System.currentTimeMillis();

    long[] initialDevicesStates = {5, 1, 2, 4, 1}; // Initial number of infected devices
    System.out.println("Devices Infected ["+ + calculateInfectedDevices(initialDevicesStates, 2) +"] after 2 min: " );
    System.out.println("Devices Infected ["+ + calculateInfectedDevices(initialDevicesStates, 80) +"] after 80 min: " );
    System.out.println("Devices Infected ["+ + calculateInfectedDevices(initialDevicesStates, 256) +"] after 256 min: " );
//    System.out.println("Devices Infected ["+ + calculateInfectedDevices(initialStates, 787897879) +"] after 787897879 min: " );

    long end = System.currentTimeMillis();
    long elapsedTime = end - start;
    long memoryAfter = runtime.totalMemory() - runtime.freeMemory();
    System.out.println("RAM MEMORY USED: " + (memoryAfter - memoryBefore) + " B");
    System.out.println("ELAPSED TIME: " + elapsedTime + " ms.");
  }
}
