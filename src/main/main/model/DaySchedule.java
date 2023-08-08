package model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class DaySchedule {
    private int trucks;
    private ArrayList<Instructor> freeInstructors;
    private ArrayList<Student> freeStudents;
    private ArrayList<Student> classStudents;
    private ArrayList<Student> yardStudents;
    private ArrayList<Student> roadStudents;

    private ArrayList<Instructor> unavailableInstructors = new ArrayList<>();
    private ArrayList<Student> unavailableStudents = new ArrayList<>();

    private HashMap<Instructor, ArrayList<Student>> classMap;
    private HashMap<Instructor, ArrayList<Student>> yardMap;
    private HashMap<Instructor, Student> roadMap;
    private HashMap<LocalTime, HashMap<Instructor, ArrayList<Student>>> classSchedule;
    private HashMap<LocalTime, HashMap<Instructor, ArrayList<Student>>> yardSchedule;
    private HashMap<LocalTime, HashMap<Instructor, ArrayList<Student>>> roadSchedule;
    private Date date;
    private boolean yardTruckScheduled = false;
    private int numInstructors;
    private boolean classInstructorScheduled = false;
    private int numTruckInstructors = 0;

    public DaySchedule(ArrayList<Instructor> instructors, ArrayList<Student> students, Date date, int trucks) {
        freeInstructors = instructors;
        numInstructors = instructors.size();
        freeStudents = students;
        this.trucks = trucks;
        this.date = date;

        classSchedule = new HashMap<>();
        yardSchedule = new HashMap<>();
        roadSchedule = new HashMap<>();
        classMap = new HashMap<>();
        yardMap = new HashMap<>();
        roadMap = new HashMap<>();
        createSchedule();
    }

    public void createSchedule() {
        for (Instructor instructor : freeInstructors) {
            if (!instructor.isAvailable()) {
                unavailableInstructors.add(instructor);
                freeInstructors.remove(instructor);
            }
        }
        for (Student student : freeStudents) {
            if (!student.isAvailable()) {
                unavailableStudents.add(student);
                freeStudents.remove(student);
            }
        }
        dsitributeStudents();
        distributeInstructors();
    }

    private void dsitributeStudents() {

        for (Student student : freeStudents) {
            int classHours = student.getClassHoursLeft();
            int roadHours = student.getRoadHoursLeft();
            int yardHours = student.getYardHoursLeft();
            if (student.getIntroClassDaysLeft() > 0 && checkClassInstructor()) {
                classStudents.add(student);
                studentUnavailable(student);
                student.introDayComplete();
            } else if (classHours >= roadHours && classHours >= yardHours && checkClassInstructor()) {
                classStudents.add(student);
                studentUnavailable(student);
            } else if (roadHours >= classHours && roadHours >= yardHours && trucks > 0 && checkTruckInstructor()) {
                roadStudents.add(student);
                studentUnavailable(student);
                trucks =- 1;
            } else if (yardHours >= classHours && yardHours >= roadHours && checkYardTruck()){
                yardStudents.add(student);
                studentUnavailable(student);
            }
        }
        if (!freeStudents.isEmpty()) {
            classStudents.addAll(freeStudents);
            unavailableStudents.addAll(freeStudents);
            freeStudents.removeAll(freeStudents);
        }
    }

    private boolean checkTruckInstructor() {
        if (numInstructors > 0 && trucks > 0) {
            numInstructors =- 1;
            numTruckInstructors += 1;
            return true;
        } else {
            return false;
        }
    }

    private boolean checkClassInstructor() {
        if (classInstructorScheduled) {
            return true;
        } else if (numInstructors > 0) {
            numInstructors =- 1;
            classInstructorScheduled = true;
            return true;
        } else {
            return false;
        }
    }

    private boolean checkYardTruck() {
        if (yardTruckScheduled) {
            return true;
        } else if (trucks > 0) {
            trucks =- 1;
            yardTruckScheduled = true;
            return true;
        }
        else {
            return false;
        }
    }

    private void studentUnavailable(Student student) {
        unavailableStudents.add(student);
        freeStudents.remove(student);
    }

    private void distributeInstructors() {
        boolean running = true;
        if (!classStudents.isEmpty()) {
            while (running) {
                Instructor highestPriority = freeInstructors.get(0);
                for (Instructor instructor : freeInstructors) {
                    if (highestPriority.getClassPriority() > instructor.getClassPriority()) {
                        highestPriority = instructor;
                    }
                }
                if (highestPriority.getTotalSlots() == highestPriority.getAvailableSlots()) {
                    classMap.put(highestPriority, classStudents);
                    classSchedule.put(highestPriority.getFirstStartTime(), classMap);
                    useInstructorSlot(highestPriority);
                    running = false;
                } else if (anyAvailableSlots(highestPriority)) {
                    classMap.put(highestPriority, classStudents);
                    classSchedule.put(highestPriority.getSecondStartTime(), classMap);
                    useInstructorSlot(highestPriority);
                    running = false;
                } else {
                    instructorUnavailable(highestPriority);
                }
            }
        }
        distributeRoadInstructors();
    }

    private void useInstructorSlot(Instructor instructor) {
        instructor.useSlot();
        if (!anyAvailableSlots(instructor)) {
            instructorUnavailable(instructor);
        }
    }

    private void distributeRoadInstructors() {
        for (Instructor instructor : freeInstructors) {
            for (Student student : roadStudents) {
                roadMap.put(instructor, student);
                useInstructorSlot(instructor);
            }
        }
        if (!freeInstructors.isEmpty()) {
            for (Instructor instructor : freeInstructors) {
                yardMap.put(instructor, yardStudents);
                useInstructorSlot(instructor);
            }
        }
    }

    private void instructorUnavailable(Instructor highestPriority) {
        unavailableInstructors.add(highestPriority);
        freeInstructors.remove(highestPriority);
    }

    private boolean anyAvailableSlots(Instructor highestPriority) {
        return highestPriority.getAvailableSlots() > 0;
    }

}
