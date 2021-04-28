package libraries;

public class SecondsSinceEpoch {
    public interface Implementation {
        long get();
    }
    private static Implementation implementation;

    public static void setImplementation(Implementation implementation) {
        SecondsSinceEpoch.implementation = implementation;
    }

    public static long get() {
        if(implementation != null){
            return implementation.get();
        }
        throw new RuntimeException("Pass in Seconds Since Epoch implementation, at program start perhaps");
    }
}
