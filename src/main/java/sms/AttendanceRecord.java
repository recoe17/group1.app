package sms;

import java.time.LocalDate;

public class AttendanceRecord {
    private final LocalDate date;
    private AttendanceStatus status;

    public AttendanceRecord(LocalDate date, AttendanceStatus status) {
        if (date == null || status == null) {
            throw new IllegalArgumentException("Date and status cannot be null.");
        }
        this.date = date;
        this.status = status;
    }

    public LocalDate getDate() {
        return date;
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    public void setStatus(AttendanceStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null.");
        }
        this.status = status;
    }

    @Override
    public String toString() {
        return "AttendanceRecord{" +
                "date=" + date +
                ", status=" + status +
                '}';
    }
}
