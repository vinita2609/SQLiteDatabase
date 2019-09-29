package com.example.naamkaran;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.naamkaran.Interface.NameDataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrendingFragment  extends Fragment {
    RecyclerView recyclerView;
    NameDataService nameDataService;
    List<NameDisplay> nameList;
    Context context = this.getActivity();


    RecyclerviewAdapter nameAdaptor;

    public TrendingFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup v,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.trending_fragment, v, false);


        recyclerView = rootView.findViewById(R.id.rcViewt);

        nameList = new ArrayList<NameDisplay>();
        //  generateList();
        return rootView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        nameAdaptor = new RecyclerviewAdapter(nameList, getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        //recyclerView.addItemDecoration(new DividerItemDecorator(getContext(), LinearLayoutManager.VERTICAL));


        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(nameAdaptor);


    }


    public void generateList() {

        nameDataService = RetrofitInstance.getRetrofitInstance().create(NameDataService.class);
        Call<List<NameDisplay>> call = nameDataService.getNames("7", "1");


        call.enqueue(new Callback<List<NameDisplay>>() {
            @Override
            public void onResponse(Call<List<NameDisplay>> call, Response<List<NameDisplay>> response) {

                Log.d("mehul", response.body().size() + "i am size");

                if (response.body().size() > 0) {

                    Log.d("mehul", "in success method");
//nameList=response.body();
                    for (NameDisplay name : response.body()) {
                        nameList.add(name);
                    }
                    Log.d("RESPONSE: ", "" + response.toString());


                } else {
                    Log.d("mehul", "in failure");
                }
                nameAdaptor.notifyDataSetChanged();
//                    Log.d("SIZE of name list", String.valueOf(nameList.size()));
//                    for (int i = 0; i < nameList.size(); i++) {
//                        Log.d("NAME LIST", nameList.get(i).getName());
//                    }
            }

            @Override
            public void onFailure(Call<List<NameDisplay>> call, Throwable t) {
                Log.d("mehul", t.getMessage());

                // Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }


}