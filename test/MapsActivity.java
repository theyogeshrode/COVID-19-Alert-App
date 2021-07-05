package com.example.test;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
//import io.perfmark.Tag;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.jar.Attributes;

import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_BLUE;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_RED;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference dc=db.collection("Users");
    private static ArrayList<Double> latt = new ArrayList<Double>();
    private static ArrayList<Double> lang = new ArrayList<Double>();
    private static ArrayList<String> email = new ArrayList<String>();
    private static ArrayList<String> mob = new ArrayList<String>();
    private static List<Integer> Id = new ArrayList<Integer>();
    private  static Double startLatitude, startLongitude;
    private static String umob;
    private static Integer usid,flag=0,count=0;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        dc.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null){
                    return;
                }
                if(flag==0){
               latt.clear();
                lang.clear();
                Id.clear();
                email.clear();
                mob.clear();
                for( QueryDocumentSnapshot dc:value){

                    Note note = dc.toObject(Note.class);
                    String s=note.getEmail();
                  String em=getIntent().getStringExtra("Email");

                    if(s.equals(em)) {
                        umob=note.getMobile();
                        usid=note.getUid();
                    }else
                        {
                        Double lg = note.getLang();
                        Double lt = note.getLat();
                        Integer i=note.getUid();
                        String Email=note.getEmail();
                        String Mob=note.getMobile();
                        Id.add(i);
                        latt.add(lt);
                        lang.add(lg);
                        email.add(Email);
                        mob.add(Mob);
                        count++;
                    }


                }
                    flag=1;
                    Toast.makeText(MapsActivity.this, "In event change end"+count, Toast.LENGTH_LONG).show();

                }



            }
        });

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        final Date date= Calendar.getInstance().getTime();
        SimpleDateFormat ft=new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        final String today=ft.format(date);
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                    mMap.clear();
                LatLng coordinate = new LatLng(location.getLatitude(), location.getLongitude());

                mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(HUE_BLUE)).position(coordinate).title("Marker in NGOLocation"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 20f));//increase to get close
                String em = getIntent().getStringExtra("Email");


                startLatitude = location.getLatitude();
                startLongitude = location.getLongitude();
                dc.document(em).update("lat", startLatitude, "lang", startLongitude);

                if (flag == 1)
                {
                    Toast.makeText(MapsActivity.this, "Into the if", Toast.LENGTH_LONG).show();

                    Location sp = new Location("locationA");
                sp.setLatitude(startLatitude);
                sp.setLongitude(startLongitude);
                for (int j = 0; j < latt.size(); j++) {
                    Location ep = new Location("locationB");
                    ep.setLatitude(latt.get(j));
                    ep.setLongitude(lang.get(j));
                    double distance = sp.distanceTo(ep);
                    LatLng other = new LatLng(latt.get(j), lang.get(j));
                    mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(HUE_RED)).position(other).title("Marker in NGOLocation"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latt.get(j), lang.get(j))));
                    Toast.makeText(MapsActivity.this, "Distance" + distance, Toast.LENGTH_LONG).show();

                    if (distance < 20) {
                        Toast.makeText(MapsActivity.this, "Store Uid", Toast.LENGTH_LONG).show();
                     /*   Map<String, Object> note = new HashMap<>();
                        note.put("mobile",mob.get(j));
                        note.put("date",today);*/
                        date date=new date(today);
                        Mobile mobile=new Mobile(today,mob.get(j));
                        //dc.document(em).collection("date").document(today).set(note, SetOptions.merge());
                        dc.document(em).collection("date").document(today).set(date,SetOptions.merge());
                        dc.document(em).collection("date").document(today).collection("Mobile").document(mob.get(j)).set(mobile,SetOptions.merge());
                       /* Map<String, Object> user = new HashMap<>();
                        user.put("mobile",umob);
                        user.put("date",today);*/
                        Mobile umobile=new Mobile(today,umob);
                        date udate=new date(today);
                        dc.document(email.get(j)).collection("date").document(today).set(udate,SetOptions.merge());
                        dc.document(email.get(j)).collection("date").document(today).collection("Mobile").document(umob).set(umobile,SetOptions.merge());

                    } else {
                        Toast.makeText(MapsActivity.this, "Dont store", Toast.LENGTH_LONG).show();

                    }

                }
                flag = 0;
            }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };


        if (Build.VERSION.SDK_INT < 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        }else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
              /*  Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                LatLng sydney = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
            }
            // Add a marker in Sydney and move the camera
        }
    }
}