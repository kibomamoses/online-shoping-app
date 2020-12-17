package com.example.moses.frags;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.moses.R;
import com.example.moses.adapters.Adp_Img_Products;
import com.example.moses.model.Mod_Img_Prducts;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Frag_Home extends Fragment implements ViewPagerEx.OnPageChangeListener {

    RecyclerView recyl;

    Adp_Img_Products adp_imgProducts;

    List<Mod_Img_Prducts> mod_imgProducts;

    SliderLayout sliderLayout;
    HashMap<String, String> sliderImages;

    ArrayList<String> img_list = new ArrayList<>();
    ArrayList<String> img_cat = new ArrayList<>();

    LinearLayout ct_sch, ct_sprt, ct_acc, ct_cloth, ct_soft, ct_all;

    View solv;

    public Frag_Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        solv= inflater.inflate(R.layout.frag_home, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Product");

        ct_sch = solv.findViewById(R.id.cat_fries);
        ct_sprt = solv.findViewById(R.id.cat_cakes);
        ct_acc = solv.findViewById(R.id.cat_drinks);
        ct_cloth = solv.findViewById(R.id.cat_meat);
        ct_soft = solv.findViewById(R.id.cat_vegan);
        ct_all = solv.findViewById(R.id.cat_all);

        ct_sch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Taggable as ", "onClick: School" );
            }
        });

        ct_sprt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Taggable as ", "onClick: Sport" );
            }
        });

        ct_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Taggable as ", "onClick: Acces" );
            }
        });

        ct_cloth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Taggable as ", "onClick: Cloth" );
            }
        });

        ct_soft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Taggable as ", "onClick: Soft" );
            }
        });

        ct_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Taggable as ", "onClick: All" );
            }
        });

        sliderLayout = solv.findViewById(R.id.sliderLayout);
        sliderImages = new HashMap<>();

        InitImgProducts();
        InitVarr();

        InitImgProducts();
        
        return solv;
    }

    private void InitVarr() {
        DatabaseReference dref= FirebaseDatabase.getInstance().getReference("Kiboma/Product");
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int size = (int) dataSnapshot.getChildrenCount();

                for (DataSnapshot ds1: dataSnapshot.getChildren()){
                    String imglong = (String) ds1.child("pImage").getValue();
                    String imgcat = (String) ds1.child("pType").getValue();

                    img_list.add(imglong);
                    img_cat.add(imgcat);
                    Log.e("New Element", "onDataChange: " );

                }

                InitImgBanner(img_list,img_cat);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("FetchFries", "onCancelled: "+databaseError );
            }
        });
    }


    private void InitImgBanner(ArrayList<String> img_vars, ArrayList<String> cat_vars) {
        sliderLayout = (SliderLayout) solv.findViewById(R.id.sliderLayout);
        sliderImages = new HashMap<>();

        Log.e("InitImgBanner as ", "img_cat as " + img_vars.size());
        Log.e("InitImgBanner as ", "img_list as " + cat_vars.size());

        for ( int dd = 0; dd < cat_vars.size(); dd++) {
            sliderImages.put(cat_vars.get(dd), img_vars.get(dd));
        }

        for (String name : sliderImages.keySet()) {

            TextSliderView textSliderView = new TextSliderView(getActivity());
            textSliderView
                    .description(name)
                    .image(sliderImages.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {
                            String my_index = sliderImages.get(name);
                            int dd = img_vars.indexOf(my_index);
                            String cat = cat_vars.get(dd);

                            /*Intent viw = new Intent(getActivity(), SingleCategory.class);
                            viw.putExtra("kiwango",cat);
                            startActivity(viw);*/

                            /*Log.e("My_Index as ", "String as " + my_index);
                            Log.e("My_Index as ", "Index  as " + dd);
                            Log.e("My_Index as ", "Cat    as " + cat);*/
                        }
                    });
            //.setOnSliderClickListener(this);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);
            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(3000);
        sliderLayout.addOnPageChangeListener(this);

    }

    private void InitImgProducts() {
        recyl=solv.findViewById(R.id.list_fries);
        recyl.setHasFixedSize(true);

        LinearLayoutManager lim = new LinearLayoutManager(getActivity());
        lim.setReverseLayout(true);
        lim.setStackFromEnd(true);
        recyl.setLayoutManager(lim);
        mod_imgProducts=new ArrayList<>();

        FetchImgProducts();
    }

    private void FetchImgProducts() {
        DatabaseReference dref= FirebaseDatabase.getInstance().getReference("Kiboma/Product");
        dref.keepSynced(true);

        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mod_imgProducts.clear();

                for (DataSnapshot ds1: dataSnapshot.getChildren()){
                    Mod_Img_Prducts ug=ds1.getValue(Mod_Img_Prducts.class);
                    Log.e("FetchImgProducts", "onDataChange: ");

                    mod_imgProducts.add(ug);

                    adp_imgProducts =new Adp_Img_Products(getActivity(),mod_imgProducts);

                    recyl.setAdapter(adp_imgProducts);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("FetchFries", "onCancelled: "+databaseError );
            }
        });
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
}
