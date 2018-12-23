package itboom.com.elgoud.app;

import javax.inject.Singleton;

import dagger.Component;
import itboom.com.elgoud.blog.BlogFragment;
import itboom.com.elgoud.blog.di.BlogModule;
import itboom.com.elgoud.contact_us.ContactUsFragment;
import itboom.com.elgoud.contact_us.di.ContactUsModule;
import itboom.com.elgoud.free.FreeFragment;
import itboom.com.elgoud.free.di.FreeModule;
import itboom.com.elgoud.home.HomeFragment;
import itboom.com.elgoud.home.di.HomeModule;
import itboom.com.elgoud.login.LoginActivity;
import itboom.com.elgoud.MainActivity;
import itboom.com.elgoud.login.di.LoginModule;
import itboom.com.elgoud.my_courses.MyCoursesFragment;
import itboom.com.elgoud.my_courses.di.MyCoursesModule;
import itboom.com.elgoud.notifications.NotificationsFragment;
import itboom.com.elgoud.notifications.di.NotificationModule;
import itboom.com.elgoud.paid.PaidFragment;
import itboom.com.elgoud.paid.di.PaidModule;
import itboom.com.elgoud.player.PlayerFragment;
import itboom.com.elgoud.player.di.PlayerModule;
import itboom.com.elgoud.profile.ProfileFragment;
import itboom.com.elgoud.profile.di.ProfileModule;
import itboom.com.elgoud.register.RegisterActivity;
import itboom.com.elgoud.register.di.RegisterModule;
import itboom.com.elgoud.schedule.ScheduleFragment;
import itboom.com.elgoud.schedule.di.ScheduleModule;
import itboom.com.elgoud.search.SearchFragment;
import itboom.com.elgoud.search.di.SearchModule;
import itboom.com.elgoud.slider.ImageSliderFragment;
import itboom.com.elgoud.slider.WelcomeActivity;
import itboom.com.elgoud.slider.di.SliderModule;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class, RegisterModule.class, LoginModule.class, HomeModule.class,
        BlogModule.class, FreeModule.class, PaidModule.class, ProfileModule.class, NotificationModule.class, SearchModule.class,
        MyCoursesModule.class, ContactUsModule.class, ScheduleModule.class, PlayerModule.class, SliderModule.class})
public interface AppComponent {
    void inject(RegisterActivity activity);
    void inject(LoginActivity activity);
    void inject(MainActivity activity);
    void inject(HomeFragment fragment);
    void inject(BlogFragment fragment);
    void inject(FreeFragment fragment);
    void inject(PaidFragment fragment);
    void inject(ProfileFragment fragment);
    void inject(NotificationsFragment fragment);
    void inject(MyCoursesFragment fragment);
    void inject(ContactUsFragment fragment);
    void inject(PlayerFragment fragment);
    void inject(SearchFragment fragment);
    void inject(ScheduleFragment fragment);
    void inject(ImageSliderFragment fragment);
    void inject(WelcomeActivity activity);
}
