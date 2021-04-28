package stream;

import java.util.List;

import client.data.AppCallback;
import client.data.Rest;
import core.businessobjects.NewsFeedItem;
import libraries.DataScript;
import retrofit2.Call;
import retrofit2.Response;

public class StreamDataFetchScript extends DataScript.DataFetchScript<List<NewsFeedItem>> {
    @Override
    public void fetchData(final DataScript.DataFetchResult<List<NewsFeedItem>> result) {
        Rest.api().getStreamItems().enqueue(new AppCallback<List<NewsFeedItem>>() {
            @Override
            protected void onSuccess(Call<List<NewsFeedItem>> call, Response<List<NewsFeedItem>> response) {
                result.success(response.body());
            }

            @Override
            protected void onError(Call<List<NewsFeedItem>> call, Throwable t) {
                result.error(t.getMessage());
            }
        });
    }
}
