package Model;

import javax.persistence.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.math.BigDecimal;

@Entity
@Access(AccessType.PROPERTY)
public class Payment {

    private int id;
    private long ccNum;
    private short csv;
    private BigDecimal amountPaid;
    private int expMonth;
    private int expYear;
    private ExitGate exitGate;

    @Transient
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public Payment() {
    }

    public Payment(ExitGate gate, long ccNum, short csv, BigDecimal amountPaid, int expMonth, int expYear) {
        this();
        setExitGate(gate);
        setCcNum(ccNum);
        setCsv(csv);
        setAmountPaid(amountPaid);
        setExpMonth(expMonth);
        setExpYear(expYear);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }

    @Id
    @GeneratedValue()
    @Access(AccessType.PROPERTY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        int oldId = this.id;
        if(oldId != id) {
            this.id = id;
            this.pcs.firePropertyChange("id", oldId, this.id);
        }
    }

    @Column(nullable = false)
    @Access(AccessType.PROPERTY)
    public long getCcNum() {
        return ccNum;
    }

    public void setCcNum(long ccNum) {
        long oldCcNum = this.ccNum;
        if(oldCcNum != ccNum) {
            this.ccNum = ccNum;
            this.pcs.firePropertyChange("ccNum", oldCcNum, this.ccNum);
        }
    }

    @Column(nullable = false)
    @Access(AccessType.PROPERTY)
    public short getCsv() {
        return csv;
    }

    public void setCsv(short csv) {
        short oldCsv = this.csv;
        if(oldCsv != csv) {
            this.csv = csv;
            this.pcs.firePropertyChange("csv", oldCsv, this.csv);
        }
    }

    @Column(nullable = false)
    @Access(AccessType.PROPERTY)
    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        BigDecimal oldAmountPaid = this.amountPaid;
        if(oldAmountPaid != amountPaid) {
            this.amountPaid = amountPaid;
            this.pcs.firePropertyChange("amountPaid", oldAmountPaid, this.amountPaid);
        }
    }

    @Column(nullable = false)
    @Access(AccessType.PROPERTY)
    public int getExpMonth() {
        return expMonth;
    }

    public void setExpMonth(int expMonth) {
        int oldExpMonth = this.expMonth;
        if(oldExpMonth != expMonth) {
            this.expMonth = expMonth;
            this.pcs.firePropertyChange("expMonth", oldExpMonth, this.expMonth);
        }
    }

    @Column(nullable = false)
    @Access(AccessType.PROPERTY)
    public int getExpYear() {
        return expYear;
    }

    public void setExpYear(int expYear) {
        int oldExpYear = this.expYear;
        if(oldExpYear != expYear) {
            this.expYear = expYear;
            this.pcs.firePropertyChange("expYear", oldExpYear, this.expYear);
        }
    }

    @ManyToOne()
    @Access(AccessType.PROPERTY)
    public ExitGate getExitGate() {
        return exitGate;
    }

    public void setExitGate(ExitGate exitGate) {
        ExitGate oldExitGate = this.exitGate;
        if(oldExitGate != exitGate) {
            this.exitGate = exitGate;
            this.pcs.firePropertyChange("exitGate", oldExitGate, this.exitGate);
        }
    }

    @Override
    public String toString() {
        return "Payment #" + getId();
    }
}