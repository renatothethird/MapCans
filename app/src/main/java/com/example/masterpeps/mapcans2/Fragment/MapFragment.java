package com.example.masterpeps.mapcans2.Fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.masterpeps.mapcans2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {
    View fragmentView;
    ImageView addCans;
    private onGoToAddCans _toGotoAddCans;

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_map, container, false);
        init();
        return fragmentView;
    }

    public void init()
    {
        addCans = (ImageView)fragmentView.findViewById(R.id.btn_goToAddCans);
        addCans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _toGotoAddCans.toGotoAddCans();
            }
        });
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        try{
            _toGotoAddCans = (onGoToAddCans) activity;
        }catch (Exception ex){

            throw new RuntimeException(activity.toString() + " must implement UpdateProfile");

        }
    }

    @Override
    public void onDetach(){
        super.onDetach();
        _toGotoAddCans = null;
    }

    public interface onGoToAddCans {

        public void toGotoAddCans();
    }

}
