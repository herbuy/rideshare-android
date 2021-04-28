package libraries.android;

public abstract class StringUnchangedListener extends ValueUnchangedListener<String> {
    public StringUnchangedListener(long waitPeriod) {
        super(waitPeriod);
    }
}
