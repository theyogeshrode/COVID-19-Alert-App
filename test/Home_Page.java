package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Home_Page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__page);
    }
    public void Map(View view){
        String em=getIntent().getStringExtra("Email");

        Intent i=new Intent(getApplicationContext(),MapsActivity.class);

        i.putExtra("Email",em);
        startActivity(i);
    }
    public  void Notify(View view)
    {
        String em=getIntent().getStringExtra("Email");
        Intent i=new Intent(getApplicationContext(),Notify.class);
        i.putExtra("Email",em);
        startActivity(i);

    }
}