package com.example.seg2505.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.seg2505.R;
import com.example.seg2505.StoreKeeper.DatabaseHelper;

public class OrderActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private int requesterId;  // Retrieve this ID from login or intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        dbHelper = new DatabaseHelper(this);
        ListView ordersListView = findViewById(R.id.ordersListView);

        // Set up the "Create Order" button
        Button createOrderButton = findViewById(R.id.createOrderButton);
        createOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isCreated = dbHelper.addOrder(requesterId, "Pending");
                if (isCreated) {
                    Toast.makeText(OrderActivity.this, "Order created successfully!", Toast.LENGTH_SHORT).show();
                    loadOrders();
                } else {
                    Toast.makeText(OrderActivity.this, "Failed to create order.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loadOrders();  // Load orders on activity start
    }

    private void loadOrders() {
        Cursor cursor = dbHelper.getOrdersByRequester(requesterId);
        if (cursor != null) {
            OrderCursorAdapter adapter = new OrderCursorAdapter(this, cursor);
            ListView ordersListView = findViewById(R.id.ordersListView);
            ordersListView.setAdapter(adapter);
        }
    }

    // Method to delete an order
    public void deleteOrder(int orderId) {
        boolean isDeleted = dbHelper.deleteOrder(orderId);
        if (isDeleted) {
            Toast.makeText(this, "Order deleted successfully.", Toast.LENGTH_SHORT).show();
            loadOrders();  // Refresh orders list
        } else {
            Toast.makeText(this, "Failed to delete order.", Toast.LENGTH_SHORT).show();
        }
    }
}