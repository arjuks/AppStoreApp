package com.example.arjun.midtermexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by arjun on 10/5/2015.
 */
public class AppAdapter extends ArrayAdapter<Apps>{

    Context mContext;
    int mResource;
    List<Apps> mData;
    SimpleDateFormat format = new SimpleDateFormat("MMM dd,yyyy");
    public AppAdapter(Context context, int resource, List<Apps> objects) {
        //super(context);
        super(context, resource, objects);

        this.mData = objects;
        this.mContext = context;
        this.mResource = resource;
    }

    @Override // this is for every row in the list view
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource,parent,false);
        }
        Apps name = mData.get(position);

        TextView Appname = (TextView) convertView.findViewById(R.id.appsname);
        Appname.setText(name.getTitle());
        TextView devname = (TextView) convertView.findViewById(R.id.devsname);
        devname.setText(name.getDevname());
        TextView price = (TextView) convertView.findViewById(R.id.Price);
        price.setText(name.getPrice());
        TextView date = (TextView) convertView.findViewById(R.id.Date);
        date.setText(name.getDate());
        Picasso.with(mContext).load(name.getSimg()).into((ImageView) convertView.findViewById(R.id.imageView));

        return convertView;
    }


}