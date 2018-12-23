package itboom.com.elgoud.my_courses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.makeramen.roundedimageview.RoundedImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import itboom.com.elgoud.R;
import itboom.com.elgoud.pojo.my_courses.Course;
import itboom.com.elgoud.views.EljoudTextView;

public class MyCoursesAdapter extends RecyclerView.Adapter<MyCoursesAdapter.MyCoursesViewHolder> {

    Course[] courses;
    MyCoursesCallback callback;

    public void setCourses(Course[] courses) {
        this.courses = courses;
    }

    public void setCallback(MyCoursesCallback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public MyCoursesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_card, parent, false);
        return new MyCoursesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCoursesViewHolder holder, int position) {
        holder.bind(courses[position]);
    }

    @Override
    public int getItemCount() {
        return courses.length;
    }

    public class MyCoursesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_title)
        EljoudTextView title;

        @BindView(R.id.item_image)
        RoundedImageView image;

        @BindView(R.id.item_description)
        EljoudTextView description;

        public MyCoursesViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        public void bind(Course course){
            title.setText(course.getName());
            description.setText(course.getDescription());
        }
    }

    interface MyCoursesCallback {
        void onMyCourseItemPressed(Course course);
    }
}
