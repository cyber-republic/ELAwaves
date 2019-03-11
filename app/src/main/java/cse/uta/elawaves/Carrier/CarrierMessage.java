package cse.uta.elawaves.Carrier;

import org.elastos.carrier.ConnectionStatus;
import org.elastos.carrier.FriendInfo;
import org.elastos.carrier.PresenceStatus;
import org.elastos.carrier.UserInfo;

import java.util.List;

public class CarrierMessage {
    public int type;
    public ConnectionStatus connectionStatus;
    public String message;
    public String friendID;
    public UserInfo userInfo;
    public FriendInfo friendInfo;
    public List<FriendInfo> friends;
    public PresenceStatus presenceStatus;
    public byte[] bytes;
}
