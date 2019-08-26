package com.example.android.status;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class Upcoming extends Fragment {
    ArrayList<event_item> item;
    RecyclerView recyclerView;
    eventAdapter adapter;
    RecyclerView.LayoutManager layoutManager;



    private OnFragmentInteractionListener mListener;

    public Upcoming() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        item=new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View com=  inflater.inflate(R.layout.fragment_completed, container, false);
        recyclerView=com.findViewById(R.id.rview);
        recyclerView.setHasFixedSize(true);
        getinfo();
        return com;
    }
    private void getinfo()
    {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://us-central1-sportsfete18v2.cloudfunctions.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        statusApi StatusApi=retrofit.create(statusApi.class);
        Call<List<obj>> call=StatusApi.getevents("upcoming");
        call.enqueue(new Callback<List<obj>>() {
            @Override
            public void onResponse(Call<List<obj>> call, Response<List<obj>> response) {
                if(!response.isSuccessful())
                {
                    return;
                }
                final List<obj> objs=response.body();
                if(!objs.isEmpty())
                {layoutManager=new LinearLayoutManager(getContext());
                    adapter=new eventAdapter(getContext(),objs);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                    adapter.setOnItemClickListener(new eventAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            Intent intent=new Intent(getActivity(),eventDetails.class);
                            intent.putExtra("Event", objs.get(position));
                            startActivity(intent);
                        }
                    });}
                else
                {
                    Toast.makeText(getContext(),"No upcoming events..",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<obj>> call, Throwable t) {

            }
        });
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
