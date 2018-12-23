package itboom.com.elgoud.blog;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import itboom.com.elgoud.R;
import itboom.com.elgoud.pojo.blog.Blog;
import itboom.com.elgoud.views.EljoudTextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlogDescriptionFragment extends Fragment {

    @BindView(R.id.blogImage)
    ImageView image;

    @BindView(R.id.blogText)
    EljoudTextView text;

    Blog blog;

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public BlogDescriptionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blog_description, container, false);
        ButterKnife.bind(this, view);

        if (blog != null) {
            Picasso.with(getActivity()).load(blog.getThumbnail().getUrl()).into(image);
            if (blog.getBody() != null && !blog.getBody().isEmpty()) {
                text.setText(blog.getBody());
            }
        }

        return view;
    }

}
