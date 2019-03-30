package cse.uta.elawaves.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import cse.uta.elawaves.Messages.Message;
import cse.uta.elawaves.R;

public class MessageAdapter extends ArrayAdapter {

    private Context context;
    private List<Message> messages;

    public MessageAdapter(@NonNull Context context, int resource, List<Message> messages) {
        super(context, resource, messages);
        this.context = context;
        this.messages = messages;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View messageItem = convertView;
        if(messageItem == null)
            messageItem = LayoutInflater.from(context).inflate(R.layout.friend_info_list_item,parent,false);

        Message message = messages.get(position);

        return messageItem;
    }
}
