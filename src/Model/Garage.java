package Model;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
public class Garage {

    @Id
    @NotNull
    protected String name;

    @NotNull
    @OneToMany()
    private List<EntryGate> entryGates;

    @NotNull
    @OneToMany()
    private List<ExitGate> exitGates;

    public Garage() {
    }

    public Garage(String name) {
        this.name = name;
    }
}
