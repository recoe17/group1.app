package sms;

import java.time.LocalDate;

public class FeePayment {
    private final LocalDate date;
    private final double amount;
    private final String method;
    private final String reference;

    public FeePayment(LocalDate date, double amount, String method, String reference) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }
        if (method == null || method.isBlank()) {
            throw new IllegalArgumentException("Payment method cannot be blank.");
        }
        this.date = date;
        this.amount = amount;
        this.method = method.trim();
        this.reference = (reference == null) ? "" : reference.trim();
    }

    public LocalDate getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public String getMethod() {
        return method;
    }

    public String getReference() {
        return reference;
    }

    @Override
    public String toString() {
        return "FeePayment{" +
                "date=" + date +
                ", amount=" + amount +
                ", method='" + method + '\'' +
                ", reference='" + reference + '\'' +
                '}';
    }
}
