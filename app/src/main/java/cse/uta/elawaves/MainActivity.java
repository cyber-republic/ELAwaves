package cse.uta.elawaves;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.elastos.carrier.Carrier;
import org.elastos.carrier.exceptions.CarrierException;

import cse.uta.elawaves.Carrier.CarrierImplementation;
import cse.uta.elawaves.Carrier.Message.CarrierCallback;
import cse.uta.elawaves.Carrier.Message.CarrierMessage;
import cse.uta.elawaves.Carrier.Message.OnConnectionMessage;

public class MainActivity extends AppCompatActivity implements FragmentMain.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            CarrierImplementation.getInstance(getApplicationContext());
        } catch (CarrierException e) {
            e.printStackTrace();
        }

        CarrierImplementation.setCallback(CarrierImplementation.ON_CONNECTION, new CarrierCallback() {
            @Override
            public void handleMessage(CarrierMessage message) {
                System.out.println("CONNECTED TO CARRIER");
                System.out.println(((OnConnectionMessage) message).status.value());
            }
        });
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        CarrierImplementation.clearCallbacks();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
