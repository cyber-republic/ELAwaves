package cse.uta.elawaves.Carrier.Messages;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.elastos.carrier.exceptions.CarrierException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

import cse.uta.elawaves.Carrier.CarrierImplementation;
import cse.uta.elawaves.Database.DatabaseHandler;

public class MessageManager extends Observable {

    private static MessageManager instance;
    private static HashMap<String,ArrayList<Message>> messages = new HashMap<>();
    private static DatabaseHandler dbHelper;

    public static MessageManager getInstance(){
            return instance;
    }

    public static MessageManager setup(Context context){
        if(instance != null)
            return instance;

        instance = new MessageManager(context);

        return instance;
    }

    private MessageManager(Context context){
        dbHelper = new DatabaseHandler(context);

    }

    public ArrayList<Message> getMessages(String address){
        ArrayList<Message> m = messages.get(address);
        if (m != null)
            return m;

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String conversation_query = "SELECT message, address, sent_received, message_timestamp" +
                " FROM messages" +
                " ORDER BY message_timestamp ASC;";
        Cursor c = db.rawQuery(conversation_query, null);
        if (c.moveToFirst()){
            do {
                // Passing values
                String tmp_message = c.getString(0);
                String tmp_address = c.getString(1);
                Boolean tmp_sent_recieved = Boolean.valueOf(c.getString(2));
                Timestamp tmp_message_timestamp = Timestamp.valueOf(c.getString(3));

                // Do something Here with values
                Message tmp_msg = new Message(tmp_message, tmp_sent_recieved, tmp_address, tmp_message_timestamp);
                m.add(tmp_msg);

            } while(c.moveToNext());
        }
        c.close();
        db.close();
        return m;
    }

    public void addMessage(Message message){
        messages.get(message.getAddress()).add(message);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String message_query = "INSERT INTO messages (message, address, sent_received, message_timestamp) VALUES (?,?,?,?)";
        db.execSQL(message_query, new String[] {message.getMessage(), message.getAddress(), String.valueOf(message.isReceived() ? 1 : 0), message.getMessageTimeStamp().toString()});
        setChanged();
        notifyObservers(message);
    }

    public void sendMessage(Message message) throws CarrierException {
        try {
            CarrierImplementation.getCarrier().sendFriendMessage(message.getAddress(), message.getMessage());
            addMessage(message);
        } catch(CarrierException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
