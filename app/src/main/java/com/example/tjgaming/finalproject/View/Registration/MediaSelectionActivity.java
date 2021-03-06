package com.example.tjgaming.finalproject.View.Registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.tjgaming.finalproject.R;
import com.example.tjgaming.finalproject.View.Home.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MediaSelectionActivity extends AppCompatActivity implements View.OnClickListener{

    private CheckBox mMovieCheckBox;
    private CheckBox mTVCheckBox;
    private CheckBox mMusicCheckBox;
    private CheckBox mGamesCheckBox;
    private AppCompatButton mFinishButton;

    private boolean mMovies;
    private boolean mTv;
    private boolean mMusic;
    private boolean mGames;

    //From intent
    private String mUid;
    private String mEmail;
    private String mGender;
    private String mBirthDate;
    private boolean mNotifications;
    private String mUserName;

    private DocumentReference documentReference;
    FirebaseDatabase firebaseDatabase;

    private final String TAG = "MediaSelectionActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_selection);

        initializeViews();
        getDataFromIntent();
        setFinishListener();
    }

    private void initializeViews() {

        mMovies = mTv = mMusic = mGames = false;

        ( mMovieCheckBox = findViewById(R.id.media_select_movie_checkbox) )
                .setOnClickListener(this);
        ( mTVCheckBox = findViewById(R.id.media_select_tv_checkbox) )
                .setOnClickListener(this);
        ( mMusicCheckBox = findViewById(R.id.media_select_music_checkbox) )
                .setOnClickListener(this);
        ( mGamesCheckBox = findViewById(R.id.media_select_games_checkbox) )
                .setOnClickListener(this);

        mFinishButton = findViewById(R.id.btn_media_select_finish);
    }

    private void getDataFromIntent() {
        mUid = getIntent().getStringExtra("uid");
        mEmail = getIntent().getStringExtra("email");
        mGender = getIntent().getStringExtra("gender");
        mBirthDate = getIntent().getStringExtra("dateOfBirth");
        mUserName = getIntent().getStringExtra("username");
        mNotifications = getIntent().getBooleanExtra("notifications",false);
    }

    private void setFinishListener() {
        mFinishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFinishButton.setClickable(false);

                if (mMovies == false && mTv == false && mMusic == false && mGames == false){
                    Toast.makeText(MediaSelectionActivity.this, "Please select at least one type of media.", Toast.LENGTH_SHORT).show();
                    mFinishButton.setClickable(true);
                } else {
                    ArrayList<Boolean> mediaList = new ArrayList<>();
                    mediaList.add(mMovies);
                    mediaList.add(mTv);
                    mediaList.add(mMusic);
                    mediaList.add(mGames);
                    documentReference = FirebaseFirestore.getInstance().collection("users").document(mUid);

                    Map<String, Object> user = new HashMap<>();
                    user.put("username",mUserName);
                    user.put("email",mEmail);
                    user.put("gender",mGender);
                    user.put("birthdate",mBirthDate);
                    user.put("mediaTypes",mediaList);
                    user.put("typeOfUser","normal");

                    documentReference.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "Document saved!");
                            } else {
                                Log.d(TAG,"Document not saved", task.getException());
                            }
                        }
                    });

                    startActivity(new Intent(getApplicationContext(),HomeActivity.class).putExtra("user_id",mUid));
                    finish();

                }

            }
        });
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.media_select_movie_checkbox:

                mMovies = !mMovies;
                break;

            case R.id.media_select_tv_checkbox:

                mTv = !mTv;
                break;

            case R.id.media_select_music_checkbox:

                mMusic = !mMusic;
                break;
            case R.id.media_select_games_checkbox:

                mGames = !mGames;
                break;
        }

    }
}
