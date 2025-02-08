package com.aehanoidz123.librarymanagement.Services;

import com.aehanoidz123.librarymanagement.Entities.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONTokener;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GoogleBooksService {
    private static final String API_KEY = "AIzaSyDKISCdSGCglV6eeYaRHvncSQxL4Wr6LDU";
    private static final String API_URL = "https://www.googleapis.com/books/v1/volumes?q=";

    public ObservableList<Book> searchBooks(String keyWord) {
        ObservableList<Book> googleBooksData = FXCollections.observableArrayList();
        try {
            String urlString = API_URL + keyWord + "&key=" + API_KEY;
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Check if too many requests to google
            int responseCode = conn.getResponseCode();
            if (responseCode == 429) {
                System.out.println("Loading...");
                Thread.sleep(5000);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
            }

            InputStreamReader reader = new InputStreamReader(conn.getInputStream());
            JSONObject json = new JSONObject(new JSONTokener(reader));
            JSONArray items = json.getJSONArray("items");

            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                JSONObject volumeInfo = item.getJSONObject("volumeInfo");
                String title = volumeInfo.getString("title");
                String author = volumeInfo.has("authors") ? volumeInfo.getJSONArray("authors").getString(0) : "Unknown";
                String category = volumeInfo.has("categories") ? volumeInfo.getJSONArray("categories").getString(0) : "General";

                String isbn = null;

                if (volumeInfo.has("industryIdentifiers")) {
                    JSONArray identifiers = volumeInfo.getJSONArray("industryIdentifiers");
                    for (int j = 0; j < identifiers.length(); j++) {
                        JSONObject identifier = identifiers.getJSONObject(j);
                        if ("ISBN_13".equals(identifier.getString("type"))) {
                            isbn = identifier.getString("identifier");
                            break;
                        }
                    }
                }

                String imageUrl = volumeInfo.has("imageLinks") ? volumeInfo.getJSONObject("imageLinks").getString("thumbnail") : null;

                googleBooksData.add(new Book(title, author, category, isbn, imageUrl));
            }
            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return googleBooksData;
    }
}
