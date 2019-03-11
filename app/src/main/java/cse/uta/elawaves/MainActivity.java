package cse.uta.elawaves;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.elastos.carrier.exceptions.CarrierException;

import cse.uta.elawaves.Carrier.CarrierCallback;
import cse.uta.elawaves.Carrier.CarrierImplementation;
import cse.uta.elawaves.Carrier.CarrierMessage;

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

        CarrierImplementation.setCallback(new CarrierCallback() {
            @Override
            public void handleMessage(CarrierMessage message) {
                if(message.type == CarrierImplementation.ON_READY){
                    System.out.println("Carrier is ready");
                }
            }
        });
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        CarrierImplementation.clearCallback();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
