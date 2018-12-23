package itboom.com.elgoud.free.mvp;

import io.reactivex.Single;
import itboom.com.elgoud.app.NetworkResult;
import itboom.com.elgoud.pojo.course.CourseResponse;
import itboom.com.elgoud.pojo.course.Data;

public interface FreeMVP {
    interface View {
        void onCoursesSuccess(Data data);
        void onCoursesFailure(NetworkResult result);
    }

    interface Presenter {
        void setView(View view);
        void getCourses(String apiToken);
        void rxUnsubscribe();
    }

    interface Model {
        Single<CourseResponse> getCourses(String apiToken);
    }
}
