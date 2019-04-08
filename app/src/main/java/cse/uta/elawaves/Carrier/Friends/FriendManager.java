package cse.uta.elawaves.Carrier.Friends;

import org.elastos.carrier.ConnectionStatus;
import org.elastos.carrier.FriendInfo;
import org.elastos.carrier.PresenceStatus;
import org.elastos.carrier.exceptions.CarrierException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;

import cse.uta.elawaves.Carrier.CarrierImplementation;

public class FriendManager extends Observable {

    private static FriendManager instance;
    private static HashMap<String,FriendInfo> friends = new HashMap<>();

    public static FriendManager setup(List<FriendInfo> initialFriends){
        if(instance == null) {
            instance = new FriendManager();
            for(FriendInfo friend : initialFriends){
                friends.put(friend.getUserId(),friend);
            }
        }

        return instance;
    }

    public static FriendManager getInstance(){
        return instance;
    }

    public ArrayList<FriendInfo> getFriends(){
        return new ArrayList<>(friends.values());
    }

    public void addFriend(String address) throws CarrierException {
        CarrierImplementation.getCarrier().addFriend(address,"Hello!");
    }

    public void addFriend(FriendInfo friendInfo){
        System.out.println("FRIEND ADDED:" + friendInfo.getUserId());
        friends.put(friendInfo.getUserId(),friendInfo);
        setChanged();
        notifyObservers();
    }

    public void removeFriend(String address){
        System.out.println("FRIEND REMOVED:" + address);
        friends.remove(address);
        setChanged();
        notifyObservers();
    }

    public boolean isFriendConnected(String address){
        return friends.get(address).getConnectionStatus() == ConnectionStatus.Connected;
    }

    public void changeConnectionStatus(String address,ConnectionStatus status){
        FriendInfo info = friends.get(address);
        if(info != null) {
            info.setConnectionStatus(status);
            setChanged();
            notifyObservers();
        }
    }

    public void changeFriendInfo(FriendInfo info){
        friends.put(info.getUserId(),info);
        setChanged();
        notifyObservers();
    }

    public void changeFriendPresence(String address, PresenceStatus status){
        FriendInfo info = friends.get(address);
        if(info != null) {
            info.setPresence(status);
            setChanged();
            notifyObservers();
        }
    }
}
