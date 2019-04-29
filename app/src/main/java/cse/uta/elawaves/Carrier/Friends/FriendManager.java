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


// Class to create a hashmap of friends and their given address from the Carrier Network
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

    // Add address as friend on Carrier Network
    public void addFriend(String address) throws CarrierException {
        CarrierImplementation.getCarrier().addFriend(address,"Hello!");
    }

    // Add Friend to hashmap
    public void addFriend(FriendInfo friendInfo){
        System.out.println("FRIEND ADDED:" + friendInfo.getUserId());
        friends.put(friendInfo.getUserId(),friendInfo);
        setChanged();
        notifyObservers();
    }

    // Remove friend from hashmap
    public void removeFriend(String address){
        System.out.println("FRIEND REMOVED:" + address);
        friends.remove(address);
        setChanged();
        notifyObservers();
    }

    // Check connection status of friend [both users must be online to send messages]
    public boolean isFriendConnected(String address){
        return friends.get(address).getConnectionStatus() == ConnectionStatus.Connected;
    }

    // If user connects/disconnects from Carrier Network, update
    public void changeConnectionStatus(String address,ConnectionStatus status){
        FriendInfo info = friends.get(address);
        if(info != null) {
            info.setConnectionStatus(status);
            setChanged();
            notifyObservers();
        }
    }

    // Update info associated with friend
    public void changeFriendInfo(FriendInfo info){
        friends.put(info.getUserId(),info);
        setChanged();
        notifyObservers();
    }

    /// Update presence of friend
    public void changeFriendPresence(String address, PresenceStatus status){
        FriendInfo info = friends.get(address);
        if(info != null) {
            info.setPresence(status);
            setChanged();
            notifyObservers();
        }
    }
}
