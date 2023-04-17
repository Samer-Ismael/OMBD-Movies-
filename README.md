# OMBD-Movies

Users can easily enter their API key and it will be automatically saved in Documents/APIKeys/OMDB.txt or receive guidance on how to obtain one.

The app has SQLite database to search for movie titles. If the movie is not found in the database, the application will seamlessly transition to the API to retrieve the relevant information for the user. The app stores all searched movie information in the database for future searches.

To maintain database efficiency, the app will automatically delete all movies that have been in the database for more than a month. This ensures that users are always provided with the most up-to-date information.
