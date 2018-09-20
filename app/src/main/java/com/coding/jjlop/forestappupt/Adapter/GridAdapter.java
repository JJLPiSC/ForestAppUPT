package com.coding.jjlop.forestappupt.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.coding.jjlop.forestappupt.R;

public class GridAdapter extends BaseAdapter {
    Context con;
    private final String[] values;
    private final int[] images;
    View view;
    LayoutInflater li;
    //Obtenemos el contexto , titulos e imagenes que conforman el menu principal
    public GridAdapter(Context con, String[] values, int[] images) {
        this.con = con;
        this.values = values;
        this.images = images;
    }



    @Override
    public int getCount() {
        return values.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    //Inflamos el layout correspondiente y referenciamos sus componentes
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        li= (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView==null){
            view=new View(con);
            view=li.inflate(R.layout.single_item,null);
            ImageView iv1=view.findViewById(R.id.iv1);
            TextView tv1=view.findViewById(R.id.tv1);
            iv1.setImageResource(images[i]);
            tv1.setText(values[i]);
        }
        return view;
    }
}
