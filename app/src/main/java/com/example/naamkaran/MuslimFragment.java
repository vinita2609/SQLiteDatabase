package com.example.naamkaran;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.naamkaran.Interface.NameDataService;
import com.example.naamkaran.library.RecyclerviewScroller;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MuslimFragment extends Fragment {
    RecyclerView recyclerView;
    NameDataService nameDataService;
    List<NameDisplay> nameList;
    TextView txt_name1, txt_meaning1;
    Context context=this.getActivity();
    Toolbar toolbar ;

    RecyclerviewAdapter nameAdaptor;
    RecyclerviewScroller fastScroller;
    public MuslimFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup v,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.muslim_fragment, v, false);


        recyclerView = rootView.findViewById(R.id.rcViewm);
        RecyclerviewScroller fastScroller = rootView.findViewById (R.id.fastscroller);
        toolbar= rootView.findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        nameList = new ArrayList<NameDisplay>();

        generateList();


//        public void onLayoutChildren(final RecyclerView.Recycler recycler, final RecyclerView.State state) {
//            super.onLayoutChildren(recycler, state);
//            //TODO if the items are filtered, considered hiding the fast scroller here
//            final int firstVisibleItemPosition = findFirstVisibleItemPosition();
//            if (firstVisibleItemPosition != 0) {
//                // this avoids trying to handle un-needed calls
//                if (firstVisibleItemPosition == -1)
//                    //not initialized, or no items shown, so hide fast-scroller
//                    fastScroller.setVisibility(View.GONE);
//                return;
//            }
//            final int lastVisibleItemPosition = findLastVisibleItemPosition();
//            int itemsShown = lastVisibleItemPosition - firstVisibleItemPosition + 1;
//            //if all items are shown, hide the fast-scroller
//            fastScroller.setVisibility(nameAdaptor.getItemCount() > itemsShown ? View.VISIBLE : View.GONE);
//        }



        fastScroller.setRecyclerView(recyclerView);
        fastScroller.setViewsToUse(R.layout.scroll_bar, R.id.fastscroller_bubble, R.id.fastscroller_handle);
        return rootView;
    }




    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        nameAdaptor = new RecyclerviewAdapter(nameList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.addItemDecoration(new DividerItemDecorator(getActivity(), LinearLayoutManager.VERTICAL));


        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(nameAdaptor);





    }





    public void generateList() {

        nameDataService = RetrofitInstance.getRetrofitInstance().create(NameDataService.class);
        Call<List<NameDisplay>> call = nameDataService.getNames("8", "1");


        call.enqueue(new Callback<List<NameDisplay>>() {
            @Override
            public void onResponse(Call<List<NameDisplay>> call, Response<List<NameDisplay>> response) {

                Log.d("mehul", response.body().size() + "i am size");

                if (response.body().size() > 0) {

                    Log.d("mehul", "in success method");

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

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }


}

