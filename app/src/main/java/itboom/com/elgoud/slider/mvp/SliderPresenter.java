package itboom.com.elgoud.slider.mvp;

import android.util.Log;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import itboom.com.elgoud.app.NetworkResult;
import itboom.com.elgoud.pojo.slider.SliderResponse;

public class SliderPresenter implements SliderMVP.Presenter {

    SliderMVP.View view;
    SliderMVP.Model model;
    Disposable disposable;

    public SliderPresenter(SliderMVP.Model model) {
        this.model = model;
    }

    @Override
    public void setView(SliderMVP.View view) {
        this.view = view;
    }

    @Override
    public void getImage(String apiToken) {
        if (view != null) {
            model.getImages(apiToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<SliderResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable = d;
                        }

                        @Override
                        public void onSuccess(SliderResponse sliderResponse) {
                            if (sliderResponse.getStatus() == 200){
                                if (sliderResponse.getSlider() != null && sliderResponse.getSlider().length > 0) {
                                    view.onSuccess(sliderResponse.getSlider());
                                } else {
                                    view.onError(NetworkResult.NO_ITEM_AVAILABLE);
                                }
                            } else {
                                view.onError(NetworkResult.UNKNOWN_ERROR);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("slider_response", "onError: " + e.getMessage());
                            view.onError(NetworkResult.NETWORK_ERROR);
                        }
                    });
        }
    }

    @Override
    public void rxUnsubscribe() {
        if (disposable != null && !disposable.isDisposed()){
            disposable.dispose();
        }
    }
}
