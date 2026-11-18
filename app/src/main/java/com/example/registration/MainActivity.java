package com.example.registration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private TextView textViewMessage;
    private Button btnSubmit;
    private Button btnSave;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextPasswordConfirm;

    final private String FILENAME = "users.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        btnSubmit.setOnClickListener(v -> onClickSubmit(v));
        btnSave.setOnClickListener(v -> onClickSave(v));

    }


    /*
     * nazwa: checkInputs
     * parametry: brak
     * opis: sprawdza czy podane dane są prawidłowe
     * zwracana wartość: true lub false - wartość logiczna
     * autor: Dmitrii Ivakov
     */
    private boolean checkInputs() {
        String email = editTextEmail.getText().toString();
        String password1 = editTextPassword.getText().toString();
        String password2 = editTextPasswordConfirm.getText().toString();

        if(!email.isEmpty() && !password1.isEmpty() && !password2.isEmpty()) {
            if (!email.contains("@")) {
                textViewMessage.setText("Nieprawidłowy adres e-mai");
                return false;
            } else if (!password1.equals(password2)) {
                textViewMessage.setText("Hasła się różnią");
                return false;
            }
            return true;
        }

        textViewMessage.setText("Zostały puste pola. Proszę wypełnić");
        return false;
    }

    private void onClickSubmit(View view) {
//        if (checkInputs()) {
//            textViewMessage.setText("Witaj " + editTextEmail.getText().toString());
//            Toast.makeText(this, "Konto zostało zarejestrowane", Toast.LENGTH_SHORT).show();
//        }
        startActivity(new Intent(this, UsersActivity.class));
    }

    private void onClickSave(View view) {
        if (checkInputs()) {
            try {
                FileOutputStream file = openFileOutput(FILENAME, MODE_APPEND);
                OutputStreamWriter streamWriter = new OutputStreamWriter(file);
                BufferedWriter writer = new BufferedWriter(streamWriter);

                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                writer.write(email+"?"+password+"\n");

                writer.close();
                textViewMessage.setText("Witaj " + editTextEmail.getText().toString());
                Toast.makeText(this, "Konto zostało zapisane do pliku: " + FILENAME, Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Toast.makeText(this, "Błąd podczas zapisywania pliku " + FILENAME + ": " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void initViews() {
        textViewMessage = findViewById(R.id.textViewMessage);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSave = findViewById(R.id.btnSave);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextPasswordConfirm = findViewById(R.id.editTextPasswordConfirm);
    }
}