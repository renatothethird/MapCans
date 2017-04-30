package com.example.masterpeps.mapcans2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.masterpeps.mapcans2.Model.FAQ;
import com.example.masterpeps.mapcans2.R;

import java.util.ArrayList;

/**
 * Created by masterpeps on 4/28/2017.
 */
public class FAQAdapter extends ArrayAdapter<FAQ> {

    public FAQAdapter(Context context, ArrayList<FAQ> team) {
        super(context, 0, team);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        FAQ team = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_questions, parent, false);
        }

        TextView orgName = (TextView) convertView.findViewById(R.id.textView_orgName);
        TextView orgDesc = (TextView) convertView.findViewById(R.id.textView_orgDesc);

        orgName.setText(team.orgName);
        orgDesc.setText(team.orgDesc);

        return convertView;
    }
}
