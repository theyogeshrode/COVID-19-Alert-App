package com.example.test;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Notify extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    String phoneNo;
    String message;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference dc=db.collection("Users");
    private static ArrayList<String> dat = new ArrayList<String>();
    private static ArrayList<String> mob = new ArrayList<String>();

    private static int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
        String em=getIntent().getStringExtra("Email");
        dc.document(em).collection("date").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                int count=0;
                if(flag==0){
                    Toast.makeText(Notify.this, "Flag 0", Toast.LENGTH_SHORT).show();

                    for(QueryDocumentSnapshot data:value){
                        date d=data.toObject(date.class);
                        String dt=d.getdate();
                        count++;
                        dat.add(dt);

                    }
                    Toast.makeText(Notify.this, "count "+count, Toast.LENGTH_SHORT).show();

                    flag=1;
                }
            }
        });


        if(flag==1)
        {

            sendSMSMessage();
        }


    }
    protected void sendSMSMessage() {

        String em=getIntent().getStringExtra("Email");

    Toast.makeText(Notify.this, "Flag 2"+em, Toast.LENGTH_SHORT).show();

    for (int i = 0; i < dat.size(); i++) {

        dc.document(em).collection("date").document(dat.get(i)).collection("Mobile").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (QueryDocumentSnapshot m : value) {
                    Mobile mobile = m.toObject(Mobile.class);
                    String mb = mobile.getMobile();
                    Toast.makeText(Notify.this, "Mobile"+mb, Toast.LENGTH_SHORT).show();
                    phoneNo=mb;
                    message = "We are Testing Our App so if you got this sms then please ignore it";
                    if (ContextCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.SEND_SMS)
                            != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(getParent(),
                                Manifest.permission.SEND_SMS)) {
                        } else {
                            ActivityCompat.requestPermissions(getParent(),
                                    new String[]{Manifest.permission.SEND_SMS},
                                    MY_PERMISSIONS_REQUEST_SEND_SMS);
                        }
                    }else{
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phoneNo, null, message, null, null);
                        Toast.makeText(getApplicationContext(), "SMS sent."+phoneNo,
                                Toast.LENGTH_LONG).show();
                    }

                   // mob.add(mb);

                }
            }
        });



}
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, message, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent."+phoneNo,
                            Toast.LENGTH_LONG).show();
                } else {
                    //Toast.makeText(Notify.this, "Failed "+phoneNo, Toast.LENGTH_LONG).show();

                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again."+phoneNo, Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }
}