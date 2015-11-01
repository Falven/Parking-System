package Model;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity(name = "GARAGE")
public class Garage {

    @Id
    @NotNull
    @Column(name="GARAGE_NAME")
    protected String name;

    @NotNull
    @Column(name="ENTRYGATE_ID")
    @OneToMany()
    private List<EntryGate> entryGates;

    @NotNull
    @Column(name="EXITYGATE_ID")
    @OneToMany()
    private List<ExitGate> exitGates;

    public Garage() {

    }

    public Garage(String name) {
        this.name = name;
    }
}
