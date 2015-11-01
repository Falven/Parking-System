package Model;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;

@Entity(name = "PAYMENT")
public class Payment {
    @Id
    @NotNull
    @GeneratedValue()
    @Column(name="PAYMENT_ID")
    protected int id;

    @ManyToOne()
    @JoinColumn(name="EXITGATE_ID")
    private ExitGate exitGate;

    public Payment() {
    }
}