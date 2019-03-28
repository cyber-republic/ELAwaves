package cse.uta.elawaves.Wallet;

import com.elastos.spvcore.IMasterWallet;
import com.elastos.spvcore.MasterWalletManager;


public class WalletImplementation {

    private static WalletImplementation instance;
    private MasterWalletManager walletManager;

    public static WalletImplementation getInstance(String path){
        if(instance == null)
            instance = new WalletImplementation(path);

        return instance;
    }

    private WalletImplementation(String path){
        walletManager = new MasterWalletManager(path);
    }

    public String GenerateMnemonic(String language){
        return this.walletManager.GenerateMnemonic(language);
    }

    public IMasterWallet CreateMasterWallet(String walletID,String mnemonic,String phrasePassword,String payPassword){
        return walletManager.CreateMasterWallet(walletID,mnemonic,phrasePassword,payPassword,false);
    }

}
