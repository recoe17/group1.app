package sms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Student extends Person {
    private String className;
    private double totalFeesDue;
    private double totalPaid;
    private final List<AttendanceRecord> attendanceRecords;
    private final List<ResultRecord> resultRecords;
    private final List<FeePayment> feePayments;

    public Student(String id, String name, String gender, String phoneNumber, String address, String className, double totalFeesDue) {
        super(id, name, gender, phoneNumber, address);
        if (className == null || className.isBlank()) {
            throw new IllegalArgumentException("Class name cannot be blank.");
        }
        if (totalFeesDue < 0) {
            throw new IllegalArgumentException("Total fees due cannot be negative.");
        }
        this.className = className.trim();
        this.totalFeesDue = totalFeesDue;
        this.totalPaid = 0.0;
        this.attendanceRecords = new ArrayList<>();
        this.resultRecords = new ArrayList<>();
        this.feePayments = new ArrayList<>();
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        if (className == null || className.isBlank()) {
            throw new IllegalArgumentException("Class name cannot be blank.");
        }
        this.className = className.trim();
    }

    public double getTotalFeesDue() {
        return totalFeesDue;
    }

    public void setTotalFeesDue(double totalFeesDue) {
        if (totalFeesDue < 0) {
            throw new IllegalArgumentException("Total fees due cannot be negative.");
        }
        this.totalFeesDue = totalFeesDue;
    }

    public double getTotalPaid() {
        return totalPaid;
    }

    public double getFeesBalance() {
        return totalFeesDue - totalPaid;
    }

    public void addPayment(FeePayment payment) {
        if (payment == null) {
            throw new IllegalArgumentException("Payment cannot be null.");
        }
        this.feePayments.add(payment);
        this.totalPaid += payment.getAmount();
    }

    public void addAttendanceRecord(AttendanceRecord record) {
        if (record == null) {
            throw new IllegalArgumentException("Attendance record cannot be null.");
        }
        this.attendanceRecords.add(record);
    }

    public void addResultRecord(ResultRecord resultRecord) {
        if (resultRecord == null) {
            throw new IllegalArgumentException("Result record cannot be null.");
        }
        this.resultRecords.add(resultRecord);
    }

    public List<AttendanceRecord> getAttendanceRecords() {
        return Collections.unmodifiableList(attendanceRecords);
    }

    public List<ResultRecord> getResultRecords() {
        return Collections.unmodifiableList(resultRecords);
    }

    public List<FeePayment> getFeePayments() {
        return Collections.unmodifiableList(feePayments);
    }

    @Override
    public String displayDetails() {
        return "Student{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", className='" + className + '\'' +
                ", feesBalance=" + String.format("%.2f", getFeesBalance()) +
                '}';
    }
}
