package itboom.com.elgoud.register.di;

import dagger.Module;
import dagger.Provides;
import itboom.com.elgoud.app.EljoudApi;
import itboom.com.elgoud.register.mvp.RegisterMVP;
import itboom.com.elgoud.register.mvp.RegisterModel;
import itboom.com.elgoud.register.mvp.RegisterPresenter;

@Module
public class RegisterModule {
    @Provides
    public RegisterMVP.Model getModel(EljoudApi api){
        return new RegisterModel(api);
    }

    @Provides
    public RegisterMVP.Presenter getPresenter(RegisterMVP.Model model){
        return new RegisterPresenter(model);
    }
}
