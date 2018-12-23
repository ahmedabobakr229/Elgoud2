package itboom.com.elgoud.home.di;

import dagger.Module;
import dagger.Provides;
import itboom.com.elgoud.app.EljoudApi;
import itboom.com.elgoud.blog.BlogDescriptionFragment;
import itboom.com.elgoud.course_adapter.CourseRecyclerAdapter;
import itboom.com.elgoud.home.HomeFragment;
import itboom.com.elgoud.home.mvp.HomeMVP;
import itboom.com.elgoud.home.mvp.HomeModel;
import itboom.com.elgoud.home.mvp.HomePresenter;
import itboom.com.elgoud.player.PlayerFragment;

@Module
public class HomeModule {
    @Provides
    public HomeFragment getHomeFragment(){
        return new HomeFragment();
    }

    @Provides
    public HomeMVP.Presenter getPresenter(HomeMVP.Model model){
        return new HomePresenter(model);
    }

    @Provides
    public HomeMVP.Model getModel(EljoudApi api){
        return new HomeModel(api);
    }

    @Provides
    public CourseRecyclerAdapter getCourseRecyclerAdapter(){
        return new CourseRecyclerAdapter();
    }

    @Provides
    public PlayerFragment getPlayerFragment() {
        return new PlayerFragment();
    }

    @Provides
    public BlogDescriptionFragment getBlogDescriptionFragment() {
        return new BlogDescriptionFragment();
    }
}
