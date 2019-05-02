package cse.uta.elawaves.Fragments;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.content.Intent;

import com.elastos.spvcore.WalletException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.elastos.carrier.Carrier;
import org.elastos.carrier.FriendInfo;
import org.elastos.carrier.exceptions.CarrierException;

import java.net.URI;
import java.util.List;

import androidx.navigation.Navigation;
import cse.uta.elawaves.Carrier.CarrierImplementation;
import cse.uta.elawaves.Wallet.WalletImplementation;
import cse.uta.elawaves.MainActivity;
import cse.uta.elawaves.R;

import static android.app.Activity.RESULT_OK;

public class WalletFragment extends Fragment {
    public final static int WIDTH = 500;
    public static int white = 0xFFFFFFFF;
    public static int black = 0xFF000000;
    TextView balance;
    TextView sendView;
    private WalletImplementation wallet;
    private static final int PICK_IMAGE = 100;
    Uri imageURI;
    private String QRcode;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.fragment_wallet, container, false);
        balance = view.findViewById(R.id.Balance);

        final IntentIntegrator intentIntegrator = IntentIntegrator.forSupportFragment(this);

        ImageView qrCodePic = view.findViewById(R.id.userQRCode);
        sendView = view.findViewById(R.id.send_button);

        try
        {
            wallet = WalletImplementation.getWallet();
            if(wallet != null)
            {
                Long balanceVal = wallet.GetBalance();
                balance.setText(balanceVal.toString());
                //balance.setText(String.format("%.2f", balanceVal.toString()) + " ELA");
            }
            else
            {
                balance.setText("No Wallet Found.");
            }
/*
            Bitmap bmp = encodeAsBitmap(WalletImplementation.getWallet().);
            qrCodePic.setImageBitmap(bmp);
*/
        }
        /*catch(WriterException e){
            e.printStackTrace();
        } */
        catch (WalletException e) {
            e.printStackTrace();
        }

        //balance.setText("$12.00");

    //When the Send Transaction is clicked, will open QR scanner to scan address; then prompt for transaction amount
        sendView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                intentIntegrator.setBeepEnabled(false);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                intentIntegrator.setPrompt("Scan Wallet Address");
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.initiateScan();

                final AlertDialog.Builder changeAmount;
                changeAmount = new AlertDialog.Builder(getActivity());
                changeAmount.setMessage("Do you want Send a Transaction?").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final View userInput = (LayoutInflater.from(getActivity()).inflate(R.layout.user_input,null));
                                final AlertDialog.Builder sendTransaction;
                                sendTransaction = new AlertDialog.Builder(getActivity());
                                sendTransaction.setView(userInput);
                                final EditText amount = (EditText) userInput.findViewById(R.id.changeName);
                                        //final EditText amount = (EditText) userInput.findViewById(R.id.changeName);
                                /*final EditText amount = new EditText(getActivity());
                                amount.setInputType(InputType.TYPE_CLASS_TEXT);
                                changeAmount.setView(amount); */

                                sendTransaction.setMessage("Enter an Amount:").setCancelable(false).setPositiveButton("Enter", new DialogInterface.OnClickListener()
                                {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        String amt = amount.getText().toString();
                                        System.out.print(amt);
/*
                                        info.setName(newName.getText().toString());
                                        try {
                                            CarrierImplementation.getCarrier().setSelfInfo(info);
                                        } catch (CarrierException e) {
                                            e.printStackTrace();
                                        }
                                        userView.setText(info.getName());
*/
                                    }
                                })
                                .setNegativeButton("Cancel ", new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        dialog.cancel();
                                    }
                                });

                                AlertDialog alert2 = sendTransaction.create();
                                alert2.setTitle("Send Transaction");
                                alert2.show();
                            }
                        })
                        .setNegativeButton("No ", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = changeAmount.create();
                alert.setTitle("Send Transaction");
                alert.show();

            }
        });

        return view;

    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result.getContents() != null)
        {
            QRcode = result.getContents();
            System.out.println("QR: " + QRcode);
           /*
            try {
                System.out.println("QR: " + result.getContents());
                WalletImplementation.getWallet().CreateTransaction(result.getContents(), result.getContents(),)
            }
            catch (WalletException e) {
                e.printStackTrace();
            } */
        }

        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageURI = data.getData();
        }
    }

    public static Bitmap decodeSampleBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeResource(res, resId, options);
    }

    Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix result;
        Bitmap bitmap = null;
        try {
            result = new MultiFormatWriter().encode(str,
                    BarcodeFormat.QR_CODE, WIDTH, WIDTH, null);

            int w = result.getWidth();
            int h = result.getHeight();
            int[] pixels = new int[w * h];
            for (int y = 0; y < h; y++) {
                int offset = y * w;
                for (int x = 0; x < w; x++) {
                    pixels[offset + x] = result.get(x, y) ? black : white;
                }
            }
            bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, WIDTH, 0, 0, w, h);
        } catch (Exception iae) {
            iae.printStackTrace();
            return null;
        }
        return bitmap;
    }

}
