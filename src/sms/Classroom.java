package sms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Classroom {
    private final String name;
    private String subject;
    private String teacherId;
    private final List<String> studentIds;

    public Classroom(String name, String subject, String teacherId) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Class name cannot be blank.");
        }
        if (subject == null || subject.isBlank()) {
            throw new IllegalArgumentException("Subject cannot be blank.");
        }
        this.name = name.trim();
        this.subject = subject.trim();
        this.teacherId = (teacherId == null) ? "" : teacherId.trim();
        this.studentIds = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        if (subject == null || subject.isBlank()) {
            throw new IllegalArgumentException("Subject cannot be blank.");
        }
        this.subject = subject.trim();
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = (teacherId == null) ? "" : teacherId.trim();
    }

    public void addStudent(String studentId) {
        if (studentId != null && !studentId.isBlank() && !studentIds.contains(studentId.trim())) {
            studentIds.add(studentId.trim());
        }
    }

    public List<String> getStudentIds() {
        return Collections.unmodifiableList(studentIds);
    }
}
