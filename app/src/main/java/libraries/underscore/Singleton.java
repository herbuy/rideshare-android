package libraries.underscore;


public abstract class Singleton<ReturnType> {
    private ReturnType value;
    public ReturnType instance(){
        if(value == null){
            value = onCreate();
        }
        return value;
    }

    protected abstract ReturnType onCreate();

}
