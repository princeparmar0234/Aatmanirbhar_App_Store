package com.example.aatmanirbhar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.irozon.alertview.AlertActionStyle;
import com.irozon.alertview.AlertStyle;
import com.irozon.alertview.AlertTheme;
import com.irozon.alertview.AlertView;
import com.irozon.alertview.objects.AlertAction;
import com.squareup.picasso.Picasso;

public class PostAdapter extends FirebaseRecyclerAdapter<Post,PostAdapter.pastViewHolder> {

    Context context;
    public PostAdapter(@NonNull FirebaseRecyclerOptions<Post> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull pastViewHolder holder, int position, @NonNull Post model) {

        holder.title.setText(model.getTitle());
        Picasso.get().load(model.getUrl()).placeholder(R.drawable.logo).into(holder.imageView);
        holder.install.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(model.getApplink()));
                view.getContext().startActivity(intent);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertView alert = new AlertView(""+model.getTitle(), ""+model.getDesc(), AlertStyle.IOS);
                alert.addAction(new AlertAction("Install", AlertActionStyle.POSITIVE, action -> {
// Action 1 callback
                    Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(model.getApplink()));
                    view.getContext().startActivity(intent);
                }));
                alert.addAction(new AlertAction("More Info", AlertActionStyle.POSITIVE, action -> {
// Action 2 callback
                    Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(model.getApplink()));
                    view.getContext().startActivity(intent);
                }));

                alert.setTheme(AlertTheme.LIGHT);

                alert.show((AppCompatActivity) view.getContext());
            }
        });



    }

    @NonNull
    @Override
    public pastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.demmo, parent, false);

        return new pastViewHolder(view);

    }

    class pastViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageView imageView;
        Button install;
        public pastViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView);
            title=itemView.findViewById(R.id.title);
            install=itemView.findViewById(R.id.install);
        }
    }
}
