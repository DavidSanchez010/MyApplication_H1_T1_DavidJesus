// UserInputActivity.java
package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class UserInputActivity extends AppCompatActivity {

    private EditText usernameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_input);

        usernameEditText = findViewById(R.id.usernameEditText);
    }

    public void startGame(View view) {
        String username = usernameEditText.getText().toString().trim();
        if (username.isEmpty()) {
            Toast.makeText(this, "Please enter your username", Toast.LENGTH_SHORT).show();
        } else {
            // Pasamos el nombre de usuario a la siguiente actividad (GameActivity)
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra("USERNAME", username);
            startActivity(intent);
        }
    }
}
