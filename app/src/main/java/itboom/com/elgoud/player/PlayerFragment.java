package itboom.com.elgoud.player;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import itboom.com.elgoud.R;
import itboom.com.elgoud.app.App;
import itboom.com.elgoud.app.Constants;
import itboom.com.elgoud.app.NetworkResult;
import itboom.com.elgoud.player.mvp.PlayerMVP;
import itboom.com.elgoud.pojo.course.Course;
import itboom.com.elgoud.pojo.course.PlaylistItem;
import itboom.com.elgoud.pojo.schedule.InnerCourse;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerInitListener;

import javax.inject.Inject;

public class PlayerFragment extends Fragment implements PlaylistAdapter.PlaylistCallback , PlayerMVP.View {

    public static final String COURSE_EXTRA = "course_extra";
    public static final String INNER_COURSE_EXTRA = "inner_course_extra";

    private Course course;
    private InnerCourse innerCourse;

    @BindView(R.id.youtube_player_view)
    YouTubePlayerView playerView;

    @BindView(R.id.playListRecView)
    RecyclerView recyclerView;

    @Inject
    PlaylistAdapter adapter;

    @Inject
    PlayerMVP.Presenter presenter;

    @Inject
    SharedPreferences preferences;

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setInnerCourse(InnerCourse innerCourse) {
        this.innerCourse = innerCourse;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment, container, false);

        ((App) getActivity().getApplication()).getAppComponent().inject(this);
        ButterKnife.bind(this, view);

        presenter.setView(this);

        playerView.getPlayerUIController().showYouTubeButton(false);
        playerView.getPlayerUIController().showVideoTitle(false);

        if (this.course != null) {
            //Log.i("playlist_items", "onCreateView: " + course.getPlaylists().length);
            if (course.getPlaylists() != null && course.getPlaylists()[0] != null
                    && course.getPlaylists()[0].getYoutubeVideoId() != null){
                String[] list = new String[course.getPlaylists().length];
                for (int i = 0; i < course.getPlaylists().length; i++) {
                    list[i] = course.getPlaylists()[0].getYoutubeVideoId();
                }
                adapter.setItems(list);
                adapter.setCallback(this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                updatePlayer(course.getPlaylists()[0].getYoutubeVideoId());
            } else {
                Log.i("fragment_course", "onCreateView: ");
                presenter.getCourse(
                        preferences.getString(Constants.API_TOKEN, null),
                        course.getId()
                );
            }
        } else if (this.innerCourse != null) {
            if (innerCourse.getPlaylistItems() != null && innerCourse.getPlaylistItems()[0] != null
                    && innerCourse.getPlaylistItems()[0].getYoutubeVideoId() != null){
                String[] list = new String[innerCourse.getPlaylistItems().length];
                for (int i = 0; i < innerCourse.getPlaylistItems().length; i++) {
                    list[i] = innerCourse.getPlaylistItems()[0].getYoutubeVideoId();
                }
                adapter.setItems(list);
                adapter.setCallback(this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                updatePlayer(innerCourse.getPlaylistItems()[0].getYoutubeVideoId());
            } else {
                presenter.getCourse(
                        preferences.getString(Constants.API_TOKEN, null),
                        innerCourse.getId()
                );
            }
        }

        return view;
    }

    private void updatePlayer(final String link) {
        getLifecycle().addObserver(playerView);
        playerView.initialize(new YouTubePlayerInitListener() {
            @Override
            public void onInitSuccess(@NonNull final YouTubePlayer initializedYouTubePlayer) {
                initializedYouTubePlayer.addListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady() {
                        initializedYouTubePlayer.loadVideo(link, 0);
                    }
                });
            }
        }, true);
    }


    @Override
    public void onItemClicked(int index) {
        if (course != null){
            updatePlayer(course.getPlaylists()[index].getYoutubeVideoId());
        } else if (innerCourse != null){
            updatePlayer(innerCourse.getPlaylistItems()[index].getYoutubeVideoId());
        }
    }

    @Override
    public void onCourseSuccess(String[] videos) {
        adapter.setItems(videos);
        adapter.setCallback(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (videos != null && videos.length > 0)
            updatePlayer(videos[0]);
    }

    @Override
    public void onCourseError(NetworkResult result) {
        Toast.makeText(getActivity(), R.string.no_items, Toast.LENGTH_SHORT).show();
    }
}
