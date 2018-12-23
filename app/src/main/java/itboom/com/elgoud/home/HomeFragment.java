package itboom.com.elgoud.home;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import itboom.com.elgoud.AlertFragment;
import itboom.com.elgoud.MainActivity;
import itboom.com.elgoud.paid.mvp.PaidMVP;
import itboom.com.elgoud.R;
import itboom.com.elgoud.app.App;
import itboom.com.elgoud.app.Constants;
import itboom.com.elgoud.app.NetworkResult;
import itboom.com.elgoud.course_adapter.CourseAdapterCallback;
import itboom.com.elgoud.course_adapter.CourseRecyclerAdapter;
import itboom.com.elgoud.course_adapter.CourseType;
import itboom.com.elgoud.home.mvp.HomeMVP;
import itboom.com.elgoud.pojo.base.BaseResponse;
import itboom.com.elgoud.pojo.course.Course;
import itboom.com.elgoud.pojo.course.Data;
import itboom.com.elgoud.views.EljoudTextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements HomeMVP.View, CourseAdapterCallback.HomeCallback, PaidMVP.View {

    @BindView(R.id.homeRecView)
    RecyclerView recyclerView;

    @BindView(R.id.homeProgress)
    ProgressBar progressBar;

    @BindView(R.id.noItemsAvailable)
    EljoudTextView noItems;

    @BindView(R.id.courses_swipe)
    SwipeRefreshLayout refreshLayout;

    @Inject
    HomeMVP.Presenter presenter;

    @Inject
    PaidMVP.Presenter paidPresenter;

    @Inject
    SharedPreferences preferences;

    @Inject
    CourseRecyclerAdapter adapter;

    Data data;
    ProgressDialog dialog;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

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

        adapter.setCourseType(CourseType.HOME);
        adapter.setHomeCallback(this);
        adapter.setCourses(new ArrayList<>(Arrays.asList(data.getCourses())));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
    public void onRequestSuccess(BaseResponse response) {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
        AlertFragment fragment = AlertFragment.getFragment(getString(R.string.success_request));
        fragment.show(getFragmentManager(), null);
    }

    @Override
    public void onRequestFailure(NetworkResult result) {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
        Toast.makeText(getActivity(), result.name().replaceAll("_", " ").toLowerCase(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (data != null){
            outState.putSerializable("data", data);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onFreeCoursePressed(Course course) {
        Log.i("home_fragment", "onFreeCoursePressed: " + course.isFree());
        ((MainActivity) getActivity()).startPlayer(course, null);
        //startActivity(PlayerFragment.createIntent(getActivity(), course, null));
    }

    @Override
    public void onLockedCoursePressed(Course course) {
        dialog = new ProgressDialog(getActivity());
        dialog.setCancelable(false);
        dialog.show();
        paidPresenter.setView(this);
        paidPresenter.sendRequest(
                preferences.getString(Constants.API_TOKEN, null),
                preferences.getInt(Constants.USER_ID, 0),
                Settings.Secure.getString(getActivity().getContentResolver(),
                        Settings.Secure.ANDROID_ID),
                course.getId()
        );
    }

    @Override
    public void onDestroy() {
        if (presenter != null)
            presenter.rxUnsubscribe();

        if (paidPresenter != null)
            paidPresenter.rxUnsubscribe();
        super.onDestroy();
    }
}
