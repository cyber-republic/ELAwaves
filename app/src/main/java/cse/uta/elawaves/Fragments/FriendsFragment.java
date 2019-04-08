package cse.uta.elawaves.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;

import org.elastos.carrier.FriendInfo;

import java.util.List;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

import androidx.navigation.Navigation;
import cse.uta.elawaves.Adapter.FriendInfoAdapter;
import cse.uta.elawaves.Carrier.Friends.FriendManager;
import cse.uta.elawaves.Carrier.Messages.Message;
import cse.uta.elawaves.R;

public class FriendsFragment extends ListFragment implements OnItemClickListener, Observer {

    OnFriendSelectedListener callback;
    FriendInfoAdapter<FriendInfo> adapter;

    @Override
    public void update(Observable observable, final Object o) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.clear();
                adapter.addAll(FriendManager.getInstance().getFriends());
                adapter.notifyDataSetChanged();
            }
        });
    }

    public interface OnFriendSelectedListener{
        void onFriendSelected(FriendInfo info);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        FriendManager manager = FriendManager.getInstance();
            manager.addObserver(this);

        List<FriendInfo> friends = manager.getFriends();

        adapter = new FriendInfoAdapter<>(Objects.requireNonNull(getActivity()), friends);

        setListAdapter(adapter);

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

        if(!FriendManager.getInstance().isFriendConnected(friend.getUserId()))
            return;

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
        FriendManager.getInstance().deleteObserver(this);
        super.onDetach();
    }
}
