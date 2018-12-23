package itboom.com.elgoud.notifications.mvp;

import android.util.Log;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import itboom.com.elgoud.app.NetworkResult;
import itboom.com.elgoud.pojo.base.BaseResponse;
import itboom.com.elgoud.pojo.notification.NotificationResponse;

public class NotificationPresenter implements NotificationMVP.Presenter {

    NotificationMVP.View view;
    NotificationMVP.Model model;
    Disposable disposable;

    public NotificationPresenter(NotificationMVP.Model model) {
        this.model = model;
    }

    @Override
    public void setView(NotificationMVP.View view) {
        this.view = view;
    }

    @Override
    public void getNotifications(String apiToken, int userId) {
        if (view != null) {
            this.model.getNotification(apiToken, userId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<NotificationResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable = d;
                        }

                        @Override
                        public void onSuccess(NotificationResponse notificationResponse) {
                            if (notificationResponse.getStatus() == 200) {
                                view.onNotificationSuccess(notificationResponse.getData());
                            } else {
                                view.onNotificationFailure(NetworkResult.NETWORK_ERROR);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.i("network_result", "onError: " + e.getMessage());
                            view.onNotificationFailure(NetworkResult.NETWORK_ERROR);
                        }
                    });
        }
    }

    @Override
    public void makeSeen(String apiToken, int userId, int notificationId) {
        if (view != null) {
            model.makeSeen(apiToken, userId, notificationId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<BaseResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onSuccess(BaseResponse response) {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }
                    });
        }
    }

    @Override
    public void rxUnsubscribe() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
