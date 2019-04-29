package cse.uta.elawaves;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.elastos.spvcore.ElastosWalletUtils;
import com.elastos.spvcore.WalletException;

import org.elastos.carrier.Carrier;
import org.elastos.carrier.exceptions.CarrierException;

import cse.uta.elawaves.Carrier.CarrierImplementation;
import cse.uta.elawaves.Wallet.WalletImplementation;

public class
MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Startup Carrier
        Context applicationContext = getApplicationContext();
        try{
            CarrierImplementation.getInstance(applicationContext);
        } catch (CarrierException e) {
            e.printStackTrace();
        }

        if (CarrierImplementation.isReady()){
            Intent i = new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(i);
            finish();
        }

        CarrierImplementation.onReady(this);

        // Startup Wallet
        String rootPath = getApplicationContext().getFilesDir().getParent();
        try
        {
            ElastosWalletUtils.InitConfig(this,rootPath);
            WalletImplementation.getInstance(rootPath, "masterWalletID", "", "elastos2018");
        }
        catch (WalletException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    public void carrierReady(){
        Intent i = new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(i);
        finish();
    }
}
