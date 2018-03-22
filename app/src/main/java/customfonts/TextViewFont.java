package customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatTextView;

/**
 * Created by onurp on 14.03.2018.
 */
public class TextViewFont extends AppCompatTextView  {

    public TextViewFont(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TextViewFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextViewFont(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/robotoregular.ttf");
            setTypeface(tf);
        }
    }

}
