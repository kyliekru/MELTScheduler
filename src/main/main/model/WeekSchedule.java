package model;

import java.time.LocalDate;
import java.util.HashMap;

public class WeekSchedule {

    private HashMap<LocalDate, DaySchedule> weekSchedule;

    public WeekSchedule() {
        weekSchedule = new HashMap<>();
    }

    public void addDaySched(DaySchedule daySched) {
        weekSchedule.put(daySched.getDate(), daySched);
    }


}
