package itboom.com.elgoud.home.mvp;

import android.util.Log;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import itboom.com.elgoud.app.Constants;
import itboom.com.elgoud.app.NetworkResult;
import itboom.com.elgoud.pojo.course.CourseResponse;

public class HomePresenter implements HomeMVP.Presenter {

    HomeMVP.Model model;
    HomeMVP.View view;
    Disposable disposable;

    public HomePresenter(HomeMVP.Model model) {
        this.model = model;
    }

    @Override
    public void setView(HomeMVP.View view) {
        this.view = view;
    }

    @Override
    public void getCourses(String apiToken) {
        if (view != null){
            model.getCourses(apiToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<CourseResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable = d;
                        }

                        @Override
                        public void onSuccess(CourseResponse courseResponse) {
                            Log.i(Constants.PRESENTER_RESPONSE, "onSuccess: " + courseResponse.getStatus());
                            if (courseResponse.getStatus() == 200){
                                if (courseResponse.getData() != null && courseResponse.getData().getCourses() != null
                                        && courseResponse.getData().getCourses().length > 0) {
                                    view.onCoursesSuccess(courseResponse.getData());
                                } else {
                                    view.onCoursesFailure(NetworkResult.NO_ITEM_AVAILABLE);
                                }
                            } else {
                                view.onCoursesFailure(NetworkResult.UNKNOWN_ERROR);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.i(Constants.PRESENTER_ERROR, "onError: " + e.getMessage());
                            view.onCoursesFailure(NetworkResult.UNKNOWN_ERROR);
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
