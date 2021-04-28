package libraries;

import java.util.HashMap;

public class HashMapBuilder<K,V> {
    private HashMap<K,V> hashMap = new HashMap<>();


    public HashMapBuilder put(K key, V value){
        hashMap.put(key,value);
        return this;
    }
    public HashMap<K,V> build(){
        return hashMap;
    }
}
