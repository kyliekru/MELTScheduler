package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class InstructorTest {
    private Instructor instructorOne;
    private Instructor instructorTwo;

    @BeforeEach
    void runBefore() {
        instructorOne = new Instructor("Bonnie", 1, 1);
        instructorTwo = new Instructor("Greg", 2, 2);
    }

    @Test
    void instructorTest() {
        assertEquals("Bonnie", instructorOne.getName());
        assertEquals("Greg", instructorTwo.getName());

        assertEquals(1, instructorOne.getClassPriority());
        assertEquals(2, instructorTwo.getClassPriority());

        assertEquals(1, instructorOne.getTotalSlots());
        assertEquals(2, instructorTwo.getTotalSlots());

        assertEquals(1, instructorOne.getAvailableSlots());
        assertEquals(2, instructorTwo.getAvailableSlots());

        assertEquals(LocalTime.of(8, 0), instructorOne.getFirstStartTime());
        assertEquals(LocalTime.of(13, 0), instructorOne.getSecondStartTime());

        assertTrue(instructorOne.getUnavailableDays().isEmpty());
        instructorOne.changePriority(3);
        assertEquals(3, instructorOne.getClassPriority());
    }

    @Test
    void useSlotTest() {
        instructorOne.useSlot();
        assertEquals(0, instructorOne.getAvailableSlots());
        assertEquals(1, instructorOne.getTotalSlots());

        instructorOne.resetSlots();
        assertEquals(1, instructorOne.getAvailableSlots());
    }

    @Test
    void instructorUnavailableTest() {
        assertTrue(instructorOne.isAvailable());
        instructorOne.instructorUnavailable();
        assertFalse(instructorOne.isAvailable());
        instructorOne.instructorAvailable();
        assertTrue(instructorOne.isAvailable());

        ArrayList<LocalDate> days = new ArrayList<>();
        days.add(LocalDate.of(2024, 2, 2));
        days.add(LocalDate.of(2023, 9, 23));
        instructorOne.instructorUnavailable(days);
        assertEquals(2, instructorOne.getUnavailableDays().size());
        instructorOne.instructorUnavailable(LocalDate.of(2023, 12, 2));
        assertEquals(3, instructorOne.getUnavailableDays().size());

        instructorOne.instructorAvailable(LocalDate.of(2023, 12, 2));
        assertEquals(2, instructorOne.getUnavailableDays().size());
    }

}
