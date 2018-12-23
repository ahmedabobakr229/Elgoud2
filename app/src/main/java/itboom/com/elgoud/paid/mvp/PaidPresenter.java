package itboom.com.elgoud.paid.mvp;

import android.util.Log;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import itboom.com.elgoud.app.Constants;
import itboom.com.elgoud.app.NetworkResult;
import itboom.com.elgoud.home.mvp.HomeMVP;
import itboom.com.elgoud.pojo.base.BaseResponse;
import itboom.com.elgoud.pojo.course.CourseResponse;

public class PaidPresenter implements PaidMVP.Presenter {
    PaidMVP.Model model;
    PaidMVP.View view;
    Disposable disposable;

    public PaidPresenter(PaidMVP.Model model) {
        this.model = model;
    }

    @Override
    public void setView(PaidMVP.View view) {
        this.view = view;
    }

    @Override
    public void getCourses(String apiToken, int userId) {
        if (view != null){
            model.getCourses(apiToken, userId)
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
    public void sendRequest(String apiToken, int user_id, String deviceId, int course_id) {
        if (view != null) {
            model.reserveCourse(apiToken, user_id, deviceId, course_id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<BaseResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable = d;
                        }

                        @Override
                        public void onSuccess(BaseResponse response) {
                            if (response.getStatus() == 200){
                                view.onRequestSuccess(response);
                            } else if (response.getStatus() == 500){
                                view.onRequestFailure(NetworkResult.COURSE_ALREADY_RESERVED);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            view.onRequestFailure(NetworkResult.NETWORK_ERROR);
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
