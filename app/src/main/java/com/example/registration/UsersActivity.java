package com.example.registration;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class UsersActivity extends AppCompatActivity {
    private Button btnReturn;
    private ListView listView;

    ArrayList<String> usersArray;
    ArrayAdapter<String> adapter;

    final private String FILENAME = "users.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_users);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        btnReturn.setOnClickListener(v -> finish());
        readUsers();
    }

    private void readUsers() {
        try {
            FileInputStream file = openFileInput(FILENAME);
            InputStreamReader streamReader = new InputStreamReader(file);
            BufferedReader reader = new BufferedReader(streamReader);

            usersArray.clear();

            String user;
            while ((user = reader.readLine()) != null) {
                StringBuilder email = new StringBuilder();
                StringBuilder password = new StringBuilder();
                StringBuilder tmp = email;

                for (int i=0; i<user.length(); i++) {
                    char c = user.charAt(i);

                    if (c == '?') {
                        tmp = password;
                    } else {
                        tmp.append(c);
                    }
                }

                usersArray.add("E-mail: " + email.toString() + "\n" + "Hasło: " + password.toString());
            }

            reader.close();
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Konta zostały wyczytane z pliku: " + FILENAME, Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(this, "Błąd podczas wczytywania pliku " + FILENAME + ": " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void initViews() {
        btnReturn = findViewById(R.id.btnReturn);
        listView = findViewById(R.id.listView);

        usersArray = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, R.layout.list_view, usersArray);
        listView.setAdapter(adapter);
    }
}