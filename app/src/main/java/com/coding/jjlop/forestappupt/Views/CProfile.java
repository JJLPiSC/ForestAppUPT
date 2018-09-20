package com.coding.jjlop.forestappupt.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.coding.jjlop.forestappupt.Login;
import com.coding.jjlop.forestappupt.MainActivity;
import com.coding.jjlop.forestappupt.Model.User;
import com.coding.jjlop.forestappupt.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

public class CProfile extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
    private ImageView photoImageView;
    private TextView txt_nick, txt_email;
    private EditText txt_psw;
    private Spinner spn_D, spn_Q;
    final List<String> degrees = new ArrayList<>();
    private Button b1;
    private DatabaseReference mDatabase;
    private GoogleApiClient googleApiClient;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private FirebaseUser user;
    String uid, d, qu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cprofile);
        uid = getIntent().getStringExtra("Uid");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        photoImageView = findViewById(R.id.photoImageView);
        txt_nick = findViewById(R.id.txt_nick);
        txt_email = findViewById(R.id.txt_email);
        spn_D = findViewById(R.id.spn_D);
        spn_Q = findViewById(R.id.spn_Q);
        txt_psw = findViewById(R.id.txt_psw);
        b1 = findViewById(R.id.btn_inf);
        b1.setOnClickListener(this);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this,
                R.array.quarters,
                android.R.layout.simple_spinner_item);
        //link the adapter to the spinner
        spn_Q.setAdapter(adapter);
        fillSnpD();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                //setUserData(user);
                if (user != null) {
                    Check();
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

    public void fillSnpD() {
        Query q = mDatabase.child("Degree");
        final ArrayAdapter<String> tAdapter = new ArrayAdapter<>(CProfile.this, android.R.layout.simple_spinner_item, degrees);
        tAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_D.setAdapter(tAdapter);
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        q.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String tName = dataSnapshot.child("name").getValue(String.class);
                String ke = dataSnapshot.child("name").getKey();
                if (!degrees.contains(tName)) {
                    degrees.add(tName);
                }
                tAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                degrees.clear();
                tAdapter.clear();
                fillSnpD();
                tAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String tName = dataSnapshot.child("name").getValue(String.class);
                if (degrees.contains(tName)) {
                    degrees.remove(tName);
                }
                tAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void goLogInScreen() {
        Intent intent = new Intent(this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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

    public void Save() {
        Query query = mDatabase.child("Users").orderByChild("id_u").equalTo(uid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot.getValue(String.class);
                if (dataSnapshot.getChildrenCount() == 0) {
                    User u = new User(uid, getIntent().getStringExtra("Name"), getIntent().getStringExtra("Email"), spn_D.getSelectedItem().toString().trim(), spn_Q.getSelectedItem().toString().trim(), "0", txt_psw.getText().toString());
                    mDatabase.child("Users").push().setValue(u).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                FancyToast.makeText(getApplicationContext(), "Cuenta Completada!!!", Toast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("Uid", uid);
                                startActivity(intent);
                            } else {
                                FancyToast.makeText(getApplicationContext(), "Operacion Fallida!!!", Toast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void Check() {
        Query query = mDatabase.child("Users").orderByChild("id_u").equalTo(uid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() == 0) {
                    firebaseAuth = FirebaseAuth.getInstance();
                    firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
                        @Override
                        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                            user = firebaseAuth.getCurrentUser();
                            if (user != null) {
                            } else {
                                goLogInScreen();
                            }
                        }
                    };
                } else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("Uid", uid);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //Insercion de Carreras de Ejemplo
    private void addD() {
        String n = "";
        for (int i = 0; i < 6; i++) {
            if (i == 0) {
                n = "ISC";
            } else if (i == 1) {
                n = "IET";
            } else if (i == 2) {
                n = "IC";
            } else if (i == 3) {
                n = "IR";
            } else if (i == 4) {
                n = "II";
            } else if (i == 5) {
                n = "ITM";
            }
            mDatabase.child("Degree").push().child("name").setValue(n).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(CProfile.this, "Stored...", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CProfile.this, "Error..!!!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_inf:
                if (!spn_D.getSelectedItem().toString().trim().equals("") && !spn_D.getSelectedItem().toString().trim().equals("")) {
                    Save();
                } else {
                    FancyToast.makeText(getApplicationContext(), "Por favor llene los campos!!!", Toast.LENGTH_SHORT, FancyToast.WARNING, true).show();
                }
                break;
        }
    }
}
