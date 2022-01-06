package com.example.locate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView tv1, tv2, tv3, tv4, tv5;

    FusedLocationProviderClient fusedLocationProviderClient;
    private static final String FILE_NAME = "location.txt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = findViewById(R.id.txt_view_1);
        tv2 = findViewById(R.id.txt_view_2);
        tv3 = findViewById(R.id.txt_view_3);
        tv4 = findViewById(R.id.txt_view_4);
        tv5 = findViewById(R.id.txt_view_5);

        //initialize fusedlocationproviderclient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

    }

    private void save() {
        String text1 = tv1.getText().toString();
        String text2 = tv2.getText().toString();
        String text3 = tv3.getText().toString();
        String text4 = tv4.getText().toString();
        String text5 = tv5.getText().toString();
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(text1.getBytes());
            fos.write(text2.getBytes());
            fos.write(text3.getBytes());
            fos.write(text4.getBytes());
            fos.write(text5.getBytes());

            /*Toast.makeText(this, "Saved to " + getFilesDir() + "/" + FILE_NAME,
                    Toast.LENGTH_LONG).show();*/
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    try {
                        Geocoder geocoder = new Geocoder(MainActivity.this,
                                Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1
                        );
                        tv1.setText(Html.fromHtml(
                                "<font color= '#6200EE'<b> Latitude :</b><br></font>"+addresses.get(0).getLatitude()
                        ));
                        tv2.setText(Html.fromHtml(
                                "<font color= '#6200EE'<b> Longitude :</b><br></font>"+addresses.get(0).getLongitude()
                        ));
                        tv3.setText(Html.fromHtml(
                                "<font color= '#6200EE'<b> Country Name :</b><br></font>"+addresses.get(0).getCountryName()
                        ));
                        tv4.setText(Html.fromHtml(
                                "<font color= '#6200EE'<b> Locality :</b><br></font>"+addresses.get(0).getLocality()
                        ));
                        tv5.setText(Html.fromHtml(
                                "<font color= '#6200EE'<b> Address :</b><br></font>"+addresses.get(0).getAddressLine(0)
                        ));
                        save();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }
}