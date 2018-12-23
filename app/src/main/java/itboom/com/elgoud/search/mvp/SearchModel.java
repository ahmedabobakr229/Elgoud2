package itboom.com.elgoud.search.mvp;

import io.reactivex.Single;
import itboom.com.elgoud.app.EljoudApi;
import itboom.com.elgoud.pojo.search.SearchResponse;

public class SearchModel implements SearchMVP.Model {

    EljoudApi api;

    public SearchModel(EljoudApi api) {
        this.api = api;
    }

    @Override
    public Single<SearchResponse> search(String apiKey, String k) {
        return this.api.search(apiKey, k);
    }
}
