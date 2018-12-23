package itboom.com.elgoud.search;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import javax.inject.Inject;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import itboom.com.elgoud.AlertFragment;
import itboom.com.elgoud.MainActivity;
import itboom.com.elgoud.R;
import itboom.com.elgoud.app.App;
import itboom.com.elgoud.app.Constants;
import itboom.com.elgoud.app.NetworkResult;
import itboom.com.elgoud.paid.mvp.PaidMVP;
import itboom.com.elgoud.pojo.base.BaseResponse;
import itboom.com.elgoud.pojo.search.Course;
import itboom.com.elgoud.pojo.search.Data;
import itboom.com.elgoud.search.mvp.SearchMVP;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements SearchMVP.View, SearchAdapter.SearchCallback, PaidMVP.View {

    @BindView(R.id.searchRecView)
    RecyclerView searchRecyclerView;

    @BindView(R.id.searchProgress)
    ProgressBar searchProgressBar;

    @Inject
    SearchMVP.Presenter presenter;

    @Inject
    SearchAdapter searchAdapter;

    @Inject
    SharedPreferences preferences;

    @Inject
    PaidMVP.Presenter searchPresenter;

    private String key;
    ProgressDialog dialog;

    public SearchFragment() {
        // Required empty public constructor
    }

    public void setKey(String key, String apiKey) {
        this.key = key;
        if (presenter != null) {
            Log.i("inside_search", "setKey: ");
            presenter.search(apiKey, key);
            searchProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        ((App) getActivity().getApplication()).getAppComponent().inject(this);
        ButterKnife.bind(this, view);

        dialog = new ProgressDialog(getActivity());
        dialog.setCancelable(false);

        presenter.setView(this);
        searchPresenter.setView(this);

        return view;
    }

    @Override
    public void onSearchItemPressed(Course course) {
        if (getActivity() != null) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            Log.i("search_course", "onSearchItemPressed: " + course.getIsFree());
            if (course.getIsFree() == 1) {
                itboom.com.elgoud.pojo.course.Course course1 = new itboom.com.elgoud.pojo.course.Course();
                course1.setName(course.getName());
                course1.setId(course.getId());
                course1.setDescription(course.getDescription());
                ((MainActivity) getActivity()).startPlayer(course1, null);
            } else {
                dialog.show();
                searchPresenter.sendRequest(
                        preferences.getString(Constants.API_TOKEN, null),
                        preferences.getInt(Constants.USER_ID, 0),
                        Settings.Secure.getString(getActivity().getContentResolver(),
                                Settings.Secure.ANDROID_ID),
                        course.getId()
                );
            }
        }
    }

    @Override
    public void onSearchSuccess(Data data) {
        searchAdapter.setCallback(this);
        searchAdapter.setCourses(data.getCourses());
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        searchRecyclerView.setAdapter(searchAdapter);
        searchProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onSearchFailure(NetworkResult result) {
        searchProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onCoursesSuccess(itboom.com.elgoud.pojo.course.Data data) {

    }

    @Override
    public void onCoursesFailure(NetworkResult result) {

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
}
