package com.coding.jjlop.forestappupt.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.coding.jjlop.forestappupt.Adapter.ExchAdapter;
import com.coding.jjlop.forestappupt.Model.Tree;
import com.coding.jjlop.forestappupt.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class Exchange extends AppCompatActivity {

    private List<Tree> tList;
    private RecyclerView recyclerView;
    private DatabaseReference mDataBase;
    private String uid;
    private String d_p, l, type, id_t, alias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);
        uid = getIntent().getStringExtra("Uid");
        mDataBase = FirebaseDatabase.getInstance().getReference();
        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist
        tList = new ArrayList<>();

        //creating recyclerview adapter
        final ExchAdapter adapter = new ExchAdapter(this, fillList(), uid);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);
    }

    public List<Tree> fillList() {
        tList.clear();
        recyclerView.removeAllViews();
        Query q = mDataBase.child("Planted");

        q.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String id = dataSnapshot.child("id_at").getValue(String.class);
                if (uid.equals(id)) {
                    d_p = dataSnapshot.child("d_plant").getValue(String.class);
                    l = dataSnapshot.child("l_water").getValue(String.class);
                    type = dataSnapshot.child("type").getValue(String.class);
                    alias = dataSnapshot.child("alias").getValue(String.class);
                    id_t = dataSnapshot.getKey();
                    String lt = dataSnapshot.child("lat").getValue(String.class);
                    String ln = dataSnapshot.child("lng").getValue(String.class);
                    tList = c_list(d_p, l, type, id_t, alias, lt, ln);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                tList.clear();
                fillList();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                tList.clear();
                fillList();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return tList;
    }

    public List<Tree> c_list(final String d, final String l, final String t, final String it, final String alia, final String lt, final String ln) {
        Query quer = mDataBase.child("T_Ctlg");

        quer.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String n = dataSnapshot.child("name").getValue(String.class);
                if (t.equals(n)) {
                    String na = dataSnapshot.child("name").getValue(String.class);
                    String v = dataSnapshot.child("value").getValue(String.class);
                    String i = dataSnapshot.child("i_perd").getValue(String.class);
                    Tree tree = new Tree(it, t, v, i, d, l, alia, lt, ln);
                    tList.add(tree);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                tList.clear();
                fillList();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                tList.clear();
                fillList();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return tList;
    }
}
