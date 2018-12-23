package itboom.com.elgoud.paid.mvp;

import io.reactivex.Single;
import itboom.com.elgoud.app.EljoudApi;
import itboom.com.elgoud.pojo.base.BaseResponse;
import itboom.com.elgoud.pojo.course.CourseResponse;

public class PaidModel implements PaidMVP.Model {

    EljoudApi api;

    public PaidModel(EljoudApi api) {
        this.api = api;
    }

    @Override
    public Single<CourseResponse> getCourses(String apiToken, int userId) {
        return api.getPaidCourses(apiToken, userId);
    }

    @Override
    public Single<BaseResponse> reserveCourse(String apiToken, int user_id, String deviceId, int course_id) {
        return api.reserveCourse(apiToken, user_id, deviceId, course_id);
    }
}
