package com.coding.jjlop.forestappupt.Views;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.coding.jjlop.forestappupt.Login;
import com.coding.jjlop.forestappupt.Model.User;
import com.coding.jjlop.forestappupt.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.Objects;

public class Profile extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private ImageView photoImageView;
    private TextView txt_nick, txt_email, txt_p, txt_d, txt_q;
    private Button b1;
    private DatabaseReference mDatabase;
    private GoogleApiClient googleApiClient;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private FirebaseUser user;
    String uid, d, qu, p;
    Integer tp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        photoImageView = findViewById(R.id.photoImageView);
        txt_nick = findViewById(R.id.txt_nick);
        txt_email = findViewById(R.id.txt_email);
        txt_p = findViewById(R.id.txt_points);
        txt_d = findViewById(R.id.txt_d);
        txt_q = findViewById(R.id.txt_q);
        b1 = findViewById(R.id.btn1);
        b1.setOnClickListener(this);
        tp = 0;

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    setUserData(user);
                    fData(user);
                } else {
                    goLogInScreen();
                }
            }
        };
    }

    private void setUserData(FirebaseUser user) {
        txt_nick.setText(user.getDisplayName());
        txt_email.setText(user.getEmail());
        Glide.with(this).load(user.getPhotoUrl()).into(photoImageView);
        uid = user.getUid();
    }

    @Override
    protected void onStart() {
        super.onStart();

        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

    private void goLogInScreen() {
        Intent intent = new Intent(this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void logOut() {
        firebaseAuth.signOut();

        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()) {
                    goLogInScreen();
                } else {
                    Toast.makeText(getApplicationContext(), "Error Main", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void revoke() {
        firebaseAuth.signOut();

        Auth.GoogleSignInApi.revokeAccess(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()) {
                    goLogInScreen();
                } else {
                }
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onStop() {
        super.onStop();

        if (firebaseAuthListener != null) {
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void fData(final FirebaseUser user1) {

       /*Query qur = mDatabase.child("T_Ctlg").orderByChild("").equalTo(uid);
        Log.d("", "UID: " + uid);
        qur.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() != 0) {
                    dataSnapshot.getRef().addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                                Log.d("", "ALIASBD: " + areaSnapshot.child("alias").getValue(String.class));
                                if (areaSnapshot.child("id_at").getValue(String.class).equals(uid)) {
                                    tp +=Integer.parseInt(areaSnapshot.child("value").getValue(String.class)) ;
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                } else {
                    tp = 0;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        final Query q = mDatabase.child("Users");
        //Query q = mDatabase.child("T_Ctlg");
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    String tName = areaSnapshot.child("id_u").getValue(String.class);
                    if (tName.equals(user1.getUid())) {
                        p = areaSnapshot.child("t_points").getValue(String.class);
                        d = areaSnapshot.child("degree").getValue(String.class);
                        qu = areaSnapshot.child("quarter").getValue(String.class);
                        txt_p.setText("Puntos Totales: " + tp);
                        txt_d.setText("Carrera: " + d);
                        txt_q.setText("Cuatrimestre: " + qu);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                logOut();
                revoke();
                break;
        }
    }
}
