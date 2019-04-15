package cse.uta.elawaves.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import org.elastos.carrier.exceptions.CarrierException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

import cse.uta.elawaves.Adapter.MessageAdapter;
import cse.uta.elawaves.Carrier.Friends.FriendManager;
import cse.uta.elawaves.Carrier.Messages.Message;
import cse.uta.elawaves.Carrier.Messages.MessageManager;
import cse.uta.elawaves.R;

public class MessagingFragment extends Fragment implements Observer, View.OnClickListener {

    private MessageAdapter adapter;
    private String address;

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messaging,container,false);

        address = getArguments().getString("address");

        ListView messageListView =  view.findViewById(R.id.messagesList);
            messageListView.setDivider(null);

        MessageManager.getInstance().addObserver(this);

        adapter = new MessageAdapter(Objects.requireNonNull(getActivity()), MessageManager.getInstance().getMessages(this.address));
            messageListView.setAdapter(adapter);

        Button sendMessageButton = view.findViewById(R.id.sendMessageButton);
            sendMessageButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
    }

    @Override
    public void onDetach(){
        super.onDetach();
        MessageManager.getInstance().deleteObserver(this);
    }

    public void sendMessage(View view, String message)
    {
        Timestamp time = new Timestamp(System.currentTimeMillis());
        Message m = new Message(message, false, address, time);

        try {
            MessageManager.getInstance().sendMessage(m);
        } catch (CarrierException e) {
            e.printStackTrace();
        }
    }

    private void messagePopup(View view) {
        RelativeLayout rl = view.findViewById(R.id.messaging_popup_layout);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.messaging_popup_layout,null);
        final PopupWindow popupWindow = new PopupWindow(customView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        popupWindow.setElevation(5.0f);
        ImageButton messagingPopupCloseButton = customView.findViewById(R.id.messaging_popup_close_button);
        messagingPopupCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        popupWindow.showAtLocation(rl, Gravity.CENTER,0,0);
    }

    @Override
    public void onClick(View v) {

        EditText messageText = ((View) v.getParent()).findViewById(R.id.textInput);
        if(!messageText.getText().toString().equals("")) {
            this.sendMessage(v, messageText.getText().toString());
            messageText.setText("");
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }
}
