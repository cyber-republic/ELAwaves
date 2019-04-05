package cse.uta.elawaves.Fragments;

import org.elastos.carrier.Carrier;
import org.elastos.carrier.exceptions.CarrierException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.LayoutParams;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.ListFragment;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;
import java.util.Observable;
import java.util.Observer;

import cse.uta.elawaves.Adapter.MessageAdapter;
import cse.uta.elawaves.Database.DatabaseHandler;
import cse.uta.elawaves.MainActivity;
import cse.uta.elawaves.Messages.Message;
import cse.uta.elawaves.Messages.MessageManager;
import cse.uta.elawaves.R;

import static java.security.AccessController.getContext;

public class MessagingFragment extends ListFragment implements Observer {

    private List<Message> messages;
    private String address;
    private String recipientName;

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        address = savedInstanceState.getString("address");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messaging,container,false);

        messages =  view.findViewById(R.id.messagesList);

        MessageManager.getInstance().addObserver(this);

        MessageAdapter MessageArrayAdapter = new MessageAdapter(getActivity(), android.R.layout.simple_list_item_1, messages);

        setListAdapter(MessageArrayAdapter);

        messages.addAll(MessageManager.getInstance().getMessages(address));

        // fill name at the top of page
        final TextView textViewToChange = (TextView) view.findViewById(R.id.recipientName);
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
//        MessageManager manager = (MessageManager) o;

        messages.add(message);

        // store message in database here

        // Startup Database
        DatabaseHandler dbHelper = new DatabaseHandler(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String message_query = "INSERT INTO messages (message, address, sent_received, message_timestamp)" +
                "VALUES " +
                "(" + message.getMessage() + ", " + message.getAddress() + ", " + message.isReceived() + ", " + message.getMessageTimeStamp() + ");";
        db.execSQL(message_query);
    }

    private void messagePopup(View view) {
        RelativeLayout rl = (RelativeLayout) view.findViewById(R.id.messaging_popup_layout);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.messaging_popup_layout,null);
        final PopupWindow popupWindow = new PopupWindow(customView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        if(Build.VERSION.SDK_INT>=21){
            popupWindow.setElevation(5.0f);
        }
        ImageButton messagingPopupCloseButton = (ImageButton) customView.findViewById(R.id.messaging_popup_close_button);
        messagingPopupCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        popupWindow.showAtLocation(rl, Gravity.CENTER,0,0);
    }
}
