package me.harsansidhu.onestop;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import java.util.ArrayList;


public class ResultsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // Retrieve the list of businesses from the intent of the MainActivity
        Intent intent = getIntent();
        final ArrayList<Container> businessList = (ArrayList<Container>) intent.getSerializableExtra("businessList");


        // Dynamically create buttons based on the businesses within the businessList
        for (int i = 0; i < businessList.size(); i++) {

            // Create the button and set the text to the name of the business
            Button myButton = new Button(this);
            myButton.setText(businessList.get(i).businessName);

            // Select the layout for which this button will be added to
            LinearLayout ll = (LinearLayout) findViewById(R.id.btn_layout);
            LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

            // Define the behavior for each button aka open the corresponding business in Google Maps
            final int index = i;
            OnClickListener onClickListener = new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse("http://maps.google.com/maps?daddr="
                                    + String.valueOf(businessList.get(index).latitude)
                                    + ","
                                    + String.valueOf(businessList.get(index).longitude)));
                    // Default to opening in Google Maps
                    intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                    startActivity(intent);
                }
            };

            // Add the onClick behavior and add the button to the layout
            myButton.setOnClickListener(onClickListener);
            ll.addView(myButton, lp);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_results, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
