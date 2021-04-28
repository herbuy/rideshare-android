package characters;

import java.util.List;

public interface DownloadFinishedListener<T> {

    public void onSuccess(T responseBody);
    public void onError(String error);
}
