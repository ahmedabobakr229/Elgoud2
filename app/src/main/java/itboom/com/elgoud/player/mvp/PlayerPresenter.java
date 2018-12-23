package itboom.com.elgoud.player.mvp;

import android.util.Log;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import itboom.com.elgoud.app.NetworkResult;
import itboom.com.elgoud.pojo.single_course.SingleCourseResponse;

public class PlayerPresenter implements PlayerMVP.Presenter {

    PlayerMVP.View view;
    PlayerMVP.Model model;
    Disposable disposable;

    public PlayerPresenter(PlayerMVP.Model model) {
        this.model = model;
    }

    @Override
    public void setView(PlayerMVP.View view) {
        this.view = view;
    }

    @Override
    public void getCourse(String apiToken, int courseId) {
        if (view != null){
            model.getCourse(apiToken, courseId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<SingleCourseResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable = d;
                        }

                        @Override
                        public void onSuccess(SingleCourseResponse singleCourseResponse) {
                            Log.i("network_result", "onSuccess: " + singleCourseResponse.getStatus());
                            if (singleCourseResponse.getStatus() == 200){
                                if (singleCourseResponse.getVideos() != null){
                                    view.onCourseSuccess(singleCourseResponse.getVideos());
                                } else {
                                    view.onCourseError(NetworkResult.NO_ITEM_AVAILABLE);
                                }
                            } else {
                                view.onCourseError(NetworkResult.NETWORK_ERROR);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d("network_result", "onError: " + e.getMessage());
                            view.onCourseError(NetworkResult.NETWORK_ERROR);
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
