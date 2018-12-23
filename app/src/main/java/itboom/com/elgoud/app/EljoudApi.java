package itboom.com.elgoud.app;


import java.util.List;

import io.reactivex.Single;
import itboom.com.elgoud.pojo.base.BaseResponse;
import itboom.com.elgoud.pojo.blog.BlogResponse;
import itboom.com.elgoud.pojo.course.CourseResponse;
import itboom.com.elgoud.pojo.notification.NotificationResponse;
import itboom.com.elgoud.pojo.my_courses.MyCoursesResponse;
import itboom.com.elgoud.pojo.schedule.IsOpen;
import itboom.com.elgoud.pojo.schedule.ScheduleResponse;
import itboom.com.elgoud.pojo.search.SearchResponse;
import itboom.com.elgoud.pojo.single_course.SingleCourseResponse;
import itboom.com.elgoud.pojo.slider.SliderResponse;
import itboom.com.elgoud.pojo.user.UserResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface EljoudApi {

    @FormUrlEncoded
    @POST("register")
    Single<UserResponse> register(@Field("name") String name,
                                  @Field("email") String email,
                                  @Field("phone") String phone,
                                  @Field("password") String password,
                                  @Field("password_confirmation") String confirmPassword);

    @FormUrlEncoded
    @POST("register")
    Single<UserResponse> fbRegister(@Field("name") String name,
                                  @Field("email") String email,
                                    @Field("facebook_id") String fbId,
                                  @Field("password") String password,
                                  @Field("password_confirmation") String confirmPassword);

    @FormUrlEncoded
    @POST("login")
    Single<UserResponse> login(@Field("email") String email, @Field("password") String password);

    @GET("courses")
    Single<CourseResponse> getHomeCourses(@Query("api_token") String apiToken);

    @GET("courses/free")
    Single<CourseResponse> getFreeCourses(@Query("api_token") String apiToken);

    @GET("courses/notFree")
    Single<CourseResponse> getPaidCourses(@Query("api_token") String apiToken, @Query("user_id") int userId);

    @GET("blogs")
    Single<BlogResponse> getBlogs(@Query("api_token") String apiToken);

    @GET("notifications")
    Single<NotificationResponse> getNotifications(@Query("api_token") String apiToken, @Query("user_id") int userId);

    @GET("courses/search")
    Single<SearchResponse> search(@Query("api_token") String apiToken, @Query("search_key") String k);

    @GET("courses/my/courses")
    Single<MyCoursesResponse> getMyCourses(@Query("api_token") String apiToken, @Query("user_id") int userId);

    @FormUrlEncoded
    @POST("contact")
    Single<BaseResponse> contactUs(
            @Field("api_token") String apiToken,
            @Field("name") String name,
            @Field("message") String message,
            @Field("user_id") int userId
    );

    @FormUrlEncoded
    @POST("courses/reserve")
    Single<BaseResponse> reserveCourse(
            @Field("api_token") String apiToken,
            @Field("user_id") int userId,
            @Field("device_id") String deviceId,
            @Field("course_id") int courseId
    );

    @Multipart
    @POST("profile/update")
    Single<UserResponse> updateProfile(@Part("api_token") RequestBody apiToken,
                                 @Part("user_id") RequestBody userId,
                                 @Part("name") RequestBody username,
                                 @Part("phone") RequestBody phone,
                                 @Part MultipartBody.Part image);

    @Multipart
    @POST("profile/update")
    Single<UserResponse> updateProfile(@Part("api_token") RequestBody apiToken,
                                       @Part("user_id") RequestBody userId,
                                       @Part("name") RequestBody username,
                                       @Part("phone") RequestBody phone,
                                       @Part("password") RequestBody password,
                                       @Part("password_confirmation") RequestBody confirmPassword,
                                       @Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST("profile/update")
    Single<UserResponse> updateProfile(@Field("api_token") String apiToken,
                                       @Field("user_id") int userId,
                                       @Field("name") String username,
                                       @Field("phone") String phone);

    @FormUrlEncoded
    @POST("profile/update")
    Single<UserResponse> updateProfile(@Field("api_token") String apiToken,
                                       @Field("user_id") int userId,
                                       @Field("name") String username,
                                       @Field("phone") String phone,
                                       @Field("password") String password,
                                       @Field("password_confirmation") String confirmPassword);

    @GET("courses/schedules")
    Single<ScheduleResponse> getSchedules(@Query("api_token") String apiToken, @Query("user_id") int userId);

    @GET("courses/is/open/now")
    Single<IsOpen> isOpen(@Query("api_token") String apiToken, @Query("user_id") int userId, @Query("course_id") int courseId);

    @FormUrlEncoded
    @POST("notifications/seen")
    Single<BaseResponse> makeSeen(@Field("api_token") String apiToken, @Field("user_id") int userId, @Field("notification_id") int notificationId);

    @GET("courses/show")
    Single<SingleCourseResponse> getCourse(@Query("api_token") String apiToken, @Query("course_id") int courseId);

    @FormUrlEncoded
    @POST("profile/update/token")
    Single<BaseResponse> updateFCMToken(@Field("api_token") String apiToken,
                                        @Field("Token") String fcmToken,
                                        @Field("user_id") int userId);


    @GET("slider")
    Single<SliderResponse> slider(@Query("api_token") String apiToken);
}


