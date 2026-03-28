package sms;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SchoolManagementSystem {

    private enum StudentSearchMode {
        BY_NAME,
        BY_CLASS
    }

    private final Map<String, Student> students;
    private final Map<String, Teacher> teachers;
    private final Map<String, Classroom> classrooms;
    private final Scanner scanner;

    public SchoolManagementSystem() {
        this.students = new HashMap<>();
        this.teachers = new HashMap<>();
        this.classrooms = new HashMap<>();
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readInt("Choose an option: ");
            switch (choice) {
                case 1 -> registerStudent();
                case 2 -> registerTeacher();
                case 3 -> createClassroom();
                case 4 -> allocateStudentToClass();
                case 5 -> recordAttendance();
                case 6 -> recordResult();
                case 7 -> recordFeePayment();
                case 8 -> searchStudentMenu();
                case 9 -> showReportsMenu();
                case 10 -> updateStudent();
                case 11 -> updateTeacher();
                case 12 -> removeStudent();
                case 13 -> removeTeacher();
                case 0 -> running = false;
                default -> System.out.println("Invalid option.");
            }
            System.out.println();
        }
        System.out.println("System closed.");
    }

    private void printMainMenu() {
        System.out.println("=== School Management System ===");
        System.out.println("1. Register Student");
        System.out.println("2. Register Teacher");
        System.out.println("3. Create Classroom");
        System.out.println("4. Allocate Student to Class");
        System.out.println("5. Record Attendance");
        System.out.println("6. Record Result");
        System.out.println("7. Record Fee Payment");
        System.out.println("8. Search Student");
        System.out.println("9. Reports");
        System.out.println("10. Update Student");
        System.out.println("11. Update Teacher");
        System.out.println("12. Remove Student");
        System.out.println("13. Remove Teacher");
        System.out.println("0. Exit");
    }

    private void registerStudent() {
        try {
            String id = readRequired("Student ID: ");
            if (students.containsKey(id)) {
                System.out.println("Student ID already exists.");
                return;
            }

            Student student = new Student(
                    id,
                    readRequired("Name: "),
                    readRequired("Gender: "),
                    readRequired("Phone: "),
                    readRequired("Address: "),
                    readRequired("Class name: "),
                    readDouble("Total fees due: ")
            );
            students.put(student.getId(), student);
            System.out.println("Student registered successfully.");
        } catch (IllegalArgumentException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void registerTeacher() {
        try {
            String id = readRequired("Teacher ID: ");
            if (teachers.containsKey(id)) {
                System.out.println("Teacher ID already exists.");
                return;
            }
            Teacher teacher = new Teacher(
                    id,
                    readRequired("Name: "),
                    readRequired("Gender: "),
                    readRequired("Phone: "),
                    readRequired("Address: "),
                    readRequired("Specialization: ")
            );
            teachers.put(teacher.getId(), teacher);
            System.out.println("Teacher registered successfully.");
        } catch (IllegalArgumentException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void createClassroom() {
        try {
            String className = readRequired("Class name: ");
            if (classrooms.containsKey(className)) {
                System.out.println("Class already exists.");
                return;
            }
            String subject = readRequired("Subject: ");
            String teacherId = readRequired("Teacher ID (optional, enter - to skip): ");
            if ("-".equals(teacherId)) {
                teacherId = "";
            } else if (!teacherId.isBlank() && !teachers.containsKey(teacherId)) {
                System.out.println("Teacher not found.");
                return;
            }

            Classroom classroom = new Classroom(className, subject, teacherId);
            classrooms.put(className, classroom);
            if (!teacherId.isBlank()) {
                teachers.get(teacherId).assignClass(className);
            }
            System.out.println("Classroom created.");
        } catch (IllegalArgumentException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void allocateStudentToClass() {
        String studentId = readRequired("Student ID: ");
        Student student = students.get(studentId);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        String className = readRequired("Class name: ");
        Classroom classroom = classrooms.get(className);
        if (classroom == null) {
            System.out.println("Class not found.");
            return;
        }
        student.setClassName(className);
        classroom.addStudent(studentId);
        System.out.println("Student allocated to class.");
    }

    private void recordAttendance() {
        String studentId = readRequired("Student ID: ");
        Student student = students.get(studentId);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        String statusInput = readRequired("Status (PRESENT/ABSENT/LATE): ").toUpperCase();
        try {
            AttendanceStatus status = AttendanceStatus.valueOf(statusInput);
            student.addAttendanceRecord(new AttendanceRecord(LocalDate.now(), status));
            System.out.println("Attendance recorded.");
        } catch (IllegalArgumentException ex) {
            System.out.println("Invalid status.");
        }
    }

    private void recordResult() {
        String studentId = readRequired("Student ID: ");
        Student student = students.get(studentId);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        try {
            ResultRecord resultRecord = new ResultRecord(
                    readRequired("Subject: "),
                    readDouble("Mark (0-100): "),
                    readRequired("Comment: ")
            );
            student.addResultRecord(resultRecord);
            System.out.println("Result recorded.");
        } catch (IllegalArgumentException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void recordFeePayment() {
        String studentId = readRequired("Student ID: ");
        Student student = students.get(studentId);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        try {
            FeePayment payment = new FeePayment(
                    LocalDate.now(),
                    readDouble("Amount: "),
                    readRequired("Method (cash/bank/mobile): "),
                    readRequired("Reference: ")
            );
            student.addPayment(payment);
            System.out.printf("Payment recorded. New balance: %.2f%n", student.getFeesBalance());
        } catch (IllegalArgumentException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void searchStudentMenu() {
        System.out.println("Search by: 1) ID 2) Name 3) Class");
        int option = readInt("Choose search type: ");
        switch (option) {
            case 1 -> {
                String id = readRequired("Student ID: ");
                Student student = searchStudent(id);
                System.out.println(student == null ? "No student found." : student.displayDetails());
            }
            case 2 -> {
                String name = readRequired("Name: ");
                List<Student> found = searchStudent(name, StudentSearchMode.BY_NAME);
                printStudentList(found);
            }
            case 3 -> {
                String className = readRequired("Class: ");
                List<Student> found = searchStudent(className, StudentSearchMode.BY_CLASS);
                printStudentList(found);
            }
            default -> System.out.println("Invalid choice.");
        }
    }

    private Student searchStudent(String studentId) {
        return students.get(studentId);
    }

    private List<Student> searchStudent(String query, StudentSearchMode mode) {
        return switch (mode) {
            case BY_NAME -> searchStudentByName(query);
            case BY_CLASS -> searchStudentByClass(query);
        };
    }

    private List<Student> searchStudentByName(String namePart) {
        return students.values().stream()
                .filter(s -> s.getName().toLowerCase().contains(namePart.toLowerCase()))
                .sorted(Comparator.comparing(Student::getName))
                .collect(Collectors.toList());
    }

    private List<Student> searchStudentByClass(String className) {
        return students.values().stream()
                .filter(s -> s.getClassName().equalsIgnoreCase(className))
                .sorted(Comparator.comparing(Student::getName))
                .collect(Collectors.toList());
    }

    private void printStudentList(List<Student> studentsFound) {
        if (studentsFound.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        studentsFound.forEach(s -> System.out.println(s.displayDetails()));
    }

    private void showReportsMenu() {
        System.out.println("Reports: 1) All students 2) Fee balances 3) Attendance summary 4) Academic summary 5) Fee statements");
        int option = readInt("Choose report: ");
        switch (option) {
            case 1 -> reportAllStudents();
            case 2 -> reportFeeBalances();
            case 3 -> reportAttendanceSummary();
            case 4 -> reportAcademicSummary();
            case 5 -> reportFeeStatements();
            default -> System.out.println("Invalid choice.");
        }
    }

    private void reportAllStudents() {
        List<Student> list = new ArrayList<>(students.values());
        list.sort(Comparator.comparing(Student::getClassName).thenComparing(Student::getName));
        if (list.isEmpty()) {
            System.out.println("No students registered.");
            return;
        }
        list.forEach(s -> System.out.println(s.displayDetails()));
    }

    private void reportFeeBalances() {
        List<Student> list = new ArrayList<>(students.values());
        list.sort(Comparator.comparingDouble(Student::getFeesBalance).reversed());
        if (list.isEmpty()) {
            System.out.println("No students registered.");
            return;
        }
        for (Student s : list) {
            System.out.printf("%s | %s | Due: %.2f Paid: %.2f Balance: %.2f%n",
                    s.getId(), s.getName(), s.getTotalFeesDue(), s.getTotalPaid(), s.getFeesBalance());
        }
    }

    private void reportAttendanceSummary() {
        if (students.isEmpty()) {
            System.out.println("No students registered.");
            return;
        }
        for (Student s : students.values()) {
            long present = s.getAttendanceRecords().stream().filter(a -> a.getStatus() == AttendanceStatus.PRESENT).count();
            long absent = s.getAttendanceRecords().stream().filter(a -> a.getStatus() == AttendanceStatus.ABSENT).count();
            long late = s.getAttendanceRecords().stream().filter(a -> a.getStatus() == AttendanceStatus.LATE).count();
            System.out.printf("%s | %s | Present: %d Absent: %d Late: %d%n",
                    s.getId(), s.getName(), present, absent, late);
        }
    }

    private void reportAcademicSummary() {
        if (students.isEmpty()) {
            System.out.println("No students registered.");
            return;
        }
        for (Student s : students.values()) {
            double avg = s.getResultRecords().stream()
                    .mapToDouble(ResultRecord::getMark)
                    .average()
                    .orElse(0.0);
            System.out.printf("%s | %s | Results entered: %d | Average: %.2f%n",
                    s.getId(), s.getName(), s.getResultRecords().size(), avg);
        }
    }

    private void reportFeeStatements() {
        if (students.isEmpty()) {
            System.out.println("No students registered.");
            return;
        }
        String idOrAll = readRequired("Student ID (type ALL for every student): ");
        if ("ALL".equalsIgnoreCase(idOrAll)) {
            List<Student> list = new ArrayList<>(students.values());
            list.sort(Comparator.comparing(Student::getId));
            for (Student s : list) {
                printFeeStatementForStudent(s);
            }
        } else {
            Student s = students.get(idOrAll);
            if (s == null) {
                System.out.println("Student not found.");
            } else {
                printFeeStatementForStudent(s);
            }
        }
    }

    private void printFeeStatementForStudent(Student s) {
        System.out.println("--- Fee statement: " + s.getName() + " (" + s.getId() + ") ---");
        System.out.printf("Total due: %.2f | Total paid: %.2f | Balance: %.2f%n",
                s.getTotalFeesDue(), s.getTotalPaid(), s.getFeesBalance());
        List<FeePayment> payments = s.getFeePayments();
        if (payments.isEmpty()) {
            System.out.println("No payments recorded.");
        } else {
            System.out.println("Payment history:");
            for (FeePayment p : payments) {
                System.out.printf("  %s | %.2f | %s | %s%n",
                        p.getDate(), p.getAmount(), p.getMethod(), p.getReference());
            }
        }
        System.out.println();
    }

    private void removeStudent() {
        String id = readRequired("Student ID to remove: ");
        Student removed = students.remove(id);
        if (removed == null) {
            System.out.println("Student not found.");
            return;
        }
        for (Classroom c : classrooms.values()) {
            c.removeStudent(id);
        }
        System.out.println("Student removed.");
    }

    private void removeTeacher() {
        String id = readRequired("Teacher ID to remove: ");
        Teacher removed = teachers.remove(id);
        if (removed == null) {
            System.out.println("Teacher not found.");
            return;
        }
        for (Classroom c : classrooms.values()) {
            if (id.equals(c.getTeacherId())) {
                c.setTeacherId("");
            }
        }
        System.out.println("Teacher removed.");
    }

    private void updateStudent() {
        String studentId = readRequired("Student ID: ");
        Student student = students.get(studentId);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        try {
            student.setName(readRequired("New name: "));
            student.setGender(readRequired("New gender: "));
            student.setPhoneNumber(readRequired("New phone: "));
            student.setAddress(readRequired("New address: "));
            student.setClassName(readRequired("New class: "));
            student.setTotalFeesDue(readDouble("New total fees due: "));
            System.out.println("Student updated.");
        } catch (IllegalArgumentException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void updateTeacher() {
        String teacherId = readRequired("Teacher ID: ");
        Teacher teacher = teachers.get(teacherId);
        if (teacher == null) {
            System.out.println("Teacher not found.");
            return;
        }
        try {
            teacher.setName(readRequired("New name: "));
            teacher.setGender(readRequired("New gender: "));
            teacher.setPhoneNumber(readRequired("New phone: "));
            teacher.setAddress(readRequired("New address: "));
            teacher.setSpecialization(readRequired("New specialization: "));
            System.out.println("Teacher updated.");
        } catch (IllegalArgumentException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private String readRequired(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine();
            if (value != null && !value.isBlank()) {
                return value.trim();
            }
            System.out.println("Input cannot be blank.");
        }
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine();
            try {
                return Integer.parseInt(value.trim());
            } catch (NumberFormatException ex) {
                System.out.println("Enter a valid whole number.");
            }
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine();
            try {
                double num = Double.parseDouble(value.trim());
                if (num < 0) {
                    System.out.println("Value cannot be negative.");
                    continue;
                }
                return num;
            } catch (NumberFormatException ex) {
                System.out.println("Enter a valid number.");
            }
        }
    }
}
