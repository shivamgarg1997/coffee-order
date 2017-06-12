package com.example.justjava;

import java.text.NumberFormat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import static android.R.attr.name;
import static android.R.attr.order;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity=0;
    @Override
    protected void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText namefield = (EditText) findViewById(R.id.name_id);
        String name = namefield.getText().toString();
        CheckBox whippedcream= (CheckBox) findViewById(R.id.checkbox_cream_id);
        CheckBox chocolate= (CheckBox) findViewById(R.id.checkbox_choco_id);
        boolean hascream=whippedcream.isChecked();
        boolean haschoco=chocolate.isChecked();
        int finalprice= price(whippedcream , chocolate);
        String ordersummary=createorder(finalprice , hascream, haschoco, name);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_TEXT, ordersummary );
        intent.putExtra(Intent.EXTRA_SUBJECT, "just java order");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        displayMessage(ordersummary);

    }
    private int price(CheckBox cream , CheckBox choco){
        int price=0;
        if(cream.isChecked()){
            price = quantity*6;
        }
        if(choco.isChecked()){
            price = quantity*7;
        }
        if(cream.isChecked() && cream.isChecked()){
            price = quantity*8;
        }
        else
            price = quantity*5;
        return price;

    }
    private String createorder(int price , boolean cream , boolean choco , String nameenter){
        String pricemessage = getString(R.string.name , nameenter);
        pricemessage = pricemessage + "\n Add Whipped Cream? = " + cream;
        pricemessage = pricemessage + "\n Add chocolate? = " + choco;
        pricemessage = pricemessage + "\nQuantity = " + quantity;
        pricemessage = pricemessage + "\n Total = " + price + "\n";
        pricemessage =pricemessage + getString(R.string.thank_you);
        return pricemessage;
    }
    public void increement(View view){
        quantity++;
        if(quantity>100)
            display(100);
        else
        display(quantity);
    }
    public void decreement(View view){
        quantity--;
        if(quantity<0)
            display(0);
        else
        display(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.number_id);
        quantityTextView.setText("" + number);
    }

   /* private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.priceno_id);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }
    */
    private void displayMessage(String message) {
        TextView orderTextView = (TextView) findViewById(R.id.order_id);
        orderTextView.setText(message);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */



}