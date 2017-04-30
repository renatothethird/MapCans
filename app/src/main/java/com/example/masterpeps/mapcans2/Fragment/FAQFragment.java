package com.example.masterpeps.mapcans2.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.masterpeps.mapcans2.Adapter.FAQAdapter;
import com.example.masterpeps.mapcans2.Model.FAQ;
import com.example.masterpeps.mapcans2.R;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by masterpeps on 4/28/2017.
 */
public class FAQFragment extends Fragment {
    View fragmentView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_faq, container, false);
        FAQListView();
        return fragmentView;
    }

    private void FAQListView(){

        ArrayList<FAQ> arrayOfEvent = new ArrayList<FAQ>();
        final FAQAdapter adapter = new FAQAdapter(getContext(), arrayOfEvent);
        ListView listView = (ListView) fragmentView.findViewById(R.id.listView_organizations);
        listView.setAdapter(adapter);

        FAQ firstEvent = new FAQ("How to add a trashcan?", "Press the + button located on the lower right portion of the screen, choose a location on the map where you'll add the traschan," +
                "add the display name of the trashcan, and click add trashcan button.");
        FAQ second = new FAQ("How to delete trashcan?", "Click the marker/pointer of the trashcan you want to delete then click the window, " +
                "then press the x button located on the lower right portion of the screen.");

        adapter.add(firstEvent);
        adapter.add(second);

    }

}