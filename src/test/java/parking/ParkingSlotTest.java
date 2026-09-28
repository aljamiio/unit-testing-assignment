package parking;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ParkingSlotTest {

    // Test Case 01:
    // Verifies that the slot ID provided during construction is stored correctly.
    @Test
    void testSlotId() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.COMPACT);

        assertEquals("S1", slot.getSlotId());
    }


    // Test Case 02:
    // Verifies that the slot type provided during construction is stored correctly.
    @Test
    void testSlotType() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.COMPACT);

        assertEquals(ParkingSlotType.COMPACT, slot.getSlotType());
    }


    // Test Case 03:
    // Verifies that a newly created parking slot is active by default.
    @Test
    void testSlotIsActiveByDefault() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.COMPACT);

        assertTrue(slot.isActive());
    }


    // Test Case 04:
    // Verifies that a newly created parking slot has an empty booking list.
    @Test
    void testBookingsAreEmptyInitially() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.COMPACT);

        assertNotNull(slot.getBookings());
        assertTrue(slot.getBookings().isEmpty());
    }


    // Test Case 05:
    // Verifies that a newly created parking slot starts with a zero wallet balance.
    @Test
    void testInitialWalletBalance() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.COMPACT);

        assertEquals(0.0, slot.getBalance());
    }


    // Test Case 06:
    // Verifies that getWallet() returns the Wallet associated with the parking slot.
    @Test
    void testWalletReference() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.COMPACT);

        assertNotNull(slot.getWallet());
        assertEquals(0.0, slot.getWallet().getBalance());
    }


    // Test Case 07:
    // Verifies that deactivating a slot changes its active status to false.
    @Test
    void testDeactivateSlot() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.COMPACT);

        slot.deactivate();

        assertFalse(slot.isActive());
    }


    // Test Case 08:
    // Verifies that an inactive slot can be activated again.
    @Test
    void testActivateSlot() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.COMPACT);

        slot.deactivate();
        slot.activate();

        assertTrue(slot.isActive());
    }


    // Test Case 09:
    // Verifies that repeatedly activating an already active slot does not deactivate it.
    @Test
    void testActivateAlreadyActiveSlot() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.COMPACT);

        slot.activate();

        assertTrue(slot.isActive());
    }


    // Test Case 10:
    // Verifies that repeatedly deactivating an already inactive slot keeps it inactive.
    @Test
    void testDeactivateAlreadyInactiveSlot() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.COMPACT);

        slot.deactivate();
        slot.deactivate();

        assertFalse(slot.isActive());
    }


    // Test Case 11:
    // Verifies that a MOTORCYCLE is compatible with a COMPACT slot when the slot is available.
    @Test
    void testMotorcycleWithCompactSlot() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.COMPACT);

        LocalDateTime start = LocalDateTime.of(2026, 9, 28, 10, 0);
        LocalDateTime end = LocalDateTime.of(2026, 9, 28, 11, 0);

        assertTrue(slot.isCompatible(VehicleType.MOTORCYCLE, start, end));
    }


    // Test Case 12:
    // Verifies that a MOTORCYCLE is compatible with a REGULAR slot.
    @Test
    void testMotorcycleWithRegularSlot() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.REGULAR);

        LocalDateTime start = LocalDateTime.of(2026, 9, 28, 10, 0);
        LocalDateTime end = LocalDateTime.of(2026, 9, 28, 11, 0);

        assertTrue(slot.isCompatible(VehicleType.MOTORCYCLE, start, end));
    }


    // Test Case 13:
    // Verifies that a MOTORCYCLE is compatible with a LARGE slot.
    @Test
    void testMotorcycleWithLargeSlot() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.LARGE);

        LocalDateTime start = LocalDateTime.of(2026, 9, 28, 10, 0);
        LocalDateTime end = LocalDateTime.of(2026, 9, 28, 11, 0);

        assertTrue(slot.isCompatible(VehicleType.MOTORCYCLE, start, end));
    }


    // Test Case 14:
    // Verifies that a MOTORCYCLE is not compatible with a HANDICAPPED slot.
    @Test
    void testMotorcycleWithHandicappedSlot() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.HANDICAPPED);

        LocalDateTime start = LocalDateTime.of(2026, 9, 28, 10, 0);
        LocalDateTime end = LocalDateTime.of(2026, 9, 28, 11, 0);

        assertFalse(slot.isCompatible(VehicleType.MOTORCYCLE, start, end));
    }


    // Test Case 15:
    // Verifies that a CAR is compatible with a REGULAR slot.
    @Test
    void testCarWithRegularSlot() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.REGULAR);

        LocalDateTime start = LocalDateTime.of(2026, 9, 28, 10, 0);
        LocalDateTime end = LocalDateTime.of(2026, 9, 28, 11, 0);

        assertTrue(slot.isCompatible(VehicleType.CAR, start, end));
    }


    // Test Case 16:
    // Verifies that a CAR is compatible with a LARGE slot.
    @Test
    void testCarWithLargeSlot() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.LARGE);

        LocalDateTime start = LocalDateTime.of(2026, 9, 28, 10, 0);
        LocalDateTime end = LocalDateTime.of(2026, 9, 28, 11, 0);

        assertTrue(slot.isCompatible(VehicleType.CAR, start, end));
    }


    // Test Case 17:
    // Verifies that a CAR is not compatible with a COMPACT slot.
    @Test
    void testCarWithCompactSlot() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.COMPACT);

        LocalDateTime start = LocalDateTime.of(2026, 9, 28, 10, 0);
        LocalDateTime end = LocalDateTime.of(2026, 9, 28, 11, 0);

        assertFalse(slot.isCompatible(VehicleType.CAR, start, end));
    }


    // Test Case 18:
    // Verifies that a CAR is not compatible with a HANDICAPPED slot.
    @Test
    void testCarWithHandicappedSlot() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.HANDICAPPED);

        LocalDateTime start = LocalDateTime.of(2026, 9, 28, 10, 0);
        LocalDateTime end = LocalDateTime.of(2026, 9, 28, 11, 0);

        assertFalse(slot.isCompatible(VehicleType.CAR, start, end));
    }


    // Test Case 19:
    // Verifies that a BUS is compatible only with a LARGE slot.
    @Test
    void testBusWithLargeSlot() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.LARGE);

        LocalDateTime start = LocalDateTime.of(2026, 9, 28, 10, 0);
        LocalDateTime end = LocalDateTime.of(2026, 9, 28, 11, 0);

        assertTrue(slot.isCompatible(VehicleType.BUS, start, end));
    }


    // Test Case 20:
    // Verifies that a BUS is not compatible with a REGULAR slot.
    @Test
    void testBusWithRegularSlot() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.REGULAR);

        LocalDateTime start = LocalDateTime.of(2026, 9, 28, 10, 0);
        LocalDateTime end = LocalDateTime.of(2026, 9, 28, 11, 0);

        assertFalse(slot.isCompatible(VehicleType.BUS, start, end));
    }


    // Test Case 21:
    // Verifies that a BUS is not compatible with a COMPACT slot.
    @Test
    void testBusWithCompactSlot() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.COMPACT);

        LocalDateTime start = LocalDateTime.of(2026, 9, 28, 10, 0);
        LocalDateTime end = LocalDateTime.of(2026, 9, 28, 11, 0);

        assertFalse(slot.isCompatible(VehicleType.BUS, start, end));
    }


    // Test Case 22:
    // Verifies that a BUS is not compatible with a HANDICAPPED slot.
    @Test
    void testBusWithHandicappedSlot() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.HANDICAPPED);

        LocalDateTime start = LocalDateTime.of(2026, 9, 28, 10, 0);
        LocalDateTime end = LocalDateTime.of(2026, 9, 28, 11, 0);

        assertFalse(slot.isCompatible(VehicleType.BUS, start, end));
    }


    // Test Case 23:
    // Verifies that a BICYCLE is compatible with a COMPACT slot.
    @Test
    void testBicycleWithCompactSlot() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.COMPACT);

        LocalDateTime start = LocalDateTime.of(2026, 9, 28, 10, 0);
        LocalDateTime end = LocalDateTime.of(2026, 9, 28, 11, 0);

        assertTrue(slot.isCompatible(VehicleType.BICYCLE, start, end));
    }


    // Test Case 24:
    // Verifies that a BICYCLE is compatible with a REGULAR slot.
    @Test
    void testBicycleWithRegularSlot() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.REGULAR);

        LocalDateTime start = LocalDateTime.of(2026, 9, 28, 10, 0);
        LocalDateTime end = LocalDateTime.of(2026, 9, 28, 11, 0);

        assertTrue(slot.isCompatible(VehicleType.BICYCLE, start, end));
    }


    // Test Case 25:
    // Verifies that a BICYCLE is compatible with a LARGE slot.
    @Test
    void testBicycleWithLargeSlot() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.LARGE);

        LocalDateTime start = LocalDateTime.of(2026, 9, 28, 10, 0);
        LocalDateTime end = LocalDateTime.of(2026, 9, 28, 11, 0);

        assertTrue(slot.isCompatible(VehicleType.BICYCLE, start, end));
    }


    // Test Case 26:
    // Verifies that a BICYCLE is compatible with a HANDICAPPED slot.
    @Test
    void testBicycleWithHandicappedSlot() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.HANDICAPPED);

        LocalDateTime start = LocalDateTime.of(2026, 9, 28, 10, 0);
        LocalDateTime end = LocalDateTime.of(2026, 9, 28, 11, 0);

        assertTrue(slot.isCompatible(VehicleType.BICYCLE, start, end));
    }


    // Test Case 27:
    // Verifies that a MICROCAR is compatible with a COMPACT slot.
    @Test
    void testMicrocarWithCompactSlot() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.COMPACT);

        LocalDateTime start = LocalDateTime.of(2026, 9, 28, 10, 0);
        LocalDateTime end = LocalDateTime.of(2026, 9, 28, 11, 0);

        assertTrue(slot.isCompatible(VehicleType.MICROCAR, start, end));
    }


    // Test Case 28:
    // Verifies that a MICROCAR is compatible with a REGULAR slot.
    @Test
    void testMicrocarWithRegularSlot() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.REGULAR);

        LocalDateTime start = LocalDateTime.of(2026, 9, 28, 10, 0);
        LocalDateTime end = LocalDateTime.of(2026, 9, 28, 11, 0);

        assertTrue(slot.isCompatible(VehicleType.MICROCAR, start, end));
    }


    // Test Case 29:
    // Verifies that a MICROCAR is not compatible with a LARGE slot.
    @Test
    void testMicrocarWithLargeSlot() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.LARGE);

        LocalDateTime start = LocalDateTime.of(2026, 9, 28, 10, 0);
        LocalDateTime end = LocalDateTime.of(2026, 9, 28, 11, 0);

        assertFalse(slot.isCompatible(VehicleType.MICROCAR, start, end));
    }


    // Test Case 30:
    // Verifies that a MICROCAR is not compatible with a HANDICAPPED slot.
    @Test
    void testMicrocarWithHandicappedSlot() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.HANDICAPPED);

        LocalDateTime start = LocalDateTime.of(2026, 9, 28, 10, 0);
        LocalDateTime end = LocalDateTime.of(2026, 9, 28, 11, 0);

        assertFalse(slot.isCompatible(VehicleType.MICROCAR, start, end));
    }


    // Test Case 31:
    // Verifies that an inactive slot is considered incompatible with a valid vehicle and time range.
    @Test
    void testInactiveSlotIsNotCompatible() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.REGULAR);

        slot.deactivate();

        LocalDateTime start = LocalDateTime.of(2026, 9, 28, 10, 0);
        LocalDateTime end = LocalDateTime.of(2026, 9, 28, 11, 0);

        assertFalse(slot.isCompatible(VehicleType.CAR, start, end));
    }


    // Test Case 32:
    // Verifies that an inactive slot is not reported as available through isCompatible().
    @Test
    void testInactiveSlotDoesNotBecomeCompatibleAfterValidTimeRange() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.LARGE);

        slot.deactivate();

        LocalDateTime start = LocalDateTime.of(2026, 9, 28, 10, 0);
        LocalDateTime end = LocalDateTime.of(2026, 9, 28, 11, 0);

        assertFalse(slot.isCompatible(VehicleType.BUS, start, end));
    }


    // Test Case 33:
    // Verifies that an empty slot is available when no bookings exist.
    @Test
    void testEmptySlotIsAvailable() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.REGULAR);

        LocalDateTime start = LocalDateTime.of(2026, 9, 28, 10, 0);
        LocalDateTime end = LocalDateTime.of(2026, 9, 28, 11, 0);

        assertTrue(slot.isAvailable(start, end));
    }


    // Test Case 34:
    // Checks the behavior when the requested start and end times are equal.
    // This is a boundary case because the duration is zero.
    @Test
    void testSameStartAndEndTime() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.REGULAR);

        LocalDateTime time = LocalDateTime.of(2026, 9, 28, 10, 0);

        assertTrue(slot.isAvailable(time, time));
    }


    // Test Case 35:
    // Checks the behavior when the requested end time is before the start time.
    // This can reveal missing time-range validation.
    @Test
    void testEndTimeBeforeStartTime() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.REGULAR);

        LocalDateTime start = LocalDateTime.of(2026, 9, 28, 11, 0);
        LocalDateTime end = LocalDateTime.of(2026, 9, 28, 10, 0);

        assertTrue(slot.isAvailable(start, end));
    }


    // Test Case 36:
    // Checks the behavior when the start time is null.
    // This can reveal missing null validation for time inputs.
    @Test
    void testNullStartTime() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.REGULAR);

        LocalDateTime end = LocalDateTime.of(2026, 9, 28, 11, 0);

        assertDoesNotThrow(() -> slot.isAvailable(null, end));
    }


    // Test Case 37:
    // Checks the behavior when the end time is null.
    // This can reveal missing null validation for time inputs.
    @Test
    void testNullEndTime() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.REGULAR);

        LocalDateTime start = LocalDateTime.of(2026, 9, 28, 10, 0);

        assertDoesNotThrow(() -> slot.isAvailable(start, null));
    }


    // Test Case 38:
    // Checks the behavior when a null VehicleType is provided.
    // This can reveal whether the compatibility logic handles invalid vehicle types safely.
    @Test
    void testNullVehicleTypeCompatibility() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.REGULAR);

        LocalDateTime start = LocalDateTime.of(2026, 9, 28, 10, 0);
        LocalDateTime end = LocalDateTime.of(2026, 9, 28, 11, 0);

        assertFalse(slot.isCompatible(null, start, end));
    }


    // Test Case 39:
    // Checks whether a null slot ID is accepted.
    // If slot IDs are required to be non-null, this can reveal missing constructor validation.
    @Test
    void testNullSlotId() {
        ParkingSlot slot =
                new ParkingSlot(null, ParkingSlotType.REGULAR);

        assertNull(slot.getSlotId());
    }


    // Test Case 40:
    // Checks whether a null ParkingSlotType is accepted.
    // If slot types are required, this can reveal missing constructor validation.
    @Test
    void testNullSlotType() {
        ParkingSlot slot =
                new ParkingSlot("S1", null);

        assertNull(slot.getSlotType());
    }


    // Test Case 41:
    // Verifies that changing the Wallet returned by getWallet() changes the slot balance.
    // This checks whether the slot exposes and uses the same Wallet reference.
    @Test
    void testSlotBalanceReflectsWalletChanges() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.REGULAR);

        slot.getWallet().addFunds(100);

        assertEquals(100.0, slot.getBalance());
    }


    // Test Case 42:
    // Verifies that the booking list returned by getBookings() is the same live collection
    // used by the ParkingSlot.
    @Test
    void testBookingsListIsAccessible() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.REGULAR);

        assertSame(slot.getBookings(), slot.getBookings());
    }


    // Test Case 43:
    // Verifies that a slot remains available when the requested time range does not overlap
    // with an existing booking.
    @Test
    void testAvailableWhenThereIsNoOverlap() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.REGULAR);

        Vehicle vehicle =
                new Vehicle(1, VehicleType.CAR, 100);

        LocalDateTime bookingStart =
                LocalDateTime.of(2026, 9, 28, 10, 0);
        LocalDateTime bookingEnd =
                LocalDateTime.of(2026, 9, 28, 11, 0);

        Booking booking =
                new Booking(1, vehicle, slot,
                        bookingStart, bookingEnd, 100);

        slot.getBookings().add(booking);

        LocalDateTime requestedStart =
                LocalDateTime.of(2026, 9, 28, 12, 0);
        LocalDateTime requestedEnd =
                LocalDateTime.of(2026, 9, 28, 13, 0);

        assertTrue(slot.isAvailable(requestedStart, requestedEnd));
    }


    // Test Case 44:
    // Verifies that a slot is unavailable when the requested period is completely
    // inside an existing booking.
    @Test
    void testUnavailableWhenRequestedPeriodIsInsideExistingBooking() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.REGULAR);

        Vehicle vehicle =
                new Vehicle(1, VehicleType.CAR, 100);

        Booking booking =
                new Booking(
                        1,
                        vehicle,
                        slot,
                        LocalDateTime.of(2026, 9, 28, 10, 0),
                        LocalDateTime.of(2026, 9, 28, 14, 0),
                        100
                );

        slot.getBookings().add(booking);

        LocalDateTime requestedStart =
                LocalDateTime.of(2026, 9, 28, 11, 0);
        LocalDateTime requestedEnd =
                LocalDateTime.of(2026, 9, 28, 12, 0);

        assertFalse(slot.isAvailable(requestedStart, requestedEnd));
    }


    // Test Case 45:
    // Verifies that a slot is unavailable when an existing booking is completely
    // inside the requested period.
    @Test
    void testUnavailableWhenExistingBookingIsInsideRequestedPeriod() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.REGULAR);

        Vehicle vehicle =
                new Vehicle(1, VehicleType.CAR, 100);

        Booking booking =
                new Booking(
                        1,
                        vehicle,
                        slot,
                        LocalDateTime.of(2026, 9, 28, 11, 0),
                        LocalDateTime.of(2026, 9, 28, 12, 0),
                        100
                );

        slot.getBookings().add(booking);

        LocalDateTime requestedStart =
                LocalDateTime.of(2026, 9, 28, 10, 0);
        LocalDateTime requestedEnd =
                LocalDateTime.of(2026, 9, 28, 13, 0);

        assertFalse(slot.isAvailable(requestedStart, requestedEnd));
    }


    // Test Case 46:
    // Verifies that a partial overlap at the beginning of an existing booking
    // makes the slot unavailable.
    @Test
    void testUnavailableWithBeginningOverlap() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.REGULAR);

        Vehicle vehicle =
                new Vehicle(1, VehicleType.CAR, 100);

        Booking booking =
                new Booking(
                        1,
                        vehicle,
                        slot,
                        LocalDateTime.of(2026, 9, 28, 11, 0),
                        LocalDateTime.of(2026, 9, 28, 13, 0),
                        100
                );

        slot.getBookings().add(booking);

        LocalDateTime requestedStart =
                LocalDateTime.of(2026, 9, 28, 10, 0);
        LocalDateTime requestedEnd =
                LocalDateTime.of(2026, 9, 28, 12, 0);

        assertFalse(slot.isAvailable(requestedStart, requestedEnd));
    }


    // Test Case 47:
    // Verifies that a partial overlap at the end of an existing booking
    // makes the slot unavailable.
    @Test
    void testUnavailableWithEndingOverlap() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.REGULAR);

        Vehicle vehicle =
                new Vehicle(1, VehicleType.CAR, 100);

        Booking booking =
                new Booking(
                        1,
                        vehicle,
                        slot,
                        LocalDateTime.of(2026, 9, 28, 10, 0),
                        LocalDateTime.of(2026, 9, 28, 12, 0),
                        100
                );

        slot.getBookings().add(booking);

        LocalDateTime requestedStart =
                LocalDateTime.of(2026, 9, 28, 11, 0);
        LocalDateTime requestedEnd =
                LocalDateTime.of(2026, 9, 28, 13, 0);

        assertFalse(slot.isAvailable(requestedStart, requestedEnd));
    }


    // Test Case 48:
    // Verifies that exactly matching start and end times are considered an overlap.
    @Test
    void testUnavailableWithExactSameTimeRange() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.REGULAR);

        Vehicle vehicle =
                new Vehicle(1, VehicleType.CAR, 100);

        Booking booking =
                new Booking(
                        1,
                        vehicle,
                        slot,
                        LocalDateTime.of(2026, 9, 28, 10, 0),
                        LocalDateTime.of(2026, 9, 28, 12, 0),
                        100
                );

        slot.getBookings().add(booking);

        LocalDateTime requestedStart =
                LocalDateTime.of(2026, 9, 28, 10, 0);
        LocalDateTime requestedEnd =
                LocalDateTime.of(2026, 9, 28, 12, 0);

        assertFalse(slot.isAvailable(requestedStart, requestedEnd));
    }


    // Test Case 49:
    // Verifies that a new booking is allowed when its start time is exactly
    // equal to the previous booking's end time.
    // This checks the boundary condition for back-to-back bookings.
    @Test
    void testAvailableWhenNewBookingStartsAtExistingBookingEnd() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.REGULAR);

        Vehicle vehicle =
                new Vehicle(1, VehicleType.CAR, 100);

        Booking booking =
                new Booking(
                        1,
                        vehicle,
                        slot,
                        LocalDateTime.of(2026, 9, 28, 10, 0),
                        LocalDateTime.of(2026, 9, 28, 12, 0),
                        100
                );

        slot.getBookings().add(booking);

        LocalDateTime requestedStart =
                LocalDateTime.of(2026, 9, 28, 12, 0);
        LocalDateTime requestedEnd =
                LocalDateTime.of(2026, 9, 28, 14, 0);

        assertTrue(slot.isAvailable(requestedStart, requestedEnd));
    }


    // Test Case 50:
    // Verifies that a new booking is allowed when its end time is exactly
    // equal to the existing booking's start time.
    @Test
    void testAvailableWhenNewBookingEndsAtExistingBookingStart() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.REGULAR);

        Vehicle vehicle =
                new Vehicle(1, VehicleType.CAR, 100);

        Booking booking =
                new Booking(
                        1,
                        vehicle,
                        slot,
                        LocalDateTime.of(2026, 9, 28, 12, 0),
                        LocalDateTime.of(2026, 9, 28, 14, 0),
                        100
                );

        slot.getBookings().add(booking);

        LocalDateTime requestedStart =
                LocalDateTime.of(2026, 9, 28, 10, 0);
        LocalDateTime requestedEnd =
                LocalDateTime.of(2026, 9, 28, 12, 0);

        assertTrue(slot.isAvailable(requestedStart, requestedEnd));
    }


    // Test Case 51:
    // Verifies that multiple non-overlapping bookings do not make the slot unavailable
    // for a free time period.
    @Test
    void testAvailableWithMultipleNonOverlappingBookings() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.REGULAR);

        Vehicle vehicle =
                new Vehicle(1, VehicleType.CAR, 100);

        Booking booking1 =
                new Booking(
                        1, vehicle, slot,
                        LocalDateTime.of(2026, 9, 28, 8, 0),
                        LocalDateTime.of(2026, 9, 28, 9, 0),
                        100
                );

        Booking booking2 =
                new Booking(
                        2, vehicle, slot,
                        LocalDateTime.of(2026, 9, 28, 14, 0),
                        LocalDateTime.of(2026, 9, 28, 15, 0),
                        100
                );

        slot.getBookings().add(booking1);
        slot.getBookings().add(booking2);

        LocalDateTime requestedStart =
                LocalDateTime.of(2026, 9, 28, 10, 0);
        LocalDateTime requestedEnd =
                LocalDateTime.of(2026, 9, 28, 11, 0);

        assertTrue(slot.isAvailable(requestedStart, requestedEnd));
    }


    // Test Case 52:
    // Verifies that one overlapping booking makes the slot unavailable
    // even when other bookings do not overlap.
    @Test
    void testUnavailableWithMultipleBookingsAndOneOverlap() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.REGULAR);

        Vehicle vehicle =
                new Vehicle(1, VehicleType.CAR, 100);

        Booking booking1 =
                new Booking(
                        1, vehicle, slot,
                        LocalDateTime.of(2026, 9, 28, 8, 0),
                        LocalDateTime.of(2026, 9, 28, 9, 0),
                        100
                );

        Booking booking2 =
                new Booking(
                        2, vehicle, slot,
                        LocalDateTime.of(2026, 9, 28, 11, 0),
                        LocalDateTime.of(2026, 9, 28, 13, 0),
                        100
                );

        slot.getBookings().add(booking1);
        slot.getBookings().add(booking2);

        LocalDateTime requestedStart =
                LocalDateTime.of(2026, 9, 28, 12, 0);
        LocalDateTime requestedEnd =
                LocalDateTime.of(2026, 9, 28, 14, 0);

        assertFalse(slot.isAvailable(requestedStart, requestedEnd));
    }


    // Test Case 53:
    // Checks whether a COMPLETED booking still blocks the slot.
    // This test helps determine whether availability incorrectly considers
    // completed bookings as active reservations.
    @Test
    void testCompletedBookingAvailabilityBehavior() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.REGULAR);

        Vehicle vehicle =
                new Vehicle(1, VehicleType.CAR, 100);

        Booking booking =
                new Booking(
                        1, vehicle, slot,
                        LocalDateTime.of(2026, 9, 28, 10, 0),
                        LocalDateTime.of(2026, 9, 28, 12, 0),
                        100
                );

        booking.completeBooking();
        slot.getBookings().add(booking);

        LocalDateTime requestedStart =
                LocalDateTime.of(2026, 9, 28, 11, 0);
        LocalDateTime requestedEnd =
                LocalDateTime.of(2026, 9, 28, 13, 0);

        assertFalse(slot.isAvailable(requestedStart, requestedEnd));
    }


    // Test Case 54:
    // Checks whether a CANCELLED booking still blocks the slot.
    // This can reveal whether cancelled bookings are incorrectly included in availability checks.
    @Test
    void testCancelledBookingAvailabilityBehavior() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.REGULAR);

        Vehicle vehicle =
                new Vehicle(1, VehicleType.CAR, 100);

        Booking booking =
                new Booking(
                        1, vehicle, slot,
                        LocalDateTime.of(2026, 9, 28, 10, 0),
                        LocalDateTime.of(2026, 9, 28, 12, 0),
                        100
                );

        booking.cancelBooking();
        slot.getBookings().add(booking);

        LocalDateTime requestedStart =
                LocalDateTime.of(2026, 9, 28, 11, 0);
        LocalDateTime requestedEnd =
                LocalDateTime.of(2026, 9, 28, 13, 0);

        assertFalse(slot.isAvailable(requestedStart, requestedEnd));
    }


    // Test Case 55:
    // Checks whether a zero-duration existing booking affects availability.
    // This is a boundary case for startTime == endTime.
    @Test
    void testZeroDurationBookingAvailabilityBehavior() {
        ParkingSlot slot =
                new ParkingSlot("S1", ParkingSlotType.REGULAR);

        Vehicle vehicle =
                new Vehicle(1, VehicleType.CAR, 100);

        LocalDateTime time =
                LocalDateTime.of(2026, 9, 28, 10, 0);

        Booking booking =
                new Booking(
                        1, vehicle, slot,
                        time, time, 100
                );

        slot.getBookings().add(booking);

        LocalDateTime requestedStart =
                LocalDateTime.of(2026, 9, 28, 10, 0);
        LocalDateTime requestedEnd =
                LocalDateTime.of(2026, 9, 28, 11, 0);

        assertTrue(slot.isAvailable(requestedStart, requestedEnd));
    }
}
