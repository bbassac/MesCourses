package lioncorps.org.mescourses;

import android.graphics.Paint;
import android.widget.TextView;

public class TextViewUtils {

    public static void strike(TextView textView){
        textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    public static void unStrike(TextView textView){
        textView.setPaintFlags(textView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
    }

}
