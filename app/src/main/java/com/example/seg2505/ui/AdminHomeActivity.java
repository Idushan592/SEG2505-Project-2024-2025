package com.example.seg2505.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.seg2505.R;
import com.example.seg2505.StoreKeeper.DatabaseHelper;

public class AdminHomeActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        dbHelper = new DatabaseHelper(this);
    }

    private void addUser(String name, String email, String password, String role) {
        dbHelper.addUser(name, email, password, role);  // Define this in DatabaseHelper
    }

    private void deleteUser(String email) {
        dbHelper.deleteUser(email);  // Define this in DatabaseHelper
    }
}