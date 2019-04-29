package cse.uta.elawaves.Wallet;

import com.elastos.spvcore.IMasterWallet;
import com.elastos.spvcore.MasterWalletManager;
import com.elastos.spvcore.ISubWallet;

public class WalletImplementation {

    private static WalletImplementation instance = null;
    private MasterWalletManager walletManager;
    private IMasterWallet masterWallet;
    private ISubWallet isubWallet;

    public static WalletImplementation getWallet()
    {
        return instance;
    }

    public static WalletImplementation getInstance(String path, String masterWalletId, String phrasePassword, String payPassword)
    {
        if(instance == null)
            instance = new WalletImplementation(path, masterWalletId, phrasePassword, payPassword);

        return instance;
    }

    private WalletImplementation(String path,
                                 String masterWalletId, String phrasePassword, String payPassword)
    {
        String chainID = "ELA";
        long feePerKb = 10000;  // The unit of the parameter feePerKb is SELA

        walletManager = new MasterWalletManager(path);
        String language = "english";
        String mnemonic = walletManager.GenerateMnemonic(language);

        masterWallet = walletManager.CreateMasterWallet(masterWalletId, mnemonic, phrasePassword, payPassword,false);
        isubWallet = masterWallet.CreateSubWallet(chainID, feePerKb);
    }

    public String GenerateMnemonic(String language){
        return this.walletManager.GenerateMnemonic(language);
    }

    public IMasterWallet CreateMasterWallet(String walletID,String mnemonic,String phrasePassword,String payPassword){
        return walletManager.CreateMasterWallet(walletID,mnemonic,phrasePassword,payPassword,false);
    }

    public long GetBalance()
    {
        return isubWallet.GetBalance();
    }

    public String CreateTransaction(String fromAddress, String toAddress, long amount, String memo, String remark)
    {
        return isubWallet.CreateTransaction(fromAddress, toAddress, amount, memo, remark);
    }

    public String PublishTransaction(String rawTransaction)
    {
        return isubWallet.PublishTransaction(rawTransaction);
    }
}
