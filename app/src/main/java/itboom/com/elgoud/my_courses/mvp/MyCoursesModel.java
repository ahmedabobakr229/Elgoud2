package itboom.com.elgoud.my_courses.mvp;

import io.reactivex.Single;
import itboom.com.elgoud.app.EljoudApi;
import itboom.com.elgoud.pojo.my_courses.MyCoursesResponse;

public class MyCoursesModel implements MyCoursesMVP.Model {

    EljoudApi api;

    public MyCoursesModel(EljoudApi api) {
        this.api = api;
    }

    @Override
    public Single<MyCoursesResponse> getMyCourse(String apiToken, int userId) {
        return this.api.getMyCourses(apiToken, userId);
    }
}
