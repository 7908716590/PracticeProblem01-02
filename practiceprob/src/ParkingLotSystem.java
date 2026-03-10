import java.util.*;

class ParkingSpot {
    String licensePlate;
    long entryTime;
    boolean occupied;

    public ParkingSpot() {
        this.licensePlate = null;
        this.entryTime = 0;
        this.occupied = false;
    }
}

public class ParkingLotSystem {

    private ParkingSpot[] table;
    private int capacity = 500;

    public ParkingLotSystem() {
        table = new ParkingSpot[capacity];
        for (int i = 0; i < capacity; i++) {
            table[i] = new ParkingSpot();
        }
    }

    // Hash function
    private int hash(String licensePlate) {
        return Math.abs(licensePlate.hashCode()) % capacity;
    }

    // Park vehicle using linear probing
    public void parkVehicle(String licensePlate) {

        int index = hash(licensePlate);
        int probes = 0;

        while (table[index].occupied) {
            index = (index + 1) % capacity;
            probes++;
        }

        table[index].licensePlate = licensePlate;
        table[index].entryTime = System.currentTimeMillis();
        table[index].occupied = true;

        System.out.println("Vehicle " + licensePlate +
                " assigned spot #" + index +
                " (" + probes + " probes)");
    }

    // Exit vehicle
    public void exitVehicle(String licensePlate) {

        int index = hash(licensePlate);

        while (table[index].occupied) {

            if (licensePlate.equals(table[index].licensePlate)) {

                long exitTime = System.currentTimeMillis();
                long duration = (exitTime - table[index].entryTime) / 60000;

                table[index].occupied = false;
                table[index].licensePlate = null;

                double fee = duration * 0.1;

                System.out.println("Spot #" + index + " freed");
                System.out.println("Duration: " + duration + " minutes");
                System.out.println("Fee: $" + fee);
                return;
            }

            index = (index + 1) % capacity;
        }

        System.out.println("Vehicle not found");
    }

    // Parking statistics
    public void getStatistics() {

        int occupied = 0;

        for (ParkingSpot spot : table) {
            if (spot.occupied) occupied++;
        }

        double occupancyRate = (occupied * 100.0) / capacity;

        System.out.println("Total Spots: " + capacity);
        System.out.println("Occupied: " + occupied);
        System.out.println("Occupancy Rate: " + occupancyRate + "%");
    }

    public static void main(String[] args) {

        ParkingLotSystem parking = new ParkingLotSystem();

        parking.parkVehicle("ABC-1234");
        parking.parkVehicle("ABC-1235");
        parking.parkVehicle("XYZ-9999");

        parking.getStatistics();

        parking.exitVehicle("ABC-1234");

        parking.getStatistics();
    }
}