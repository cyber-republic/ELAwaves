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
        ArrayList<Message> m;
        if (messages.containsKey(address)) {
            m = messages.get(address);
        } else{
            m = dbHelper.getMessages(address);
            messages.put(address, m);
        }

        return m;
    }

    public void addMessage(Message message){
        getMessages(message.getAddress()).add(message);

        dbHelper.saveMessage(message);

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
