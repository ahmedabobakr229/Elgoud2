package itboom.com.elgoud.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import itboom.com.elgoud.R;
import itboom.com.elgoud.pojo.search.Course;
import itboom.com.elgoud.views.EljoudTextView;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    Course[] courses;
    SearchCallback callback;

    public void setCourses(Course[] courses) {
        this.courses = courses;
    }

    public void setCallback(SearchCallback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_card, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.bind(courses[position]);
    }

    @Override
    public int getItemCount() {
        return courses.length;
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_title)
        EljoudTextView title;

        @BindView(R.id.item_image)
        RoundedImageView image;

        @BindView(R.id.item_description)
        EljoudTextView description;

        @BindView(R.id.lock)
        ImageView lock;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final Course course) {
            if (course.getThumbnail().getUrl() != null){
                Transformation transformation = new RoundedTransformationBuilder()
                        .oval(false)
                        .build();

                Picasso.with(itemView.getContext())
                        .load(course.getThumbnail().getUrl())
                        .fit()
                        .transform(transformation)
                        .into(image);
            }

            title.setText(course.getName());
            if (course.getDescription() != null)
                description.setText(course.getDescription());

            if (course.getIsFree() == 1) {
                lock.setVisibility(View.GONE);
            } else {
                lock.setVisibility(View.VISIBLE);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callback != null){
                        callback.onSearchItemPressed(course);
                    }
                }
            });
        }
    }

    public interface SearchCallback {
        void onSearchItemPressed(Course course);
    }
}
