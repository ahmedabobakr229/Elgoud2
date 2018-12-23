package itboom.com.elgoud.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Locale;

import itboom.com.elgoud.blog.di.BlogModule;
import itboom.com.elgoud.contact_us.di.ContactUsModule;
import itboom.com.elgoud.free.di.FreeModule;
import itboom.com.elgoud.home.di.HomeModule;
import itboom.com.elgoud.login.di.LoginModule;
import itboom.com.elgoud.my_courses.di.MyCoursesModule;
import itboom.com.elgoud.notifications.di.NotificationModule;
import itboom.com.elgoud.paid.di.PaidModule;
import itboom.com.elgoud.player.di.PlayerModule;
import itboom.com.elgoud.profile.di.ProfileModule;
import itboom.com.elgoud.register.di.RegisterModule;
import itboom.com.elgoud.schedule.di.ScheduleModule;
import itboom.com.elgoud.search.di.SearchModule;
import itboom.com.elgoud.slider.di.SliderModule;


public class App extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule())
                .registerModule(new RegisterModule())
                .loginModule(new LoginModule())
                .homeModule(new HomeModule())
                .blogModule(new BlogModule())
                .freeModule(new FreeModule())
                .paidModule(new PaidModule())
                .profileModule(new ProfileModule())
                .notificationModule(new NotificationModule())
                .searchModule(new SearchModule())
                .myCoursesModule(new MyCoursesModule())
                .contactUsModule(new ContactUsModule())
                .scheduleModule(new ScheduleModule())
                .playerModule(new PlayerModule())
                .sliderModule(new SliderModule())
                .build();

//        String languageToLoad  = "ar";
//        Locale locale = new Locale(languageToLoad);
//        Locale.setDefault(locale);
//        Configuration config = new Configuration();
//        config.locale = locale;
//        getBaseContext().getResources().updateConfiguration(config,
//                getBaseContext().getResources().getDisplayMetrics());
    }

    public AppComponent getAppComponent() {
        return this.appComponent;
    }
}
