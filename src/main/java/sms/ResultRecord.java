package sms;

public class ResultRecord {
    private final String subject;
    private final double mark;
    private final String grade;
    private final String comment;

    public ResultRecord(String subject, double mark, String comment) {
        if (subject == null || subject.isBlank()) {
            throw new IllegalArgumentException("Subject cannot be blank.");
        }
        if (mark < 0 || mark > 100) {
            throw new IllegalArgumentException("Mark must be between 0 and 100.");
        }
        this.subject = subject.trim();
        this.mark = mark;
        this.grade = calculateGrade(mark);
        this.comment = (comment == null) ? "" : comment.trim();
    }

    private String calculateGrade(double score) {
        if (score >= 80) return "A";
        if (score >= 70) return "B";
        if (score >= 60) return "C";
        if (score >= 50) return "D";
        return "E";
    }

    public String getSubject() {
        return subject;
    }

    public double getMark() {
        return mark;
    }

    public String getGrade() {
        return grade;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public String toString() {
        return "ResultRecord{" +
                "subject='" + subject + '\'' +
                ", mark=" + mark +
                ", grade='" + grade + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
