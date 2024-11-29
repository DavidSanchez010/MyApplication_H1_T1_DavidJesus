package com.example.myapplication;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    private TextView pointsTextView;
    private TextView timerTextView; // Texto para mostrar el tiempo restante
    private Button clickButton;
    private Button saveButton;
    private Button powerUpButton;
    private Button goldenStreakButton; // Botón Golden Streak

    private int points = 0;
    private String username;
    private boolean isPowerUpActive = false;
    private boolean isGoldenStreakActive = false;
    private int streakMultiplier = 1; // Multiplicador dinámico para Golden Streak

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Obtener el nombre de usuario desde el Intent
        username = getIntent().getStringExtra("USERNAME");

        pointsTextView = findViewById(R.id.pointsTextView);
        timerTextView = findViewById(R.id.timerTextView); // Texto del temporizador
        clickButton = findViewById(R.id.clickButton);
        saveButton = findViewById(R.id.saveButton);
        powerUpButton = findViewById(R.id.powerUpButton);
        goldenStreakButton = findViewById(R.id.goldenStreakButton);

        // Inicializar puntos y tiempo
        pointsTextView.setText("Points: 0");
        timerTextView.setText("Time left: 30s");

        // Temporizador global de 30 segundos
        new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText("Time left: " + millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                timerTextView.setText("Time up!");
                pointsTextView.setText("Game Over! Final Score: " + points);

                // Deshabilitar botones
                clickButton.setEnabled(false);
                powerUpButton.setEnabled(false);
                goldenStreakButton.setEnabled(false);

                // Mostrar botón de guardar
                saveButton.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    // Incrementar puntos cuando el botón es presionado
    public void onClick(View view) {
        if (isGoldenStreakActive) {
            streakMultiplier++;
            points += streakMultiplier; // Puntos dinámicos según clics rápidos
        } else {
            points += isPowerUpActive ? 2 : 1; // Multiplica puntos si Power-Up está activo
        }

        pointsTextView.setText("Points: " + points);

        // Activar el botón de Power-Up después de 10 clics
        if (points >= 10 && !powerUpButton.isEnabled()) {
            powerUpButton.setEnabled(true);
            Toast.makeText(this, "Power-Up unlocked!", Toast.LENGTH_SHORT).show();
        }

        // Activar el botón de Golden Streak después de 20 clics
        if (points >= 20 && !goldenStreakButton.isEnabled()) {
            goldenStreakButton.setEnabled(true);
            Toast.makeText(this, "Golden Streak unlocked!", Toast.LENGTH_SHORT).show();
        }
    }

    // Activar Power-Up
    public void activatePowerUp(View view) {
        if (isPowerUpActive) return; // Prevenir activaciones múltiples

        isPowerUpActive = true; // Activar el estado Power-Up
        powerUpButton.setEnabled(false); // Deshabilitar el botón mientras está activo
        Toast.makeText(this, "Power-Up Activated! Double points for 10 seconds!", Toast.LENGTH_SHORT).show();

        // Desactivar Power-Up después de 10 segundos
        new Handler().postDelayed(() -> {
            isPowerUpActive = false; // Desactivar Power-Up
            Toast.makeText(GameActivity.this, "Power-Up expired!", Toast.LENGTH_SHORT).show();
        }, 10000); // 10 segundos
    }

    // Activar Golden Streak
    public void activateGoldenStreak(View view) {
        if (isGoldenStreakActive) return; // Evitar activaciones múltiples

        isGoldenStreakActive = true; // Activar efecto
        streakMultiplier = 1; // Reiniciar multiplicador
        goldenStreakButton.setEnabled(false); // Deshabilitar mientras está activo

        Toast.makeText(this, "Golden Streak Activated! Faster clicks, more points!", Toast.LENGTH_SHORT).show();

        // Temporizador de 10 segundos para el efecto
        new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Opcional: Mostrar cuenta atrás si quieres
            }

            @Override
            public void onFinish() {
                isGoldenStreakActive = false; // Desactivar efecto
                streakMultiplier = 1; // Reiniciar multiplicador
                Toast.makeText(GameActivity.this, "Golden Streak expired!", Toast.LENGTH_SHORT).show();

                // Enfriamiento de 15 segundos antes de volver a habilitar
                new Handler().postDelayed(() -> goldenStreakButton.setEnabled(true), 15000);
            }
        }.start();
    }

    // Guardar puntaje al presionar el botón de "Save Score"
    public void saveScore(View view) {
        if (username == null || username.isEmpty()) {
            Toast.makeText(this, "Username is required!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Guardamos el puntaje en SharedPreferences bajo el nombre de usuario
        getSharedPreferences("game_scores", MODE_PRIVATE)
                .edit()
                .putInt(username, points) // Guardamos el puntaje con el nombre de usuario como clave
                .apply();

        Toast.makeText(this, "Score saved!", Toast.LENGTH_SHORT).show();

        // Opcional: Podrías hacer algo adicional aquí, como navegar a la pantalla de ranking.
    }
}
