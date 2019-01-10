package com.example.serin.serinproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = findViewById(R.id.erase);
        Button toList = findViewById(R.id.toList);

        //Boutton d'éffacement du fichier texte
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                File file = new File("GPSPos");
                file.delete();
            }
        });

        toList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToList =new Intent(getApplicationContext(),PosListActivity.class);
                startActivity(intentToList);
            }
        });




        //Demande d'autorisation GPS au démarage
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    0);
        } else {
            startService(new Intent(this, GPService.class));
        }

    }

    public String readTextFile(String actualFile) {

        String contents ="";

        try {

            // Get the text file
            File file = new File(actualFile);
            // read the file to get contents
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                // store the text file line to contents variable
                contents += line + ":";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contents;

    }


}
