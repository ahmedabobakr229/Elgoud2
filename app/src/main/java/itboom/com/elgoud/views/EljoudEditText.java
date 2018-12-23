package itboom.com.elgoud.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

public class EljoudEditText extends EditText  {

    public EljoudEditText(Context context) {
        super(context);
        setUpView(context);
    }

    public EljoudEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUpView(context);
    }

    public EljoudEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUpView(context);
    }

    private void setUpView(Context context){
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "Hacen-Algeria.ttf"));
    }
}
