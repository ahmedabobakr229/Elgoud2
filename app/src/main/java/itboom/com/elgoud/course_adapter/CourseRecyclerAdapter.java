package itboom.com.elgoud.course_adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import itboom.com.elgoud.R;
import itboom.com.elgoud.pojo.course.Course;
import itboom.com.elgoud.views.EljoudTextView;

public class CourseRecyclerAdapter extends RecyclerView.Adapter<CourseRecyclerAdapter.CoursesViewHolder> {

    ArrayList<Course> courses;
    CourseAdapterCallback.HomeCallback homeCallback;
    CourseAdapterCallback.FreeCallback freeCallback;
    CourseAdapterCallback.PaidCallback paidCallback;
    CourseType courseType;

    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public void setHomeCallback(CourseAdapterCallback.HomeCallback homeCallback) {
        this.homeCallback = homeCallback;
    }

    public void setFreeCallback(CourseAdapterCallback.FreeCallback freeCallback) {
        this.freeCallback = freeCallback;
    }

    public void setPaidCallback(CourseAdapterCallback.PaidCallback paidCallback) {
        this.paidCallback = paidCallback;
    }

    @NonNull
    @Override
    public CoursesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (courseType == CourseType.HOME){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_card, parent, false);
        } else if (courseType == CourseType.PAID){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.paid_card, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.free_card, parent, false);
        }

        return new CoursesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoursesViewHolder holder, int position) {
        holder.bind(courses.get(position));
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public class CoursesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_image)
        RoundedImageView image;

        @BindView(R.id.item_title)
        EljoudTextView title;

        @BindView(R.id.item_description)
        @Nullable EljoudTextView description;

        @BindView(R.id.unlock)
        @Nullable ImageView unlock;

        @BindView(R.id.lock)
        @Nullable ImageView lock;

        public CoursesViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final Course course){
            if (course.getThumbnail().getUrl() != null){
                Transformation transformation = new RoundedTransformationBuilder()
                        .oval(false)
                        .build();

                Picasso.with(itemView.getContext())
                        .load(course.getThumbnail().getUrl())
                        .fit()
                        .transform(transformation)
                        .into(image);
                //Picasso.with(itemView.getContext()).load(course.getThumbnail().getUrl()).into(image);
            }

            if (course.getName() != null){
                title.setText(course.getName());
            }

            if (courseType == CourseType.HOME || courseType == CourseType.PAID) {
                if (course.getDescription() != null) {
                    description.setText(course.getDescription());
                }

                if (lock != null && courseType == CourseType.HOME) {
                    if (course.isFree() == 0) {
                        lock.setVisibility(View.VISIBLE);
                    } else {
                        lock.setVisibility(View.GONE);
                    }
                }
            }

            if (courseType == CourseType.PAID && unlock != null){
                unlock.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //todo unlock course
                    }
                });
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (courseType == CourseType.HOME && homeCallback != null) {
                        if (course.isFree() == 1)
                            homeCallback.onFreeCoursePressed(course);
                        else
                            homeCallback.onLockedCoursePressed(course);
                    }

                    else if (courseType == CourseType.PAID && paidCallback != null)
                        paidCallback.onCoursePressed(course);

                    else if (courseType == CourseType.FREE && freeCallback != null)
                        freeCallback.onCoursePressed(course);
                }
            });
        }
    }
}
