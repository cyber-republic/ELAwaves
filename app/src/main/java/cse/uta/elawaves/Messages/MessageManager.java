package cse.uta.elawaves.Messages;

import org.elastos.carrier.exceptions.CarrierException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

import cse.uta.elawaves.Carrier.CarrierImplementation;

public class MessageManager extends Observable {

    private static MessageManager instance;
    private static HashMap<String,ArrayList<Message>> messages = new HashMap<>();

    public static MessageManager getInstance(){
        if(instance != null)
            return instance;

        return new MessageManager();
    }

    private MessageManager(){

    }


    public ArrayList<Message> getMessages(String address){
        return messages.get(address);
    }

    public void addMessage(String address,Message message){
        setChanged();
        messages.get(address).add(message);
        notifyObservers(message);
    }

    public void sendMessage(String recipient,String message) throws CarrierException {
        addMessage(recipient,new Message(message,false,recipient,new Timestamp(System.currentTimeMillis())));
        CarrierImplementation.getCarrier().sendFriendMessage(recipient,message);
    }
}
