package com.example.memoir.network;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class SearchMovieAPI {

    private static final String API_KEY = "b43e380b2a3295ab244b24f4887d9d0d";

    public static String searchMovie(String s){

        String param = s.replace(" ", "+");
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";

        String aim = "https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&query=" + param;
        System.out.println("srattttttttt"+aim);
        try {
            url = new URL(aim);
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                textResult += scanner.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        System.out.println("sratttttttttweeeeee"+textResult);
        return textResult;
    }
}
