package w.cong.hostapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ResourceActivity extends BaseActivity {

    private TextView textV;
    private ImageView imageView;
    private LinearLayout layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource);

        textV = (TextView)findViewById(R.id.text);
        imageView = (ImageView)findViewById(R.id.img);
        layout = (LinearLayout)findViewById(R.id.layout);

        findViewById(R.id.Btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PluginInfo pluginInfo = plugins.get("plugin1.apk");

                loadResources(pluginInfo.getDexPath());
                doSomething(pluginInfo.getClassLoader());
            }
        });

        findViewById(R.id.Btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PluginInfo pluginInfo = plugins.get("plugin2.apk");

                loadResources(pluginInfo.getDexPath());
                doSomething(pluginInfo.getClassLoader());
            }
        });
    }

    private void doSomething(ClassLoader cl) {
        try {
//            Class clazz = cl.loadClass("w.cong.plugin1.UIUtil");
//            String text = (String)RefInvoke.invokeStaticMethod(clazz,"getTextString", new Class[]{Context.class},new Object[]{this});
//            textV.setText(text);
//
//            Drawable drawable = (Drawable)RefInvoke.invokeStaticMethod(clazz,"getImageDrawable", new Class[]{Context.class},new Object[]{this});
//            imageView.setBackground(drawable);
//
//            layout.removeAllViews();
//            View view = (View) RefInvoke.invokeStaticMethod(clazz, "getLayout",  new Class[]{Context.class},new Object[]{this});
//            layout.addView(view);
            Class stringClass = cl.loadClass("w.cong.plugin1.R$string");
            int resId1 = (int) RefInvoke.getStaticFieldObject(stringClass,"hello_message");
            textV.setText(getResources().getString(resId1));

            Class drawableClass = cl.loadClass("w.cong.plugin1.R$drawable");
            int resId2 = (int) RefInvoke.getStaticFieldObject(drawableClass,"koala");
            imageView.setBackground(getResources().getDrawable(resId2));

            Class layoutClass = cl.loadClass("w.cong.plugin1.R$layout");
            int resId3 = (int) RefInvoke.getStaticFieldObject(layoutClass,"activity_main");
            View view = (View) LayoutInflater.from(this).inflate(resId3,null);
            layout.removeAllViews();
            layout.addView(view);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
