package cse.uta.elawaves.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import org.elastos.carrier.FriendInfo;
import org.elastos.carrier.exceptions.CarrierException;

import java.util.List;

import androidx.navigation.Navigation;
import cse.uta.elawaves.Adapter.FriendInfoAdapter;
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

        FriendInfoAdapter friendInfoArrayAdapter = new FriendInfoAdapter(getActivity(), android.R.layout.simple_list_item_1, friends);

        setListAdapter(friendInfoArrayAdapter);

        getListView().setOnItemClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);

        Button button_add_friend = view.findViewById(R.id.button_add_friend);

        button_add_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_friendsFragment_to_addFriendFragment);
            }
        });
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FriendInfo friend = (FriendInfo) parent.getAdapter().getItem(position);
        callback.onFriendSelected(friend);

        Bundle bundle = new Bundle();
            bundle.putString("address",friend.getUserId());

        Navigation.findNavController(view).navigate(R.id.action_friendsFragment_to_MessagingFragment,bundle);
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
