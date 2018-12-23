package itboom.com.elgoud.free.mvp;

import io.reactivex.Single;
import itboom.com.elgoud.app.EljoudApi;
import itboom.com.elgoud.pojo.course.CourseResponse;

public class FreeModel implements FreeMVP.Model {

    EljoudApi api;

    public FreeModel(EljoudApi api) {
        this.api = api;
    }

    @Override
    public Single<CourseResponse> getCourses(String apiToken) {
        return api.getFreeCourses(apiToken);
    }
}
