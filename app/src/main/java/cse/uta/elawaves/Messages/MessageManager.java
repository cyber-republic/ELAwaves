package cse.uta.elawaves.Messages;

import android.content.Context;
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

    public static MessageManager getInstance(){
        if(instance != null)
            return instance;

        return new MessageManager();
    }

    private MessageManager(){
        // pull all previous messages from database here
    }


    public ArrayList<Message> getMessages(String address){
        return messages.get(address);
    }

    public void addMessage(Message message){
        setChanged();
        messages.get(message.getAddress()).add(message);
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
