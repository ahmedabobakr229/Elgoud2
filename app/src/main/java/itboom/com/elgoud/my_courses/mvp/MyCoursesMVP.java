package itboom.com.elgoud.my_courses.mvp;

import io.reactivex.Single;
import itboom.com.elgoud.app.NetworkResult;
import itboom.com.elgoud.pojo.my_courses.Data;
import itboom.com.elgoud.pojo.my_courses.MyCoursesResponse;

public interface MyCoursesMVP {
    interface View {
        void onCoursesSuccess(Data data);
        void onCoursesFailure(NetworkResult result);
    }

    interface Presenter {
        void setView(View view);
        void getMyCourses(String apiToken, int userId);
        void unSubscribe();
    }

    interface Model {
        Single<MyCoursesResponse> getMyCourse(String apiToken, int userId);
    }
}
