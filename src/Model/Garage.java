package Model;

import javax.persistence.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

@Entity
@Access(AccessType.PROPERTY)
public class Garage {

    private String name;
    private int occupancy;
    private List<EntryGate> entryGates;
    private int entryGateCount;
    private List<ExitGate> exitGates;
    private int exitGateCount;

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

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
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
            this.pcs.firePropertyChange("name", oldName, this.name);
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
            this.pcs.firePropertyChange("occupancy", oldOccupancy, this.occupancy);
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
            this.pcs.firePropertyChange("entryGates", oldEntryGates, this.entryGates);
        }
    }

    @Column(nullable = false)
    @Access(AccessType.PROPERTY)
    public int getEntryGateCount() {
        return this.entryGateCount;
    }

    public void setEntryGateCount(int entryGateCount) {
        int oldEntryGateCount = this.entryGateCount;
        if(oldEntryGateCount != entryGateCount) {
            this.entryGateCount = entryGateCount;
            this.pcs.firePropertyChange("entryGateCount", oldEntryGateCount, this.entryGateCount);
        }
    }

    public void incrementEntryGateCount() {
        this.setEntryGateCount(this.getEntryGateCount() + 1);
    }

    public void decrementEntryGateCount() {
        this.setEntryGateCount(this.getEntryGateCount() - 1);
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
            this.pcs.firePropertyChange("exitGates", oldExitGates, this.exitGates);
        }
    }

    @Column(nullable = false)
    @Access(AccessType.PROPERTY)
    public int getExitGateCount() {
        return this.exitGateCount;
    }

    public void setExitGateCount(int exitGateCount) {
        int oldExitGateCount = this.exitGateCount;
        if(oldExitGateCount != exitGateCount) {
            this.exitGateCount = exitGateCount;
            this.pcs.firePropertyChange("exitGateCount", oldExitGateCount, this.exitGateCount);
        }
    }

    public void incrementExitGateCount() {
        this.setExitGateCount(this.getExitGateCount() + 1);
    }

    public void decrementExitGateCount() {
        this.setExitGateCount(this.getExitGateCount() - 1);
    }

    @Override
    public String toString() {
        return name;
    }
}
