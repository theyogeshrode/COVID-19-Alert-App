package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.VolumeShaper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.core.ComponentProvider;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static String Email = "";
    private static String Password = "";


    private EditText name;
    private EditText email;
    private EditText password;
    private EditText uid;
    private EditText mobile;
    private EditText city;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadlocale();
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.Name);
        email = findViewById(R.id.Email);
        password = findViewById(R.id.Password);
        uid = findViewById(R.id.UID);
        mobile = findViewById(R.id.Mobile);
        city = findViewById(R.id.City);
        mAuth = FirebaseAuth.getInstance();

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_name));

        Button changeLang=findViewById(R.id.changeMyLang);
        changeLang.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view) {

            }

            public void onclick (View view)
            {
                Languages();
            }
        });

    }

    private void Languages()
    {
        final String[] listItems= {"Hindi","Marathi","English"};
        AlertDialog.Builder mBuilder=new AlertDialog.Builder(MainActivity.this);
        mBuilder.setTitle("Choose Language");

        mBuilder.setSingleChoiceItems(listItems,  -1, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i==0)
                {
                    setLocale("hi");
                    recreate();
                }
                else if(i==1)
                {
                    setLocale("mr");
                    recreate();
                }
                else if(i==2)
                {
                    setLocale("en");
                    recreate();
                }
                dialogInterface.dismiss();
            }


        });
        AlertDialog mDialog=mBuilder.create();
        mDialog.show();
    }

    private void setLocale(String lang) {
        Locale locale=new Locale(lang);
        Locale.setDefault(locale);

        Configuration config=new Configuration();
        config.locale=locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor= (SharedPreferences.Editor) getSharedPreferences("Settings", MODE_PRIVATE);


        editor.putString("My_lang",lang);
        editor.apply();
    }


    public void loadlocale(){
        SharedPreferences prefs=getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language=prefs.getString("My_lang","" );
        setLocale(language);

}

    

    public void Register(View v) {

        Email = email.getText().toString();
        //Toast.makeText(MainActivity.this, "Register" +Email, Toast.LENGTH_SHORT).show();
if(password.length()>=6)
        Password = password.getText().toString();
else
    Toast.makeText(MainActivity.this, "Password should have at least 6 char" , Toast.LENGTH_LONG).show();


        mAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(MainActivity.this, "Authentication Successfull",
                                    Toast.LENGTH_SHORT).show();

                            FirebaseUser user = mAuth.getCurrentUser();
                            String Name = name.getText().toString().toUpperCase();

                            String City = city.getText().toString().toUpperCase();
                            String Ud = uid.getText().toString();
                            int Uid = Integer.parseInt(Ud);
                            Double lat=0.0;
                            Double lang=0.0;
                            String Mobile = mobile.getText().toString();
                            //int Mobile = Integer.parseInt(mb);
                          /*  Map<String, Object> note = new HashMap<>();
                            note.put("Name", Name);
                            note.put( "Email", Email);
                            note.put("Password", Password);
                            note.put("City", City);
                            note.put("Uid", Uid);
                            note.put("Mobile", Mobile);
                           // note.put("lat",0);
                            //note.put("lang",0);*/
                            Note note = new Note(City,Email,Mobile,Password,Name,Uid,lat,lang);

                            db.collection("Users").document(Email).set(note);/*
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(MainActivity.this, "Register", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                                            Log.d(TAG, e.toString());
                                        }
                                    });*/
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }
    public void Login(View v)
    {
        Intent i=new Intent(this,Login.class);
        startActivity(i);

    }
}