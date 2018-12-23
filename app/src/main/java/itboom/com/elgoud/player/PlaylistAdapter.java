package itboom.com.elgoud.player;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.makeramen.roundedimageview.RoundedImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import itboom.com.elgoud.R;
import itboom.com.elgoud.pojo.course.PlaylistItem;
import itboom.com.elgoud.views.EljoudTextView;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder> {

    String[] items;
    PlaylistCallback callback;

    public void setItems(String[] items) {
        this.items = items;
    }

    public void setCallback(PlaylistCallback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_card, parent, false);
        return new PlaylistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position) {
        holder.bind(items[position], position);
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    public class PlaylistViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_title)
        EljoudTextView title;


        public PlaylistViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final String item, final int index) {
            title.setText(String.valueOf(index));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callback != null) {
                        callback.onItemClicked(index);
                    }
                }
            });
        }
    }

    public interface PlaylistCallback {
        void onItemClicked(int index);
    }
}
