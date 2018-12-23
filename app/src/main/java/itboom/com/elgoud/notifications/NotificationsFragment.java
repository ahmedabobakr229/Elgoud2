 package itboom.com.elgoud.notifications;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import itboom.com.elgoud.notifications.mvp.NotificationMVP;
import itboom.com.elgoud.pojo.notification.Data;
import itboom.com.elgoud.pojo.notification.Notification;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import javax.inject.Inject;

 public class NotificationsFragment extends Fragment implements NotificationMVP.View, NotificationAdapter.NotificationCallback {

    @BindView(R.id.notificationRecView)
    RecyclerView recyclerView;

    @BindView(R.id.notificationRefresh)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.notificationProgress)
    ProgressBar progressBar;

    @Inject
    NotificationMVP.Presenter presenter;

    @Inject
    SharedPreferences preferences;

    @Inject
    NotificationAdapter adapter;

    boolean loadingData = false;
    Data data;
    LinearLayoutManager layoutManager;


     @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        ((App) getActivity().getApplication()).getAppComponent().inject(this);
        ButterKnife.bind(this, view);

        if (savedInstanceState != null && savedInstanceState.getSerializable("data") != null) {
            this.onNotificationSuccess((Data) savedInstanceState.getSerializable("data"));
        } else {
            this.layoutManager = new LinearLayoutManager(getActivity());
            presenter.setView(this);
            presenter.getNotifications(
                    preferences.getString(Constants.API_TOKEN, null),
                    preferences.getInt(Constants.USER_ID, 0)
            );
        }

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!refreshLayout.isRefreshing()) {
                    presenter.getNotifications(
                            preferences.getString(Constants.API_TOKEN, null),
                            preferences.getInt(Constants.USER_ID, 0)
                    );
                }
            }
        });

        return view;
    }

     @Override
     public void onNotificationSuccess(Data data) {
        this.data = data;
        adapter.setCallback(this);
        adapter.setNotifications(data.getNotifications());
        recyclerView.setLayoutManager(this.layoutManager);
        recyclerView.setAdapter(adapter);
        refreshLayout.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
         Log.i("notificationActivity", "onNotificationSuccess: " + data.getCount());

         if (data.getNotifications() != null && data.getNotifications().length > 0){
             for (Notification notification:data.getNotifications()) {
                 if (notification.getSeen() == 0) {
                     presenter.makeSeen(
                             preferences.getString(Constants.API_TOKEN, null),
                             preferences.getInt(Constants.USER_ID, 0),
                             notification.getId()
                     );
                 }
             }
         }
     }

     @Override
     public void onNotificationFailure(NetworkResult result) {
         refreshLayout.setRefreshing(false);
         progressBar.setVisibility(View.GONE);
         Log.i("notificationActivity", "onNotificationFailure: " + result.name());
     }

     @Override
     public void onNotificationItemPressed(Notification notification) {
         if (notification.getBlog() != null) {
             if (getActivity() != null)
                ((MainActivity) getActivity()).startBlogDescription(notification.getBlog());
         } else if (notification.getCourse() != null) {
             if (notification.getCourse().isFree() == 1) {
                 if (getActivity() != null)
                    ((MainActivity) getActivity()).startPlayer(notification.getCourse(), null);
             } else {
                 if (getActivity() != null)
                     ((MainActivity) getActivity()).startPaid();
             }
         } else {

         }
     }


     @Override
     public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("data", data);
        super.onSaveInstanceState(outState);
     }
 }
