package com.example.byahed2;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


public class HomeFragment extends Fragment {

    DatabaseReference locRef = FirebaseDatabase.getInstance().getReference();
    FirebaseStorage storage;

    ImageView H_TopTourismSpot1;
    ImageView H_TopTourismSpot2;
    ImageView H_TopTourismSpot3;
    ImageView H_TopTourismSpot4;
    ImageView H_TopTourismSpot5;
    ImageView H_TopTourismSpot6;
    ImageView H_TopTourismSpot7;

    TextView H_TopTourismSpotText1;
    TextView H_TopTourismSpotText2;
    TextView H_TopTourismSpotText3;
    TextView H_TopTourismSpotText4;
    TextView H_TopTourismSpotText5;
    TextView H_TopTourismSpotText6;
    TextView H_TopTourismSpotText7;

    TextView Overlay;
    ProgressBar Pbar;
    BottomNavigationView bottomnav;

    void Enable()
    {
        Overlay.setVisibility(View.GONE);
        Pbar.setVisibility(View.GONE);
        for(int i = 0 ; i < bottomnav.getMenu().size(); i++)
        {
            bottomnav.getMenu().getItem(i).setEnabled(true);
        }
        bottomnav.setElevation(1);
    }
    void Disable()
    {
        Overlay.setVisibility(View.VISIBLE);
        Pbar.setVisibility(View.VISIBLE);
        for(int i = 0 ; i < bottomnav.getMenu().size(); i++)
        {
            bottomnav.getMenu().getItem(i).setEnabled(false);
        }
        bottomnav.setElevation(-2);
    }

    ArrayList<String> locList = new ArrayList<>();

    void loadImage(ImageView img,TextView text, StorageReference ref, String locName)
    {

        Glide.with(getActivity().getApplicationContext()).load(ref).into(img);
        text.setText(locName);



    }


    void getTopLocations(View v)
    {

        locRef.child("imageloc").child("toploc").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful())
                {

                    storage = FirebaseStorage.getInstance("gs://byahed2.appspot.com");
                    int maxindex = 0;
                    for(DataSnapshot data : task.getResult().getChildren())
                    {
                        maxindex++;
                    }
                    int index = 1;
                    for(DataSnapshot data : task.getResult().getChildren())
                    {
                        int imgid = getResources().getIdentifier("H_TopTourismSpot"+index, "id", getActivity().getPackageName());
                        int txtid = getResources().getIdentifier("H_TopTourismSpotText"+index, "id", getActivity().getPackageName());
                        ImageView imgv = v.findViewById(imgid);
                        TextView txtv = v.findViewById(txtid);

                        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                        String locname = "";
                        for(DataSnapshot d: data.getChildren())
                        {
                            if(String.valueOf(d.getKey()).equals("locName"))
                            {
                                locname = String.valueOf(d.getValue());
                            }
                            else if(d.getKey().equals("locPath"))
                            {
                                storageRef = storage.getReference().child(String.valueOf(d.getValue()));
                            }



                        }
                        loadImage(imgv,txtv,storageRef,locname);
                        if(index == maxindex)
                        {
                            Enable();
                        }
                        else

                        {
                            index++;
                        }

                    }




                }
                else

                {
                    Toast.makeText(getActivity().getApplicationContext(), task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });







    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_home, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        H_TopTourismSpot1 = v.findViewById(R.id.H_TopTourismSpot1);
        H_TopTourismSpot2 = v.findViewById(R.id.H_TopTourismSpot2);
        H_TopTourismSpot3 = v.findViewById(R.id.H_TopTourismSpot3);
        H_TopTourismSpot4 = v.findViewById(R.id.H_TopTourismSpot4);
        H_TopTourismSpot5 = v.findViewById(R.id.H_TopTourismSpot5);
        H_TopTourismSpot6 = v.findViewById(R.id.H_TopTourismSpot6);
        H_TopTourismSpot7 = v.findViewById(R.id.H_TopTourismSpot7);

        H_TopTourismSpotText1 = v.findViewById(R.id.H_TopTourismSpotText1);
        H_TopTourismSpotText2 = v.findViewById(R.id.H_TopTourismSpotText2);
        H_TopTourismSpotText3 = v.findViewById(R.id.H_TopTourismSpotText3);
        H_TopTourismSpotText4 = v.findViewById(R.id.H_TopTourismSpotText4);
        H_TopTourismSpotText5 = v.findViewById(R.id.H_TopTourismSpotText5);
        H_TopTourismSpotText6 = v.findViewById(R.id.H_TopTourismSpotText6);
        H_TopTourismSpotText7 = v.findViewById(R.id.H_TopTourismSpotText7);

        Overlay = getActivity().findViewById(R.id.M_Overlay);
        Pbar = getActivity().findViewById(R.id.M_ProgressBar);
        bottomnav = getActivity().findViewById(R.id.MainBottomNav);

        Disable();

        getTopLocations(v);













        return v;
    }
}