package com.neomatrix.marvelcharacters.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.neomatrix.marvelcharacters.DetailsActivity;
import com.neomatrix.marvelcharacters.R;
import com.neomatrix.marvelcharacters.models.Data;
import com.neomatrix.marvelcharacters.models.RespostaApi;

import java.util.List;

public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.ViewHolder> {
    private List<Data> listaPersonagens;
    private Context context;
    private RespostaApi item;

    public CharactersAdapter( List<Data> listaPersonagens,Context context) {
        this.listaPersonagens = listaPersonagens;
        this.context = context;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_character,
                parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
 //   item = listaPersonagens.get(position);
 //   holder.textView1.setText(item.getData().getResults());
 //   //holder.textView2.setText(item.getDescription());
 //   Picasso.get()
 //           .load(item.getThumbnail().getPath() + "." + item.getThumbnail().getExtension())
 //           //.resize(170, 170)
 //           .into(holder.imageView);

    }


    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView1;
        TextView textView2;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewCharacterComics);
            textView1 = itemView.findViewById(R.id.textViewNameCharacter);
            //textView2 = itemView.findViewById(R.id.textViewDescription);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    Intent intent = new Intent(v.getContext(), DetailsActivity.class);



       //           intent.putExtra("nome",listaPersonagens.get(getAdapterPosition()).getName());
       //           intent.putExtra("id",listaPersonagens.get(getAdapterPosition()).getId());
       //           intent.putExtra("desc",listaPersonagens.get(getAdapterPosition()).getDescription());
       //           intent.putExtra("thumb",listaPersonagens.get(getAdapterPosition()).getThumbnail().getPath());
       //           intent.putExtra("ext",listaPersonagens.get(getAdapterPosition()).getThumbnail().getExtension());

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    v.getContext().startActivity(intent);



                }
            });

        }


    }

    @Override
    public int getItemCount() {
        return listaPersonagens.size();
    }




}

