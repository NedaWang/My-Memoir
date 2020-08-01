# My-Memoir
The practical assignments (server-side and client-side) aim towards building a mobile and distributed, personalised diary application for keeping track of movies you watched and their details. The mobile app will be a movie memoir that allows people to create, delete, add or view a collection of memories that you had about the movies.
The mobile app will interact with the RESTful web service that you create in Assignment 1, and retrieve some useful and related information from public web APIs.
The assignment MUST be implemented in Android Studio.
You will invoke and consume at least three public APIs. These APIs include: YouTube, Twitter, and a movie
related API (e.g. TheMovieDB). We have provided a tutorial for using the Google API as a starting point.
You need to make yourself familiar with the three APIs mentioned above by reading their online documentation. You will then SIGN IN to their FREE access only, and get a KEY so you can make http calls to their APIs (find out about the parameters you need to pass and their JSON response structure that you will receive and how to parse it to get the right information). This is part of this task.
 You will use YouTube API to get the trailer of the movie.
 You will use Twitter API to get up to FIVE comments posted about the movie (use the movie name + ‘movie’ as a keyword).
   
 You will use a movie API to get the details about the movie. This information must be retrieved: a synopsis/plot summary/storyline about the movie, genre, actors (up to 5), director(s), and review score (needs to be or converted to out of 10).
(The data from these APIs will be used in the Movie Search screen of the app)
