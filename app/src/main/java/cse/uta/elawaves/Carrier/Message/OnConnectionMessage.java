package cse.uta.elawaves.Carrier.Message;

import org.elastos.carrier.ConnectionStatus;

public class OnConnectionMessage implements CarrierMessage {

    public ConnectionStatus status;

    public OnConnectionMessage(ConnectionStatus status){
        this.status = status;
    }
}
