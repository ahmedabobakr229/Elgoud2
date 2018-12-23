package itboom.com.elgoud.search.mvp;

import android.util.Log;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import itboom.com.elgoud.app.Constants;
import itboom.com.elgoud.app.NetworkResult;
import itboom.com.elgoud.pojo.search.SearchResponse;

public class SearchPresenter implements SearchMVP.Presenter {

    SearchMVP.View view;
    SearchMVP.Model model;
    Disposable disposable;

    public SearchPresenter(SearchMVP.Model model) {
        this.model = model;
    }

    @Override
    public void setView(SearchMVP.View view) {
        this.view = view;
    }

    @Override
    public void search(String apiKey, String k) {
        if (view != null) {
            model.search(apiKey, k)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<SearchResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable = d;
                        }

                        @Override
                        public void onSuccess(SearchResponse searchResponse) {
                            Log.i(Constants.PRESENTER_RESPONSE, "onSuccess: " + searchResponse.getStatus());
                            if (searchResponse.getStatus() == 200){
                                view.onSearchSuccess(searchResponse.getData());
                            } else {
                                view.onSearchFailure(NetworkResult.UNKNOWN_ERROR);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.i(Constants.PRESENTER_ERROR, "onError: " + e.getMessage());
                            view.onSearchFailure(NetworkResult.NETWORK_ERROR);
                        }
                    });
        }
    }

    @Override
    public void unSubscribe() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
