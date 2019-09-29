package com.example.naamkaran;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.naamkaran.Interface.NameDataService;
import com.example.naamkaran.Interface.UpdateFragment;
import com.example.naamkaran.library.RecyclerviewScroller;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HinduFragment  extends Fragment {
    RecyclerView recyclerView;
    NameDataService nameDataService;
    List<NameDisplay> nameList;
    Context context = this.getActivity();
    Toolbar toolbar;
    MenuInflater inflater;
    RecyclerviewAdapter nameAdaptor;
    RecyclerviewScroller fastScroller;
    WebView webview;
    ProgressBar pbar;
    public HinduFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup v,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.hindu_fragment, v, false);
        // pbar = (ProgressBar)rootView.findViewById(R.id.progressBar1);
        //webview.loadUrl();

        recyclerView = rootView.findViewById(R.id.rcViewh);
        fastScroller = rootView.findViewById(R.id.fastscroller);
        toolbar = rootView.findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        nameList = new ArrayList<NameDisplay>();

        generateList();

        return rootView;

    }




    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        nameAdaptor = new RecyclerviewAdapter(nameList,getActivity());
        // recyclerView.addItemDecoration(new DividerItemDecorator(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(nameAdaptor);

        ((TabActivity)getActivity()).updateApi(new UpdateFragment() {

            @Override
            public void searchList(String search_data) {
                filter(search_data);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public void onLayoutChildren(final RecyclerView.Recycler recycler, final RecyclerView.State state) {
                super.onLayoutChildren(recycler, state);
                final int firstVisibleItemPosition = findFirstVisibleItemPosition();
                if (firstVisibleItemPosition != 0) {
                    // this avoids trying to handle un-needed calls
                    if (firstVisibleItemPosition == -1)
                        //not initialized, or no items shown, so hide fast-scroller
                        fastScroller.setVisibility(View.GONE);
                    return;
                }
                final int lastVisibleItemPosition = findLastVisibleItemPosition();
                int itemsShown = lastVisibleItemPosition - firstVisibleItemPosition + 1;
                //if all items are shown, hide the fast-scroller
                fastScroller.setVisibility(nameAdaptor.getItemCount() > itemsShown ? View.VISIBLE : View.GONE);
            }
        });
        fastScroller.setRecyclerView(recyclerView);
        fastScroller.setViewsToUse(R.layout.scroll_bar, R.id.fastscroller_bubble, R.id.fastscroller_handle);



    }

    void filter(String text){
        List<NameDisplay> temp = new ArrayList();
        for(NameDisplay d: nameList){
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if(d.getName().startsWith(text)){
                temp.add(d);
            }
//            else{Toast toast = Toast.makeText(getActivity(),
//                    "This is a message displayed in a Toast",
//                    Toast.LENGTH_SHORT);
//
//                toast.show();}
        }
        //update recyclerview
        nameAdaptor.setNameList(temp);
    }
    public void generateList() {

        nameDataService = RetrofitInstance.getRetrofitInstance().create(NameDataService.class);
        Call<List<NameDisplay>> call = nameDataService.getNames("3", "1");

//        final ProgressDialog progressDoalog;
//        progressDoalog = new ProgressDialog(context);
//        progressDoalog.setMax(100);
//        progressDoalog.setMessage("Loading....");
//        progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//        // show it
//        progressDoalog.show();
        call.enqueue(new Callback<List<NameDisplay>>() {
            @Override
            public void onResponse(Call<List<NameDisplay>> call, Response<List<NameDisplay>> response) {

//                Log.d("mehul", response.body().size() + "i am size");
                //  progressDoalog.dismiss();

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

            }

            @Override
            public void onFailure(Call<List<NameDisplay>> call, Throwable t) {
                Log.d("mehul", t.getMessage());

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                // progressDoalog.dismiss();

            }
        });

    }

    public void onPageStarted(WebView view, String url, Bitmap favicon) {

        // TODO Auto-generated method stub
        onPageStarted(view, url, favicon);
    }


    public boolean shouldOverrideUrlLoading(WebView view, String url) {

        // TODO Auto-generated method stub
        view.loadUrl(url);
        return true;
    }


    public void onPageFinished(WebView view, String url) {

        // TODO Auto-generated method stub

        onPageFinished(view, url);
        pbar.setVisibility(View.GONE);

    }
}





