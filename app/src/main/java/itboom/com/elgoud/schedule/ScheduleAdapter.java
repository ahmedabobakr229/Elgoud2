package itboom.com.elgoud.schedule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.makeramen.roundedimageview.RoundedImageView;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import itboom.com.elgoud.R;
import itboom.com.elgoud.pojo.schedule.InnerCourse;
import itboom.com.elgoud.pojo.schedule.OutterCourse;
import itboom.com.elgoud.views.EljoudTextView;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {

    OutterCourse[] course;
    ScheduleCallback callback;

    public void setCourse(OutterCourse[] course) {
        this.course = course;
    }

    public void setCallback(ScheduleCallback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_date, parent, false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        holder.bind(course[position]);
    }

    @Override
    public int getItemCount() {
        return course.length;
    }

    public class ScheduleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_image)
        RoundedImageView image;

        @BindView(R.id.item_title)
        EljoudTextView title;

        @BindView(R.id.item_description)
        EljoudTextView description;

        @BindView(R.id.date)
        EljoudTextView date;

        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        public void bind(final OutterCourse outterCourse) {
            title.setText(outterCourse.getInnerCourse().getName());
            description.setText(outterCourse.getInnerCourse().getDescription());
            if (outterCourse.getSchedules() != null && outterCourse.getSchedules().length > 0) {
                date.setText("from: " + outterCourse.getSchedules()[0].getFrom() + " to: " + outterCourse.getSchedules()[0].getTo());
            }

            if (outterCourse.getInnerCourse().getThumbnail().getUrl() != null){
                if (outterCourse.getInnerCourse().getThumbnail().getUrl() != null){
                    Transformation transformation = new RoundedTransformationBuilder()
                            .oval(false)
                            .build();

                    Picasso.with(itemView.getContext())
                            .load(outterCourse.getInnerCourse().getThumbnail().getUrl())
                            .fit()
                            .transform(transformation)
                            .into(image);
                    //Picasso.with(itemView.getContext()).load(course.getThumbnail().getUrl()).into(image);
                }
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callback != null)
                        callback.onCoursePressed(outterCourse.getInnerCourse());
                }
            });
        }
    }

    public interface ScheduleCallback {
        void onCoursePressed(InnerCourse innerCourse);
    }
}
