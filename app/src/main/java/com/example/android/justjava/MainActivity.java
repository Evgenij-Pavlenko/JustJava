/**
 * IMPORTANT: Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 * <p>
 * package com.example.android.justjava;
 */
package com.example.android.justjava;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {


        //Figure out if the user wants whipped cream topping
        CheckBox checkBoxWhippedCream = findViewById(R.id.checkbox_whipped_cream);
        boolean isWhippedCream = checkBoxWhippedCream.isChecked();

        //Figure out if the user wants whipped cream topping
        CheckBox checkBoxChocolate = findViewById(R.id.checkbox_chocolate);
        boolean isChocolate = checkBoxChocolate.isChecked();

        //
        EditText editTextUser = findViewById(R.id.name_view);
        String usersName = editTextUser.getText().toString();

        //Calculate the price
        int price = calculatePrice(isWhippedCream, isChocolate);
        String priceMessage = createOrderSummary(price, isWhippedCream, isChocolate, usersName);


        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, false);
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject, usersName));
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Calculates the price of the order.
     *
     * @param isWhippedCream
     * @param isChocolate
     * @return total price
     */
    private int calculatePrice(boolean isWhippedCream, boolean isChocolate) {

        int basePriceOneCup = 5;

        //Add 1$ if the user wants Whipped cream
        if (isWhippedCream)
            basePriceOneCup += 1;

        //Add 2$ if the user wants Chocolate
        if (isChocolate)
            basePriceOneCup += 2;

        //Calculate the total order price by multiplying by quantity
        return quantity * basePriceOneCup;
    }

    /**
     * Create summary of the order
     *
     * @param price          of the order
     * @param isWhippedCream is whether or not the user wants whipper cream topping
     * @param isChocolate    is whether or not the user wants chocolate topping
     * @param usersName
     * @return text summary
     */
    @SuppressLint("StringFormatMatches")
    private String createOrderSummary(int price, boolean isWhippedCream, boolean isChocolate, String usersName) {
        String priceMessage = getString(R.string.order_summary_name, usersName);
        priceMessage += "\n" + getString(R.string.order_summary_whipped_cream, isWhippedCream);
        priceMessage += "\n" + getString(R.string.order_summary_chocolate, isChocolate);
        priceMessage += "\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage += "\n" + getString(R.string.order_summary_price, NumberFormat.getCurrencyInstance().format(price));
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }

        quantity = quantity + 1;
        displayQuantity(quantity);
        //comment
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }


}
