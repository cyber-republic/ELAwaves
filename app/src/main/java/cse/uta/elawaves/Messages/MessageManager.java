package cse.uta.elawaves.Messages;

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

import static java.security.AccessController.getContext;

public class MessageManager extends Observable {

    private static MessageManager instance;
    private static HashMap<String,ArrayList<Message>> messages = new HashMap<>();
    DatabaseHandler dbHelper;

    public static MessageManager getInstance(){
        if(instance != null)
            return instance;

        return new MessageManager();
    }

    private MessageManager() {

    }
    private MessageManager(Context context){
        dbHelper = new DatabaseHandler(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String conversation_query = "SELECT message, address, sent_received, message_timestamp" +
                " FROM messages" +
                " ORDER BY message_timestamp ASC;";
        Cursor c = db.rawQuery(conversation_query, null);
        if (c.moveToFirst()){
            do {
                // Passing values
                String message = c.getString(0);
                String address = c.getString(1);
                String sent_recieved = c.getString(2);
                Timestamp message_timestamp = Timestamp.valueOf(c.getString(3));

                // Do something Here with values

            } while(c.moveToNext());
        }
        c.close();
        db.close();

    }


    public ArrayList<Message> getMessages(String address){
        return messages.get(address);
    }

    public void addMessage(Message message){
        setChanged();
        messages.get(message.getAddress()).add(message);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String message_query = "INSERT INTO messages (message, address, sent_received, message_timestamp)" +
                "VALUES " +
                "(" + message.getMessage() + ", " + message.getAddress() + ", " + message.isReceived() + ", " + message.getMessageTimeStamp() + ");";
        db.execSQL(message_query);

        notifyObservers(message);
    }

    public void sendMessage(Message message) throws CarrierException {
        try {
            CarrierImplementation.getCarrier().sendFriendMessage(message.getAddress(), message.getMessage());
            addMessage(message); // this doesn't get reached if failed
        } catch(CarrierException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
