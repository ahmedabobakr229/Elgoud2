package itboom.com.elgoud.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import itboom.com.elgoud.R;
import itboom.com.elgoud.player.PlaylistAdapter;

@Module
public class AppModule {
    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Singleton
    @Provides
    public Application getApplication(){
        return  application;
    }

    @Singleton
    @Provides
    public SharedPreferences getSharedPreferences(){
        return getApplication().getSharedPreferences(application.getString(R.string.app_name), Context.MODE_PRIVATE);
    }

    @Singleton
    @Provides
    public PlaylistAdapter getPlaylistAdapter(){
        return new PlaylistAdapter();
    }
}
