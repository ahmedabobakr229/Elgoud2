package itboom.com.elgoud.home.mvp;

import io.reactivex.Single;
import itboom.com.elgoud.app.EljoudApi;
import itboom.com.elgoud.pojo.course.CourseResponse;

public class HomeModel implements HomeMVP.Model {

    EljoudApi api;

    public HomeModel(EljoudApi api) {
        this.api = api;
    }

    @Override
    public Single<CourseResponse> getCourses(String apiToken) {
        return api.getHomeCourses(apiToken);
    }
}
