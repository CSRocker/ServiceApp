package com.example.clarachen.services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.net.URL;
import java.util.Timer;
import java.util.TooManyListenersException;

/**
 * Created by ClaraChen on 3/13/16.
 * File download Services working in the background
 */
public class DownloadFiles extends Service{
    public DownloadFiles(){

    }
    int counter =0;
    public URL[] urls;

    static final int UPDATE_INTERNAL = 1000;
    private  Timer timer = new Timer();

    private final IBinder binder = new MyBinder();

    public class MyBinder extends Binder {
        DownloadFiles getService(){
            return DownloadFiles.this;
        }
    }

    @Override
    public IBinder onBind(Intent arg0){
        //return null;
        return binder;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        //We want this service to continue running until it is explicity stopped,
        //so return sticky.

        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        //get the urls added by mainactivity
        Object[] objURLs = (Object[]) intent.getExtras().get("URLs");

        //create a new URL array to save the urls
        URL[] urls = new URL[objURLs.length];

        for(int i=0; i<objURLs.length-1; i++){
            urls[i] = (URL) objURLs[i];
        }

        new DoBackgroundTask().execute(urls);

        return START_STICKY;

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(timer !=null){
            timer.cancel();
        }
        Toast.makeText(this, "Service Destoryed", Toast.LENGTH_LONG).show();
    }

    private int DownloadFile(URL url){
        try{
            //--- stimulate taking some time to download a file ---
            Thread.sleep(5000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        return 100;
    }

    //-- calculate and return percentage downloaded,
    //--- Log percentage downloaded
    private class DoBackgroundTask extends AsyncTask<URL, Integer,Long>{
        protected Long doInBackground(URL... urls) {
            int count = urls.length;
            long totalBytesDownloaded = 0;
            for (int i = 0; i < count; i++) {
                totalBytesDownloaded += DownloadFile(urls[i]);
                //---calculate precentage downloaded and
                // report its progress---
                publishProgress((int) (((i+1) / (float) count) * 100));
            }
            return totalBytesDownloaded;
        }

        //Log the downloaded percentage
        protected void onProgressUpdate(Integer... progress){
            Log.d("Downloading files",
                    String.valueOf(progress[0]) + "% downloaded");
            Toast.makeText(getBaseContext(),
                    String.valueOf(progress[0]) + "% downloaded",
                    Toast.LENGTH_LONG).show();
        }

        protected void onPostExecute(Long result){
            Toast.makeText(getBaseContext(),
                    "Downloaded "+ result + "bytes",
                    Toast.LENGTH_LONG).show();
            stopSelf();
        }

    }


}
