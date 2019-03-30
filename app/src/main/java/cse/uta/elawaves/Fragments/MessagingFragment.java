package cse.uta.elawaves.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.ListFragment;
import android.widget.EditText;

import org.elastos.carrier.Carrier;
import org.elastos.carrier.FriendInfo;
import org.elastos.carrier.exceptions.CarrierException;

import java.util.List;
import java.sql.Timestamp;

import cse.uta.elawaves.Adapter.MessageAdapter;
import cse.uta.elawaves.Messages.Message;
import cse.uta.elawaves.R;

public class MessagingFragment extends ListFragment {

    private List<Message> messages;

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messaging,container,false);

        messages =  view.findViewById(R.id.messagesList);

        MessageAdapter MessageArrayAdapter = new MessageAdapter(getActivity(), android.R.layout.simple_list_item_1, messages);

        setListAdapter(MessageArrayAdapter);

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

    public void sendMessage(View view)
    {
        EditText messageText = view.findViewById(R.id.textInput);
        String message = messageText.getText().toString();

        // actually send message later
        // just append to messages list now
        String address = "";// get address of recipient
        Timestamp time = new Timestamp(System.currentTimeMillis());
        Message m = new Message(message, false, address, time);
        messages.add(m);

//        try {
//            Carrier.getInstance().sendFriendMessage(address, message);
//        } catch (CarrierException e) {
//            e.printStackTrace();
//        }

        // store message in database here

        messageText.setText("");
    }

}
