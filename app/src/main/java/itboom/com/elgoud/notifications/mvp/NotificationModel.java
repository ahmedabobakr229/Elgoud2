package itboom.com.elgoud.notifications.mvp;

import io.reactivex.Single;
import itboom.com.elgoud.app.EljoudApi;
import itboom.com.elgoud.pojo.base.BaseResponse;
import itboom.com.elgoud.pojo.notification.NotificationResponse;

public class NotificationModel implements NotificationMVP.Model {

    EljoudApi api;

    public NotificationModel(EljoudApi api) {
        this.api = api;
    }

    @Override
    public Single<NotificationResponse> getNotification(String apiToken, int userId) {
        return this.api.getNotifications(apiToken, userId);
    }

    @Override
    public Single<BaseResponse> makeSeen(String apiToken, int userId, int notificationId) {
        return this.api.makeSeen(apiToken, userId, notificationId);
    }
}
