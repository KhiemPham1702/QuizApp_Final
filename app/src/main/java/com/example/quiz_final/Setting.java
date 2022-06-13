package com.example.quiz_final;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Setting#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Setting extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Setting() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Setting.
     */
    // TODO: Rename and change types and number of parameters
    public static Setting newInstance(String param1, String param2) {
        Setting fragment = new Setting();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    View feedback,sign_out,message, messageTxt,mo,voice,unvoice;
    TextView btfeedback,nickname,guide;
    DocumentReference documentReference;
    FirebaseFirestore fStore;
    String userID;
    FirebaseAuth mAuth;
    CircleImageView avatar;
    MediaPlayer mediaPlayer;
    SeekBar seekBar;
    AudioManager audioManager;
    SharedPreferences sharedPreferences;
    Handler handler=new Handler();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_setting, container, false);
        sign_out=v.findViewById(R.id.sign_out);
        feedback = v.findViewById(R.id.feedback);
        mo = v.findViewById(R.id.mo_star);
        btfeedback = v.findViewById(R.id.btfeedback);

        nickname=v.findViewById(R.id.nickname);
        avatar = v.findViewById(R.id.avatar);
        mAuth = FirebaseAuth.getInstance();
        guide=v.findViewById(R.id.guide);
        message = v.findViewById(R.id.message);
        messageTxt = v.findViewById(R.id.textMessage);
        voice=v.findViewById(R.id.voice);
        unvoice=v.findViewById(R.id.unvoice);
        fStore = FirebaseFirestore.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        fStore = FirebaseFirestore.getInstance();
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        seekBar = v.findViewById(R.id.seekbar);
        seekBar.setMax(maxVolume);
        seekBar.setProgress(currentVolume);
        if(!Start_layout.mediaPlayer.isPlaying())
        {
            Start_layout.mediaPlayer.start();
        }
        sharedPreferences= getActivity().getSharedPreferences("music", Context.MODE_PRIVATE);
        showUserProfile();
        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getActivity(), login_layout.class);
                startActivity(intent2);
            }
        });
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:" + "skhiem1722002@gmail.com"));
                startActivity(Intent.createChooser(intent, "Chooser Title"));
            }
        });
        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voice.setVisibility(View.INVISIBLE);
                unvoice.setVisibility(View.VISIBLE);
                Start_layout.mediaPlayer.pause();
            }
        });
        unvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unvoice.setVisibility(View.INVISIBLE);
                voice.setVisibility(View.VISIBLE);
                Start_layout.mediaPlayer.start();

            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress,0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        btfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:" + "skhiem1722002@gmail.com"));
                startActivity(Intent.createChooser(intent, "Chooser Title"));
            }
        });
        guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(message.getVisibility() == View.VISIBLE) {
                    message.setVisibility(View.INVISIBLE);
                    messageTxt.setVisibility(View.INVISIBLE);
                    mo.setVisibility(View.INVISIBLE);

                } else {
                    message.setVisibility(View.VISIBLE);
                    messageTxt.setVisibility(View.VISIBLE);
                    mo.setVisibility(View.VISIBLE);

                }
            }
        });
        return v;
    }
    private void showUserProfile() {
        ArrayList<String>image=new ArrayList<>();
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        documentReference = fStore.collection("users").document(userID);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    nickname.setText(documentSnapshot.getString("nickname"));
                }
            }
        });
//        database.collection("users")
//               .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                for(DocumentSnapshot snapshot : queryDocumentSnapshots) {
//                    User user = snapshot.toObject(User.class);
//                    image.add(user.avatar);
//                }
//            }
//        });
        FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
        Glide.with(Setting.this)
                .load(user1.getPhotoUrl())
                .into(avatar);

        }


}
