package Controller;

import Model.Payment;

public class PaymentController {

    private final Payment bean;

    public PaymentController(Payment payment) throws NoSuchMethodException {
        this.bean = payment;
    }

    public Payment getBean() {
        return this.bean;
    }
}
