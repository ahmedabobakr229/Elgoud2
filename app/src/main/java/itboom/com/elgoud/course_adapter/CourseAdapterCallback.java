package itboom.com.elgoud.course_adapter;

import itboom.com.elgoud.pojo.course.Course;

public interface CourseAdapterCallback {

    interface HomeCallback {
        void onFreeCoursePressed(Course course);
        void onLockedCoursePressed(Course course);
    }

    interface FreeCallback {
        void onCoursePressed(Course course);
    }

    interface PaidCallback {
        void onCoursePressed(Course course);
    }
}
