package com.example.seg2505.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.seg2505.R;
import com.example.seg2505.StoreKeeper.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);  // Ensure this points to the correct layout

        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Get references to the EditText views from the layout
        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.password);

        // Get reference to the login button and set its click listener
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                // Authenticate the user
                if (authenticateUser(email, password)) {
                    // If authentication is successful, navigate to AdminHomeActivity
                    Intent intent = new Intent(LoginActivity.this, AdminHomeActivity.class);  // Adjust to correct next activity
                    startActivity(intent);
                    finish();
                } else {
                    // If authentication fails, show an error message
                    Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean authenticateUser(String email, String password) {
        // Check credentials in the database (simplified for this example)
        return dbHelper.authenticateUser(email, password);  // Implement this method in DatabaseHelper
    }
}
