package itboom.com.elgoud.player.di;

import dagger.Module;
import dagger.Provides;
import itboom.com.elgoud.app.EljoudApi;
import itboom.com.elgoud.player.mvp.PlayerMVP;
import itboom.com.elgoud.player.mvp.PlayerModel;
import itboom.com.elgoud.player.mvp.PlayerPresenter;

@Module
public class PlayerModule {
    @Provides
    public PlayerMVP.Model getModel(EljoudApi api){
        return new PlayerModel(api);
    }

    @Provides
    public PlayerMVP.Presenter getPresenter(PlayerMVP.Model model){
        return new PlayerPresenter(model);
    }
}
