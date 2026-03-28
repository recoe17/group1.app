package sms;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ResultRecordTest {

    @Test
    void calculatesGradeBands() {
        assertEquals("A", new ResultRecord("Math", 100, "").getGrade());
        assertEquals("A", new ResultRecord("Math", 80, "").getGrade());
        assertEquals("B", new ResultRecord("Math", 79, "").getGrade());
        assertEquals("B", new ResultRecord("Math", 70, "").getGrade());
        assertEquals("C", new ResultRecord("Math", 69, "").getGrade());
        assertEquals("C", new ResultRecord("Math", 60, "").getGrade());
        assertEquals("D", new ResultRecord("Math", 59, "").getGrade());
        assertEquals("D", new ResultRecord("Math", 50, "").getGrade());
        assertEquals("E", new ResultRecord("Math", 49, "").getGrade());
        assertEquals("E", new ResultRecord("Math", 0, "").getGrade());
    }

    @Test
    void rejectsInvalidMark() {
        assertThrows(IllegalArgumentException.class, () -> new ResultRecord("Eng", -1, ""));
        assertThrows(IllegalArgumentException.class, () -> new ResultRecord("Eng", 101, ""));
    }
}
