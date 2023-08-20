package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

public class DayScheduleTest {
    private Instructor instructorOne;
    private Instructor instructorTwo;

    private Instructor instructorThree;
    private Student stuOne;
    private Student stuTwo;
    private Student stuThree;
    private Student stuFour;

    private Student stuFive;
    private Student stuSix;

    private DaySchedule dayOne;
    private DaySchedule dayTwo;
    ArrayList<Student> students = new ArrayList<>();
    ArrayList<Instructor> instructors = new ArrayList<>();

    @BeforeEach
    void runBefore() {
        instructorOne = new Instructor("Bonnie", 1, 1);
        instructorTwo = new Instructor("Greg", 2, 2);
        instructorThree = new Instructor("Jake", 2, 2);

        stuOne = new Student("Jane");
        stuTwo = new Student("Mark");
        stuThree = new Student("Jake");
        stuFour = new Student("Ari");
        stuFive = new Student("Ella");
        stuSix = new Student("Bell");
        students.add(stuOne);
        students.add(stuTwo);
        students.add(stuThree);
        students.add(stuFour);
        students.add(stuFive);
        students.add(stuSix);

        instructors.add(instructorOne);
        instructors.add(instructorTwo);
        instructors.add(instructorThree);
    }

    @Test
    void dayScheduleTestNormalHours() {

        dayOne = new DaySchedule(instructors, students, LocalDate.of(2023, 8, 9), 2);
        System.out.println("Day One");
        System.out.println(dayOne.getClassSchedule());
        System.out.println(dayOne.getYardSchedule());
        System.out.println(dayOne.getRoadSchedule());
    }

    @Test
    void dayScheduleTestChangedHours() {
        stuOne.setRoadHoursLeft(70);
        stuTwo.setRoadHoursLeft(65);
        stuThree.setRoadHoursLeft(67);
        stuTwo.setYardHoursLeft(64);
        stuFour.setYardHoursLeft(80);
        for (Student student : students) {
            student.introDayComplete();
            student.introDayComplete();
            student.introDayComplete();
        }

        dayTwo = new DaySchedule(instructors, students, LocalDate.of(2023, 8 ,10), 2);
        System.out.println("Day Two");
        System.out.println(dayTwo.getClassSchedule());
        System.out.println(dayTwo.getYardSchedule());
        System.out.println(dayTwo.getRoadSchedule());
    }
}
