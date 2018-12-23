package itboom.com.elgoud.paid.mvp;

import io.reactivex.Single;
import itboom.com.elgoud.app.NetworkResult;
import itboom.com.elgoud.pojo.base.BaseResponse;
import itboom.com.elgoud.pojo.course.CourseResponse;
import itboom.com.elgoud.pojo.course.Data;

public interface PaidMVP {
    interface View {
        void onCoursesSuccess(Data data);
        void onCoursesFailure(NetworkResult result);
        void onRequestSuccess(BaseResponse response);
        void onRequestFailure(NetworkResult result);
    }

    interface Presenter {
        void setView(View view);
        void getCourses(String apiToken, int userId);
        void sendRequest(String apiToken, int user_id, String deviceId, int course_id);
        void rxUnsubscribe();
    }

    interface Model {
        Single<CourseResponse> getCourses(String apiToken, int userId);
        Single<BaseResponse> reserveCourse(String apiToken, int user_id, String deviceId, int course_id);
    }
}
