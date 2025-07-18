package interfaces;

import exceptions.InvalidPaymentException;

public interface PaymentHandler {
    void validatePayment(double amount) throws InvalidPaymentException;
}
