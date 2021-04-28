package cache;

import com.google.gson.Gson;

import libraries.data.LocalDatabase;

public abstract class AppDatabase<T> extends LocalDatabase<T> {
    private static Gson gson = new Gson();

    @Override
    protected final T deserialize(String serializedObject) {

        return gsonDeserialize(serializedObject);
    }

    private T gsonDeserialize(String serializedObject) {
        return gson.fromJson(serializedObject, getClassOfT());
    }

    protected abstract Class<T> getClassOfT();

    @Override
    protected final String serialize(T value) {
        return gson.toJson(value);
    }
}
