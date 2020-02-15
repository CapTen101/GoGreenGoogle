package com.example.gogreengoogle.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gogreengoogle.R;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

    private Context context;
    ArrayList<String> TextList;
    ArrayList<String> UrlList;
                                                              
    public ListAdapter(Context c, ArrayList<String> textlist, ArrayList<String> urllist) {
        context = c;
        TextList = textlist;
        UrlList = urllist;
    }

    @Override
    public ListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.searchlist, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.MyViewHolder holder, int position) {
        holder.text.setText(TextList.get(position));
        holder.url.setText(UrlList.get(position));
    }

    @Override
    public int getItemCount() {
        return TextList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text, url;

        public MyViewHolder(View itemView) {
            super(itemView);

            text = itemView.findViewById(R.id.text);
            url = itemView.findViewById(R.id.url);
        }
    }
}
