package com.example.omarg.scavenge_client;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ZingQR extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QrScanner();
        //setContentView(R.layout.activity_zing_qr);

    }

    public void QrScanner(){
       mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);
                mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.<br />
        mScannerView.startCamera();         // Start camera<br />
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();   // Stop camera on pause<br />
    }

    @Override
    public void handleResult(Result rawResult) {
        setContentView(R.layout.activity_list);

        // Do something with the result here
        Log.e("handler", rawResult.getText()); // Prints scan results
        Log.e("handler", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode)
        // show the scanner result into dialog box.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setMessage(rawResult.getText());
        create(rawResult.getText());
        AlertDialog alert1 = builder.create();
        alert1.show();
        // If you would like to resume scanning, call this method below:
         mScannerView.resumeCameraPreview(this);
    }

    public void create(String URL) {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ListView list = findViewById(R.id.Dynamic);
        String[] ListElements = new String[]{
        };

        ArrayList<Building> buildList = new Handler().main(URL);

        if (buildList != null) {
            final List<String> ListElementsArrayList = new ArrayList<String>(Arrays.asList(ListElements));


            final ArrayAdapter<String> adapter = new ArrayAdapter<String>
                    (ZingQR.this, android.R.layout.simple_list_item_1, ListElementsArrayList);

            list.setAdapter(adapter);
            for (int i = 0; i < buildList.size(); i++) {
                ListElementsArrayList.add("id : " + buildList.get(i).get_id() + "\nbuilding id : " + buildList.get(i).getBuilding_id() + "\nDescription : " +
                        buildList.get(i).getDescription() + "\nLocation type : " + buildList.get(i).getLocation_type() + "\nRoom Number : " + buildList.get(i).getRoom());
            }

            adapter.notifyDataSetChanged();
        } else {

        }
    }
}
