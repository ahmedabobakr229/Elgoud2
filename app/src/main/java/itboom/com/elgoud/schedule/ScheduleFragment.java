package itboom.com.elgoud.schedule;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import itboom.com.elgoud.AlertFragment;
import itboom.com.elgoud.MainActivity;
import itboom.com.elgoud.R;
import itboom.com.elgoud.app.App;
import itboom.com.elgoud.app.Constants;
import itboom.com.elgoud.app.NetworkResult;
import itboom.com.elgoud.pojo.schedule.InnerCourse;
import itboom.com.elgoud.pojo.schedule.Data;
import itboom.com.elgoud.schedule.mvp.ScheduleMVP;
import itboom.com.elgoud.views.EljoudTextView;

public class ScheduleFragment extends Fragment implements ScheduleMVP.View, ScheduleAdapter.ScheduleCallback {

    @BindView(R.id.homeRecView)
    RecyclerView recyclerView;

    @BindView(R.id.homeProgress)
    ProgressBar progressBar;

    @BindView(R.id.noItemsAvailable)
    EljoudTextView noItems;

    @BindView(R.id.courses_swipe)
    SwipeRefreshLayout refreshLayout;

    @Inject
    ScheduleMVP.Presenter presenter;

    @Inject
    SharedPreferences preferences;

    @Inject
    ScheduleAdapter adapter;

    Data data;
    ProgressDialog dialog;
    InnerCourse innerCourse;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ((App) getActivity().getApplication()).getAppComponent().inject(this);
        ButterKnife.bind(this, view);

        noItems.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        presenter.setView(this);

        if (savedInstanceState != null && savedInstanceState.getSerializable("data") != null){
            data = (Data) savedInstanceState.getSerializable("data");
            onScheduleSuccess(data);
        } else {
            presenter.getSchedule(preferences.getString(Constants.API_TOKEN, null), preferences.getInt(Constants.USER_ID, 0));
        }

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getSchedule(preferences.getString(Constants.API_TOKEN, null), preferences.getInt(Constants.USER_ID, 0));
            }
        });

        return view;
    }

    @Override
    public void onScheduleSuccess(Data data) {
        noItems.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        this.data = data;

        if (refreshLayout.isRefreshing()){
            refreshLayout.setRefreshing(false);
        }

        adapter.setCallback(this);
        adapter.setCourse(data.getCourses());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onScheduleFailure(NetworkResult result) {
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
    public void onOpenSuccess(boolean isOpen) {
        if (dialog != null)
            dialog.dismiss();
        if (isOpen){
            ((MainActivity) getActivity()).startPlayer(null, innerCourse);
            //startActivity(PlayerFragment.createIntent(getActivity(), null, innerCourse));
        } else {
            AlertFragment fragment = AlertFragment.getFragment(getString(R.string.is_open_response));
            fragment.show(getFragmentManager(), null);
        }
    }

    @Override
    public void onOpenFailure(NetworkResult result) {
        if (dialog != null)
            dialog.dismiss();
        Toast.makeText(getActivity(), result.name().toLowerCase().replaceAll("_", " "), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        if (presenter != null)
            presenter.unSubscribe();

        super.onDestroy();
    }

    @Override
    public void onCoursePressed(InnerCourse innerCourse) {
        dialog = new ProgressDialog(getActivity());
        dialog.setCancelable(false);
        dialog.show();

        this.innerCourse = innerCourse;
        presenter.isOpen(
                preferences.getString(Constants.API_TOKEN, null),
                preferences.getInt(Constants.USER_ID, 0),
                innerCourse.getId()
        );
    }
}
