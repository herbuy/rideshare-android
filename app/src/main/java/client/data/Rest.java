package client.data;

public class Rest {

    private static HerbuyApi api;

    public static HerbuyApi api() {
        if(api == null){
            api = makeAPI();
        }
        return api;
    }

    private static HerbuyApi makeAPI() {
        return new HerbuyApi() {
        };
    }
}
