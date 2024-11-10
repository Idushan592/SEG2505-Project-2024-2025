package com.example.seg2505.StoreKeeper;

import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class DatabaseHelperTest {
    private DatabaseHelper dbHelper;

    @Before
    public void setup() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        dbHelper = new DatabaseHelper(context);
    }

    @Test
    public void testAddOrder() {
        boolean isCreated = dbHelper.addOrder(1, "Pending");
        assertTrue(isCreated);
    }

    @Test
    public void testDeleteOrder() {
        boolean isDeleted = dbHelper.deleteOrder(1);  // Replace with a valid order ID if necessary
        assertTrue(isDeleted);
    }

    @After
    public void tearDown() {
        dbHelper.close();
    }
}
