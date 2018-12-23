package itboom.com.elgoud.my_courses.mvp;

import android.util.Log;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import itboom.com.elgoud.app.NetworkResult;
import itboom.com.elgoud.pojo.my_courses.MyCoursesResponse;

public class MyCoursesPresenter implements MyCoursesMVP.Presenter {

    MyCoursesMVP.View view;
    MyCoursesMVP.Model model;
    Disposable disposable;

    public MyCoursesPresenter(MyCoursesMVP.Model model) {
        this.model = model;
    }

    @Override
    public void setView(MyCoursesMVP.View view) {
        this.view = view;
    }

    @Override
    public void getMyCourses(String apiToken, int userId) {
        if (view != null) {
            model.getMyCourse(apiToken, userId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<MyCoursesResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable = d;
                        }

                        @Override
                        public void onSuccess(MyCoursesResponse myCoursesResponse) {
                            if (myCoursesResponse.getStatus() == 200){
                                view.onCoursesSuccess(myCoursesResponse.getData());
                            } else {
                                view.onCoursesFailure(NetworkResult.UNKNOWN_ERROR);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            view.onCoursesFailure(NetworkResult.NETWORK_ERROR);
                            Log.i("network_result", "onError: " + e.getMessage());
                        }
                    });
        }
    }

    @Override
    public void unSubscribe() {
        if (disposable != null && !disposable.isDisposed())
            disposable.dispose();
    }
}
