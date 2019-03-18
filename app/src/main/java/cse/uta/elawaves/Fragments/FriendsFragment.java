package cse.uta.elawaves.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import org.elastos.carrier.FriendInfo;
import org.elastos.carrier.exceptions.CarrierException;

import java.util.List;

import cse.uta.elawaves.Carrier.CarrierImplementation;
import cse.uta.elawaves.R;

public class FriendsFragment extends ListFragment implements OnItemClickListener {

    OnFriendSelectedListener callback;

    public interface OnFriendSelectedListener{
        void onFriendSelected(FriendInfo info);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        List<FriendInfo> friends = null;
        try {
            friends = CarrierImplementation.getCarrier().getFriends();
        } catch (CarrierException e) {
            e.printStackTrace();
        }

        ArrayAdapter<FriendInfo> friendInfoArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, friends);

        setListAdapter(friendInfoArrayAdapter);

        getListView().setOnItemClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        if(context instanceof OnFriendSelectedListener)
            callback = (OnFriendSelectedListener) context;
        else
            throw new ClassCastException(context.toString() + " must implement OnFriendSelectedListener");
    }

    @Override
    public void onDetach(){
        callback = null;
        super.onDetach();
    }
}
