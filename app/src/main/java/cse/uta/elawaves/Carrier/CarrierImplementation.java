package cse.uta.elawaves.Carrier;


import android.content.Context;
import android.os.Handler;
import android.util.SparseArray;

import org.elastos.carrier.Carrier;
import org.elastos.carrier.exceptions.CarrierException;

import cse.uta.elawaves.Carrier.Message.CarrierCallback;
import cse.uta.elawaves.Carrier.Message.CarrierMessage;

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

    public static CarrierImplementation getInstance(Context context) throws CarrierException {
        if(instance == null)
            instance = new CarrierImplementation(context);

        return instance;
    }

    private static SparseArray<CarrierCallback> callbacks = new SparseArray<>(14);
    private CarrierImplementation(Context context) throws CarrierException {
        Options options = new Options(context.getFilesDir().getParent());

        Carrier.initializeInstance(options,new CarrierHandler(this));

        Carrier carrier = Carrier.getInstance();
            carrier.start(0);
    }

    public static void setCallback(int i, CarrierCallback callback){
        callbacks.put(i,callback);
    }

    public static void clearCallbacks(){
        callbacks.clear();
    }

    public static void removeCallback(int i){
        callbacks.remove(i);
    }

    public static void handleMessage(int i, CarrierMessage carrierCallback){
        // Attempt to call all registered callbacks
        CarrierCallback callback = callbacks.get(i);
        if (callback != null)
            callback.handleMessage(carrierCallback);

    }
}
