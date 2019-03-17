package cse.uta.elawaves.Carrier;

import org.elastos.carrier.Carrier;
import org.elastos.carrier.ConnectionStatus;
import org.elastos.carrier.FriendInfo;
import org.elastos.carrier.PresenceStatus;
import org.elastos.carrier.UserInfo;

import java.util.List;

class CarrierHandler implements org.elastos.carrier.CarrierHandler {

    public CarrierHandler(){}

    @Override
    public void onIdle(Carrier carrier) {
        CarrierMessage carrierMessage = new CarrierMessage();
            carrierMessage.type = CarrierImplementation.ON_IDLE;

       CarrierImplementation.handleMessage(carrierMessage);
    }

    @Override
    public void onConnection(Carrier carrier, ConnectionStatus connectionStatus) {
        CarrierMessage carrierMessage = new CarrierMessage();
            carrierMessage.type = CarrierImplementation.ON_CONNECTION;
            carrierMessage.connectionStatus = connectionStatus;

       CarrierImplementation.handleMessage(carrierMessage);
    }

    @Override
    public void onReady(Carrier carrier) {
        CarrierMessage carrierMessage = new CarrierMessage();
            carrierMessage.type = CarrierImplementation.ON_READY;

       CarrierImplementation.carrier_ready = true;

       CarrierImplementation.handleMessage(carrierMessage);
    }

    @Override
    public void onSelfInfoChanged(Carrier carrier, UserInfo userInfo) {
        CarrierMessage carrierMessage = new CarrierMessage();
            carrierMessage.type = CarrierImplementation.ON_SELF_INFO_CHANGED;
            carrierMessage.userInfo = userInfo;

       CarrierImplementation.handleMessage(carrierMessage);
    }

    @Override
    public void onFriends(Carrier carrier, List<FriendInfo> list) {
        CarrierMessage carrierMessage = new CarrierMessage();
            carrierMessage.type = CarrierImplementation.ON_FRIENDS;
            carrierMessage.friends = list;

       CarrierImplementation.handleMessage(carrierMessage);
    }

    @Override
    public void onFriendConnection(Carrier carrier, String friendID, ConnectionStatus connectionStatus) {
        CarrierMessage carrierMessage = new CarrierMessage();
            carrierMessage.type = CarrierImplementation.ON_FRIEND_CONNECTION;
            carrierMessage.friendID = friendID;
            carrierMessage.connectionStatus = connectionStatus;

       CarrierImplementation.handleMessage(carrierMessage);
    }

    @Override
    public void onFriendInfoChanged(Carrier carrier, String friendID, FriendInfo friendInfo) {
        CarrierMessage carrierMessage = new CarrierMessage();
            carrierMessage.type = CarrierImplementation.ON_FRIEND_INFO_CHANGED;
            carrierMessage.friendID = friendID;
            carrierMessage.friendInfo = friendInfo;

       CarrierImplementation.handleMessage(carrierMessage);
    }

    @Override
    public void onFriendPresence(Carrier carrier, String friendID, PresenceStatus presenceStatus) {
        CarrierMessage carrierMessage = new CarrierMessage();
            carrierMessage.type = CarrierImplementation.ON_FRIEND_PRESENCE;
            carrierMessage.friendID = friendID;
            carrierMessage.presenceStatus = presenceStatus;

       CarrierImplementation.handleMessage(carrierMessage);
    }

    @Override
    public void onFriendRequest(Carrier carrier, String friendID, UserInfo userInfo, String message) {
        CarrierMessage carrierMessage = new CarrierMessage();
            carrierMessage.type = CarrierImplementation.ON_FRIEND_REQUEST;
            carrierMessage.friendID = friendID;
            carrierMessage.userInfo = userInfo;
            carrierMessage.message = message;

       CarrierImplementation.handleMessage(carrierMessage);
    }

    @Override
    public void onFriendAdded(Carrier carrier, FriendInfo friendInfo) {
        CarrierMessage carrierMessage = new CarrierMessage();
            carrierMessage.type = CarrierImplementation.ON_FRIEND_ADDED;
            carrierMessage.friendInfo = friendInfo;

       CarrierImplementation.handleMessage(carrierMessage);
    }

    @Override
    public void onFriendRemoved(Carrier carrier, String friendID) {
        CarrierMessage carrierMessage = new CarrierMessage();
            carrierMessage.type = CarrierImplementation.ON_FRIEND_REMOVE;
            carrierMessage.friendID = friendID;

       CarrierImplementation.handleMessage(carrierMessage);
    }

    @Override
    public void onFriendMessage(Carrier carrier, String friendID, byte[] bytes) {
        CarrierMessage carrierMessage = new CarrierMessage();
            carrierMessage.type = CarrierImplementation.ON_FRIEND_MESSAGE;
            carrierMessage.friendID = friendID;
            carrierMessage.bytes = bytes;

       CarrierImplementation.handleMessage(carrierMessage);
    }

    @Override
    public void onFriendInviteRequest(Carrier carrier, String friendID, String message) {
        CarrierMessage carrierMessage = new CarrierMessage();
            carrierMessage.type = CarrierImplementation.ON_FRIEND_INVITE_REQUEST;
            carrierMessage.friendID = friendID;
            carrierMessage.message = message;

       CarrierImplementation.handleMessage(carrierMessage);
    }

    @Override
    public void onGroupInvite(Carrier carrier, String from, byte[] bytes) {
        CarrierMessage carrierMessage = new CarrierMessage();
            carrierMessage.type = CarrierImplementation.ON_GROUP_INVITE;
            carrierMessage.friendID = from;
            carrierMessage.bytes = bytes;

       CarrierImplementation.handleMessage(carrierMessage);
    }
}
