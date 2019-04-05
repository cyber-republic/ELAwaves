package cse.uta.elawaves.Fragments;

import org.elastos.carrier.exceptions.CarrierException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.ListFragment;
import android.widget.EditText;

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
    private String address = "";// get address of recipient

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messaging,container,false);

        messages =  view.findViewById(R.id.messagesList);

        MessageManager.getInstance().addObserver(this);

        MessageAdapter MessageArrayAdapter = new MessageAdapter(getActivity(), android.R.layout.simple_list_item_1, messages);

        setListAdapter(MessageArrayAdapter);

        messages.addAll(MessageManager.getInstance().getMessages(address));

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
            // notify user that message failed to send
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
}
