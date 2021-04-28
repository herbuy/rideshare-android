package characters;

public interface AdapterScript<Input, Output> {
    Output run(Input input);
}
