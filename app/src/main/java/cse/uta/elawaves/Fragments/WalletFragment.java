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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.content.Intent;

import com.elastos.spvcore.ElastosWalletUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.net.URI;

import cse.uta.elawaves.Wallet.WalletException;
import cse.uta.elawaves.Wallet.WalletImplementation;
import cse.uta.elawaves.MainActivity;
import cse.uta.elawaves.R;

import static android.app.Activity.RESULT_OK;

public class WalletFragment extends Fragment {
    public final static int WIDTH = 500;
    public static int white = 0xFFFFFFFF;
    public static int black = 0xFF000000;
    TextView balance;
    private WalletImplementation wallet;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.fragment_wallet, container, false);
        balance = view.findViewById(R.id.Balance);
        ImageView qrCodePic = view.findViewById(R.id.userQRCode);

        try
        {
            wallet = WalletImplementation.getWallet();
            if(wallet != null)
            {
                Long balanceVal = wallet.GetBalance();
                balance.setText(balanceVal.toString());
            }
            else
            {
                balance.setText("Error");
            }
/*
            Bitmap bmp = encodeAsBitmap();
            Bitmap bmp = encodeAsBitmap(CarrierImplementation.getCarrier().getAddress());
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
