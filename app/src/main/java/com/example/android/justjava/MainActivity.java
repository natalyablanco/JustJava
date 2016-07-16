package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {
    int chocolatePrice = 2;
    int whippedCreamPrice = 1;
    int coffeePrice = 5;
    String addresses = "duncant@gmail.com";
    String subject = "Coffee order summary. ";
    String textEmail ="";
    int quantity = 1;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * This method is called when summit button is pressed
     */
    public void submitOrder(View view) {

        CheckBox hasWhippedCreamCB = (CheckBox) findViewById(R.id.topping1_checkbox);
        Boolean hasWhippedCream = hasWhippedCreamCB.isChecked();

        CheckBox hasChocolateCB = (CheckBox) findViewById(R.id.topping2_checkbox);
        Boolean hasChocolate = hasChocolateCB.isChecked();

        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        int price = calculatePrice(hasWhippedCream, hasChocolate);

        textEmail = createOrderSummary(name, price, hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT,textEmail);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


    }

    /**
     * Calculates the price of the order.
     *
     * @return total price
     */
    private int calculatePrice(boolean whippedCream, boolean chocolate) {
        int basePrice = coffeePrice;
        if (whippedCream) {
            basePrice = basePrice + whippedCreamPrice;
        }
        if (chocolate) {
            basePrice = basePrice + chocolatePrice;
        }
        return basePrice * quantity;
    }

    /**
     * This method is called when the increment button is clicked.
     */
    public void increment(View view) {
        if(quantity==100){
            Toast.makeText(this,getString(R.string.toast_increment), Toast.LENGTH_SHORT).show();
            return;
        }
        quantity++;
        display(quantity);
    }

    /**
     * This method is called when the decrement button is clicked.
     */
    public void decrement(View view) {

        if(quantity==1){
            Toast.makeText(this,getString(R.string.toast_decrement), Toast.LENGTH_SHORT).show();
            return;
        }

        quantity--;
        display(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int quantity) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + quantity);
    }

    private String createOrderSummary(String name, int price, boolean hasWhippedCream, boolean hasChocolate) {
        String priceMessage = getString(R.string.name, name);
        priceMessage += "\n" + getString(R.string.add_whipped_cream,hasWhippedCream);
        priceMessage += "\n" + getString(R.string.add_chocolate, hasChocolate);
        priceMessage += "\n" + getString(R.string.quantity, quantity);
        priceMessage += "\n" + getString(R.string.total, price);
        priceMessage += "\n" + getString(R.string.thank_you);

        return priceMessage;
    }

}