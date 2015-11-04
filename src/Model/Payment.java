package Model;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Payment {

    @Id
    @GeneratedValue()
    private int id;

    @Column(nullable = false)
    private long ccNum;

    @Column(nullable = false)
    private short csv;

    @Column(nullable = false)
    private BigDecimal amountPaid;

    @Column(nullable = false)
    private int expMonth;

    @Column(nullable = false)
    private int expYear;

    @ManyToOne()
    private ExitGate exitGate;

    public Payment() {
    }

    public Payment(ExitGate gate, long ccNum, short csv, BigDecimal amountPaid, int expMonth, int expYear) {
        this();
        this.exitGate = gate;
        this.ccNum = ccNum;
        this.csv = csv;
        this.amountPaid = amountPaid;
        this.expMonth = expMonth;
        this.expYear = expYear;
    }
}