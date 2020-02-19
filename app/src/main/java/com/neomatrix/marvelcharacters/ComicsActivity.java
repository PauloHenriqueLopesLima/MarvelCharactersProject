package com.neomatrix.marvelcharacters;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.neomatrix.marvelcharacters.interfaces.MarvelApi;
import com.neomatrix.marvelcharacters.models.ComicsApi;
import com.neomatrix.marvelcharacters.models.Result;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ComicsActivity extends AppCompatActivity {

    private ImageView imageViewCharacterComics;
    private TextView textViewIdCharacterComics;
    private TextView textViewDescription;
    private TextView textViewPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comics);

        overridePendingTransition(R.anim.anim_one, R.anim.anim_one);

        imageViewCharacterComics = findViewById(R.id.imageViewCharacterComics);
        textViewIdCharacterComics = findViewById(R.id.textViewIdCharacterComics);
        textViewDescription = findViewById(R.id.textViewDescription);
        textViewPrice = findViewById(R.id.textViewPrice);

        String id= getIntent().getExtras().getString("id");
        textViewIdCharacterComics.setText("id do Personagem: "+id);




        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MarvelApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MarvelApi api = retrofit.create(MarvelApi.class);


        String tudo = "characters/"+id+"/comics?limit=100&offset=0&ts=1581772705783&apikey=9df87fa1d8b665f808852975ceb42844&hash=b3b889dc48b0069c81668384b28c2e31";



        Call<ComicsApi> requestApi = api.getComics(tudo);

        requestApi.enqueue(new Callback<ComicsApi>() {
            @Override
            public void onResponse(Call<ComicsApi> call, Response<ComicsApi> response) {
                
                try {
                    
                
                if (response.isSuccessful()) {

                    List<Result> comics = response.body().getData().getResults();


                    Comparator<Result> value = (Result o1, Result o2) ->
                            o2.getPrices().get(0).getPrice().compareTo( o1.getPrices().get(0).getPrice() );

                    Collections.sort(comics,value);
                    

                    textViewIdCharacterComics.setText(comics.get(0).getTitle());

                    textViewDescription.setText(comics.get(0).getDescription());

                    textViewPrice.setText("Preco USD: "+comics.get(0).getPrices().get(0).getPrice());



                    Picasso.get()


                            .load(comics.get(0).getImages().get(0).getPath()+"."+comics.get(0).getImages().get(0).getExtension())

                            //.resize(170, 170)
                            .into(imageViewCharacterComics);

                } else {

                }

                }catch (Exception e){
                    Toast.makeText(ComicsActivity.this, "Aconteceu algo não esperado :(", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ComicsApi> call, Throwable t) {

            }
        });
    }
}
