package me.harsansidhu.onestop;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // When the button is pressed, the Async nested inner class executes
                // and creates a toast of the closest business
                new OneStopAsync().execute();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Private nested inner async class
    // Get user's current lat and long via Location Manger
    // Pass lat and long to GooglePlaceSearch class which returns the closest open business
    // Then prints the name of the closest business and gives directions to it in Google Maps
    private class OneStopAsync extends AsyncTask<Void, Void, Void> {
        Container business; // Custom data type with businessName, latitude, longitude

        @Override
        protected Void doInBackground(Void... params) {
            // Get latitude and longitude via Location Manager
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            // Get the container object from the GooglePlaceSearch class
            business = GooglePlaceSearch.returnClosestOpen(latitude, longitude);

            return null;
        }

        protected void onPostExecute(Void result) {
            // Use the resulting name to create a toast of the closest business

            // If the response is null display no result
            if (business == null) {
                Toast.makeText(getApplicationContext(), "No result", Toast.LENGTH_LONG).show();
            }

            // If the business is valid display a toast with the name of the closest business
            // Then open Google maps with directions to that location from the user's current location
            else {
                Toast.makeText(getApplicationContext(), "Closest Open Business: " + business.businessName, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr="
                                + String.valueOf(business.latitude)
                                + ","
                                + String.valueOf(business.longitude)));
                // Default to opening in Google Maps
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
            }
        }
    }
}
