package com.coding.jjlop.forestappupt.Views;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.coding.jjlop.forestappupt.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Own extends AppCompatActivity {

    private ListView ListV;
    private DatabaseReference mDataBase;
    //private ArrayList<String> tree_List = new ArrayList<>();
    //private ArrayList<String> keys_List = new ArrayList();
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own);
        uid = getIntent().getStringExtra("Uid");
        mDataBase = FirebaseDatabase.getInstance().getReference();
        ListV = findViewById(R.id.T_List);
        Show_mt();
    }

    public void Show_mt() {

        final List<String> trees = new ArrayList<>();
        final ArrayAdapter<String> tAdapter = new ArrayAdapter<>(Own.this, android.R.layout.simple_expandable_list_item_1, trees);
        tAdapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        ListV.setAdapter(tAdapter);

        Query q = mDataBase.child("Planted");

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
                String id = dataSnapshot.child("id_at").getValue(String.class);
                if (uid.equals(id)) {
                    String value = dataSnapshot.child("type").getValue(String.class);
                    trees.add(value);
                    tAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                trees.clear();
                tAdapter.clear();
                Show_mt();
                tAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String id = dataSnapshot.child("id_at").getValue(String.class);
                String tName = dataSnapshot.child("name").getValue(String.class);
                if (trees.contains(tName) && (uid == id)) {
                    trees.clear();
                    tAdapter.clear();
                    Show_mt();
                    tAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
