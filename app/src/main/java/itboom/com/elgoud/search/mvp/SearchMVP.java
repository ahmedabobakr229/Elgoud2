package itboom.com.elgoud.search.mvp;

import io.reactivex.Single;
import itboom.com.elgoud.app.NetworkResult;
import itboom.com.elgoud.pojo.search.Data;
import itboom.com.elgoud.pojo.search.SearchResponse;

public interface SearchMVP {
    interface View {
        void onSearchSuccess(Data data);
        void onSearchFailure(NetworkResult result);
    }

    interface Presenter {
        void setView(View view);
        void search(String apiKey, String k);
        void unSubscribe();
    }

    interface Model {
        Single<SearchResponse> search(String apiKey, String k);
    }
}
