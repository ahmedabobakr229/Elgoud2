package itboom.com.elgoud.login.di;

import dagger.Module;
import dagger.Provides;
import itboom.com.elgoud.app.EljoudApi;
import itboom.com.elgoud.login.mvp.LoginMVP;
import itboom.com.elgoud.login.mvp.LoginModel;
import itboom.com.elgoud.login.mvp.LoginPresenter;

@Module
public class LoginModule {
    @Provides
    public LoginMVP.Presenter getPresenter(LoginMVP.Model model){
        return new LoginPresenter(model);
    }

    @Provides
    public LoginMVP.Model getModel(EljoudApi api){
        return new LoginModel(api);
    }
}
