package sms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Teacher extends Person {
    private String specialization;
    private final List<String> assignedClasses;

    public Teacher(String id, String name, String gender, String phoneNumber, String address, String specialization) {
        super(id, name, gender, phoneNumber, address);
        if (specialization == null || specialization.isBlank()) {
            throw new IllegalArgumentException("Specialization cannot be blank.");
        }
        this.specialization = specialization.trim();
        this.assignedClasses = new ArrayList<>();
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        if (specialization == null || specialization.isBlank()) {
            throw new IllegalArgumentException("Specialization cannot be blank.");
        }
        this.specialization = specialization.trim();
    }

    public void assignClass(String className) {
        if (className != null && !className.isBlank() && !assignedClasses.contains(className.trim())) {
            assignedClasses.add(className.trim());
        }
    }

    public List<String> getAssignedClasses() {
        return Collections.unmodifiableList(assignedClasses);
    }

    @Override
    public String displayDetails() {
        return "Teacher{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", specialization='" + specialization + '\'' +
                ", assignedClasses=" + assignedClasses +
                '}';
    }
}
