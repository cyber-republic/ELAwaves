package cse.uta.elawaves.Adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.elastos.carrier.FriendInfo;

import java.util.List;

import cse.uta.elawaves.R;

public class FriendInfoAdapter extends ArrayAdapter {

    private Context context;
    private List<FriendInfo> friends;

    public FriendInfoAdapter(@NonNull Context context, int resource, List<FriendInfo> friends) {
        super(context, resource, friends);
        this.context = context;
        this.friends = friends;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View friendItem = convertView;
        if(friendItem == null)
            friendItem = LayoutInflater.from(context).inflate(R.layout.friend_info_list_item,parent,false);

        FriendInfo friend = friends.get(position);

        TextView nick = friendItem.findViewById(R.id.friend_nick_textview);
            nick.setText(friend.getName());

        TextView address = friendItem.findViewById(R.id.friend_address_textview);
            address.setText(friend.getUserId());

        return friendItem;
    }
}
