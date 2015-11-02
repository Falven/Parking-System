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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EntryGate> getEntryGates() {
        return entryGates;
    }

    public void setEntryGates(List<EntryGate> entryGates) {
        this.entryGates = entryGates;
    }

    public List<ExitGate> getExitGates() {
        return exitGates;
    }

    public void setExitGates(List<ExitGate> exitGates) {
        this.exitGates = exitGates;
    }

    public String toString() {
        return name;
    }
}
