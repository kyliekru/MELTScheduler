package model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class Instructor {

    private String name;
    private Boolean available = true;
    private ArrayList<Date> unavailableDays;
    private int classPriority;
    private LocalTime firstStartTime;
    private LocalTime secondStartTime;
    private int availableSlots = 2;
    private int totalSlots = 2;

    public Instructor(String name, int classPriority, int totalSlots) {
        this.name = name;
        this.classPriority = classPriority;
        unavailableDays = new ArrayList<>();
        this.firstStartTime = LocalTime.of(8, 0);
        this.secondStartTime = LocalTime.of(13, 0);
        this.totalSlots = totalSlots;
    }

    public String getName() {
        return this.name;
    }

    public int getClassPriority() {
        return this.classPriority;
    }

    public Boolean isAvailable() {
        return this.available;
    }

    public ArrayList<Date> getUnavailableDays() {
        return this.unavailableDays;
    }

    public int getTotalSlots() {
        return this.totalSlots;
    }

    public int getAvailableSlots() {
        return this.availableSlots;
    }

    public LocalTime getFirstStartTime() {
        return this.firstStartTime;
    }

    public LocalTime getSecondStartTime() {
        return this.secondStartTime;
    }
    public void setTotalSlots(int slots) {
        totalSlots = slots;
    }

    public void resetSlot() {
        availableSlots = totalSlots;
    }

    public void useSlot() {
        availableSlots =- 1;
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
    public void instructorUnavailable(ArrayList<Date> dates) {
        unavailableDays.addAll(dates);
    }

    //MODIFIES: unavailableDays
    //EFFECTS: adds unavailable day to list of unavailableDays
    public void instructorUnavailable(Date date) {
        unavailableDays.add(date);
    }

    //MODIFIES: classPriority
    //EFFECTS: changes classPriority to given number
    public void changePriority(int priority) {
        this.classPriority = priority;
    }

}
