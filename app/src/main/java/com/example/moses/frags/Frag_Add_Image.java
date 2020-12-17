package com.example.moses.frags;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.moses.R;
import com.example.moses.model.Mod_Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class Frag_Add_Image extends Fragment {

    Button p_add;
    ImageView p_sed, imgsee;

    ProgressDialog pd2;

    EditText P_price, P_desc, P_weight;

    Spinner P_type, P_deli;

    String typ, price,desc,dat,img, deli, weight;

    private Uri mUriCrop;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;

    public Frag_Add_Image() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fraghome= inflater.inflate(R.layout.frag_add_image, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Add Product");

        p_sed = fraghome.findViewById(R.id.prod_send);
        p_add = fraghome.findViewById(R.id.prod_img);
        imgsee = fraghome.findViewById(R.id.prod_pic);

        P_price = fraghome.findViewById(R.id.prod_price);
        P_desc = fraghome.findViewById(R.id.prod_desc);
        P_type = fraghome.findViewById(R.id.prod_type);
        P_deli = fraghome.findViewById(R.id.prod_delivery);
        P_weight = fraghome.findViewById(R.id.prod_weight);

        pd2=new ProgressDialog(getActivity());

        mStorageRef = FirebaseStorage.getInstance().getReference("Kiboma/Product");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Kiboma/Product");

        p_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenImg();
            }
        });

        p_sed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(getActivity(), "Upload in progress", Toast.LENGTH_LONG).show();
                } else {
                    typ = P_type.getSelectedItem().toString();
                    price = P_price.getText().toString();
                    desc = P_desc.getText().toString();
                    deli = P_deli.getSelectedItem().toString();
                    weight = P_weight.getText().toString();

                    if (typ.isEmpty() || typ.contains("Suit Type") ||price.isEmpty() || desc.isEmpty() || deli.isEmpty()|| deli.contains("Location") || weight.isEmpty()){
                        Toast.makeText(getActivity(), "Enter the Price, Description, Type, Delivery and Weight to proceed", Toast.LENGTH_LONG).show();
                    }else {
                        Send_Product();
                    }

                }
            }
        });

        return fraghome;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mUriCrop = result.getUri();
                imgsee.setVisibility(View.VISIBLE);
                Picasso.with(getActivity()).load(mUriCrop).into(imgsee);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                String er=error.getMessage().toString();
                Toast.makeText(getActivity(), "onActivityResult error\n"+er, Toast.LENGTH_LONG).show();
            }
        }else {
            Log.e("onActivityResult", "onActivityResult:  is ture 3" );
        }
    }

    private void OpenImg(){
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setAllowRotation(true)
                .setAllowCounterRotation(true)
                .setAllowFlipping(true)
                .setBorderLineColor(Color.RED)
                .setBorderCornerColor(Color.BLUE)
                .setGuidelinesColor(Color.GREEN)
                .setBorderLineThickness(2)
                .setActivityTitle("Modify Image")
                .setMultiTouchEnabled(true)
                .setAutoZoomEnabled(true)
                .start(getContext(),this);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void Send_Product() {
        if (mUriCrop != null) {
            pd2.setMessage("Publishing your new Product");
            pd2.show();
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mUriCrop));

            mUploadTask = fileReference.putFile(mUriCrop)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                            result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Toast.makeText(getActivity(), "Upload successful", Toast.LENGTH_LONG).show();
                                    String uploadId = mDatabaseRef.push().getKey();

                                    Calendar cal= Calendar.getInstance();
                                    SimpleDateFormat ctime=new SimpleDateFormat("HH:mm:ss");
                                    SimpleDateFormat cdate=new SimpleDateFormat("dd-MMMM-yyyy");

                                    final String ctim=ctime.format(cal.getTime());
                                    final String cdat=cdate.format(cal.getTime());

                                    dat = String.valueOf(cdat+" "+ctim);

                                    Mod_Product posed = new Mod_Product(typ,price,desc,dat,uri.toString(),uploadId,deli,weight);
                                    mDatabaseRef.child(uploadId).setValue(posed);
                                }
                            });

                            mUriCrop=null;
                            pd2.dismiss();

                            AppCompatActivity activity = (AppCompatActivity) getContext();
                            Fragment myFragment = new Frag_Home();
                            activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, myFragment).addToBackStack(null).commit();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd2.dismiss();
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            //mProg.setText(String.valueOf(progress));
                        }
                    });
        } else {
            Toast.makeText(getActivity(), "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

}
