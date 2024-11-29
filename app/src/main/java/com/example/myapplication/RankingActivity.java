package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class RankingActivity extends AppCompatActivity {

    private TextView rankingTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        rankingTextView = findViewById(R.id.rankingTextView);

        // Cargar y mostrar el ranking
        loadAndDisplayRanking();
    }

    private void loadAndDisplayRanking() {
        // Obtener los datos guardados en SharedPreferences
        SharedPreferences preferences = getSharedPreferences("game_scores", Context.MODE_PRIVATE);
        Map<String, ?> allEntries = preferences.getAll();

        // Crear una lista de usuarios con sus puntos
        List<Player> playerList = new ArrayList<>();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String username = entry.getKey();
            int points = (int) entry.getValue();
            playerList.add(new Player(username, points));
        }

        // Ordenar la lista por puntos en orden descendente
        Collections.sort(playerList, new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {
                return Integer.compare(o2.getPoints(), o1.getPoints());
            }
        });

        // Construir el texto del ranking
        StringBuilder rankingBuilder = new StringBuilder();
        int position = 1;
        for (Player player : playerList) {
            rankingBuilder.append(position)
                    .append(". ")
                    .append(player.getUsername())
                    .append(": ")
                    .append(player.getPoints())
                    .append(" points\n");
            position++;
        }

        // Mostrar el ranking en el TextView
        rankingTextView.setText(rankingBuilder.toString());
    }

    // Clase para almacenar usuarios y sus puntos
    private static class Player {
        private final String username;
        private final int points;

        public Player(String username, int points) {
            this.username = username;
            this.points = points;
        }

        public String getUsername() {
            return username;
        }

        public int getPoints() {
            return points;
        }
    }
}
