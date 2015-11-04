package Model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Garage {

    @Transient
    public static final int MAX_OCCUPANCY = 20;

    @Id
    @Basic(optional = false)
    @Column(nullable = false)
    protected String name;

    @Column(nullable = false)
    private int occupancy;

    @OneToMany(mappedBy="garage", cascade = CascadeType.ALL)
    private List<EntryGate> entryGates;

    @OneToMany(mappedBy="garage", cascade = CascadeType.ALL)
    private List<ExitGate> exitGates;

    public Garage() {
        occupancy = 0;
    }

    public Garage(String name) {
        this();
        this.name = name;
    }

    public int getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(int occupancy) {
        this.occupancy = occupancy;
    }

    public String getName() {
        return name;
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
