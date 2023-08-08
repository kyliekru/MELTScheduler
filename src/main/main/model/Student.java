package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

// Student with a name, id, hours left for the classroom, yard and road,
// a boolean for availability and a list of scheduled unavailable days
public class Student {
    private static int nextId = 1;
    private int id;
    private String name;
    private int classHoursLeft;
    private int yardHoursLeft;
    private int roadHoursLeft;
    private Boolean available = true;
    private ArrayList<LocalDate> unavailableDays;
    private LocalDate roadTestDate;
    private int introClassDaysLeft = 3;

    public Student(String name) {
        this.name = name;
        this.id = nextId++;
        this.classHoursLeft = 50;
        this.yardHoursLeft = 40;
        this.roadHoursLeft = 50;
        unavailableDays = new ArrayList<>();

    }

    public String getName() {
        return this.name;
    }

    public LocalDate getRoadTestDate() {
        return this.roadTestDate;
    }

    public int getId() {
        return id;
    }
    public int getClassHoursLeft() {
        return this.classHoursLeft;
    }
    public int getYardHoursLeft() {
        return this.yardHoursLeft;
    }
    public int getRoadHoursLeft() {
        return this.roadHoursLeft;
    }

    public boolean isAvailable() {
        return this.available;
    }

    public int getIntroClassDaysLeft() {
        return introClassDaysLeft;
    }

    public ArrayList<LocalDate> getUnavailableDays() {
        return unavailableDays;
    }

    public void introDayComplete() {
        introClassDaysLeft -= 1;
        classLesson();
    }

    public void setRoadTestDate(LocalDate date) {
        roadTestDate = date;
    }


    //MODIFIES: classHoursLeft
    //EFFECTS: subtract 4 hours (lesson time) from hours left
    public void classLesson() {
        this.classHoursLeft = classHoursLeft - 4;
        this.available = true;
    }

    //MODIFIES: classHoursLeft
    //EFFECTS: subtracts given lesson time hours from hours left
    public void classLesson(int hours) {
        this.classHoursLeft = classHoursLeft - hours;
        this.available = true;
    }

    //MODIFIES: yardHoursLeft
    //EFFECTS: subtract 4 hours (lesson time) from hours left
    public void yardLesson() {
        this.yardHoursLeft = yardHoursLeft - 4;
        this.available = true;
    }

    //MODIFIES: yardHoursLeft
    //EFFECTS: subtract 4 hours (lesson time) from hours left
    public void yardLesson(int hours) {
        this.yardHoursLeft = yardHoursLeft - hours;
        this.available = true;
    }

    //MODIFIES: roadHoursLeft
    //EFFECTS: subtract 4 hours (lesson time) from hours left
    public void roadLesson() {
        this.roadHoursLeft = roadHoursLeft - 4;
        this.available = true;
    }

    //MODIFIES: roadHoursLeft
    //EFFECTS: subtract 4 hours (lesson time) from hours left
    public void roadLesson(int hours) {
        this.roadHoursLeft = roadHoursLeft - hours;
        this.available = true;
    }

    //MODIFIES: available
    //EFFECTS: sets student availability to false for the day
    public void studentUnavailable() {
        this.available = false;
    }

    //MODIFIES: unavailableDays
    //EFFECTS: adds list of dates to unavailable date list
    public void studentUnavailable(ArrayList<LocalDate> dateList) {
        unavailableDays.addAll(dateList);
    }

    //MODIFIES: unavailableDays
    //EFFECTS: adds unavailable date to list of unavailable days
    public void studentUnavailable(LocalDate date) {
        unavailableDays.add(date);
    }

    //EFFECTS: returns total number of hours left
    public int addTotalHours() {
        return classHoursLeft + yardHoursLeft + roadHoursLeft;
    }


}
