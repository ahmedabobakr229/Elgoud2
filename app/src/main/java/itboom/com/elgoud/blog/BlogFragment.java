package itboom.com.elgoud.blog;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import itboom.com.elgoud.MainActivity;
import itboom.com.elgoud.R;
import itboom.com.elgoud.app.App;
import itboom.com.elgoud.app.Constants;
import itboom.com.elgoud.app.NetworkResult;
import itboom.com.elgoud.blog.mvp.BlogMVP;
import itboom.com.elgoud.pojo.blog.Blog;
import itboom.com.elgoud.pojo.blog.Data;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlogFragment extends Fragment implements BlogMVP.View, BlogsAdapater.BlogCallback {

    private static final String TAG = BlogFragment.class.getName();
    private Data data;

    @BindView(R.id.blogRecView)
    RecyclerView recyclerView;

    @BindView(R.id.blogRefresh)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.blogProgress)
    ProgressBar progressBar;

    @Inject
    BlogMVP.Presenter presenter;

    @Inject
    BlogsAdapater adapater;

    @Inject
    SharedPreferences preferences;

    LinearLayoutManager layoutManager;
    boolean loadingData = false;

    public BlogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_blog, container, false);

        ((App) getActivity().getApplication()).getAppComponent().inject(this);
        ButterKnife.bind(this, view);

        if (savedInstanceState != null && savedInstanceState.getSerializable("data") != null){
            onBlogsSuccess(data);
        } else {
            presenter.setView(this);
            presenter.getBlog(preferences.getString(Constants.API_TOKEN, null));
        }

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!refreshLayout.isRefreshing())
                    presenter.getBlog(preferences.getString(Constants.API_TOKEN, null));
            }
        });

        return view;
    }

    private void registerScroll() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        if (!loadingData) {
                            Log.v("SCROLL_DOWN", "Last Item Wow !");
                            loadingData = true;
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onBlogsSuccess(Data data) {
        this.data = data;
        adapater.setCallback(this);
        adapater.setBlogs(data.getBlogs());
        recyclerView.setAdapter(adapater);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressBar.setVisibility(View.GONE);
        if (refreshLayout.isRefreshing()){
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onBlogsError(NetworkResult result) {
        Log.i(TAG, "onBlogsError: " + result.name());
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onItemPressed(Blog blog) {
        if (getActivity() != null)
            ((MainActivity) getActivity()).startBlogDescription(blog);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable("data", data);
        super.onSaveInstanceState(outState);
    }
}
