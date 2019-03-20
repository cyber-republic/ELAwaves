package cse.uta.elawaves.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.elastos.carrier.FriendInfo;

import cse.uta.elawaves.R;

public class AddFriendFragment extends Fragment {

    OnAddFriendFragmentListener callback;

    public interface OnAddFriendFragmentListener {
        void onAddFriend(FriendInfo info);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_friend,container,false);
        return view;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        if(context instanceof OnAddFriendFragmentListener)
            callback = (OnAddFriendFragmentListener) context;
        else
            throw new ClassCastException(context.toString() + " must implement OnFriendSelectedListener");
    }

    @Override
    public void onDetach(){
        callback = null;
        super.onDetach();
    }

}
