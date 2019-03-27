package cse.uta.elawaves.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.elastos.carrier.FriendInfo;

import cse.uta.elawaves.R;

public class MessagingFragment extends Fragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messaging,container,false);
        return view;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);


    }

    @Override
    public void onDetach(){
        super.onDetach();
    }

}
