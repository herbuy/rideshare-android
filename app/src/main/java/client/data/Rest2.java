package client.data;


import java.io.IOException;

import core.businessmessages.fromServer.ServerResponse;
import libraries.JsonEncoder;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Rest2 {
    private static Retrofit retrofit;

    public static Api2 api() {
        makeRetrofitIfNull();
        return retrofit.create(Api2.class);
    }

    private static void makeRetrofitIfNull() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Api2.BASE_URL)
                    .client(makeClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }

    private static OkHttpClient makeClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {

                    @Override
                    public Response intercept(Chain chain) throws IOException {


                        Request request = chain.request();
                        //Log.d("TO URL", request.url().toString());
                        //Log.d("REQUEST METHOD", request.method());

                        Response response = chain.proceed(request);

                        String body = response.body().string();

                        ServerResponse serverResponse = JsonEncoder.decode(body,ServerResponse.class);
                        if(serverResponse == null){
                            throw new IOException("response format can not be decoded to server response");
                        }

                        if(serverResponse.getError() != null && !serverResponse.getError().trim().equalsIgnoreCase("") ){
                            throw new IOException(serverResponse.getError());
                        }

                        return response.newBuilder()
                                .body(
                                        ResponseBody.create(
                                                response.body().contentType(),
                                                JsonEncoder.encode(serverResponse.getResponse())
                                        )
                                )
                                .build();

                    }
                })

                .build();

    }


}

