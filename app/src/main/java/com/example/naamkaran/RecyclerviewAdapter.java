package com.example.naamkaran;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.naamkaran.library.RecyclerviewScroller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.NameViewHolder> implements Filterable, RecyclerviewScroller.BubbleTextGetter {
    private List<NameDisplay> nameList;
    private List<NameDisplay> nameList_filtered;
    private RecyclerviewAdapterListener listener;


    private Context context;

    private static int currentPosition = 0;

    class NameViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name1, txt_meaning1;
        ImageView imageView;
        //LinearLayout linearLayout;

        NameViewHolder(View itemView) {
            super(itemView);

            txt_name1 = (TextView) itemView.findViewById(R.id.txt_name);
            txt_meaning1 = (TextView) itemView.findViewById(R.id.txt_meaning);
//imageView=(ImageView) itemView.findViewById(R.id.blankheart);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onNameSelected(nameList_filtered.get(getAdapterPosition()));
                }
            });


            // linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
        }}


    RecyclerviewAdapter(List<NameDisplay> nameList) {
        this.nameList = nameList;
        nameList_filtered = new ArrayList<>(nameList);
//        nameList = list;
//        nameList_filtered=list;
        this.context = context;
    }

    public RecyclerviewAdapter(int numberOfItems){

        List<String> nameList = new ArrayList<>();
        java.util.Random r = new java.util.Random();

        for (int i = 0; i < numberOfItems; i++)
            nameList.add(((char) ('A' + r.nextInt('Z' - 'A'))) + " " + Integer.toString(i));
        java.util.Collections.sort(nameList);
        // this.nameList = nameList;

    }



    public void setNameList(List<NameDisplay> nameList) {
        this.nameList = nameList;

        notifyDataSetChanged();
    }





    @NonNull
    @Override
    public NameViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.display_name,viewGroup,false);
        return new NameViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NameViewHolder nameViewHolder, int i) {


        NameDisplay name1 = nameList.get(i);

        nameViewHolder.txt_name1.setText(name1.getName());
        nameViewHolder.txt_meaning1.setText(name1.getMeaning());
        // nameViewHolder.imageView.setText(name1.getFav());

    }

    @Override
    public String getTextToShowInBubble(final int pos) {
        return Character.toString(nameList.get(pos).charAt(0));
    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }


    @Override
    public Filter getFilter() {

        return exampleFilter; }



    protected Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<NameDisplay> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(nameList_filtered);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (NameDisplay item : nameList_filtered) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }
//                @Override
//                protected FilterResults performFiltering(CharSequence charSequence) {
//                    String charString = charSequence.toString();
//                    if (charString.isEmpty()) {
//                        nameList_filtered = nameList;
//                    } else {
//                        List<NameDisplay> filteredList = new ArrayList<>();
//                        for (NameDisplay row : nameList) {
//
//                            // name match condition. this might differ depending on your requirement
//                            // here we are looking for name or phone number match
//                            if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getMeaning().contains(charSequence)) {
//                                filteredList.add(row);
//                            }
//                        }
//
//                        nameList_filtered = filteredList;
//                    }
//
//                    FilterResults filterResults = new FilterResults();
//                    filterResults.values = nameList_filtered;
//                    return filterResults;
//                }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            nameList.clear();
            nameList.addAll((List) results.values);
            notifyDataSetChanged();
//                    nameList_filtered = (ArrayList<NameDisplay>) filterResults.values;
//                    notifyDataSetChanged();
        }
    };

    public interface RecyclerviewAdapterListener {
        void onNameSelected(NameDisplay nameDisplay);

    }
}

