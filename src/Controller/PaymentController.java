package Controller;

import Model.Payment;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.adapter.*;

import java.math.BigDecimal;

public class PaymentController {

    private final Payment bean;
    private JavaBeanIntegerProperty id;
    private JavaBeanLongProperty ccNum;
    private JavaBeanIntegerProperty csv;
    private JavaBeanObjectProperty<BigDecimal> amountPaid;
    private JavaBeanIntegerProperty expMonth;
    private JavaBeanIntegerProperty expYear;

    public PaymentController(Payment payment) throws NoSuchMethodException {
        this.bean = payment;
        this.id = JavaBeanIntegerPropertyBuilder.create().bean(this.bean).name("id").build();
        this.ccNum = JavaBeanLongPropertyBuilder.create().bean(this.bean).name("ccNum").build();
        this.csv = JavaBeanIntegerPropertyBuilder.create().bean(this.bean).name("csv").build();
        this.amountPaid = JavaBeanObjectPropertyBuilder.create().bean(this.bean).name("amountPaid").build();
        this.expMonth = JavaBeanIntegerPropertyBuilder.create().bean(this.bean).name("expMonth").build();
        this.expYear = JavaBeanIntegerPropertyBuilder.create().bean(this.bean).name("expYear").build();
    }

    public Payment getBean() {
        return this.bean;
    }

    public IntegerProperty idProperty() {
        return this.id;
    }

    public LongProperty ccNumProperty() {
        return this.ccNum;
    }

    public IntegerProperty csvProperty() {
        return this.csv;
    }

    public ObjectProperty<BigDecimal> amountPaidProperty() {
        return this.amountPaid;
    }

    public IntegerProperty expMonthProperty() {
        return this.expMonth;
    }

    public IntegerProperty expYearProperty() {
        return this.expYear;
    }

    public int getId() {
        return this.id.get();
    }

    public void setId(int value) {
        this.id.set(value);
    }

    public long getCcNum() {
        return this.ccNum.get();
    }

    public void setCcNum(long value) {
        this.ccNum.set(value);
    }

    public int getCsv() {
        return this.csv.get();
    }

    public void setCsv(int value) {
        this.csv.set(value);
    }

    public BigDecimal getAmountPaid() {
        return this.amountPaid.get();
    }

    public void setAmountPaid(BigDecimal value) {
        this.amountPaid.set(value);
    }

    public int getExpMonth() {
        return this.expMonth.get();
    }

    public void setExpMonth(int value) {
        this.expMonth.set(value);
    }

    public int getExpYear() {
        return this.expYear.get();
    }

    public void setExpYear(int value) {
        this.expYear.set(value);
    }
}
