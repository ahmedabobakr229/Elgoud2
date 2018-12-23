package itboom.com.elgoud.pojo.my_courses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import itboom.com.elgoud.pojo.schedule.Schedule;

public class Course {
    @SerializedName("id")
    @Expose
    int id;

    @SerializedName("name")
    @Expose
    String name;

    @SerializedName("description")
    @Expose
    String description;

    @SerializedName("created_at")
    @Expose
    String createdAt;

    @SerializedName("schedules")
    @Expose
    Schedule[] schedules;

    public Schedule[] getSchedules() {
        return schedules;
    }

    public void setSchedules(Schedule[] schedules) {
        this.schedules = schedules;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
