package parking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParkingSystemTest {

    private ParkingSystem system;
    private LocalDateTime start;
    private LocalDateTime end;

    @BeforeEach
    void setUp() {
        system = ParkingSystem.getInstance();
        system.resetForTesting();

        start = LocalDateTime.of(2026, 1, 1, 10, 0);
        end = LocalDateTime.of(2026, 1, 1, 12, 0);
    }

    // Test Case 01: Verify that getInstance() returns the same ParkingSystem instance
    @Test
    void testGetInstanceReturnsSameInstance() {
        ParkingSystem first = ParkingSystem.getInstance();
        ParkingSystem second = ParkingSystem.getInstance();

        assertSame(first, second);
    }

    // Test Case 02: Verify that an empty list is returned when no parking slots exist
    @Test
    void testGetAvailableSlotsWhenNoSlotsExist() {
        Vehicle vehicle = new Vehicle(1, VehicleType.CAR, 100.0);

        List<ParkingSlot> result =
                system.getAvailableParkingSlots(vehicle, start, end);

        assertTrue(result.isEmpty());
    }

    // Test Case 03: Verify that a compatible and available slot is returned
    @Test
    void testGetAvailableCompatibleSlot() {
        Vehicle vehicle = new Vehicle(1, VehicleType.CAR, 100.0);
        ParkingSlot slot = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        system.getParkingSlots().add(slot);

        List<ParkingSlot> result =
                system.getAvailableParkingSlots(vehicle, start, end);

        assertEquals(1, result.size());
        assertSame(slot, result.get(0));
    }

    // Test Case 04: Verify that an incompatible slot is not returned
    @Test
    void testGetAvailableExcludesIncompatibleSlot() {
        Vehicle vehicle = new Vehicle(1, VehicleType.CAR, 100.0);
        ParkingSlot slot = new ParkingSlot("S1", ParkingSlotType.COMPACT);

        system.getParkingSlots().add(slot);

        List<ParkingSlot> result =
                system.getAvailableParkingSlots(vehicle, start, end);

        assertTrue(result.isEmpty());
    }


    // Test Case 05: Verify that an inactive slot is not returned
    @Test
    void testGetAvailableExcludesInactiveSlot() {
        Vehicle vehicle = new Vehicle(1, VehicleType.CAR, 100.0);
        ParkingSlot slot = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        slot.deactivate();
        system.getParkingSlots().add(slot);

        List<ParkingSlot> result =
                system.getAvailableParkingSlots(vehicle, start, end);

        assertTrue(result.isEmpty());
    }

    // Test Case 06: Verify that an occupied slot is not returned
    @Test
    void testGetAvailableExcludesOccupiedSlot() {
        Vehicle vehicle = new Vehicle(1, VehicleType.CAR, 100.0);
        ParkingSlot slot = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        Booking existingBooking = new Booking(
                1,
                vehicle,
                slot,
                start,
                end,
                20.0
        );

        slot.getBookings().add(existingBooking);
        system.getParkingSlots().add(slot);

        List<ParkingSlot> result =
                system.getAvailableParkingSlots(
                        vehicle,
                        LocalDateTime.of(2026, 1, 1, 11, 0),
                        LocalDateTime.of(2026, 1, 1, 13, 0)
                );

        assertTrue(result.isEmpty());
    }

    // Test Case 07: Verify that only compatible and available slots are returned
    @Test
    void testGetAvailableMultipleSlots() {
        Vehicle vehicle = new Vehicle(1, VehicleType.CAR, 100.0);

        ParkingSlot compact = new ParkingSlot("S1", ParkingSlotType.COMPACT);
        ParkingSlot regular = new ParkingSlot("S2", ParkingSlotType.REGULAR);
        ParkingSlot large = new ParkingSlot("S3", ParkingSlotType.LARGE);

        system.getParkingSlots().add(compact);
        system.getParkingSlots().add(regular);
        system.getParkingSlots().add(large);

        List<ParkingSlot> result =
                system.getAvailableParkingSlots(vehicle, start, end);

        assertEquals(2, result.size());
        assertTrue(result.contains(regular));
        assertTrue(result.contains(large));
        assertFalse(result.contains(compact));
    }

    // Test Case 08: Verify that a slot is available when the new booking starts exactly at the existing booking end
    @Test
    void testGetAvailableAtExactEndBoundary() {
        Vehicle vehicle = new Vehicle(1, VehicleType.CAR, 100.0);
        ParkingSlot slot = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        Booking existingBooking = new Booking(
                1,
                vehicle,
                slot,
                LocalDateTime.of(2026, 1, 1, 10, 0),
                LocalDateTime.of(2026, 1, 1, 12, 0),
                20.0
        );

        slot.getBookings().add(existingBooking);
        system.getParkingSlots().add(slot);

        List<ParkingSlot> result =
                system.getAvailableParkingSlots(
                        vehicle,
                        LocalDateTime.of(2026, 1, 1, 12, 0),
                        LocalDateTime.of(2026, 1, 1, 14, 0)
                );

        assertEquals(1, result.size());
    }

    // Test Case 09: Verify that a slot with an exactly overlapping booking is not available
    @Test
    void testGetAvailableExactOverlap() {
        Vehicle vehicle = new Vehicle(1, VehicleType.CAR, 100.0);
        ParkingSlot slot = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        Booking existingBooking = new Booking(
                1,
                vehicle,
                slot,
                start,
                end,
                20.0
        );

        slot.getBookings().add(existingBooking);
        system.getParkingSlots().add(slot);

        List<ParkingSlot> result =
                system.getAvailableParkingSlots(vehicle, start, end);

        assertTrue(result.isEmpty());
    }

    // Test Case 10: Verify that a slot is unavailable when the requested period partially overlaps the beginning
    @Test
    void testGetAvailablePartialOverlapAtBeginning() {
        Vehicle vehicle = new Vehicle(1, VehicleType.CAR, 100.0);
        ParkingSlot slot = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        Booking existingBooking = new Booking(
                1,
                vehicle,
                slot,
                LocalDateTime.of(2026, 1, 1, 10, 0),
                LocalDateTime.of(2026, 1, 1, 12, 0),
                20.0
        );

        slot.getBookings().add(existingBooking);
        system.getParkingSlots().add(slot);

        List<ParkingSlot> result =
                system.getAvailableParkingSlots(
                        vehicle,
                        LocalDateTime.of(2026, 1, 1, 11, 0),
                        LocalDateTime.of(2026, 1, 1, 13, 0)
                );

        assertTrue(result.isEmpty());
    }

    // Test Case 11: Verify that a slot is unavailable when the requested period partially overlaps the end
    @Test
    void testGetAvailablePartialOverlapAtEnd() {
        Vehicle vehicle = new Vehicle(1, VehicleType.CAR, 100.0);
        ParkingSlot slot = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        Booking existingBooking = new Booking(
                1,
                vehicle,
                slot,
                LocalDateTime.of(2026, 1, 1, 12, 0),
                LocalDateTime.of(2026, 1, 1, 14, 0),
                20.0
        );

        slot.getBookings().add(existingBooking);
        system.getParkingSlots().add(slot);

        List<ParkingSlot> result =
                system.getAvailableParkingSlots(
                        vehicle,
                        LocalDateTime.of(2026, 1, 1, 10, 0),
                        LocalDateTime.of(2026, 1, 1, 13, 0)
                );

        assertTrue(result.isEmpty());
    }

    // Test Case 12: Verify behavior when a null vehicle type is provided
    @Test
    void testGetAvailableSlotsWithNullVehicleType() {
        Vehicle vehicle = new Vehicle(1, null, 100.0);
        ParkingSlot slot = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        system.getParkingSlots().add(slot);

        assertDoesNotThrow(() ->
                system.getAvailableParkingSlots(vehicle, start, end)
        );
    }

    // Test Case 13: Verify that a valid booking is created successfully
    @Test
    void testBookValidBooking() {
        Vehicle vehicle = new Vehicle(1, VehicleType.CAR, 100.0);
        ParkingSlot slot = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        Booking booking = system.book(vehicle, slot, start, end);

        assertNotNull(booking);
    }

    // Test Case 14: Verify that a newly created booking has ACTIVE status
    @Test
    void testBookCreatesActiveBooking() {
        Vehicle vehicle = new Vehicle(1, VehicleType.CAR, 100.0);
        ParkingSlot slot = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        Booking booking = system.book(vehicle, slot, start, end);

        assertEquals(BookingStatus.ACTIVE, booking.getBookingStatus());
    }

    // Test Case 15: Verify that the booking contains the correct vehicle and parking slot
    @Test
    void testBookStoresCorrectReferences() {
        Vehicle vehicle = new Vehicle(1, VehicleType.CAR, 100.0);
        ParkingSlot slot = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        Booking booking = system.book(vehicle, slot, start, end);

        assertSame(vehicle, booking.getVehicle());
        assertSame(slot, booking.getParkingSlot());
    }

    // Test Case 16: Verify that the successful booking is added to the system booking list
    @Test
    void testBookAddsBookingToSystemList() {
        Vehicle vehicle = new Vehicle(1, VehicleType.CAR, 100.0);
        ParkingSlot slot = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        Booking booking = system.book(vehicle, slot, start, end);

        assertEquals(1, system.getBookings().size());
        assertSame(booking, system.getBookings().get(0));
    }

    // Test Case 17: Verify that the successful booking is added to the parking slot booking list
    @Test
    void testBookAddsBookingToSlotList() {
        Vehicle vehicle = new Vehicle(1, VehicleType.CAR, 100.0);
        ParkingSlot slot = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        Booking booking = system.book(vehicle, slot, start, end);

        assertEquals(1, slot.getBookings().size());
        assertSame(booking, slot.getBookings().get(0));
    }

    // Test Case 18: Verify that a booking with equal start and end times is rejected
    @Test
    void testBookWithEqualStartAndEnd() {
        Vehicle vehicle = new Vehicle(1, VehicleType.CAR, 100.0);
        ParkingSlot slot = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        assertThrows(
                IllegalBookingTimeException.class,
                () -> system.book(vehicle, slot, start, start)
        );
    }

    // Test Case 19: Verify that a booking ending before its start time is rejected
    @Test
    void testBookWithEndBeforeStart() {
        Vehicle vehicle = new Vehicle(1, VehicleType.CAR, 100.0);
        ParkingSlot slot = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        assertThrows(
                IllegalBookingTimeException.class,
                () -> system.book(vehicle, slot, end, start)
        );
    }

    // Test Case 20: Verify that a one-hour booking is accepted
    @Test
    void testBookOneHourBooking() {
        Vehicle vehicle = new Vehicle(1, VehicleType.CAR, 100.0);
        ParkingSlot slot = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        Booking booking = system.book(
                vehicle,
                slot,
                start,
                start.plusHours(1)
        );

        assertEquals(10.0, booking.getAmount(), 0.0001);
    }

    // Test Case 21: Verify that a valid booking shorter than one hour is handled correctly
    @Test
    void testBookThirtyMinuteBooking() {
        Vehicle vehicle = new Vehicle(1, VehicleType.CAR, 100.0);
        ParkingSlot slot = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        Booking booking = system.book(
                vehicle,
                slot,
                start,
                start.plusMinutes(30)
        );

        assertEquals(5.0, booking.getAmount(), 0.0001);
    }

    // Test Case 22: Verify pricing for a CAR using a REGULAR slot
    @Test
    void testBookPriceCarRegular() {
        Vehicle vehicle = new Vehicle(1, VehicleType.CAR, 100.0);
        ParkingSlot slot = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        Booking booking = system.book(vehicle, slot, start, end);

        assertEquals(20.0, booking.getAmount(), 0.0001);
    }

    // Test Case 23: Verify pricing for a BICYCLE using a REGULAR slot
    @Test
    void testBookPriceBicycleRegular() {
        Vehicle vehicle = new Vehicle(1, VehicleType.BICYCLE, 100.0);
        ParkingSlot slot = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        Booking booking = system.book(vehicle, slot, start, end);

        assertEquals(4.0, booking.getAmount(), 0.0001);
    }

    // Test Case 24: Verify pricing for a MOTORCYCLE using a COMPACT slot
    @Test
    void testBookPriceMotorcycleCompact() {
        Vehicle vehicle = new Vehicle(1, VehicleType.MOTORCYCLE, 100.0);
        ParkingSlot slot = new ParkingSlot("S1", ParkingSlotType.COMPACT);

        Booking booking = system.book(vehicle, slot, start, end);

        assertEquals(8.0, booking.getAmount(), 0.0001);
    }

    // Test Case 25: Verify pricing for a MICROCAR using a LARGE slot
    @Test
    void testBookPriceMicrocarLarge() {
        Vehicle vehicle = new Vehicle(1, VehicleType.MICROCAR, 100.0);
        ParkingSlot slot = new ParkingSlot("S1", ParkingSlotType.LARGE);

        Booking booking = system.book(vehicle, slot, start, end);

        assertEquals(45.0, booking.getAmount(), 0.0001);
    }

    // Test Case 26: Verify pricing for a BUS using a LARGE slot
    @Test
    void testBookPriceBusLarge() {
        Vehicle vehicle = new Vehicle(1, VehicleType.BUS, 100.0);
        ParkingSlot slot = new ParkingSlot("S1", ParkingSlotType.LARGE);

        Booking booking = system.book(vehicle, slot, start, end);

        assertEquals(60.0, booking.getAmount(), 0.0001);
    }

    // Test Case 27: Verify that booking an inactive slot is rejected
    @Test
    void testBookInactiveSlot() {
        Vehicle vehicle = new Vehicle(1, VehicleType.CAR, 100.0);
        ParkingSlot slot = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        slot.deactivate();

        assertThrows(
                IllegalArgumentException.class,
                () -> system.book(vehicle, slot, start, end)
        );
    }

    // Test Case 28: Verify that booking an incompatible slot is rejected
    @Test
    void testBookIncompatibleSlot() {
        Vehicle vehicle = new Vehicle(1, VehicleType.CAR, 100.0);
        ParkingSlot slot = new ParkingSlot("S1", ParkingSlotType.COMPACT);

        assertThrows(
                IllegalArgumentException.class,
                () -> system.book(vehicle, slot, start, end)
        );
    }

    // Test Case 29: Verify that booking an occupied slot is rejected
    @Test
    void testBookOccupiedSlot() {
        Vehicle firstVehicle = new Vehicle(1, VehicleType.CAR, 100.0);
        Vehicle secondVehicle = new Vehicle(2, VehicleType.CAR, 100.0);

        ParkingSlot slot = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        system.book(firstVehicle, slot, start, end);

        assertThrows(
                IllegalArgumentException.class,
                () -> system.book(
                        secondVehicle,
                        slot,
                        start.plusHours(1),
                        end.plusHours(1)
                )
        );
    }

    // Test Case 30: Verify that the full booking amount is transferred from the vehicle to the system
    @Test
    void testBookTransfersFullAmount() {
        Vehicle vehicle = new Vehicle(1, VehicleType.CAR, 100.0);
        ParkingSlot slot = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        Booking booking = system.book(vehicle, slot, start, end);

        assertEquals(80.0, vehicle.getBalance(), 0.0001);
        assertEquals(20.0, system.getBalance(), 0.0001);
        assertEquals(booking.getAmount(), system.getBalance(), 0.0001);
    }

    // Test Case 31: Verify that booking fails when the vehicle has insufficient funds
    @Test
    void testBookWithInsufficientFunds() {
        Vehicle vehicle = new Vehicle(1, VehicleType.CAR, 10.0);
        ParkingSlot slot = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        assertThrows(
                InsufficientFundsException.class,
                () -> system.book(vehicle, slot, start, end)
        );
    }

    // Test Case 32: Verify that a failed booking is not added to the system booking list
    @Test
    void testFailedBookingIsNotAddedToSystem() {
        Vehicle vehicle = new Vehicle(1, VehicleType.CAR, 10.0);
        ParkingSlot slot = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        assertThrows(
                InsufficientFundsException.class,
                () -> system.book(vehicle, slot, start, end)
        );

        assertTrue(system.getBookings().isEmpty());
    }

    // Test Case 33: Verify that a failed booking does not occupy the parking slot
    @Test
    void testFailedBookingIsNotAddedToSlot() {
        Vehicle vehicle = new Vehicle(1, VehicleType.CAR, 10.0);
        ParkingSlot slot = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        assertThrows(
                InsufficientFundsException.class,
                () -> system.book(vehicle, slot, start, end)
        );

        assertTrue(slot.getBookings().isEmpty());
    }

    // Test Case 34: Verify that a failed booking does not change either wallet balance
    @Test
    void testFailedBookingDoesNotChangeBalances() {
        Vehicle vehicle = new Vehicle(1, VehicleType.CAR, 10.0);
        ParkingSlot slot = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        assertThrows(
                InsufficientFundsException.class,
                () -> system.book(vehicle, slot, start, end)
        );

        assertEquals(10.0, vehicle.getBalance(), 0.0001);
        assertEquals(0.0, system.getBalance(), 0.0001);
    }

    // Test Case 35: Verify that a failed booking attempt does not consume a booking ID
    @Test
    void testBookingIdAfterFailedBooking() {
        Vehicle poorVehicle = new Vehicle(1, VehicleType.CAR, 10.0);
        ParkingSlot firstSlot = new ParkingSlot("S1", ParkingSlotType.REGULAR);

        assertThrows(
                InsufficientFundsException.class,
                () -> system.book(poorVehicle, firstSlot, start, end)
        );

        Vehicle validVehicle = new Vehicle(2, VehicleType.CAR, 100.0);
        ParkingSlot secondSlot = new ParkingSlot("S2", ParkingSlotType.REGULAR);

        Booking booking =
                system.book(validVehicle, secondSlot, start, end);

        assertEquals(1, booking.getBookingId());
    }

}
