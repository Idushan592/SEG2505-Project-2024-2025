package com.example.seg2505.StoreKeeper;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.seg2505.R;

import java.util.ArrayList;

public class StoreKeeperActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> stockItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_keeper);

        dbHelper = new DatabaseHelper(this);
        listView = findViewById(R.id.listViewStock);
        Button btnAddItem = findViewById(R.id.btnAddItem);

        // Ouvrir le dialogue pour ajouter un élément
        btnAddItem.setOnClickListener(view -> openAddItemDialog());

        // Charger et afficher les éléments en stock
        loadStockItems();
    }

    // Charger les éléments en stock depuis la base de données et mettre à jour la liste
    private void loadStockItems() {
        stockItems = new ArrayList<>();
        Cursor cursor = dbHelper.getAllStockItems();

        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TITLE));
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_QUANTITY));
                stockItems.add(title + " - Quantity: " + quantity);
            } while (cursor.moveToNext());
        }
        cursor.close();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stockItems);
        listView.setAdapter(adapter);
    }

    // Boîte de dialogue pour ajouter un élément en stock
    private void openAddItemDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_item, null);
        builder.setView(dialogView);

        EditText etType = dialogView.findViewById(R.id.etType);
        EditText etSubtype = dialogView.findViewById(R.id.etSubtype);
        EditText etTitle = dialogView.findViewById(R.id.etTitle);
        EditText etQuantity = dialogView.findViewById(R.id.etQuantity);
        EditText etComment = dialogView.findViewById(R.id.etComment);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String type = etType.getText().toString();
            String subtype = etSubtype.getText().toString();
            String title = etTitle.getText().toString();
            int quantity;
            try {
                quantity = Integer.parseInt(etQuantity.getText().toString());
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Please enter a valid quantity", Toast.LENGTH_SHORT).show();
                return;
            }
            String comment = etComment.getText().toString();

            // Ajouter l'élément dans la base de données
            boolean success = dbHelper.addStockItem(type, subtype, title, quantity, comment);
            if (success) {
                Toast.makeText(this, "Item added successfully", Toast.LENGTH_SHORT).show();
                refreshStockList();
            } else {
                Toast.makeText(this, "Error adding item", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    // Rafraîchir la liste après une mise à jour de stock
    private void refreshStockList() {
        stockItems.clear();
        loadStockItems();
        adapter.notifyDataSetChanged();
    }
}
