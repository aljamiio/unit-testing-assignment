package parking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {

    // Test Case 01:
    // Verifies that the vehicle ID is stored correctly when a Vehicle is created with a Wallet.
    @Test
    void testVehicleIdWithWallet() {
        Wallet wallet = new Wallet(100);

        Vehicle vehicle = new Vehicle(101, VehicleType.CAR, wallet);

        assertEquals(101, vehicle.getVehicleId());
    }


    // Test Case 02:
    // Verifies that the VehicleType provided to the constructor is stored correctly.
    @Test
    void testVehicleType() {
        Wallet wallet = new Wallet(100);

        Vehicle vehicle = new Vehicle(101, VehicleType.CAR, wallet);

        assertEquals(VehicleType.CAR, vehicle.getVehicleType());
    }


    // Test Case 03:
    // Verifies that the same Wallet object provided to the constructor is returned by getWallet().
    // This can detect an incorrect Wallet reference or an unexpected Wallet replacement.
    @Test
    void testVehicleWalletReference() {
        Wallet wallet = new Wallet(100);

        Vehicle vehicle = new Vehicle(101, VehicleType.CAR, wallet);

        assertSame(wallet, vehicle.getWallet());
    }


    // Test Case 04:
    // Verifies the boundary behavior when the vehicle ID is zero.
    @Test
    void testZeroVehicleId() {
        Wallet wallet = new Wallet(100);

        Vehicle vehicle = new Vehicle(0, VehicleType.CAR, wallet);

        assertEquals(0, vehicle.getVehicleId());
    }


    // Test Case 05:
    // Checks whether a negative vehicle ID is accepted.
    // If negative IDs are not allowed by the business rules, this may reveal a missing validation.
    @Test
    void testNegativeVehicleId() {
        Wallet wallet = new Wallet(100);

        Vehicle vehicle = new Vehicle(-1, VehicleType.CAR, wallet);

        assertEquals(-1, vehicle.getVehicleId());
    }


    // Test Case 06:
    // Verifies that the MOTORCYCLE vehicle type is stored and returned correctly.
    @Test
    void testMotorcycleVehicleType() {
        Vehicle vehicle =
                new Vehicle(1, VehicleType.MOTORCYCLE, new Wallet(100));

        assertEquals(VehicleType.MOTORCYCLE, vehicle.getVehicleType());
    }


    // Test Case 07:
    // Verifies that the BUS vehicle type is stored and returned correctly.
    @Test
    void testBusVehicleType() {
        Vehicle vehicle =
                new Vehicle(1, VehicleType.BUS, new Wallet(100));

        assertEquals(VehicleType.BUS, vehicle.getVehicleType());
    }


    // Test Case 08:
    // Verifies that the MICROCAR vehicle type is stored and returned correctly.
    @Test
    void testMicrocarVehicleType() {
        Vehicle vehicle =
                new Vehicle(1, VehicleType.MICROCAR, new Wallet(100));

        assertEquals(VehicleType.MICROCAR, vehicle.getVehicleType());
    }


    // Test Case 09:
    // Checks the behavior when a null VehicleType is provided.
    // This can reveal missing null validation if null types are not allowed by the requirements.
    @Test
    void testNullVehicleType() {
        Wallet wallet = new Wallet(100);

        Vehicle vehicle = new Vehicle(1, null, wallet);

        assertNull(vehicle.getVehicleType());
    }


    // Test Case 10:
    // Verifies that the initial balance is correctly assigned when using the balance-based constructor.
    @Test
    void testInitialBalance() {
        Vehicle vehicle =
                new Vehicle(101, VehicleType.CAR, 250.0);

        assertEquals(250.0, vehicle.getBalance());
    }


    // Test Case 11:
    // Verifies the boundary behavior when the initial balance is zero.
    @Test
    void testZeroInitialBalance() {
        Vehicle vehicle =
                new Vehicle(101, VehicleType.CAR, 0.0);

        assertEquals(0.0, vehicle.getBalance());
    }


    // Test Case 12:
    // Checks whether a negative initial balance is accepted.
    // If negative balances are not allowed, this can reveal missing validation in the Wallet constructor.
    @Test
    void testNegativeInitialBalance() {
        Vehicle vehicle =
                new Vehicle(101, VehicleType.CAR, -50.0);

        assertEquals(-50.0, vehicle.getBalance());
    }


    // Test Case 13:
    // Checks the behavior when NaN is provided as the initial balance.
    // This can reveal missing validation for invalid numeric values.
    @Test
    void testNaNInitialBalance() {
        Vehicle vehicle =
                new Vehicle(101, VehicleType.CAR, Double.NaN);

        assertTrue(Double.isNaN(vehicle.getBalance()));
    }


    // Test Case 14:
    // Verifies that getBalance() returns the current balance of the Vehicle's Wallet.
    @Test
    void testGetBalance() {
        Wallet wallet = new Wallet(100);

        Vehicle vehicle =
                new Vehicle(101, VehicleType.CAR, wallet);

        assertEquals(100.0, vehicle.getBalance());
    }


    // Test Case 15:
    // Verifies that the Vehicle balance reflects funds added directly to its Wallet.
    // This can detect synchronization or incorrect Wallet reference issues.
    @Test
    void testBalanceAfterAddingFundsToWallet() {
        Wallet wallet = new Wallet(100);

        Vehicle vehicle =
                new Vehicle(101, VehicleType.CAR, wallet);

        wallet.addFunds(50);

        assertEquals(150.0, vehicle.getBalance());
    }


    // Test Case 16:
    // Verifies that the Vehicle balance reflects funds deducted directly from its Wallet.
    @Test
    void testBalanceAfterDeductingFundsFromWallet() {
        Wallet wallet = new Wallet(100);

        Vehicle vehicle =
                new Vehicle(101, VehicleType.CAR, wallet);

        wallet.deductFunds(40);

        assertEquals(60.0, vehicle.getBalance());
    }


    // Test Case 17:
    // Verifies that modifying the Wallet returned by getWallet() affects the Vehicle balance.
    // This can detect an incorrect Wallet copy or broken reference relationship.
    @Test
    void testReturnedWalletIsLinkedToVehicle() {
        Wallet wallet = new Wallet(100);

        Vehicle vehicle =
                new Vehicle(101, VehicleType.CAR, wallet);

        Wallet returnedWallet = vehicle.getWallet();

        returnedWallet.addFunds(25);

        assertEquals(125.0, vehicle.getBalance());
    }


    // Test Case 18:
    // Verifies that the toString() output contains the correct vehicle ID.
    // The complete string is not hard-coded so that harmless formatting changes do not break the test.
    @Test
    void testToStringContainsVehicleId() {
        Vehicle vehicle =
                new Vehicle(101, VehicleType.CAR, 100.0);

        String result = vehicle.toString();

        assertTrue(result.contains("vehicleId=101"));
    }


    // Test Case 19:
    // Verifies that the toString() output contains the correct vehicle type.
    @Test
    void testToStringContainsVehicleType() {
        Vehicle vehicle =
                new Vehicle(101, VehicleType.CAR, 100.0);

        String result = vehicle.toString();

        assertTrue(result.contains("vehicleType=CAR"));
    }


    // Test Case 20:
    // Verifies that toString() reflects the updated Wallet balance.
    // This can detect stale or separately stored balance information.
    @Test
    void testToStringReflectsUpdatedBalance() {
        Wallet wallet = new Wallet(100);

        Vehicle vehicle =
                new Vehicle(101, VehicleType.CAR, wallet);

        wallet.addFunds(50);

        String result = vehicle.toString();

        assertTrue(result.contains("walletBalance=150.0"));
    }
}
