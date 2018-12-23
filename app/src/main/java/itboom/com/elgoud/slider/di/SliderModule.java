package itboom.com.elgoud.slider.di;

import dagger.Module;
import dagger.Provides;
import itboom.com.elgoud.app.EljoudApi;
import itboom.com.elgoud.slider.mvp.SliderMVP;
import itboom.com.elgoud.slider.mvp.SliderModel;
import itboom.com.elgoud.slider.mvp.SliderPresenter;

@Module
public class SliderModule {
    @Provides
    public SliderMVP.Presenter getPresenter(SliderMVP.Model model){
        return new SliderPresenter(model);
    }

    @Provides
    public SliderMVP.Model getModel(EljoudApi api){
        return new SliderModel(api);
    }
}
