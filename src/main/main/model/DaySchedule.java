package model;

import javafx.util.Pair;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public class DaySchedule {
    private int trucks;
    private ArrayList<Instructor> freeInstructors;
    private ArrayList<Student> freeStudents;
    private ArrayList<Student> classStudents = new ArrayList<>();
    private ArrayList<Student> yardStudents = new ArrayList<>();
    private ArrayList<Student> roadStudents = new ArrayList<>();

    private ArrayList<Instructor> unavailableInstructors = new ArrayList<>();
    private ArrayList<Student> unavailableStudents = new ArrayList<>();

    private HashMap<Instructor, ArrayList<Student>> classMap;
    private HashMap<Instructor, ArrayList<Student>> yardMap;
    private HashMap<LocalTime, HashMap<Instructor, ArrayList<Student>>> classSchedule;
    private HashMap<LocalTime, HashMap<Instructor, ArrayList<Student>>> yardSchedule;
    private HashMap<LocalTime, Pair<Instructor, Student>> roadSchedule;
    private LocalDate date;
    private boolean yardTruckScheduled = false;
    private int numInstructors;
    private boolean classInstructorScheduled = false;
    private int numTruckInstructors = 0;

    public DaySchedule(ArrayList<Instructor> instructors, ArrayList<Student> students, LocalDate date, int trucks) {
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
        createSchedule();
    }

    public HashMap<LocalTime, HashMap<Instructor, ArrayList<Student>>> getClassSchedule() {
        return this.classSchedule;
    }

    public HashMap<LocalTime, HashMap<Instructor, ArrayList<Student>>> getYardSchedule() {
        return this.yardSchedule;
    }

    public HashMap<LocalTime, Pair<Instructor, Student>> getRoadSchedule() {
        return this.roadSchedule;
    }

    public LocalDate getDate() {
        return this.date;
    }

    //MODIFIES: this
    //EFFECTS: checks instructors and students availability for that day, then distributes them
    public void createSchedule() {
        for (Instructor instructor : freeInstructors) {
            if (!instructor.checkAvailabilityForDay(date)) {
                unavailableInstructors.add(instructor);
                freeInstructors.remove(instructor);
            }
        }
        for (Student student : freeStudents) {
            if (!student.checkAvailabiltyForDay(date)) {
                unavailableStudents.add(student);
                freeStudents.remove(student);
            }
        }
        dsitributeStudents();
        distributeInstructors();
    }

    //MODIFIES: this
    //EFFECTS: Assigns students based on highest hours remaining & instructor availability
    private void dsitributeStudents() {
        ArrayList<Student> freeStudentBase = new ArrayList<>(freeStudents);

        for (Student student : freeStudentBase) {
            int classHours = student.getClassHoursLeft();
            int roadHours = student.getRoadHoursLeft();
            int yardHours = student.getYardHoursLeft();
            int flexHours = student.getFlexHoursLeft();
            if (student.getIntroClassDaysLeft() > 0 && checkClassInstructor()) {
                classStudents.add(student);
                studentUnavailable(student);
                student.introDayComplete();
            } else if (classHours >= roadHours && classHours >= yardHours && checkClassInstructor() && classHours > 0) {
                classStudents.add(student);
                studentUnavailable(student);
            } else if (roadHours >= classHours && roadHours >= yardHours && trucks > 0 && checkTruckInstructor() &&
                    roadHours > 0) {
                roadStudents.add(student);
                studentUnavailable(student);
                trucks -= 1;
            } else if (yardHours >= classHours && yardHours >= roadHours && checkYardTruck() && yardHours > 0){
                yardStudents.add(student);
                studentUnavailable(student);
            } else if (flexHours > 0) {
                assignFlexHours(student);
            }
        }
        if (!freeStudents.isEmpty()) {
            classStudents.addAll(freeStudents);
            unavailableStudents.addAll(freeStudents);
            freeStudents.removeAll(freeStudents);
        }
    }

    public void assignFlexHours(Student student) {
        if (!classStudents.isEmpty() && classStudents.size() >= yardStudents.size() || yardStudents.isEmpty()) {
            classStudents.add(student);

        } else {
            yardStudents.add(student);
        }
        studentUnavailable(student);
        student.flexLesson(4);
    }


    //EFFECTS: returns true if there is both an instructor and truck available for a road lesson
    private boolean checkTruckInstructor() {
        if (numInstructors > 0 && trucks > 0) {
            numInstructors -= 1;
            numTruckInstructors += 1;
            return true;
        } else {
            return false;
        }
    }

    //EFFECTS: checks if classroom instructor has been scheduled; if not, assigns instructor if one is available
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

    //EFFECTS: checks if yard truck has been scheduled; if not, assigns yard truck if available
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

    //MODIFIES: unavailableStudents, freeStudents
    //EFFECTS: moves student from freeStudents list to unavailableStudents list
    private void studentUnavailable(Student student) {
        unavailableStudents.add(student);
        freeStudents.remove(student);
    }

    //MODIFIES: this
    //EFFECTS: assigns instructor with highest priority to classroom based on startTime if slots are left
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

    //MODIFIES: instructor
    //EFFECTS: removes available slot from instructor and checks if there are any slots left
    private void useInstructorSlot(Instructor instructor) {
        instructor.useSlot();
        if (!anyAvailableSlots(instructor)) {
            instructorUnavailable(instructor);
        }
    }

    //MODIFIES: this
    //EFFECTS: assigns one road instructor to each student; any extra instructors go to yard
    private void distributeRoadInstructors() {
        for (Instructor instructor : freeInstructors) {
            for (Student student : roadStudents) {
                Pair<Instructor, Student> roadPair = new Pair<> (instructor, student);
                if (instructor.getAvailableSlots() == instructor.getTotalSlots()) {
                    roadSchedule.put(instructor.getFirstStartTime(), roadPair);
                } else {
                    roadSchedule.put(instructor.getSecondStartTime(), roadPair);
                }
                useInstructorSlot(instructor);
            }
        }
        if (!freeInstructors.isEmpty()) {
            for (Instructor instructor : freeInstructors) {
                yardMap.put(instructor, yardStudents);
                if (instructor.getAvailableSlots() == instructor.getTotalSlots()) {
                    yardSchedule.put(instructor.getFirstStartTime(), yardMap);
                } else {
                    yardSchedule.put(instructor.getSecondStartTime(), yardMap);
                }
                useInstructorSlot(instructor);
            }
        }
    }

    //MODIFIES: unavailableInstructors, freeInstructors
    //EFFECTS: removes instructor from freeInstructors and adds to unavailableInstructors
    private void instructorUnavailable(Instructor instructor) {
        unavailableInstructors.add(instructor);
        freeInstructors.remove(instructor);
    }

    //EFFECTS: returns true if instructor has any availableSlots left
    private boolean anyAvailableSlots(Instructor instructor) {
        return instructor.getAvailableSlots() > 0;
    }

}
