package com.example.seg2505.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.cursoradapter.widget.CursorAdapter;

import com.example.seg2505.R;
import com.example.seg2505.StoreKeeper.DatabaseHelper;


public class OrderCursorAdapter extends CursorAdapter {
    private final OrderActivity orderActivity;

    public OrderCursorAdapter(OrderActivity context, Cursor c) {
        super(context, c, 0);
        this.orderActivity = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView orderIdTextView = view.findViewById(R.id.orderIdTextView);
        TextView statusTextView = view.findViewById(R.id.statusTextView);
        Button deleteButton = view.findViewById(R.id.deleteButton);

        int orderId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ORDER_ID));
        String status = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ORDER_STATUS));

        orderIdTextView.setText("Order ID: " + orderId);
        statusTextView.setText("Status: " + status);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderActivity.deleteOrder(orderId);
            }
        });
    }
}
