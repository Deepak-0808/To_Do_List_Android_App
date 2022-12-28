package com.example.mytask;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mytask.databinding.FragmentCBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class CFragment extends Fragment {

    FirebaseAuth auth;
    String  uid;
    FirebaseDatabase database;
    FragmentCBinding binding;
    private FirebaseUser user;
    private String userID;
    private DatabaseReference reference;

    public CFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();

    }

    TextView txtPrfile,calender, shareBtn,contactUs;
//    ActivityResultBinding binding;
    Button signout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_c, container, false);

        txtPrfile=view.findViewById(R.id.txtProfile);
        calender=view.findViewById(R.id.calender);
        shareBtn = view.findViewById(R.id.inviteFriends);
        signout=view.findViewById(R.id.signout);
        contactUs=view.findViewById(R.id.contactUs);




        txtPrfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Profile.class);
                startActivity(intent);
            }
        });


        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),calendar.class);
                startActivity(intent);
            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String shareBody = "Hey this is a Task Monitoring App.\n\nDownload this app from link below...\nhttps://github.com/i-manish-3";
                String shareSub = "";
                myIntent.putExtra(Intent.EXTRA_SUBJECT,shareBody);
                myIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
                startActivity(Intent.createChooser(myIntent, "Share using"));

            }
        });

        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),contactUs.class);
                startActivity(intent);
            }
        });

        binding=FragmentCBinding.inflate(inflater,container,false);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                signOutUser();
            }
        });


        return view;
    }

    private void signOutUser() {
        Toast.makeText(getActivity(), "Sign out successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(),signin.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);


    }
}