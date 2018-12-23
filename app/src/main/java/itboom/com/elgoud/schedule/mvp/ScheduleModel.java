package itboom.com.elgoud.schedule.mvp;

import io.reactivex.Single;
import itboom.com.elgoud.app.EljoudApi;
import itboom.com.elgoud.pojo.my_courses.MyCoursesResponse;
import itboom.com.elgoud.pojo.schedule.IsOpen;
import itboom.com.elgoud.pojo.schedule.ScheduleResponse;

public class ScheduleModel implements ScheduleMVP.Model {

    EljoudApi api;

    public ScheduleModel(EljoudApi api) {
        this.api = api;
    }

    @Override
    public Single<ScheduleResponse> getSchedule(String apiToken, int userId) {
        return this.api.getSchedules(apiToken, userId);
    }

    @Override
    public Single<IsOpen> isOpen(String apiToken, int userId, int courseId) {
        return this.api.isOpen(apiToken, userId, courseId);
    }
}
