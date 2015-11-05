package Model;

import javafx.beans.property.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Access(AccessType.PROPERTY)
public class Payment {

    private IntegerProperty id;
    private LongProperty ccNum;
    private IntegerProperty csv;
    private DoubleProperty amountPaid;
    private IntegerProperty expMonth;
    private IntegerProperty expYear;
    private ObjectProperty<ExitGate> exitGate;

    public Payment() {
        this(0, 0, 0.0, 0, 0, null);
    }

    public Payment(long ccNum, int csv, double amountPaid, int expMonth, int expYear, ExitGate gate) {
        this.id = new SimpleIntegerProperty();
        this.ccNum = new SimpleLongProperty(ccNum);
        this.csv = new SimpleIntegerProperty(csv);
        this.amountPaid = new SimpleDoubleProperty(amountPaid);
        this.expMonth = new SimpleIntegerProperty(expMonth);
        this.expYear = new SimpleIntegerProperty(expYear);
        this.exitGate = new SimpleObjectProperty<>(gate);
    }

    @Id
    @GeneratedValue()
    public int getId() {
        return this.id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return this.id;
    }

    @Column(nullable = false)
    public long getCcNum() {
        return this.ccNum.get();
    }

    public void setCcNum(long ccNum) {
        this.ccNum.setValue(ccNum);
    }

    public LongProperty ccNumProperty() {
        return this.ccNum;
    }

    @Column(nullable = false)
    public int getCsv() {
        return this.csv.get();
    }

    public void setCsv(int csv) {
        this.csv.set(csv);
    }

    public IntegerProperty csvProperty() {
        return this.csv;
    }

    @Column(nullable = false)
    public double getAmountPaid() {
        return this.amountPaid.get();
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid.set(amountPaid);
    }

    public DoubleProperty amountPaidProperty() {
        return this.amountPaid;
    }

    @Column(nullable = false)
    public int getExpMonth() {
        return this.expMonth.get();
    }

    public void setExpMonth(int expMonth) {
        this.expMonth.set(expMonth);
    }

    public IntegerProperty expMonthProperty() {
        return this.expMonth;
    }

    @Column(nullable = false)
    public int getExpYear() {
        return this.expYear.get();
    }

    public void setExpYear(int expYear) {
        this.expYear.set(expYear);
    }

    public IntegerProperty expYearProperty() {
        return this.expYear;
    }

    @ManyToOne()
    public ExitGate getExitGate() {
        return this.exitGate.get();
    }

    public void setExitGate(ExitGate exitGate) {
        this.exitGate.set(exitGate);
    }

    public ObjectProperty<ExitGate> exitGateProperty() {
        return this.exitGate;
    }

    @Override
    public String toString() {
        return Integer.toString(getId());
    }
}