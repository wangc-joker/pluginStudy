package w.cong.hostapp;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;
import w.cong.mypluginlibrary.IBean;
import w.cong.mypluginlibrary.ICallback;
import w.cong.mypluginlibrary.IDynamic;

public class MainActivity extends BaseActivity{

    private AssetManager mAssetManager;
    private Resources mResources;
    private Resources.Theme mTheme;
    private String dexpath = null;    //apk文件地址
    private File fileRelease = null;  //释放目录
    private DexClassLoader classLoader = null;

    TextView tv;

    private String apkName = "plugin1-debug.apk";    //apk名称

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        try {
            Utils.extractAssets(newBase,apkName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emain);

        File extractFile = this.getFileStreamPath(apkName);
        dexpath = extractFile.getPath();

        fileRelease = getDir("dex",0);//0 表示Context.MODE_PRIVATE

        Log.d("cong", "dexpath:" + dexpath);
        Log.d("cong", "fileRelease.getAbsolutePath():" +
                fileRelease.getAbsolutePath());

        classLoader = new DexClassLoader(dexpath,fileRelease.getAbsolutePath(),null,getClassLoader());
        Class mLoadClass;
        IBean bean  = null;
        try {
            mLoadClass = classLoader.loadClass("w.cong.plugin1.Bean");
            Object o = mLoadClass.newInstance();
            bean = (IBean)o;
        } catch (Exception e) {

        }

        tv = (TextView)findViewById(R.id.button);
        ICallback callback = new ICallback() {
            @Override
            public void sendResult(String result) {
                tv.setText(result);
            }
        };
        bean.register(callback);
        initButton();
    }


    private void initButton() {
        Button btn = findViewById(R.id.getResource);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadResource();
                Class mLoadClassDynamic = null;

                try {
                    mLoadClassDynamic = classLoader.loadClass("w.cong.plugin1.Dynamic");
                    Object dynamicObject = mLoadClassDynamic.newInstance();
                    IDynamic dynamic = (IDynamic)dynamicObject;
                    String content = dynamic.getStringForResId(MainActivity.this);
                    tv.setText(content);
                } catch (Exception e) {

                }
            }
        });
    }

    private void loadResource() {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method method = assetManager.getClass().getMethod("addAssetPath", String.class);
            method.invoke(assetManager,dexpath);
            mAssetManager = assetManager;
        } catch (Exception e) {

        }

        mResources = new Resources(mAssetManager,super.getResources().getDisplayMetrics(),super.getResources().getConfiguration());
        mTheme = mResources.newTheme();
        mTheme.setTo(super.getTheme());
    }

    @Override
    public AssetManager getAssets() {
        if(mAssetManager == null) {
            return super.getAssets();
        }
       return mAssetManager;
    }

    @Override
    public Resources getResources() {
        if (mResources == null) {
            return super.getResources();
        }
        return mResources;
    }

    @Override
    public Resources.Theme getTheme() {
        if (mTheme == null) {
            return super.getTheme();
        }
       return mTheme;
    }
}
