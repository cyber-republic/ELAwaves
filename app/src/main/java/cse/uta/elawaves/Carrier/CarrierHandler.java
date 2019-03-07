package cse.uta.elawaves.Carrier;

import android.os.Message;

import org.elastos.carrier.Carrier;
import org.elastos.carrier.ConnectionStatus;
import org.elastos.carrier.FriendInfo;
import org.elastos.carrier.PresenceStatus;
import org.elastos.carrier.UserInfo;

import java.util.List;

import cse.uta.elawaves.Carrier.Message.OnConnectionMessage;
import cse.uta.elawaves.Carrier.Message.OnIdleMessage;

public class CarrierHandler implements org.elastos.carrier.CarrierHandler {

    private CarrierImplementation implementation;

    public CarrierHandler(CarrierImplementation implementation){
        this.implementation = implementation;
    }

    @Override
    public void onIdle(Carrier carrier) {
        OnIdleMessage message = new OnIdleMessage();

        implementation.handleMessage(CarrierImplementation.ON_IDLE,message);
    }

    @Override
    public void onConnection(Carrier carrier, ConnectionStatus connectionStatus) {
        OnConnectionMessage message = new OnConnectionMessage(connectionStatus);

        implementation.handleMessage(CarrierImplementation.ON_CONNECTION,message);
    }

    @Override
    public void onReady(Carrier carrier) {

    }

    @Override
    public void onSelfInfoChanged(Carrier carrier, UserInfo userInfo) {
    }

    @Override
    public void onFriends(Carrier carrier, List<FriendInfo> list) {
    }

    @Override
    public void onFriendConnection(Carrier carrier, String friendID, ConnectionStatus connectionStatus) {
    }

    @Override
    public void onFriendInfoChanged(Carrier carrier, String friendID, FriendInfo friendInfo) {

    }

    @Override
    public void onFriendPresence(Carrier carrier, String friendID, PresenceStatus presenceStatus) {
    }

    @Override
    public void onFriendRequest(Carrier carrier, String friendID, UserInfo userInfo, String message) {
    }

    @Override
    public void onFriendAdded(Carrier carrier, FriendInfo friendInfo) {

    }

    @Override
    public void onFriendRemoved(Carrier carrier, String friendID) {

    }

    @Override
    public void onFriendMessage(Carrier carrier, String friendID, byte[] bytes) {

    }

    @Override
    public void onFriendInviteRequest(Carrier carrier, String friendID, String message) {

    }

    @Override
    public void onGroupInvite(Carrier carrier, String from, byte[] bytes) {

    }
}
