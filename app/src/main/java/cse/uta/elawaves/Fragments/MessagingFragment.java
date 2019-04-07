package cse.uta.elawaves.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.elastos.carrier.Carrier;
import org.elastos.carrier.exceptions.CarrierException;

import java.sql.Timestamp;
import java.util.Observable;
import java.util.Observer;

import cse.uta.elawaves.Adapter.MessageAdapter;
import cse.uta.elawaves.Messages.Message;
import cse.uta.elawaves.Messages.MessageManager;
import cse.uta.elawaves.R;

public class MessagingFragment extends ListFragment implements Observer {

    private MessageAdapter adapter;
    private String address;

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        address = savedInstanceState.getString("address");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messaging,container,false);

        String address = savedInstanceState.getString("address");

        ListView messageListView =  view.findViewById(R.id.messagesList);

        MessageManager.getInstance().addObserver(this);

        adapter = new MessageAdapter(getActivity(), android.R.layout.simple_list_item_1,MessageManager.getInstance().getMessages(address));
        messageListView.setAdapter(adapter);

        // fill name at the top of page
        final TextView textViewToChange = view.findViewById(R.id.recipientText);
        textViewToChange.setText(Carrier.getUserIdByAddress(address));

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

        Timestamp time = new Timestamp(System.currentTimeMillis());
        Message m = new Message(message, false, address, time);

        try {
            MessageManager.getInstance().sendMessage(m);
        } catch (CarrierException e) {
            messagePopup(view);
            e.printStackTrace();
        }

        messageText.setText("");
    }

    @Override
    public void update(Observable o, Object arg) {
        Message message = (Message) arg;

        adapter.add(message);
        adapter.notifyDataSetChanged();
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
}
