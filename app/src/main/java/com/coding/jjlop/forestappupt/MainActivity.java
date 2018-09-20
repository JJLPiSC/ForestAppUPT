package com.coding.jjlop.forestappupt;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.coding.jjlop.forestappupt.Adapter.GridAdapter;
import com.coding.jjlop.forestappupt.Views.Adopt;
import com.coding.jjlop.forestappupt.Views.Exchange;
import com.coding.jjlop.forestappupt.Views.Own;
import com.coding.jjlop.forestappupt.Views.Plant;
import com.coding.jjlop.forestappupt.Views.Profile;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    GridView gvm;
    String[] values={"Perfil","Plantar","Mis Arboles","Adoptar"};
    int[] images={
            R.drawable.exchange,
            R.drawable.plant,
            R.drawable.own,
            R.drawable.adopt
    };
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       uid = getIntent().getStringExtra("Uid");

        gvm=findViewById(R.id.gv_Menu);
        GridAdapter ga= new GridAdapter(this,values,images);
        gvm.setAdapter(ga);
        gvm.setOnItemClickListener(this);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog1 = new AlertDialog.Builder(MainActivity.this);
        alertDialog1.setTitle("Salir");
        alertDialog1.setMessage("Realmente quiere salir???");
        alertDialog1.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                finish();
            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
            }
        }).setNeutralButton("...", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
            }
        });
        alertDialog1.setCancelable(true);
        alertDialog1.create();
        alertDialog1.show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (i==0){
            Intent it = new Intent(MainActivity.this, Profile.class);
            it.putExtra("Uid",uid);
            startActivity(it);
        }if (i==1){
            Intent it = new Intent(MainActivity.this,Plant.class);
            it.putExtra("Uid",uid);
            startActivity(it);
        }
        if (i==2){
            Intent it = new Intent(MainActivity.this,Exchange.class);
            it.putExtra("Uid",uid);
            startActivity(it);
        }
        if (i==3){
            Intent it = new Intent(MainActivity.this,Adopt.class);
            it.putExtra("Uid",uid);
            startActivity(it);
        }

    }
}
