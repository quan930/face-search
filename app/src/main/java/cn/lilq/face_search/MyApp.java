package cn.lilq.face_search;

import android.Manifest;
import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.StrictMode;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

public class MyApp extends Application {
    private Client client;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onCreate() {
        super.onCreate();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        client = new Client();
    }

    public Client getClient() {
        return client;
    }
}
