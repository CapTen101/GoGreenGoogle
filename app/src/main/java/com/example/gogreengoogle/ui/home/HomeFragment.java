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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.gogreengoogle.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private String search;
    private TextInputLayout searchQuery;
    private Button searchButton;
//    private TextView yoyo;
    ArrayList<String> TextList = new ArrayList<>(1);
    ArrayList<String> UrlList = new ArrayList<>(1);

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        searchQuery = root.findViewById(R.id.google_query);
        searchButton = root.findViewById(R.id.search_button);
//        yoyo = root.findViewById(R.id.test);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search = searchQuery.getEditText().getText().toString();

                SearchRequest request = new SearchRequest();
                request.execute();

                Integer searchCounter=0;
                searchCounter++;
                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("counter", searchCounter);
                editor.apply();

            }
        });

        return root;
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
//            yoyo.setText(TextList.toString());
            Intent openSearchResult = new Intent(getContext(), SearchPageActivity.class);
            openSearchResult.putExtra("TextArrayList", TextList);
            openSearchResult.putExtra("UrlArrayList", UrlList);
            startActivity(openSearchResult);
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
//                Log.e("relatedtopic array", RelatedTopicsArray.toString());
                for (int i = 0; i < RelatedTopicsArray.length(); i++) {

                    for (int k = 0; k < RelatedTopicsArray.length(); k++) {
                        TextList.add(RelatedTopicsArray.getJSONObject(k).getString("Text"));
                        UrlList.add(RelatedTopicsArray.getJSONObject(k).getString("FirstURL"));
//                        Log.e("array", TextList.get(k));
                    }

                    if (TextList.toString() == "[]" || UrlList.toString() == "[]") {
                        TopicsArray = RelatedTopicsArray.getJSONObject(i).getJSONArray("Topics");
                        for (int j = 0; j < TopicsArray.length(); j++) {
                            TextList.add(TopicsArray.getJSONObject(j).getString("Text"));
                            UrlList.add(TopicsArray.getJSONObject(j).getString("FirstURL"));
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