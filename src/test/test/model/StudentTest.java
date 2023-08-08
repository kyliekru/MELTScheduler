package model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {
    private Student studentOne = new Student("Jane");
    private Student studentTwo = new Student("Mark");
    private Student studentThree = new Student ("Bell");



    @Test
    void studentTest() {
        assertEquals("Jane", studentOne.getName());
        assertEquals(1, studentOne.getId());
        assertEquals("Mark", studentTwo.getName());
        assertEquals(2, studentTwo.getId());

    }

    @Test
    void introDayCompleteTest() {
        assertEquals(3, studentTwo.getIntroClassDaysLeft());
        studentTwo.introDayComplete();
        assertEquals(2, studentTwo.getIntroClassDaysLeft());
        studentTwo.introDayComplete();
        assertEquals(1, studentTwo.getIntroClassDaysLeft());
        studentTwo.introDayComplete();
        assertEquals(0, studentTwo.getIntroClassDaysLeft());

    }

    @Test
    void setRoadTestDateTest() {
        studentOne.setRoadTestDate(LocalDate.of(2023, 9, 11));
        assertEquals(LocalDate.of(2023, 9, 11), studentOne.getRoadTestDate());
    }

    @Test
    void classLessonTest() {
        assertEquals(50, studentOne.getClassHoursLeft());
        studentOne.classLesson();
        assertEquals(46, studentOne.getClassHoursLeft());
        studentOne.classLesson(2);
        assertEquals(44, studentOne.getClassHoursLeft());
    }

    @Test
    void yardLessonTest() {
        assertEquals(40, studentThree.getYardHoursLeft());
        studentThree.yardLesson();
        assertEquals(36, studentThree.getYardHoursLeft());
        studentThree.yardLesson(1);
        assertEquals(35, studentThree.getYardHoursLeft());
    }

    @Test
    void roadLessonTest() {
        assertEquals(50, studentOne.getRoadHoursLeft());
        studentOne.roadLesson();
        assertEquals(46, studentOne.getRoadHoursLeft());
        studentOne.roadLesson(5);
        assertEquals(41, studentOne.getRoadHoursLeft());
    }

    @Test
    void setAvailableTest() {
        assertTrue(studentOne.isAvailable());
        studentOne.studentUnavailable();
        assertFalse(studentOne.isAvailable());
    }

    @Test
    void unavailableDaysTest() {
        studentTwo.studentUnavailable(LocalDate.of(2023, 9, 14));
        ArrayList<LocalDate> dateList = new ArrayList<>();
        dateList.add(LocalDate.of(2023, 9, 14));
        assertEquals(dateList, studentTwo.getUnavailableDays());
        ArrayList<LocalDate> secondList = new ArrayList<>();
        secondList.add(LocalDate.of(2024, 2, 2));
        secondList.add(LocalDate.of(2023, 12, 5));
        secondList.add(dateList.get(0));

        studentThree.studentUnavailable(secondList);
        assertEquals(secondList, studentThree.getUnavailableDays());

    }

    @Test
    void addTotalHoursLeftTest() {
        studentOne.classLesson();
        studentOne.classLesson(2);
        studentTwo.roadLesson();
        assertEquals(134, studentOne.addTotalHours());
        assertEquals(136, studentTwo.addTotalHours());
        assertEquals(140, studentThree.addTotalHours());
    }


}
