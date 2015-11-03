package Model;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
public class ExitGate extends Gate {
    @NotNull
    @OneToMany(cascade=CascadeType.REMOVE)
    private List<Payment> payments;

    public ExitGate() {
    }

    public ExitGate(Garage owner) {
        setGarage(owner);
    }
}