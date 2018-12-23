package itboom.com.elgoud.blog;

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
import itboom.com.elgoud.pojo.blog.Blog;
import itboom.com.elgoud.views.EljoudTextView;

public class BlogsAdapater extends RecyclerView.Adapter<BlogsAdapater.BlogViewHolder> {

    private Blog[] blogs;
    private BlogCallback callback;

    public void setBlogs(Blog[] blogs) {
        this.blogs = blogs;
    }

    public void setCallback(BlogCallback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public BlogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blog_card, parent, false);
        return new BlogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BlogViewHolder holder, int position) {
        holder.bind(this.blogs[position]);
    }

    @Override
    public int getItemCount() {
        return this.blogs.length;
    }

    public class BlogViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_title)
        EljoudTextView title;

        @BindView(R.id.item_image)
        RoundedImageView image;

        @BindView(R.id.shareItem)
        ImageView share;

        public BlogViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        public void bind(final Blog blog) {
            title.setText(blog.getTitle());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callback != null)
                        callback.onItemPressed(blog);
                }
            });

            if (blog.getThumbnail() != null && blog.getThumbnail().getUrl() != null){
                Transformation transformation = new RoundedTransformationBuilder()
                        .oval(false)
                        .build();

                Picasso.with(itemView.getContext())
                        .load(blog.getThumbnail().getUrl())
                        .fit()
                        .transform(transformation)
                        .into(image);
            }
        }
    }

    public interface BlogCallback{
        void onItemPressed(Blog blog);
    }
}
