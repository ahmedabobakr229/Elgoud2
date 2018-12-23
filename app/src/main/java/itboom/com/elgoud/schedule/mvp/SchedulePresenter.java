package itboom.com.elgoud.schedule.mvp;

import android.util.Log;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import itboom.com.elgoud.app.Constants;
import itboom.com.elgoud.app.NetworkResult;
import itboom.com.elgoud.pojo.my_courses.MyCoursesResponse;
import itboom.com.elgoud.pojo.schedule.IsOpen;
import itboom.com.elgoud.pojo.schedule.ScheduleResponse;

public class SchedulePresenter implements ScheduleMVP.Presenter {

    ScheduleMVP.View view;
    ScheduleMVP.Model model;
    Disposable disposable;

    public SchedulePresenter(ScheduleMVP.Model model) {
        this.model = model;
    }

    @Override
    public void setView(ScheduleMVP.View view) {
        this.view = view;
    }

    @Override
    public void getSchedule(String apiToken, int userId) {
        if (view != null){
            model.getSchedule(apiToken, userId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<ScheduleResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable = d;
                        }

                        @Override
                        public void onSuccess(ScheduleResponse scheduleResponse) {
                            if (scheduleResponse.getStatus() == 200){
                                if(scheduleResponse.getData() != null && scheduleResponse.getData().getCourses() != null
                                        && scheduleResponse.getData().getCourses().length > 0)
                                    view.onScheduleSuccess(scheduleResponse.getData());
                                else
                                    view.onScheduleFailure(NetworkResult.NO_ITEM_AVAILABLE);
                            } else {
                                view.onScheduleFailure(NetworkResult.UNKNOWN_ERROR);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.i(Constants.PRESENTER_ERROR, "onError: " + e.getMessage());
                            view.onScheduleFailure(NetworkResult.NETWORK_ERROR);
                        }
                    });
        }
    }

    @Override
    public void isOpen(String apiToken, int userId, int CourseId) {
        if (view != null){
            model.isOpen(apiToken, userId, CourseId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<IsOpen>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable  = d;
                        }

                        @Override
                        public void onSuccess(IsOpen isOpen) {
                            Log.i(Constants.PRESENTER_RESPONSE, "onSuccess: " + isOpen.getStatus());
                            if (isOpen.getStatus() == 200){
                                if (isOpen.isOpen()){
                                    view.onOpenSuccess(true);
                                } else {
                                    view.onOpenSuccess(false);
                                }
                            } else {
                                view.onScheduleFailure(NetworkResult.UNKNOWN_ERROR);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.i(Constants.PRESENTER_ERROR, "onError: " + e.getMessage());
                            view.onScheduleFailure(NetworkResult.UNKNOWN_ERROR);
                        }
                    });
        }
    }

    @Override
    public void unSubscribe() {
        if (disposable != null && !disposable.isDisposed()){
            disposable.dispose();
        }
    }
}
