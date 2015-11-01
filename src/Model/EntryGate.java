package Model;

import javax.persistence.Entity;

@Entity
public class EntryGate extends Gate {
    public EntryGate() {
    }

    public EntryGate(Garage owner) {
        setGarage(owner);
    }
}