package com.app.dailyexpenses;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class ListAdapter extends BaseAdapter{
    String[] title;

    Context context;
    String[] text;

    public ListAdapter(Context context, String[] title, String[] text) {
// TODO Auto-generated constructor stub

        this.title=title;
        this.context= context;
        this.text=text;


    }

    @Override
    public int getCount() {
// TODO Auto-generated method stub
        return title.length;
    }

    @Override
    public Object getItem(int position) {
// TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
// TODO Auto-generated method stub
        return position;
    }

    public static class Holder
    {
        TextView title;
        TextView text;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
 // TODO Auto-generated method stub
        Holder holder= new Holder();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        view = inflater.inflate(R.layout.layout_list,null);

        holder.title= view.findViewById(R.id.title);
        holder.text= view.findViewById(R.id.textView);

        holder.title.setText(title[position]);
        holder.text.setText(text[position]);

        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub
              Toast.makeText(context, "You Clicked " + title[position], Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

}