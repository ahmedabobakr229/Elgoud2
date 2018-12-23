package itboom.com.elgoud.my_courses;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import itboom.com.elgoud.MainActivity;
import itboom.com.elgoud.R;
import itboom.com.elgoud.app.App;
import itboom.com.elgoud.app.Constants;
import itboom.com.elgoud.app.NetworkResult;
import itboom.com.elgoud.my_courses.mvp.MyCoursesMVP;
import itboom.com.elgoud.pojo.my_courses.Course;
import itboom.com.elgoud.pojo.my_courses.Data;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import javax.inject.Inject;

public class MyCoursesFragment extends Fragment implements MyCoursesMVP.View, MyCoursesAdapter.MyCoursesCallback {

    private static final String TAG = MyCoursesFragment.class.getName();
    @BindView(R.id.myCoursesRecView)
    RecyclerView recyclerView;

    @BindView(R.id.myCoursesProgress)
    ProgressBar progressBar;

    @Inject
    MyCoursesMVP.Presenter presenter;

    @Inject
    MyCoursesAdapter adapter;

    @Inject
    SharedPreferences preferences;

    Data data;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_courses, container, false);

        ((App) getActivity().getApplication()).getAppComponent().inject(this);
        ButterKnife.bind(this, view);

        presenter.setView(this);
        presenter.getMyCourses(preferences.getString(Constants.API_TOKEN, null), preferences.getInt(Constants.USER_ID, 0));

        return view;
    }

    @Override
    public void onCoursesSuccess(Data data) {
        this.data = data;
        adapter.setCourses(data.getCourses());
        adapter.setCallback(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onCoursesFailure(NetworkResult result) {
        Log.i(TAG, "onCoursesFailure: " + result.name());
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onMyCourseItemPressed(Course course) {
        itboom.com.elgoud.pojo.course.Course course1 = new itboom.com.elgoud.pojo.course.Course();
        course1.setId(course.getId());
        course1.setName(course.getName());
        ((MainActivity) getActivity()).startPlayer(course1, null);
    }
}
