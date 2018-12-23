package itboom.com.elgoud.schedule.di;

import dagger.Module;
import dagger.Provides;
import itboom.com.elgoud.app.EljoudApi;
import itboom.com.elgoud.schedule.ScheduleAdapter;
import itboom.com.elgoud.schedule.ScheduleFragment;
import itboom.com.elgoud.schedule.mvp.ScheduleMVP;
import itboom.com.elgoud.schedule.mvp.ScheduleModel;
import itboom.com.elgoud.schedule.mvp.SchedulePresenter;

@Module
public class ScheduleModule {

    @Provides
    public ScheduleFragment getScheduleFragment() {
        return new ScheduleFragment();
    }

    @Provides
    public ScheduleMVP.Model getModel(EljoudApi api) {
        return new ScheduleModel(api);
    }

    @Provides
    public ScheduleMVP.Presenter getPresenter(ScheduleMVP.Model model){
        return new SchedulePresenter(model);
    }

    @Provides
    public ScheduleAdapter getAdapter() {
        return new ScheduleAdapter();
    }
}
