package Model;

import javafx.beans.property.*;

public class Payment {

    private IntegerProperty id;
    private LongProperty ccNum;
    private IntegerProperty csv;
    private DoubleProperty amountPaid;
    private IntegerProperty expMonth;
    private IntegerProperty expYear;
    private IntegerProperty exitGateId;

    public Payment() {
        this(0, 0, 0, 0.0, 0, 0, 0);
    }

    public Payment(int id, long ccNum, int csv, double amountPaid, int expMonth, int expYear, int exitGateId) {
        this.id = new SimpleIntegerProperty(id);
        this.ccNum = new SimpleLongProperty(ccNum);
        this.csv = new SimpleIntegerProperty(csv);
        this.amountPaid = new SimpleDoubleProperty(amountPaid);
        this.expMonth = new SimpleIntegerProperty(expMonth);
        this.expYear = new SimpleIntegerProperty(expYear);
        this.exitGateId = new SimpleIntegerProperty(exitGateId);
    }

    public int getId() {
        return this.id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return this.id;
    }

    public long getCcNum() {
        return this.ccNum.get();
    }

    public void setCcNum(long ccNum) {
        this.ccNum.setValue(ccNum);
    }

    public LongProperty ccNumProperty() {
        return this.ccNum;
    }

    public int getCsv() {
        return this.csv.get();
    }

    public void setCsv(int csv) {
        this.csv.set(csv);
    }

    public IntegerProperty csvProperty() {
        return this.csv;
    }

    public double getAmountPaid() {
        return this.amountPaid.get();
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid.set(amountPaid);
    }

    public DoubleProperty amountPaidProperty() {
        return this.amountPaid;
    }

    public int getExpMonth() {
        return this.expMonth.get();
    }

    public void setExpMonth(int expMonth) {
        this.expMonth.set(expMonth);
    }

    public IntegerProperty expMonthProperty() {
        return this.expMonth;
    }

    public int getExpYear() {
        return this.expYear.get();
    }

    public void setExpYear(int expYear) {
        this.expYear.set(expYear);
    }

    public IntegerProperty expYearProperty() {
        return this.expYear;
    }

    public int getExitGateId() {
        return this.exitGateId.get();
    }

    public void setExitGateId(int exitGateId) {
        this.exitGateId.set(exitGateId);
    }

    public IntegerProperty exitGateIdProperty() {
        return this.exitGateId;
    }

    @Override
    public String toString() {
        return Integer.toString(getId());
    }
}