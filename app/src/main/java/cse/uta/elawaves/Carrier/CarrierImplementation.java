package cse.uta.elawaves.Carrier;


import android.content.Context;

import org.elastos.carrier.Carrier;
import org.elastos.carrier.exceptions.CarrierException;

import java.util.HashMap;

public class CarrierImplementation {

    public static final int ON_IDLE = 0;
    public static final int ON_CONNECTION = 1;
    public static final int ON_READY = 2;
    public static final int ON_SELF_INFO_CHANGED = 3;
    public static final int ON_FRIENDS = 4;
    public static final int ON_FRIEND_CONNECTION = 5;
    public static final int ON_FRIEND_INFO_CHANGED = 6;
    public static final int ON_FRIEND_PRESENCE = 7;
    public static final int ON_FRIEND_REQUEST = 8;
    public static final int ON_FRIEND_ADDED = 9;
    public static final int ON_FRIEND_REMOVE = 10;
    public static final int ON_FRIEND_MESSAGE = 11;
    public static final int ON_FRIEND_INVITE_REQUEST = 12;
    public static final int ON_GROUP_INVITE = 13;

    private static CarrierImplementation instance;

    static HashMap<String,Boolean> friend_connection_status = new HashMap<>();
    static boolean carrier_ready = false;

    private static Carrier carrier_instance;

    public static CarrierImplementation getInstance(Context context) throws CarrierException {
        if(instance == null)
            instance = new CarrierImplementation(context);

        return instance;
    }

    private CarrierImplementation(Context context) throws CarrierException {
        Options options = new Options(context.getFilesDir().getParent());

        Carrier.initializeInstance(options,new CarrierHandler());

        Carrier carrier = Carrier.getInstance();
            carrier.start(0);

        carrier_instance = carrier;
    }

    public static Carrier getCarrier(){
        return carrier_instance;
    }

    public static boolean isReady(){
        return carrier_ready;
    }

    private static CarrierCallback callback;

    public static void setCallback(CarrierCallback newCallback){
        callback = newCallback;
    }

    public static void clearCallback(){
        callback = null;
    }

    public static void handleMessage(CarrierMessage message){
        if(callback != null)
            callback.handleMessage(message);
    }
}
