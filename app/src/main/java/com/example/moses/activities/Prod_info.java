package com.example.moses.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.moses.R;
import com.example.moses.auth.UserLogin;
import com.example.moses.model.Mod_Cart;
import com.example.moses.utilities.Konstants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Prod_info extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{

    Intent getbun;

    DatabaseReference mDatabaseRef;
    FirebaseAuth mAuth;
    FirebaseUser userf;

    ProgressDialog pds;

    TextView nm ,des ,spec,pr,cart;

    ArrayList<String> img_tag = new ArrayList<>();

    public SliderLayout sliderLayout;
    public  HashMap<String, String> sliderImages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desc);

        getSupportActionBar().setTitle("Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mAuth= FirebaseAuth.getInstance();
        userf=mAuth.getCurrentUser();

        pds=new ProgressDialog(Prod_info.this);

        nm = findViewById(R.id.prod_nm);
        des = findViewById(R.id.prod_desc);
        spec = findViewById(R.id.prod_spec);
        pr = findViewById(R.id.prod_price);

        cart  = findViewById(R.id.prod_cart);

        getbun = getIntent();
        String nambari=getbun.getStringExtra("nambari");

        sliderLayout = findViewById(R.id.sliderLayout);
        sliderLayout = (SliderLayout)findViewById(R.id.sliderLayout);
        sliderImages = new HashMap<>();

        mDatabaseRef= FirebaseDatabase.getInstance().getReference("Kiboma/Product").child(nambari);
        mDatabaseRef.keepSynced(true);

        try {
            pds.setMessage("Loading Info");
            pds.show();

            mDatabaseRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String gUid, gPrice, gName, gSpec, gDesc, gImage, gTime, gUser;
                    String pType, pPrice,pDesc,pDate,pImage,pId, pDeliver, pWeight;

                    gPrice= (String) dataSnapshot.child("pPrice").getValue();
                    gName= (String) dataSnapshot.child("pWeight").getValue();
                    gSpec= (String) dataSnapshot.child("pDeliver").getValue();
                    gDesc= (String) dataSnapshot.child("pDesc").getValue();
                    gImage= (String) dataSnapshot.child("pImage").getValue();

                    double kgs = Double.parseDouble(gName)/1000;

                    nm.setText("Weight "+kgs+" kgs");
                    des.setText(gDesc);
                    spec.setText("Delivered to "+gSpec);
                    pr.setText(gPrice+" KShs");

                    String imageUrls[],imagelement[];
                    int fd=0;

                    gImage = gImage.replace("|-|","\t");
                    imageUrls = gImage.split("\t");

                    for (String code : imageUrls){
                        img_tag.add(code);
                        sliderImages.put(fd+"", code);
                        fd+=1;
                    }

                    for (String name : sliderImages.keySet()) {

                        TextSliderView textSliderView = new TextSliderView(Prod_info.this);
                        textSliderView
                                .description(name)
                                .image(sliderImages.get(name))
                                .setScaleType(BaseSliderView.ScaleType.Fit)
                                .setOnSliderClickListener(Prod_info.this);
                        textSliderView.bundle(new Bundle());
                        textSliderView.getBundle()
                                .putString("extra", name);
                        sliderLayout.addSlider(textSliderView);
                    }
                    sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
                    sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                    sliderLayout.setCustomAnimation(new DescriptionAnimation());
                    sliderLayout.setDuration(3000);
                    sliderLayout.addOnPageChangeListener(Prod_info.this);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }catch (Exception ex){
            Log.e("mDatabaseRef ", "Load Data ********************: \n" +ex.getMessage());
        }

        pds.dismiss();

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseRef = FirebaseDatabase.getInstance().getReference(Konstants.Data_Cart+"/" +userf.getUid()+"/"+nambari);

                Mod_Cart map = new Mod_Cart(userf.getUid(),nambari,String.valueOf(Calendar.getInstance().getTimeInMillis()));
                mDatabaseRef.setValue(map);
                Toast.makeText(Prod_info.this, "Added New Product to cart", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser fuse=mAuth.getCurrentUser();
        if (fuse!=null){}else {
            startActivity(new Intent(this, UserLogin.class));
            finish();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        FirebaseUser fuse=mAuth.getCurrentUser();
        if (fuse!=null){}else {
            startActivity(new Intent(this, UserLogin.class));
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int idd=item.getItemId();
        if (idd==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
