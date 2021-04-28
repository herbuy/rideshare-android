package libraries;

/**
 * This simple library provides a single instance that allows you to encode and decode objects from strings [serialize and deserialize]
 * in a technology agnostic way
 *
 * To use it, u need in set the actual encoder, during start of the program, such as in the main method.
 *
 * Example is given below
 *
 * //In the main method...
 JsonEncoder.setParameters(new JsonEncoder.Parameters(){
 Gson gson = new Gson();
 public String encode(Object object){
 return gson.toJson(object);
 }
 public <T> T decode(String json, Class<T> classOfT){
 return gson.fromJson(json,classOfT);
 }
 });

 //once initialized, u can use in yo code wherever u wan to encode or decode as follows

 String serialized = JsonEncoder.encode(new Object());
 TTRequest deserialized = JsonEncoder.decode("hello",TTRequest.class);


 ** NOTE: a similar wrapper can be made for XML etc by just changing the name of the class
 * THE NAME OF THE CLASS HERE IS ONLY MEANT TO MAKE IT CLEAR THAT WE EXPECT A JSON ENCODER



 */
public class JsonEncoder {
    //the parameter object is the actual object that does the encoding
    private static Parameters parameters;

    public static void setParameters(Parameters parameters) {
        JsonEncoder.parameters = parameters;
    }

    public static String encode(Object object) {
        return parameters.encode(object);
    }

    public static <T> T decode(String object, Class<T> toClassOfT) {
        return parameters.decode(object,toClassOfT);
    }

    public interface Parameters{
    public String encode(Object object);

    public <T> T decode(String json, Class<T> classOfT);
    }
}
