package sms;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

class FeePaymentTest {

    @Test
    void rejectsInvalidInput() {
        LocalDate today = LocalDate.now();
        assertThrows(IllegalArgumentException.class, () -> new FeePayment(null, 10, "cash", "r1"));
        assertThrows(IllegalArgumentException.class, () -> new FeePayment(today, 0, "cash", "r1"));
        assertThrows(IllegalArgumentException.class, () -> new FeePayment(today, -5, "cash", "r1"));
        assertThrows(IllegalArgumentException.class, () -> new FeePayment(today, 10, "", "r1"));
        assertThrows(IllegalArgumentException.class, () -> new FeePayment(today, 10, "   ", "r1"));
    }
}
