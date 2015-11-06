package Controller;

public class ModelController<T> extends ViewController {

    private final T model;

    public ModelController(T model) {
        this.model = model;
    }

    public T getModel() {
        return model;
    }
}
