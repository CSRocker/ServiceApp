package com.example.clarachen.services;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.net.MalformedURLException;
import java.net.URL;

public class StartDownload extends AppCompatActivity {

    IntentFilter intentFilter;
    private DownloadFiles downloadFiles;
    Intent i;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_download);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //---intent to filter for file downloaded intent --
        intentFilter = new IntentFilter();
        intentFilter.addAction("FILE_DOWNLOADED_ACTION");

        //---register the receiver --
        registerReceiver(intentReciver, intentFilter);


        Button btnStartDownload = (Button) findViewById(R.id.start_download);

        btnStartDownload.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), DownloadFiles.class);


                EditText pdfUrl1 = (EditText) findViewById(R.id.pdf1);
                String pdf1 = pdfUrl1.getText().toString();

                EditText pdfUrl2 = (EditText) findViewById(R.id.pdf2);
                String pdf2 = pdfUrl2.getText().toString();

                EditText pdfUrl3 = (EditText) findViewById(R.id.pdf3);
                String pdf3 =pdfUrl3.getText().toString();

                EditText pdfUrl4 = (EditText) findViewById(R.id.pdf4);
                String pdf4 =pdfUrl3.getText().toString();

                EditText pdfUrl5 = (EditText) findViewById(R.id.pdf5);
                String pdf5 =pdfUrl5.getText().toString();


                try {
                    URL[] urls = new URL[]{
                            new URL(pdf1),
                            new URL(pdf2),
                            new URL(pdf3),
                            new URL(pdf4),
                            new URL(pdf5)
                    };
                    intent.putExtra("URLs", urls);
                } catch (MalformedURLException e) {
                    e.printStackTrace();

                }
                startService(intent);
                returnMain();

                StartDownload.this.finish();
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void returnMain(){
        Intent main = new Intent(StartDownload.this, MainActivity.class);
        startActivity(main);

    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //---called when the connection is made ---
            downloadFiles = ((DownloadFiles.MyBinder) service).getService();

            EditText pdfUrl1 = (EditText) findViewById(R.id.pdf1);
            String pdf1 = pdfUrl1.getText().toString();

            EditText pdfUrl2 = (EditText) findViewById(R.id.pdf2);
            String pdf2 = pdfUrl2.getText().toString();

            EditText pdfUrl3 = (EditText) findViewById(R.id.pdf3);
            String pdf3 =pdfUrl3.getText().toString();

            EditText pdfUrl4 = (EditText) findViewById(R.id.pdf4);
            String pdf4 =pdfUrl3.getText().toString();

            EditText pdfUrl5 = (EditText) findViewById(R.id.pdf5);
            String pdf5 =pdfUrl5.getText().toString();


            try {
                URL[] urls = new URL[]{
                        new URL(pdf1),
                        new URL(pdf2),
                        new URL(pdf3),
                        new URL(pdf4),
                        new URL(pdf5)
                };
                downloadFiles.urls = urls;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            startService(i);

        }



        @Override
        public void onServiceDisconnected(ComponentName name) {
            // called when the service disconnects ---
            downloadFiles = null;
        }
    };


    //---- Receive the broadcast notification from DownloadFiles
    private BroadcastReceiver intentReciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(getBaseContext(), "File downloaded!",
                    Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.clarachen.services/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.clarachen.services/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

}
