package itboom.com.elgoud.notifications.di;

import dagger.Module;
import dagger.Provides;
import itboom.com.elgoud.app.EljoudApi;
import itboom.com.elgoud.notifications.NotificationAdapter;
import itboom.com.elgoud.notifications.NotificationsFragment;
import itboom.com.elgoud.notifications.mvp.NotificationMVP;
import itboom.com.elgoud.notifications.mvp.NotificationModel;
import itboom.com.elgoud.notifications.mvp.NotificationPresenter;

@Module
public class NotificationModule {

    @Provides
    public NotificationsFragment getNotificationsFragment() {
        return new NotificationsFragment();
    }

    @Provides
    public NotificationAdapter getAdapter() {
        return new NotificationAdapter();
    }

    @Provides
    public NotificationMVP.Model getModel(EljoudApi api) {
        return new NotificationModel(api);
    }

    @Provides
    public NotificationMVP.Presenter getPresenter(NotificationMVP.Model model){
        return new NotificationPresenter(model);
    }
}
