package cse.uta.elawaves;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.elastos.spvcore.ElastosWalletUtils;
import com.elastos.spvcore.WalletException;

import org.elastos.carrier.exceptions.CarrierException;

import cse.uta.elawaves.Carrier.CarrierCallback;
import cse.uta.elawaves.Carrier.CarrierImplementation;
import cse.uta.elawaves.Carrier.CarrierMessage;
import cse.uta.elawaves.Fragments.FragmentMain;
import cse.uta.elawaves.Wallet.WalletImplementation;

public class MainActivity extends AppCompatActivity implements FragmentMain.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Startup Carrier
        Context applicationContext = getApplicationContext();
        try{
            CarrierImplementation.getInstance(applicationContext);
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

        // Startup Wallet
        String rootPath = getApplicationContext().getFilesDir().getParent();
        try{
            ElastosWalletUtils.InitConfig(this,rootPath);
            WalletImplementation.getInstance(rootPath);
        }catch (WalletException e){
            e.printStackTrace();
        }
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
