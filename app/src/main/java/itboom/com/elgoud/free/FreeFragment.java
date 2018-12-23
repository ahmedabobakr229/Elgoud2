package itboom.com.elgoud.free;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import itboom.com.elgoud.MainActivity;
import itboom.com.elgoud.R;
import itboom.com.elgoud.app.App;
import itboom.com.elgoud.app.Constants;
import itboom.com.elgoud.app.NetworkResult;
import itboom.com.elgoud.course_adapter.CourseAdapterCallback;
import itboom.com.elgoud.course_adapter.CourseRecyclerAdapter;
import itboom.com.elgoud.course_adapter.CourseType;
import itboom.com.elgoud.free.mvp.FreeMVP;
import itboom.com.elgoud.pojo.course.Course;
import itboom.com.elgoud.pojo.course.Data;
import itboom.com.elgoud.views.EljoudTextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FreeFragment extends Fragment implements FreeMVP.View, CourseAdapterCallback.FreeCallback{

    @BindView(R.id.homeRecView)
    RecyclerView recyclerView;

    @BindView(R.id.homeProgress)
    ProgressBar progressBar;

    @BindView(R.id.noItemsAvailable)
    EljoudTextView noItems;

    @BindView(R.id.courses_swipe)
    SwipeRefreshLayout refreshLayout;

    @Inject
    FreeMVP.Presenter presenter;

    @Inject
    SharedPreferences preferences;

    @Inject
    CourseRecyclerAdapter adapter;

    Data data;

    public FreeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_free, container, false);

        ButterKnife.bind(this, view);
        ((App) getActivity().getApplication()).getAppComponent().inject(this);

        noItems.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        presenter.setView(this);

        if (savedInstanceState != null && savedInstanceState.getSerializable("data") != null){
            data = (Data) savedInstanceState.getSerializable("data");
            onCoursesSuccess(data);
        } else {
            presenter.getCourses(preferences.getString(Constants.API_TOKEN, null));
        }

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getCourses(preferences.getString(Constants.API_TOKEN, null));
            }
        });

        return view;
    }

    @Override
    public void onCoursesSuccess(Data data) {
        noItems.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        this.data = data;

        if (refreshLayout.isRefreshing()){
            refreshLayout.setRefreshing(false);
        }
        adapter.setCourseType(CourseType.FREE);
        adapter.setFreeCallback(this);
        adapter.setCourses(new ArrayList<>(Arrays.asList(data.getCourses())));
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCoursesFailure(NetworkResult result) {
        noItems.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        if (result == NetworkResult.NO_ITEM_AVAILABLE){
            noItems.setVisibility(View.VISIBLE);
        }

        if (refreshLayout.isRefreshing()){
            refreshLayout.setRefreshing(false);
        }

        Toast.makeText(getActivity(), result.name().toLowerCase().replaceAll("_", " "), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (data != null){
            outState.putSerializable("data", data);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCoursePressed(Course course) {
        //startActivity(PlayerFragment.createIntent(getActivity(), course, null));
        ((MainActivity) getActivity()).startPlayer(course, null);
    }

    @Override
    public void onDestroy() {
        presenter.rxUnsubscribe();
        super.onDestroy();
    }
}
