package com.example.myapplication;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openGame(View view) {
        Intent intent = new Intent(this, UserInputActivity.class);
        startActivity(intent);
    }


    public void openRanking(View view) {
        Intent intent = new Intent(this, RankingActivity.class);
        startActivity(intent);
    }


    public void openContact(View view) {
        Intent intent = new Intent(this, ContactActivity.class);
        startActivity(intent);
    }
}
