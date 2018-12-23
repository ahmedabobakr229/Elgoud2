package itboom.com.elgoud.notifications.mvp;

import io.reactivex.Single;
import itboom.com.elgoud.app.NetworkResult;
import itboom.com.elgoud.pojo.base.BaseResponse;
import itboom.com.elgoud.pojo.notification.Data;
import itboom.com.elgoud.pojo.notification.NotificationResponse;

public interface NotificationMVP {
    interface View {
        void onNotificationSuccess(Data data);
        void onNotificationFailure(NetworkResult result);
    }

    interface Presenter {
        void setView(View view);
        void getNotifications(String apiToken, int userId);
        void makeSeen(String apiToken, int userId, int notificationId);
        void rxUnsubscribe();
    }

    interface Model {
        Single<NotificationResponse> getNotification(String apiToken, int userId);
        Single<BaseResponse> makeSeen(String apiToken, int userId, int notificationId);
    }
}
