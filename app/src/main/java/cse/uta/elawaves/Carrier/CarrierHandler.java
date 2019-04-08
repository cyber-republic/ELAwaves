package cse.uta.elawaves.Carrier;

import android.util.Log;

import org.elastos.carrier.Carrier;
import org.elastos.carrier.ConnectionStatus;
import org.elastos.carrier.FriendInfo;
import org.elastos.carrier.PresenceStatus;
import org.elastos.carrier.UserInfo;
import org.elastos.carrier.exceptions.CarrierException;

import java.sql.Timestamp;
import java.util.List;

import cse.uta.elawaves.Carrier.Friends.FriendManager;
import cse.uta.elawaves.Carrier.Messages.Message;
import cse.uta.elawaves.Carrier.Messages.MessageManager;

class CarrierHandler implements org.elastos.carrier.CarrierHandler {

    private FriendManager friendManager;

    public CarrierHandler(){}

    @Override
    public void onIdle(Carrier carrier) {

    }

    @Override
    public void onConnection(Carrier carrier, ConnectionStatus connectionStatus) {
    }

    @Override
    public void onReady(Carrier carrier) {
        System.out.println("CARRIER READY");
        CarrierImplementation.setReady();
    }

    @Override
    public void onSelfInfoChanged(Carrier carrier, UserInfo userInfo) {

    }

    @Override
    public void onFriends(Carrier carrier, List<FriendInfo> list) {
        friendManager = FriendManager.setup(list);
    }

    @Override
    public void onFriendConnection(Carrier carrier, String friendID, ConnectionStatus connectionStatus) {
       friendManager.changeConnectionStatus(friendID,connectionStatus);
    }

    @Override
    public void onFriendInfoChanged(Carrier carrier, String friendID, FriendInfo friendInfo) {
        friendManager.changeFriendInfo(friendInfo);
    }

    @Override
    public void onFriendPresence(Carrier carrier, String friendID, PresenceStatus presenceStatus) {
        friendManager.changeFriendPresence(friendID,presenceStatus);
    }

    @Override
    public void onFriendRequest(Carrier carrier, String friendID, UserInfo userInfo, String message) {
        try {
            CarrierImplementation.getCarrier().acceptFriend(friendID);
        } catch (CarrierException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFriendAdded(Carrier carrier, FriendInfo friendInfo) {
        friendManager.addFriend(friendInfo);
    }

    @Override
    public void onFriendRemoved(Carrier carrier, String friendID) {
        friendManager.removeFriend(friendID);
    }

    @Override
    public void onFriendMessage(Carrier carrier, String friendID, byte[] bytes) {
        Message message = new Message(new String(bytes),true,friendID,new Timestamp(System.currentTimeMillis()));
        MessageManager messageManager = MessageManager.getInstance();
            messageManager.addMessage(message);
    }

    @Override
    public void onFriendInviteRequest(Carrier carrier, String friendID, String message) {

    }

    @Override
    public void onGroupInvite(Carrier carrier, String from, byte[] bytes) {

    }
}
