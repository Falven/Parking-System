package Model;

import javax.persistence.*;
import java.beans.PropertyChangeSupport;
import java.util.List;

@Entity
@Access(AccessType.PROPERTY)
public class Garage {

    protected String name;
    private int occupancy;
    private List<EntryGate> entryGates;
    private List<ExitGate> exitGates;

    @Transient
    public static final int MAX_OCCUPANCY = 20;
    @Transient
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public Garage() {
    }

    public Garage(String name) {
        this();
        setName(name);
    }

    @Id
    @Basic(optional = false)
    @Column(nullable = false)
    @Access(AccessType.PROPERTY)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        String oldName = this.name;
        if(oldName != name) {
            this.name = name;
            pcs.firePropertyChange("name", oldName, this.name);
        }
    }

    @Column(nullable = false)
    @Access(AccessType.PROPERTY)
    public int getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(int occupancy) {
        int oldOccupancy = this.occupancy;
        if(oldOccupancy != occupancy) {
            this.occupancy = occupancy;
            pcs.firePropertyChange("occupancy", oldOccupancy, this.occupancy);
        }
    }

    @OneToMany(mappedBy="garage", cascade = CascadeType.ALL)
    @Access(AccessType.PROPERTY)
    public List<EntryGate> getEntryGates() {
        return entryGates;
    }

    public void setEntryGates(List<EntryGate> entryGates) {
        List<EntryGate> oldEntryGates = this.entryGates;
        if(oldEntryGates != entryGates) {
            this.entryGates = entryGates;
            pcs.firePropertyChange("entryGates", oldEntryGates, this.entryGates);
        }
    }

    @OneToMany(mappedBy="garage", cascade = CascadeType.ALL)
    @Access(AccessType.PROPERTY)
    public List<ExitGate> getExitGates() {
        return exitGates;
    }

    public void setExitGates(List<ExitGate> exitGates) {
        List<ExitGate> oldExitGates = this.exitGates;
        if(oldExitGates != exitGates) {
            this.exitGates = exitGates;
            pcs.firePropertyChange("exitGates", oldExitGates, this.exitGates);
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
