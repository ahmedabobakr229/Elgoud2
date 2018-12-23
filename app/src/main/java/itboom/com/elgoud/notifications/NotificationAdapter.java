package itboom.com.elgoud.notifications;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import itboom.com.elgoud.R;
import itboom.com.elgoud.pojo.notification.Notification;
import itboom.com.elgoud.views.EljoudTextView;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    Notification[] notifications;
    NotificationCallback callback;

    public void setNotifications(Notification[] notifications) {
        this.notifications = notifications;
    }

    public void setCallback(NotificationCallback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_card, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        holder.bind(notifications[position]);
    }

    @Override
    public int getItemCount() {
        return this.notifications.length;
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {

//        @BindView(R.id.notification_image)
//        CircleImageView image;

        @BindView(R.id.static_title)
        EljoudTextView title;

        @BindView(R.id.notification_title)
        EljoudTextView description;

        @BindView(R.id.notification_date)
        EljoudTextView date;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        public void bind(final Notification notification){
            title.setText(notification.getName());
            description.setText(notification.getBody());
            date.setText(notification.getCreatedAt().getData());

//            Picasso.with(itemView.getContext()).load("").into(image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callback != null){
                        callback.onNotificationItemPressed(notification);
                    }
                }
            });
        }
    }

    public interface NotificationCallback {
        void onNotificationItemPressed(Notification notification);
    }
}
