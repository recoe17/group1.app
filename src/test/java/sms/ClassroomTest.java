package sms;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClassroomTest {

    @Test
    void addAndRemoveStudent() {
        Classroom c = new Classroom("Form1A", "Math", "");
        c.addStudent("S1");
        c.addStudent("S2");
        assertEquals(2, c.getStudentIds().size());
        c.removeStudent("S1");
        assertEquals(1, c.getStudentIds().size());
        assertTrue(c.getStudentIds().contains("S2"));
    }
}
