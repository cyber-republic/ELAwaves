package cse.uta.elawaves.Fragments;
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

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import org.elastos.carrier.Carrier;
import org.elastos.carrier.UserInfo;
import org.elastos.carrier.exceptions.CarrierException;

import java.net.URI;

import cse.uta.elawaves.Carrier.CarrierImplementation;
import cse.uta.elawaves.HomeActivity;
import cse.uta.elawaves.MainActivity;
import cse.uta.elawaves.R;

import static android.app.Activity.RESULT_OK;

public class AccountInfoFragment extends Fragment{

    public final static int WIDTH = 500;
    public static int white = 0xFFFFFFFF;
    public static int black = 0xFF000000;
    TextView userView;
    TextView emailView;
    TextView phoneView;
    ImageView profilePic;
    private static UserInfo info;
    String name;
    String email;
    String phone;
    private static final int PICK_IMAGE = 100;
    Uri imageURI;

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_account_info,container,false);
        profilePic = view.findViewById(R.id.profilePic);
        profilePic.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openGallery();
            }

        });

        ImageView qrCodePic = view.findViewById(R.id.userQRCode);

        profilePic.setImageBitmap(decodeSampleBitmapFromResource(getResources(),R.drawable.profilepictest,100,100));

        userView = view.findViewById(R.id.userName);
        emailView = view.findViewById(R.id.email_text);
        phoneView = view.findViewById(R.id.phone_text);

        try{
            info = CarrierImplementation.getCarrier().getSelfInfo();
            name = info.getName();
            email = info.getEmail();
            phone = info.getPhone();

            if(name.isEmpty()) {
                name = "Click to set name";
            }
            if(email.isEmpty()) {
                email = "Click to set email";
            }
            if(phone.isEmpty()) {
                phone = "Click to set phone number";
            }
            userView.setText(name);
            emailView.setText(email);
            phoneView.setText(phone);
            Bitmap bmp = encodeAsBitmap(CarrierImplementation.getCarrier().getAddress());
            qrCodePic.setImageBitmap(bmp);

        }
        catch(WriterException e){
            e.printStackTrace();
        } catch (CarrierException e) {
            e.printStackTrace();
        }

        //When the display name is clicked, will prompt the user if they want to change their current display name
        userView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder changeName;
                changeName = new AlertDialog.Builder(getActivity());
                changeName.setMessage("Do you want to change your display name?").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                View userInput = (LayoutInflater.from(getActivity()).inflate(R.layout.user_input,null));
                                final AlertDialog.Builder enterUserName;
                                enterUserName = new AlertDialog.Builder(getActivity());
                                enterUserName.setView(userInput);
                                final EditText newName = (EditText) userInput.findViewById(R.id.changeName);

                                enterUserName.setMessage("Enter a username:").setCancelable(false).setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        info.setName(newName.getText().toString());
                                        try {
                                            CarrierImplementation.getCarrier().setSelfInfo(info);
                                        } catch (CarrierException e) {
                                            e.printStackTrace();
                                        }
                                        userView.setText(info.getName());

                                    }
                                });

                                AlertDialog alert2 = enterUserName.create();
                                alert2.setTitle("Change Display Name");
                                alert2.show();

                            }


                        })
                        .setNegativeButton("No ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();

                            }
                        });
                AlertDialog alert = changeName.create();
                alert.setTitle("Change Display Name");
                alert.show();
            }
        });
        //When the email address is clicked, will prompt the user if they want to change their current email address
        emailView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder changeEmail;
                changeEmail = new AlertDialog.Builder(getActivity());
                changeEmail.setMessage("Do you want to change your email address?").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                View userInput = (LayoutInflater.from(getActivity()).inflate(R.layout.user_input,null));
                                final AlertDialog.Builder enterEmail;
                                enterEmail = new AlertDialog.Builder(getActivity());
                                enterEmail.setView(userInput);
                                final EditText newEmail = (EditText) userInput.findViewById(R.id.changeName);

                                enterEmail.setMessage("Enter your email address:").setCancelable(false).setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        info.setEmail(newEmail.getText().toString());
                                        try {
                                            CarrierImplementation.getCarrier().setSelfInfo(info);
                                        } catch (CarrierException e) {
                                            e.printStackTrace();
                                        }
                                        emailView.setText(info.getEmail());

                                    }
                                });

                                AlertDialog alert2 = enterEmail.create();
                                alert2.setTitle("Change Email Address");
                                alert2.show();

                            }


                        })
                        .setNegativeButton("No ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();

                            }
                        });
                AlertDialog alert = changeEmail.create();
                alert.setTitle("Change Email Address");
                alert.show();
            }
        });
        //When the phone number is clicked, will prompt the user if they want to change their current phone number
        phoneView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder changePhone;
                changePhone = new AlertDialog.Builder(getActivity());
                changePhone.setMessage("Do you want to change your phone number?").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                View userInput = (LayoutInflater.from(getActivity()).inflate(R.layout.user_input,null));
                                final AlertDialog.Builder enterPhone;
                                enterPhone = new AlertDialog.Builder(getActivity());
                                enterPhone.setView(userInput);
                                final EditText newPhone = (EditText) userInput.findViewById(R.id.changeName);

                                enterPhone.setMessage("Enter your phone number:").setCancelable(false).setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        info.setPhone(newPhone.getText().toString());
                                        try {
                                            CarrierImplementation.getCarrier().setSelfInfo(info);
                                        } catch (CarrierException e) {
                                            e.printStackTrace();
                                        }
                                        phoneView.setText(info.getPhone());

                                    }
                                });

                                AlertDialog alert2 = enterPhone.create();
                                alert2.setTitle("Change Phone Number");
                                alert2.show();

                            }


                        })
                        .setNegativeButton("No ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();

                            }
                        });
                AlertDialog alert = changePhone.create();
                alert.setTitle("Change Phone Number");
                alert.show();
            }
        });
        return view;

    }
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight){
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if(height > reqHeight || width > reqWidth){
            final int halfHeight = height/2;
            final int halfWidth = width/2;

            while((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth){
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
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageURI = data.getData();
            profilePic.setImageURI(imageURI);
        }
    }


    public static Bitmap decodeSampleBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeResource(res, resId, options);
    }

    Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix result;
        Bitmap bitmap=null;
        try
        {
            result = new MultiFormatWriter().encode(str,
                    BarcodeFormat.QR_CODE, WIDTH, WIDTH, null);

            int w = result.getWidth();
            int h = result.getHeight();
            int[] pixels = new int[w * h];
            for (int y = 0; y < h; y++) {
                int offset = y * w;
                for (int x = 0; x < w; x++) {
                    pixels[offset + x] = result.get(x, y) ? black:white;
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


    public void onAttach(Context context){
        super.onAttach(context);
    }

    @Override
    public void onDetach(){
        super.onDetach();
    }

}
