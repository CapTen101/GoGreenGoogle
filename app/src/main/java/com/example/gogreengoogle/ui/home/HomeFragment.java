package com.example.gogreengoogle.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.gogreengoogle.R;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Random;

public class HomeFragment extends Fragment {

    private String search;
    private TextInputLayout searchQuery;
    private Button searchButton;
    ArrayList<String> TextList = new ArrayList<>(1);
    ArrayList<String> UrlList = new ArrayList<>(1);
    private TextView quote, author;
    int searchCounter = 0;
    private static final String MY_REQUEST_URL = "https://api.paperquotes.com/quotes/?tags=environment";
    private Random rand = new Random();
    private int randomIndex = rand.nextInt(5);
    private ProgressBar quoteProgress;
    private ProgressBar authorProgress;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        searchQuery = root.findViewById(R.id.google_query);
        searchButton = root.findViewById(R.id.search_button);
        quote = root.findViewById(R.id.quote);
        author = root.findViewById(R.id.author);
        quoteProgress = root.findViewById(R.id.quote_progress_bar);
        authorProgress = root.findViewById(R.id.author_progress_bar);


        HttpQuoteRequest requestAPIQuote = new HttpQuoteRequest();
        requestAPIQuote.execute();
        HttpAuthorRequest requestAPIAuthor = new HttpAuthorRequest();
        requestAPIAuthor.execute();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search = searchQuery.getEditText().getText().toString();

                SearchRequest request = new SearchRequest();
                request.execute();

                searchCounter++;
                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("counter", searchCounter);
                editor.commit();

            }
        });

        return root;
    }

    public class HttpQuoteRequest extends AsyncTask<URL, String, String> {

        @Override
        protected String doInBackground(URL... urls) {

            URL url;
            try {
                url = new URL(MY_REQUEST_URL);
            } catch (MalformedURLException exception) {
                Log.e("errorTag", "Error with creating URL", exception);
                return null;
            }

            String jsonResponse = "";
            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                Log.e("errorTag", "Error in request");
            }
            return jsonResponse;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            quoteProgress.setVisibility(View.GONE);
            quote.setVisibility(View.VISIBLE);
            Log.e("quote",s);
            quote.setText("\"" + s + "\"");
        }

        private String makeHttpRequest(URL url) throws IOException {
            String jsonResponse = "";
            HttpURLConnection urlConnection;
            InputStream inputStream;

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Authorization", "Token 2e9072a007f0fcd23d80fc5537a5c174bee9ff47");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.connect();

            inputStream = urlConnection.getInputStream();
            jsonResponse = readInputStream(inputStream);

            return jsonResponse;
        }

        private String readInputStream(InputStream inputStream) throws IOException {
            String copyOutput = null;
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }

            String finalOutput = output.toString();
            JSONObject parentObject;
            JSONArray parentArray;
            JSONObject quoteObject;
            try {
                parentObject = new JSONObject(finalOutput);
                parentArray = parentObject.getJSONArray("results");
                if (randomIndex != 4) {
                    quoteObject = parentArray.getJSONObject(randomIndex);
                    String author = quoteObject.getString("quote");
                    copyOutput = author;
                } else {
                    randomIndex = 2;
                    quoteObject = parentArray.getJSONObject(randomIndex);
                    String author = quoteObject.getString("quote");
                    copyOutput = author;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return copyOutput;
        }
    }

    public class HttpAuthorRequest extends AsyncTask<URL, String, String> {

        @Override
        protected String doInBackground(URL... urls) {

            URL url;
            try {
                url = new URL(MY_REQUEST_URL);
            } catch (MalformedURLException exception) {
                Log.e("errorTag", "Error with creating URL", exception);
                return null;
            }

            String jsonResponse = "";
            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                Log.e("errorTag", "Error in request");
            }
            return jsonResponse;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            authorProgress.setVisibility(View.GONE);
            author.setVisibility(View.VISIBLE);
            Log.e("author",s);
            author.setText(s);
        }

        private String makeHttpRequest(URL url) throws IOException {
            String jsonResponse = "";
            HttpURLConnection urlConnection;
            InputStream inputStream;

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Authorization", "Token 2e9072a007f0fcd23d80fc5537a5c174bee9ff47");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.connect();

            inputStream = urlConnection.getInputStream();
            jsonResponse = readInputStream(inputStream);

            return jsonResponse;
        }

        private String readInputStream(InputStream inputStream) throws IOException {
            String copyOutput = null;
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }

            String finalOutput = output.toString();
            JSONObject parentObject;
            JSONArray parentArray;
            JSONObject authorObject;
            try {
                parentObject = new JSONObject(finalOutput);
                parentArray = parentObject.getJSONArray("results");
                if (randomIndex != 4) {
                    authorObject = parentArray.getJSONObject(randomIndex);
                    String author = authorObject.getString("author");
                    copyOutput = author;
                } else {
                    randomIndex = 2;
                    authorObject = parentArray.getJSONObject(randomIndex);
                    String author = authorObject.getString("author");
                    copyOutput = author;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return copyOutput;
        }
    }


    public class SearchRequest extends AsyncTask<URL, String, String> {

        @Override
        protected String doInBackground(URL... urls) {

            URL url;
            try {
                String GOOGLE_SEARCH_URL = "https://api.duckduckgo.com/?q=" + search + "&format=json&pretty=1";
                url = new URL(GOOGLE_SEARCH_URL);
            } catch (MalformedURLException exception) {
                Log.e("errorTag", "Error with creating URL", exception);
                return null;
            }

            String jsonResponse = "";
            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                Log.e("errorTag", "Error in request");
            }
            return jsonResponse;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Intent openSearchResult = new Intent(getContext(), SearchPageActivity.class);
//            openSearchResult.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            openSearchResult.putExtra("TextArrayList", TextList);
            openSearchResult.putExtra("UrlArrayList", UrlList);
            openSearchResult.putExtra("counter",searchCounter);
            startActivity(openSearchResult);
            TextList.clear();
            UrlList.clear();

            SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("counter", searchCounter);
            editor.apply();
        }

        private String makeHttpRequest(URL url) throws IOException {
            String jsonResponse = "";
            HttpURLConnection urlConnection;
            InputStream inputStream;

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            inputStream = urlConnection.getInputStream();
            jsonResponse = readInputStream(inputStream);

            return jsonResponse;
        }

        private String readInputStream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }

            String finalOutput = output.toString();
            JSONObject parentObject;
            JSONArray RelatedTopicsArray;
            JSONArray TopicsArray;
            try {
                parentObject = new JSONObject(finalOutput);
                RelatedTopicsArray = parentObject.getJSONArray("RelatedTopics");
                for (int i = 0; i < RelatedTopicsArray.length(); i++) {
                    for (int k = 0; k < RelatedTopicsArray.length(); k++) {
                        TextList.add(RelatedTopicsArray.getJSONObject(k).getString("Text"));
                        UrlList.add(RelatedTopicsArray.getJSONObject(k).getString("FirstURL"));
//                        Integer size = UrlList.get(k).length();
//                        String ch1 =  UrlList.get(k).substring(0,8);
////                        String ch2 =  UrlList.get(k).substring(8,18);
//                        if(size>18) {
//                            String ch3 =  UrlList.get(k).substring(18,size);
//                            String ch2 = "google";
//                            String finalURL = ch1+ch2+ch3;
//                            UrlList.set(k,finalURL);
//                        }


//                        Log.e("yoyoyostring replace",finalURL);
//                        Log.e("string replace",UrlList.get(k));
//                        Log.e("string replace",ch2);
                    }

                    if (TextList.toString() == "[]" || UrlList.toString() == "[]") {
                        TopicsArray = RelatedTopicsArray.getJSONObject(i).getJSONArray("Topics");
                        for (int j = 0; j < TopicsArray.length(); j++) {
                            TextList.add(TopicsArray.getJSONObject(j).getString("Text"));
                            UrlList.add(TopicsArray.getJSONObject(j).getString("FirstURL"));
//                            Integer size = UrlList.get(j).length();
//                            String ch1 =  UrlList.get(j).substring(0,8);
////                            String ch2 =  UrlList.get(j).substring(8,18);
//                            if(size>18) {
//                                String ch3 =  UrlList.get(j).substring(18,size);
//                                String ch2 = "google";
//                                String finalURL = ch1+ch2+ch3;
//                                UrlList.set(j,finalURL);
//                            }

//                            Log.e("yoyoyostring replace",finalURL);
//                            Log.e("string replace",UrlList.get(j));
//                            Log.e("string replace",ch2);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return finalOutput;
        }
    }
}
