package itboom.com.elgoud.player.mvp;

import io.reactivex.Single;
import itboom.com.elgoud.app.EljoudApi;
import itboom.com.elgoud.pojo.single_course.SingleCourseResponse;

public class PlayerModel implements PlayerMVP.Model {

    EljoudApi api;

    public PlayerModel(EljoudApi api) {
        this.api = api;
    }

    @Override
    public Single<SingleCourseResponse> getCourse(String apiToken, int courseId) {
        return this.api.getCourse(apiToken, courseId);
    }
}
