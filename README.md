# OneStop
An Android application that gives you directions to the closest location for food.

Using the Google Places API for web services as well as the 'LocationManager' class for Android, this application finds a list of the nearest food businesses given the user's current location. 

Then it checks whether or not the business has hours listed. If so, it then checks whether or not the business is open at the time of query. If it is then that business is returned. 

Using an intent, the business's latitude and longitude is then opened in Google Maps and the user is given directions from their current location to that business. 

Happy eating! 

# ToDo 
* Return a list of the closest businesses and allow the user to pick which one they want directions to, instead of automatically returning the closest business
