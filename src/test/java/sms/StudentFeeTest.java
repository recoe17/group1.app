package sms;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentFeeTest {

    @Test
    void paymentsUpdateBalance() {
        Student s = new Student("S1", "Ann", "F", "1", "Addr", "Form1", 500);
        assertEquals(500, s.getFeesBalance(), 0.001);
        s.addPayment(new FeePayment(LocalDate.now(), 100, "cash", "R1"));
        assertEquals(400, s.getFeesBalance(), 0.001);
        s.addPayment(new FeePayment(LocalDate.now(), 150, "bank", "R2"));
        assertEquals(250, s.getFeesBalance(), 0.001);
        assertEquals(2, s.getFeePayments().size());
    }
}
