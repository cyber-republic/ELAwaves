package cse.uta.elawaves.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.elastos.carrier.FriendInfo;

import cse.uta.elawaves.R;

public class FriendInfoFragment extends Fragment {

    OnFriendInfoFragmentListener callback;

    public interface OnFriendInfoFragmentListener {
        void onRemoveFriend(FriendInfo info);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend_info,container,false);

        return view;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        if(context instanceof FriendInfoFragment.OnFriendInfoFragmentListener)
            callback = (FriendInfoFragment.OnFriendInfoFragmentListener) context;
        else
            throw new ClassCastException(context.toString() + " must implement OnFriendInfoFragmentListener");
    }

    @Override
    public void onDetach(){
        callback = null;
        super.onDetach();
    }
}
