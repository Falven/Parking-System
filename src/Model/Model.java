package Model;

public abstract class Model<T> {

    private T controller;

    public T getController() {
        return controller;
    }

    public void setController(T controller) {
        this.controller = controller;
    }
}
