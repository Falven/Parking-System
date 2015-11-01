package Model;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;

@Entity
public class Payment {
    @Id
    @NotNull
    @GeneratedValue()
    protected int id;

    @ManyToOne()
    private ExitGate exitGate;

    public Payment() {
    }
}