package Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ExitGate {

    private IntegerProperty id;
    private StringProperty garageName;

    public ExitGate(int id, String garageName) {
        this.id = new SimpleIntegerProperty(id);
        this.garageName = new SimpleStringProperty(garageName);
    }

    public int getId() {
        return this.id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return this.id;
    }

    public String getGarageName() {
        return this.garageName.get();
    }

    public void setGarageName(String garageName) {
        this.garageName.set(garageName);
    }

    public StringProperty garageNameProperty() {
        return this.garageName;
    }

    @Override
    public String toString() {
        return Integer.toString(getId());
    }
}