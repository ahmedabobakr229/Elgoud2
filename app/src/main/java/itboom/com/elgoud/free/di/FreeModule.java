package itboom.com.elgoud.free.di;

import dagger.Module;
import dagger.Provides;
import itboom.com.elgoud.app.EljoudApi;
import itboom.com.elgoud.free.FreeFragment;
import itboom.com.elgoud.free.mvp.FreeMVP;
import itboom.com.elgoud.free.mvp.FreeModel;
import itboom.com.elgoud.free.mvp.FreePresenter;

@Module
public class FreeModule {
    @Provides
    public FreeFragment getFreeFragment(){
        return new FreeFragment();
    }

    @Provides
    public FreeMVP.Presenter getPresenter(FreeMVP.Model model){
        return new FreePresenter(model);
    }

    @Provides
    public FreeMVP.Model getModel(EljoudApi api){
        return new FreeModel(api);
    }
}
