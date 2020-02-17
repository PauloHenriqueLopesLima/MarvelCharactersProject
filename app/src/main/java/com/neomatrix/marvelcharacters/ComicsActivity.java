package com.neomatrix.marvelcharacters;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.neomatrix.marvelcharacters.interfaces.MarvelApi;
import com.neomatrix.marvelcharacters.models.CharactersApi;
import com.neomatrix.marvelcharacters.models.ComicsApi;
import com.neomatrix.marvelcharacters.models.RespostaApi;
import com.neomatrix.marvelcharacters.models.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ComicsActivity extends AppCompatActivity {

    private ImageView imageViewCharacterComics;
    private TextView textViewIdCharacterComics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comics);

        imageViewCharacterComics = findViewById(R.id.imageViewCharacterComics);
        textViewIdCharacterComics = findViewById(R.id.textViewIdCharacterComics);

        int id= getIntent().getExtras().getInt("id");
        textViewIdCharacterComics.setText("id do Personagem: "+id);

        System.out.println(textViewIdCharacterComics.getText());


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
                if (response.isSuccessful()) {

                    List<Result> personagens = response.body().getData().getResults();



                } else {

                }
            }

            @Override
            public void onFailure(Call<ComicsApi> call, Throwable t) {

            }
        });
    }
}
