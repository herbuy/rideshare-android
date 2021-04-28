package libraries.underscore;

public abstract class Interceptor<TypeIntercepted> {
    public abstract TypeIntercepted intercept(TypeIntercepted input);

    public static abstract class OfString extends Interceptor<String>{

    }
}
