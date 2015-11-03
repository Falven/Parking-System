package Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import javax.persistence.*;
import java.util.List;

@Entity
public class Garage {

    @Id
    @Basic(optional = false)
    @Column(nullable = false)
    protected String name;

    @OneToMany(mappedBy="garage", cascade = CascadeType.ALL)
    private List<EntryGate> entryGates;

    @OneToMany(mappedBy="garage")
    private List<ExitGate> exitGates;

    public Garage() {
    }

    public Garage(String name) {
        this();
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

    public List<ExitGate> getExitGates() {
        return exitGates;
    }

    public String toString() {
        return name;
    }
}
