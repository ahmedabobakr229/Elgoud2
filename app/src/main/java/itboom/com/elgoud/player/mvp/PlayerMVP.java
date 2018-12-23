package itboom.com.elgoud.player.mvp;

import com.google.api.services.youtube.model.Video;

import io.reactivex.Single;
import itboom.com.elgoud.app.NetworkResult;
import itboom.com.elgoud.pojo.single_course.SingleCourseResponse;

public interface PlayerMVP {
    interface View {
        void onCourseSuccess(String[] videos);
        void onCourseError(NetworkResult result);
    }

    interface Presenter {
        void setView(View view);
        void getCourse(String apiToken, int courseId);
        void rxUnsubscribe();
    }

    interface Model {
        Single<SingleCourseResponse> getCourse(String apiToken, int courseId);
    }
}
