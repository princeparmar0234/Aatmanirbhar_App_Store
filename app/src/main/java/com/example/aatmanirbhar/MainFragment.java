package com.example.aatmanirbhar;

import android.graphics.Color;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainFragment extends Fragment {

    RecyclerView recyclerView;

    ProgressBar progressBar;
    PostAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView=view.findViewById(R.id.recycler);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        progressBar=view.findViewById(R.id.progress);

        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);


         String title=getArguments().getString("title");


         FirebaseRecyclerOptions<Post> options =
                new FirebaseRecyclerOptions.Builder<Post>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child(""+title), Post.class)
                        .build();

        adapter=new PostAdapter(options);

        recyclerView.setAdapter(adapter);

//        def.addListenerForSingleValueEvent(valueEventListener);
//
//        swipeRefreshLayout=view.findViewById(R.id.swipe);
//        int myColor = Color.parseColor("#34495E");
//        swipeRefreshLayout.setColorSchemeColors(myColor);
//        swipeRefreshLayout.setRefreshing(false);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                list.clear();
//                def= FirebaseDatabase.getInstance().getReference().child(""+title);
//                def.addListenerForSingleValueEvent(valueEventListener);
//            }
//        });

        return view;
    }

//    ValueEventListener valueEventListener=new ValueEventListener() {
//        @Override
//        public void onDataChange(@NonNull DataSnapshot snapshot) {
//            for (DataSnapshot dataSnapshot:snapshot.getChildren()){
//                MainModel Model=dataSnapshot.getValue(MainModel.class);
//
//                    list.add(Model);
//
//                    swipeRefreshLayout.setRefreshing(false);
//
//            }
//
//            LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
//            recyclerView.setLayoutManager(layoutManager);
//            mainAdapter=new MainAdapter(getContext(),list);
//            recyclerView.setAdapter(mainAdapter);
//            Collections.reverse(list);
//            mainAdapter.notifyDataSetChanged();
//        }
//
//        @Override
//        public void onCancelled(@NonNull DatabaseError error) {
//            Toast.makeText(getContext(), ""+error, Toast.LENGTH_SHORT).show();
//        }
//    };

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
        progressBar.setVisibility(View.GONE);

    }
    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();

        progressBar.setVisibility(View.VISIBLE);
    }
}