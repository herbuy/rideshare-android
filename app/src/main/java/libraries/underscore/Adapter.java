package libraries.underscore;

public abstract class Adapter<InputType, ReturnType> {
    public abstract ReturnType adapt(InputType input);
}
