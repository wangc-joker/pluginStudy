package w.cong.plugin1;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;

public class UIUtil {

    public static String getTextString(Context ctx) {
        return ctx.getResources().getString(R.string.hello_message);
    }

    public static Drawable getImageDrawable(Context ctx) {
        return ctx.getResources().getDrawable(R.drawable.koala);
    }

    public static View getLayout(Context ctx) {
        return LayoutInflater.from(ctx).inflate(R.layout.activity_main,null);
    }
}

