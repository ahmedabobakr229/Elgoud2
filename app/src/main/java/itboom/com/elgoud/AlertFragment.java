package itboom.com.elgoud;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import itboom.com.elgoud.views.EljoudButton;
import itboom.com.elgoud.views.EljoudTextView;

public class AlertFragment extends DialogFragment {

    @BindView(R.id.alertText)
    EljoudTextView message;

    @BindView(R.id.ok_btn)
    EljoudButton ok;

    public static AlertFragment getFragment(String message) {
        AlertFragment fragment = new AlertFragment();
        Bundle bundle = new Bundle();
        bundle.putString("message", message);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alert, container, false);

        ButterKnife.bind(this, view);

        if (getArguments() != null && getArguments().getString("message") != null)
            message.setText(getArguments().getString("message"));

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }
}
