package itboom.com.elgoud.my_courses.di;

import dagger.Module;
import dagger.Provides;
import itboom.com.elgoud.app.EljoudApi;
import itboom.com.elgoud.my_courses.MyCoursesAdapter;
import itboom.com.elgoud.my_courses.MyCoursesFragment;
import itboom.com.elgoud.my_courses.mvp.MyCoursesMVP;
import itboom.com.elgoud.my_courses.mvp.MyCoursesModel;
import itboom.com.elgoud.my_courses.mvp.MyCoursesPresenter;

@Module
public class MyCoursesModule {
    @Provides
    public MyCoursesFragment getMyCoursesFragment() {
        return new MyCoursesFragment();
    }

    @Provides
    public MyCoursesMVP.Model getModel(EljoudApi api) {
        return new MyCoursesModel(api);
    }

    @Provides
    public MyCoursesMVP.Presenter getPresenter(MyCoursesMVP.Model model){
        return new MyCoursesPresenter(model);
    }

    @Provides
    public MyCoursesAdapter getAdapter() {
        return new MyCoursesAdapter();
    }
}
