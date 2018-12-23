package itboom.com.elgoud;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.esafirm.imagepicker.features.ImagePicker;
import com.facebook.login.LoginManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.RemoteMessage;
import com.makeramen.roundedimageview.RoundedImageView;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.Slide;
import com.transitionseverywhere.TransitionManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import itboom.com.elgoud.app.App;
import itboom.com.elgoud.app.Constants;
import itboom.com.elgoud.app.CurrentPage;
import itboom.com.elgoud.app.EljoudApi;
import itboom.com.elgoud.app.NetworkResult;
import itboom.com.elgoud.blog.BlogDescriptionFragment;
import itboom.com.elgoud.blog.BlogFragment;
import itboom.com.elgoud.contact_us.ContactUsFragment;
import itboom.com.elgoud.firebase.AljoudFirebase;
import itboom.com.elgoud.free.FreeFragment;
import itboom.com.elgoud.home.HomeFragment;
import itboom.com.elgoud.login.LoginActivity;
import itboom.com.elgoud.my_courses.MyCoursesFragment;
import itboom.com.elgoud.notifications.NotificationsFragment;
import itboom.com.elgoud.notifications.mvp.NotificationMVP;
import itboom.com.elgoud.paid.PaidFragment;
import itboom.com.elgoud.player.PlayerFragment;
import itboom.com.elgoud.pojo.blog.Blog;
import itboom.com.elgoud.pojo.notification.Notification;
import itboom.com.elgoud.pojo.schedule.InnerCourse;
import itboom.com.elgoud.profile.ProfileFragment;
import itboom.com.elgoud.schedule.ScheduleFragment;
import itboom.com.elgoud.search.SearchFragment;
import itboom.com.elgoud.slider.WelcomeActivity;
import itboom.com.elgoud.views.EljoudEditText;
import itboom.com.elgoud.views.EljoudTextView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NotificationMVP.View ,
        AljoudFirebase.NotificationCallback {

    @BindView(R.id.toolbar)
    View toolbar;

    @BindView(R.id.sideMenuContainer)
    FrameLayout sideMenu;

    @BindView(R.id.container)
    FrameLayout container;

    @BindView(R.id.profile)
    LinearLayout profile;

    @BindView(R.id.my_courses)
    LinearLayout myCourses;

    @BindView(R.id.course_schedule)
    LinearLayout courseSchedule;

    @BindView(R.id.contact_us)
    LinearLayout contactUs;

    @BindView(R.id.logout)
    LinearLayout logout;

    @BindView(R.id.navigation)
    BottomNavigationView navigationView;

    @BindView(R.id.username)
    EljoudTextView username;

    @BindView(R.id.email)
    EljoudTextView email;

    @BindView(R.id.profilePic)
    RoundedImageView userImage;

    @Inject
    SharedPreferences preferences;

    @Inject
    HomeFragment homeFragment;

    @Inject
    BlogFragment blogFragment;

    @Inject
    ProfileFragment profileFragment;

    @Inject
    FreeFragment freeFragment;

    @Inject
    PaidFragment paidFragment;

    @Inject
    MyCoursesFragment myCoursesFragment;

    @Inject
    NotificationsFragment notificationsFragment;

    @Inject
    ContactUsFragment contactUsFragment;

    @Inject
    SearchFragment searchFragment;

    @Inject
    ScheduleFragment scheduleFragment;

    @Inject
    PlayerFragment playerFragment;

    @Inject
    BlogDescriptionFragment blogDescriptionFragment;

    @Inject
    EljoudApi api;

    @Inject
    NotificationMVP.Presenter nPresenter;

    CurrentPage currentPage;
    CurrentPage lastPage;
    SearchCallback searchCallback;
    EljoudTextView pageTitle;
    ImageView toggle;
    ImageView notification;
    EljoudEditText searchText;
    ImageView back;
    boolean isSideMenuVisible = false;
    boolean isSearchVisible = false;
    FrameLayout notificationBG;
    EljoudTextView notificationNumber;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (isSideMenuVisible)
                toggleSideMenu();

            switch (item.getItemId()) {
                case R.id.home:
                    animatePageTitle(getString(R.string.home));
                    setCurrentScreen(homeFragment);
                    currentPage = CurrentPage.HOME;
                    return true;
                case R.id.free_courses:
                    animatePageTitle(getString(R.string.free_courses));
                    setCurrentScreen(freeFragment);
                    currentPage = CurrentPage.FREE;
                    return true;
                case R.id.paid_courses:
                    animatePageTitle(getString(R.string.paid_courses));
                    setCurrentScreen(paidFragment);
                    currentPage = CurrentPage.PAID;
                    return true;
                case R.id.blog:
                    animatePageTitle(getString(R.string.blog));
                    setCurrentScreen(blogFragment);
                    currentPage = CurrentPage.BLOG;
                    return true;
                case R.id.profile:
                    animatePageTitle(getString(R.string.profile));
                    setCurrentScreen(profileFragment);
                    currentPage = CurrentPage.PROFILE;
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        ((App) getApplication()).getAppComponent().inject(this);

        AljoudFirebase.callback = this;

        if (FirebaseInstanceId.getInstance().getToken() != null) {
            Log.i("fcm_token", FirebaseInstanceId.getInstance().getToken());
            this.api.updateFCMToken(
                    preferences.getString(Constants.API_TOKEN, null),
                    FirebaseInstanceId.getInstance().getToken(),
                    preferences.getInt(Constants.USER_ID, 0)
            );
        }

        username.setText(preferences.getString(Constants.USERNAME, null));
        email.setText(preferences.getString(Constants.EMAIL, null));

        if (!preferences.getBoolean(Constants.LOGGED_IN, false)) {
            startActivity(new Intent(this, WelcomeActivity.class));
            finish();
        }

        this.updateProfilePicture();

        toggle = toolbar.findViewById(R.id.sideMenu);
        pageTitle = toolbar.findViewById(R.id.pageTitle);
        notification = toolbar.findViewById(R.id.notification);
        searchText = toolbar.findViewById(R.id.searchText);
        back = toolbar.findViewById(R.id.back);
        notificationBG = toolbar.findViewById(R.id.notificationBannerLayout);
        notificationNumber = toolbar.findViewById(R.id.notificationNumber);

        notificationBG.setVisibility(View.GONE);
        notificationNumber.setVisibility(View.GONE);

        nPresenter.setView(this);
        nPresenter.getNotifications(preferences.getString(Constants.API_TOKEN, null),
                preferences.getInt(Constants.USER_ID, 0));

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0){
                    if (currentPage != CurrentPage.SEARCH){
                        animatePageTitle(getString(R.string.search));
                        setCurrentScreen(searchFragment);
                        currentPage = CurrentPage.SEARCH;
                        searchFragment.setKey(s.toString(), preferences.getString(Constants.API_TOKEN, null));
                    } else {
                        searchFragment.setKey(s.toString(), preferences.getString(Constants.API_TOKEN, null));
                    }
                } else {
                    //HideSearch();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPage != CurrentPage.NOTIFICATION) {
                    animatePageTitle(getString(R.string.title_notifications));
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.appContainer, notificationsFragment)
                            .commit();
                    currentPage = CurrentPage.NOTIFICATION;
                    notificationBG.setVisibility(View.GONE);
                    notificationNumber.setVisibility(View.GONE);
                }
            }
        });

        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSideMenu();
            }
        });


        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        profile.setOnClickListener(this);
        myCourses.setOnClickListener(this);
        courseSchedule.setOnClickListener(this);
        contactUs.setOnClickListener(this);
        logout.setOnClickListener(this);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.appContainer, homeFragment)
                .commit();
        currentPage = CurrentPage.HOME;
    }

    private void toggleSideMenu(){
        if (!isSideMenuVisible)
            showSideMenu();
        else
            HideSideMenu();
    }

    private void showSideMenu(){
        if (!isSideMenuVisible){
            Slide slide = new Slide(Gravity.START);
            slide.setDuration(300);
            slide.addTarget(sideMenu);

            TransitionManager.beginDelayedTransition(container, slide);
            sideMenu.setVisibility(View.VISIBLE);
            isSideMenuVisible = true;

        }
    }

    private void HideSideMenu(){
        if (isSideMenuVisible){
            Slide slide = new Slide(Gravity.START);
            slide.setDuration(300);
            slide.addTarget(sideMenu);

            TransitionManager.beginDelayedTransition(container, slide);
            sideMenu.setVisibility(View.GONE);
            isSideMenuVisible = false;
        }
    }

    private void animatePageTitle(String title) {
        Fade slide = new Fade();
        slide.setDuration(300);
        slide.addTarget(pageTitle);

        TransitionManager.beginDelayedTransition(container, slide);
        pageTitle.setText(title);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.profile:
                toggleSideMenu();
                navigationView.setSelectedItemId(R.id.profile);
                break;
            case R.id.my_courses:
                toggleSideMenu();
                if (currentPage != CurrentPage.MY_COURSES) {
                    animatePageTitle(getString(R.string.my_courses));
                    setCurrentScreen(myCoursesFragment);
                    currentPage = CurrentPage.MY_COURSES;
                }
                break;
            case R.id.course_schedule:
                toggleSideMenu();
                if (currentPage != CurrentPage.COURSES_SCHEDULE) {
                    animatePageTitle(getString(R.string.courses_date));
                    setCurrentScreen(scheduleFragment);
                    currentPage = CurrentPage.COURSES_SCHEDULE;
                }
                break;
            case R.id.contact_us:
                toggleSideMenu();
                if (currentPage != CurrentPage.CONTACT_US) {
                    animatePageTitle(getString(R.string.contact_us));
                    setCurrentScreen(contactUsFragment);
                    currentPage = CurrentPage.CONTACT_US;
                }
                break;
            case R.id.logout:
                toggleSideMenu();
                preferences.edit()
                        .putBoolean(Constants.LOGGED_IN, false)
                        .putInt(Constants.USER_ID, 0)
                        .putString(Constants.API_TOKEN, null)
                        .putString(Constants.EMAIL, null)
                        .putString(Constants.USERNAME, null)
                        .putString(Constants.PHONE, null)
                        .putString(Constants.PP_URL, null)
                        .apply();
                if (preferences.getBoolean(Constants.IS_FACEBOOK_USER, false)) {
                    preferences.edit().putBoolean(Constants.IS_FACEBOOK_USER, false).apply();
                    LoginManager.getInstance().logOut();
                }
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                break;
        }
    }

    private void setCurrentScreen(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.appContainer, fragment)
                .commit();
    }

    public void pickImage(){
        ImagePicker.create(this)
                .folderMode(true)
                .toolbarFolderTitle(getString(R.string.folders))
                .toolbarImageTitle(getString(R.string.select_image))
                .toolbarArrowColor(getResources().getColor(R.color.white))
                .includeVideo(false)
                .theme(R.style.ImagePickerTheme)
                .limit(1)
                .start();
    }

    @Override
    public void onNotificationSuccess(itboom.com.elgoud.pojo.notification.Data data) {
        if (data.getNotifications() != null && data.getNotifications().length > 0){
            int notSeenCount = 0;
            for (Notification notification:data.getNotifications()) {
                if (notification.getSeen() == 0){
                    notSeenCount++;
                }
            }

            if (notSeenCount > 0) {
                notificationBG.setVisibility(View.VISIBLE);
                notificationNumber.setVisibility(View.VISIBLE);
                notificationNumber.setText(String.valueOf(notSeenCount));
            }
        }
    }

    @Override
    public void onNotificationFailure(NetworkResult result) {
        notificationBG.setVisibility(View.GONE);
        notificationNumber.setVisibility(View.GONE);
    }

    @Override
    public void onMessageReceived(final RemoteMessage.Notification notification) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.i("receive_message", "onMessageReceived: " + notification.getBody());
                Fade slide = new Fade();
                slide.setDuration(300);
                slide.addTarget(notificationBG);
                slide.addTarget(notificationNumber);

                TransitionManager.beginDelayedTransition(container, slide);
                notificationBG.setVisibility(View.VISIBLE);
                notificationNumber.setVisibility(View.VISIBLE);

                int count = (!notificationNumber.getText().toString().isEmpty()) ?
                        Integer.parseInt(notificationNumber.getText().toString()) + 1 : 1;
                notificationNumber.setText(String.valueOf(count));
            }
        });
    }

    public interface SearchCallback {
        void onSearchTerm(String k);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("profile_fragment", "onActivityResult: activity");

        if (currentPage == CurrentPage.PROFILE) {
            if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
                Log.i("profile_fragment", "onActivityResult: activity");
                profileFragment.setImage(ImagePicker.getFirstImageOrNull(data));
            }
        }
    }

    public void startPlayer(@Nullable itboom.com.elgoud.pojo.course.Course c, @Nullable InnerCourse innerCourse){
        if (currentPage != CurrentPage.PLAYER) {
            if (c != null) {
                animatePageTitle(c.getName());
                playerFragment.setCourse(c);
                playerFragment.setInnerCourse(null);
            } else if (innerCourse != null) {
                animatePageTitle(innerCourse.getName());
                playerFragment.setCourse(null);
                playerFragment.setInnerCourse(innerCourse);
            }

            hideShowBottomNav(true);
            setCurrentScreen(playerFragment);
            lastPage = currentPage;
            currentPage = CurrentPage.PLAYER;
        }
    }

    private void hideShowBottomNav(boolean hide) {
        Slide slide = new Slide(Gravity.BOTTOM);
        slide.setDuration(300);
        slide.addTarget(navigationView);

        TransitionManager.beginDelayedTransition(container, slide);
        if (hide) {
//            navigationView.setVisibility(View.GONE);
//            toggle.setVisibility(View.GONE);
//            notification.setVisibility(View.GONE);
//            back.setVisibility(View.GONE);
        }
        else {
//            navigationView.setVisibility(View.VISIBLE);
//            toggle.setVisibility(View.VISIBLE);
//            notification.setVisibility(View.VISIBLE);
//            back.setVisibility(View.VISIBLE);
        }
    }

    public void startBlogDescription(Blog blog){
        if (currentPage != CurrentPage.BLOG_DESCRIPTION) {
            blogDescriptionFragment.setBlog(blog);
            setCurrentScreen(blogDescriptionFragment);
            animatePageTitle(blog.getTitle());
            lastPage = currentPage;
            currentPage = CurrentPage.BLOG_DESCRIPTION;
        }
    }

    public void updateProfilePicture(){
        if (preferences.getString(Constants.PP_URL, null) != null){
            Transformation transformation = new RoundedTransformationBuilder()
                    .oval(false)
                    .build();

            Picasso.with(this)
                    .load(preferences.getString(Constants.PP_URL, null))
                    .fit()
                    .transform(transformation)
                    .into(userImage);
        }
    }

    public void startPaid() {
        animatePageTitle(getString(R.string.paid_courses));
        setCurrentScreen(paidFragment);
        lastPage = currentPage;
        currentPage = CurrentPage.PAID;
    }

    @Override
    public void onBackPressed() {
        if (currentPage == CurrentPage.PLAYER || currentPage == CurrentPage.SEARCH || currentPage == CurrentPage.BLOG_DESCRIPTION) {
            animatePageTitle(getString(R.string.home));
            hideShowBottomNav(false);
            switch (lastPage) {
                case FREE:
                    setCurrentScreen(freeFragment);
                    animatePageTitle(getString(R.string.free_courses));
                    currentPage = CurrentPage.FREE;
                    navigationView.setSelectedItemId(R.id.free_courses);
                    break;
                case HOME:
                    setCurrentScreen(homeFragment);
                    animatePageTitle(getString(R.string.home));
                    currentPage = CurrentPage.HOME;
                    navigationView.setSelectedItemId(R.id.home);
                    break;
                case BLOG:
                    setCurrentScreen(blogFragment);
                    animatePageTitle(getString(R.string.blog));
                    currentPage = CurrentPage.BLOG;
                    navigationView.setSelectedItemId(R.id.blog);
                    break;
                default:
                    setCurrentScreen(homeFragment);
                    animatePageTitle(getString(R.string.home));
                    currentPage = CurrentPage.HOME;
            }
        } else {
            super.onBackPressed();
        }
    }
}
