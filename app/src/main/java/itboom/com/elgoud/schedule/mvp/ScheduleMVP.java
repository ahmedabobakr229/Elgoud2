package itboom.com.elgoud.schedule.mvp;

import io.reactivex.Single;
import itboom.com.elgoud.app.NetworkResult;
import itboom.com.elgoud.pojo.schedule.Data;
import itboom.com.elgoud.pojo.schedule.IsOpen;
import itboom.com.elgoud.pojo.schedule.ScheduleResponse;

public interface ScheduleMVP {

    interface View {
        void onScheduleSuccess(Data data);
        void onScheduleFailure(NetworkResult result);
        void onOpenSuccess(boolean isOpen);
        void onOpenFailure(NetworkResult result);
    }

    interface Presenter {
        void setView(View view);
        void getSchedule(String apiToken, int userId);
        void isOpen(String apiToken, int userId, int CourseId);
        void unSubscribe();
    }

    interface Model {
        Single<ScheduleResponse> getSchedule(String apiToken, int userId);
        Single<IsOpen> isOpen(String apiToken, int userId, int courseId);
    }
}
