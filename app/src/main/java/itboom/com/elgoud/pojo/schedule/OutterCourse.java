package itboom.com.elgoud.pojo.schedule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OutterCourse implements Serializable {

    @SerializedName("schedules")
    @Expose
    Schedule[] schedules;

    @SerializedName("course")
    @Expose
    InnerCourse innerCourse;

    public Schedule[] getSchedules() {
        return schedules;
    }

    public void setSchedules(Schedule[] schedules) {
        this.schedules = schedules;
    }

    public InnerCourse getInnerCourse() {
        return innerCourse;
    }

    public void setInnerCourse(InnerCourse innerCourse) {
        this.innerCourse = innerCourse;
    }
}
