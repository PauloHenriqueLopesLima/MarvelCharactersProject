package com.neomatrix.marvelcharacters;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.neomatrix.marvelcharacters.adapter.CharactersAdapter;
import com.neomatrix.marvelcharacters.interfaces.MarvelApi;
import com.neomatrix.marvelcharacters.models.Characters;
import com.neomatrix.marvelcharacters.models.CharactersApi;
import com.neomatrix.marvelcharacters.models.Data;
import com.neomatrix.marvelcharacters.models.RespostaApi;
import com.neomatrix.marvelcharacters.models.Result;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String TAG;
    private CharactersAdapter adapter;
    private GridLayoutManager gridLayout;
    private ProgressBar progressBar;
    private List<Result> personagens;
    private CharactersApi retornoApi;



    private int offset=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        gridLayout = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);

        personagens = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MarvelApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MarvelApi api = retrofit.create(MarvelApi.class);

        Call<CharactersApi> requestApi = api.getCharacters();

        requestApi.enqueue(new Callback<CharactersApi>() {
            @Override
            public void onResponse(Call<CharactersApi> call, Response<CharactersApi> response) {
                if (response.isSuccessful()) {

                    personagens = response.body().getData().getResults();
                    offset= response.body().getData().getOffset();
                    adapter = new CharactersAdapter(personagens, getApplicationContext());
                    recyclerView.setLayoutManager(gridLayout);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();


                } else {

                }
            }

            @Override
            public void onFailure(Call<CharactersApi> call, Throwable t) {

            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (! recyclerView.canScrollVertically(1)){ //1 for down
                    Toast.makeText(MainActivity.this, "Carregando novos personagens", Toast.LENGTH_LONG).show();

                    offset = offset+20;


                    String offset2 = String.valueOf(offset);


                    String tudo = "characters?limit=20&offset="+offset2+"&ts=1581772705783&apikey=9df87fa1d8b665f808852975ceb42844&hash=b3b889dc48b0069c81668384b28c2e31";

                    Call<CharactersApi> requestApi = api.getMoreCharacters(tudo);

                    requestApi.enqueue(new Callback<CharactersApi>() {
                        @Override
                        public void onResponse(Call<CharactersApi> call, Response<CharactersApi> response) {
                            if (response.isSuccessful()) {

                                personagens.addAll(response.body().getData().getResults()) ;
                                adapter.notifyDataSetChanged();

                            } else {

                                Toast.makeText(MainActivity.this, "erro"+ response.code(), Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<CharactersApi> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "Ocorreu um erro"+ t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }

            }

        });


    }






}




