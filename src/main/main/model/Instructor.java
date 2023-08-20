package model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Instructor {

    private SimpleStringProperty name;
    private Boolean available = true;
    private SimpleObjectProperty<ArrayList<LocalDate>> unavailableDays;
    private int classPriority;
    private SimpleObjectProperty<LocalTime> firstStartTime;
    private SimpleObjectProperty<LocalTime> secondStartTime;
    private int availableSlots;
    private int totalSlots;

    public Instructor(String name, int classPriority, int totalSlots) {
        this.name = new SimpleStringProperty(name);
        this.classPriority = classPriority;
        unavailableDays = new SimpleObjectProperty<>(new ArrayList<>());
        this.firstStartTime = new SimpleObjectProperty<>(LocalTime.of(8, 0));
        this.secondStartTime = new SimpleObjectProperty<>(LocalTime.of(13, 0));
        this.totalSlots = totalSlots;
        this.availableSlots = totalSlots;
    }

    //GETTERS
    public String getName() {
        return this.name.get();
    }

    public int getClassPriority() {
        return this.classPriority;
    }

    public Boolean isAvailable() {
        return this.available;
    }

    public ArrayList<LocalDate> getUnavailableDays() {
        return this.unavailableDays.get();
    }

    public int getTotalSlots() {
        return this.totalSlots;
    }

    public int getAvailableSlots() {
        return this.availableSlots;
    }

    public LocalTime getFirstStartTime() {
        return this.firstStartTime.get();
    }

    public LocalTime getSecondStartTime() {
        return this.secondStartTime.get();
    }

    //SETTERS
    public void setName(String name) {
        this.name.set(name);
    }

    public void setClassPriority(int priority) {
        this.classPriority = priority;
    }

    public void setFirstStartTime(LocalTime time) {
        this.firstStartTime.set(time);
    }

    public void setSecondStartTime(LocalTime time) {
        this.secondStartTime.set(time);
    }
    public void setTotalSlots(int slots) {
        totalSlots = slots;
    }

    //MODIFIES: availableSlots
    //EFFECTS: sets availableSlots to totalSlots value
    public void resetSlots() {
        availableSlots = totalSlots;
    }

    //MODIFIES: availableSlots
    //EFFECTS: subtracts one from availableSlots
    public void useSlot() {
        availableSlots -= 1;
    }
    //MODIFIES: available
    //EFFECTS: sets availability to false
    public void instructorUnavailable() {
        this.available = false;
    }

    //MODIFIES: available
    //EFFECTS: sets availability to true
    public void instructorAvailable() {
        this.available = true;
    }

    //MODIFIES: unavailableDays
    //EFFECTS: adds list of unavailable days to unavailableDays list
    public void instructorUnavailable(ArrayList<LocalDate> dates) {
        unavailableDays.get().addAll(dates);
    }

    //MODIFIES: unavailableDays
    //EFFECTS: adds unavailable day to list of unavailableDays
    public void instructorUnavailable(LocalDate date) {
        unavailableDays.get().add(date);
    }

    //MODIFIES: unavailableDays
    //EFFECTS: removes given date from unavailableDays list
    public void instructorAvailable(LocalDate date) {
        unavailableDays.get().remove(date);
    }

    public boolean checkAvailabilityForDay(LocalDate date) {
        for (LocalDate unavailableDay: unavailableDays.get()) {
            if (unavailableDay.getDayOfYear() == date.getDayOfYear()) {
                return false;
            }
        }
        return true;
    }



    //MODIFIES: classPriority
    //EFFECTS: changes classPriority to given number
    public void changePriority(int priority) {
        this.classPriority = priority;
    }

}
