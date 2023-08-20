package ui;

import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;

public class SchedulerGui extends Application {

    // A class to represent a tutoring session with a date, time, tutor, students, and subject
    public static class Session {
        private final SimpleObjectProperty<LocalDate> date;
        private final SimpleObjectProperty<LocalTime> time;
        private final SimpleStringProperty tutor;
        private final SimpleStringProperty students;
        private final SimpleStringProperty subject;

        public Session(LocalDate date, LocalTime time, String tutor, String students, String subject) {
            this.date = new SimpleObjectProperty<>(date);
            this.time = new SimpleObjectProperty<>(time);
            this.tutor = new SimpleStringProperty(tutor);
            this.students = new SimpleStringProperty(students);
            this.subject = new SimpleStringProperty(subject);
        }

        public LocalDate getDate() {
            return date.get();
        }

        public void setDate(LocalDate date) {
            this.date.set(date);
        }

        public LocalTime getTime() {
            return time.get();
        }

        public void setTime(LocalTime time) {
            this.time.set(time);
        }

        public String getTutor() {
            return tutor.get();
        }

        public void setTutor(String tutor) {
            this.tutor.set(tutor);
        }

        public String getStudents() {
            return students.get();
        }

        public void setStudents(String students) {
            this.students.set(students);
        }

        public String getSubject() {
            return subject.get();
        }

        public void setSubject(String subject) {
            this.subject.set(subject);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Create a panel for the calendar component
        VBox calendarPanel = new VBox(100);
        // Create a date picker using JavaFX library
        DatePicker datePicker = new DatePicker();
        // Set the current date and the format
        datePicker.setValue(LocalDate.now());
        // Add the date picker to the panel
        calendarPanel.getChildren().add(new Label("Select a date:"));
        calendarPanel.getChildren().add(datePicker);

        // Create a panel for the table component
        VBox tablePanel = new VBox(20);

        // Create an observable list of sessions using JavaFX library
        ObservableList<Session> sessions = FXCollections.observableArrayList();
        // Add some sample sessions to the list
        sessions.add(new Session(LocalDate.of(2023, 8, 1), LocalTime.of(8, 0), "Alice", "Bob and Carol", "Math"));
        sessions.add(new Session(LocalDate.of(2023, 8, 1), LocalTime.of(8, 0), "David", "Eve", "Science"));
        sessions.add(new Session(LocalDate.of(2023, 8, 1), LocalTime.of(13, 0), "Frank", "Grace and Harry", "Art"));

        // Create a table view using JavaFX library
        TableView<Session> tableView = new TableView<>();

        // Create a table column for the date using JavaFX library
        TableColumn<Session, LocalDate> dateColumn = new TableColumn<>("Date");

        // Set the cell value factory for the date column using JavaFX library
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().date);

        // Create a table column for the time using JavaFX library
        TableColumn<Session, LocalTime> timeColumn = new TableColumn<>("Time");

        // Set the cell value factory for the time column using JavaFX library
        timeColumn.setCellValueFactory(cellData -> cellData.getValue().time);

        // Create a table column for the tutor using JavaFX library
        TableColumn<Session, String> tutorColumn = new TableColumn<>("Tutor");

        // Set the cell value factory for the tutor column using JavaFX library
        tutorColumn.setCellValueFactory(cellData -> cellData.getValue().tutor);

        // Create a table column for the students using JavaFX library
        TableColumn<Session, String> studentsColumn = new TableColumn<>("Students");

        // Set the cell value factory for the students column using JavaFX library
        studentsColumn.setCellValueFactory(cellData -> cellData.getValue().students);

        // Create a table column for the subject using JavaFX library
        TableColumn<Session, String> subjectColumn = new TableColumn<>("Subject");

        // Set the cell value factory for the subject column using JavaFX library
        subjectColumn.setCellValueFactory(cellData -> cellData.getValue().subject);

        // Add the columns to the table view using JavaFX library
        tableView.getColumns().addAll(dateColumn, timeColumn, tutorColumn, studentsColumn, subjectColumn);

        // Set the items for the table view using JavaFX library
        tableView.setItems(sessions);

        // Add the table view to the panel
        tablePanel.getChildren().add(tableView);

        // Create a main panel for the GUI
        VBox mainPanel = new VBox(20);
        // Add the calendar panel and the table panel to the main panel
        mainPanel.getChildren().add(calendarPanel);
        mainPanel.getChildren().add(tablePanel);

        // Create a scene for the stage using JavaFX library
        Scene scene = new Scene(mainPanel, 600, 400);

        // Set the title and scene of the stage using JavaFX library
        primaryStage.setTitle("Scheduler GUI Example");
        primaryStage.setScene(scene);

        // Show the stage using JavaFX library
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Launch the application
        Application.launch(args);
    }
}




