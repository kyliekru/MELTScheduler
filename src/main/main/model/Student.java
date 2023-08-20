package model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.util.ArrayList;

// Student with a name, id, hours left for the classroom, yard and road,
// a boolean for availability and a list of scheduled unavailable days
public class Student {
    private static int nextId = 1;
    private int id;
    private SimpleStringProperty name;
    private int classHoursLeft;
    private int yardHoursLeft;
    private int roadHoursLeft;

    private int flexHoursLeft;
    private Boolean available = true;
    private SimpleObjectProperty<ArrayList<LocalDate>> unavailableDays;
    private SimpleObjectProperty<LocalDate> roadTestDate;
    private int introClassDaysLeft;

    public Student(String name) {
        this.name = new SimpleStringProperty(name);
        this.id = nextId++;
        this.classHoursLeft = 50;
        this.yardHoursLeft = 40;
        this.roadHoursLeft = 50;
        this.flexHoursLeft = 6;
        unavailableDays = new SimpleObjectProperty<>(new ArrayList<>());
        introClassDaysLeft = 3;

    }

    public String getName() {
        return name.get();
    }

    public LocalDate getRoadTestDate() {
        return roadTestDate.get();
    }

    public int getFlexHoursLeft() {
        return this.flexHoursLeft;
    }

    public void flexLesson(int hours) {
        this.flexHoursLeft -= hours;
        this.available = true;
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
        return unavailableDays.get();
    }

    public void introDayComplete() {
        introClassDaysLeft -= 1;
        classLesson();
    }

    public void setRoadTestDate(LocalDate date) {
        roadTestDate = new SimpleObjectProperty<>(date);
    }

    public void setClassHoursLeft(int hours) {
        classHoursLeft = hours;
    }

    public void setYardHoursLeft(int hours) {
        yardHoursLeft = hours;
    }

    public void setRoadHoursLeft(int hours) {
        roadHoursLeft = hours;
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

    public void undoRoadLesson(int hours) {
        this.roadHoursLeft += hours;
    }

    public void undoClassLesson(int hours) {
        this.classHoursLeft += hours;
    }

    public void undoYardLesson(int hours) {
        this.yardHoursLeft += hours;
    }


    //MODIFIES: available
    //EFFECTS: sets student availability to false for the day
    public void studentUnavailable() {
        this.available = false;
    }

    //MODIFIES: unavailableDays
    //EFFECTS: adds list of dates to unavailable date list
    public void studentUnavailable(ArrayList<LocalDate> dateList) {
        unavailableDays.get().addAll(dateList);
    }

    //MODIFIES: unavailableDays
    //EFFECTS: adds unavailable date to list of unavailable days
    public void studentUnavailable(LocalDate date) {
        unavailableDays.get().add(date);
    }

    public boolean checkAvailabiltyForDay(LocalDate date) {
        for (LocalDate unavailableDay: unavailableDays.get()) {
            if (unavailableDay.getDayOfYear() == date.getDayOfYear()) {
                return false;
            }
        }
        return true;
    }

    //EFFECTS: returns total number of hours left
    public int addTotalHours() {
        return classHoursLeft + yardHoursLeft + roadHoursLeft;
    }


}
