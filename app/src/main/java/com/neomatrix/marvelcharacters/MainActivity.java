package com.neomatrix.marvelcharacters;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mlsdev.animatedrv.AnimatedRecyclerView;
import com.neomatrix.marvelcharacters.adapter.CharactersAdapter;
import com.neomatrix.marvelcharacters.interfaces.MarvelApi;
import com.neomatrix.marvelcharacters.models.CharactersApi;
import com.neomatrix.marvelcharacters.models.Result;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private AnimatedRecyclerView recyclerView;
    private String TAG;
    private CharactersAdapter adapter;
    private GridLayoutManager gridLayout;
    private ProgressDialog progressDialog;
    private List<Result> personagens;
    private CharactersApi retornoApi;
    private int progressStatus = 0;
    private TextView textView;
    private Handler handler = new Handler();




    private int offset=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //overridePendingTransition(R.anim.anim_one, R.anim.anim_one);


        textView = findViewById(R.id.textViewProgressBar);


        setupRecyclerView();

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
                    //recyclerView.setLayoutManager(gridLayout);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    recyclerView.scheduleLayoutAnimation();


                } else {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Atenção")
                            .setMessage("Não foi possivel carregar os personagens, tentar novamente?")
                            .setCancelable(false)


                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {


                                }
                            })


                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
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

                if (! recyclerView.canScrollVertically(1)){

                    progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.setMax(100);
                    progressDialog.setMessage("Carregando personagens....");
                    progressDialog.setTitle("Recebendo dados da API");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressDialog.show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                while (progressDialog.getProgress() <= progressDialog
                                        .getMax()) {
                                    Thread.sleep(200);
                                    handler.sendMessage(handler.obtainMessage());
                                    if (progressDialog.getProgress() == progressDialog
                                            .getMax()) {
                                        progressDialog.dismiss();
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();



                    //Toast.makeText(MainActivity.this, "Carregando novos personagens", Toast.LENGTH_LONG).show();

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


        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                progressDialog.incrementProgressBy(10);
            }
        };
    }






    private void setupRecyclerView() {
        recyclerView = new AnimatedRecyclerView.Builder(this)

                .orientation(GridLayout.VERTICAL)
                .layoutManagerType(AnimatedRecyclerView.LayoutManagerType.GRID)
                .animation(R.anim.layout_animation_from_scale)
                .animationDuration(10000)
                .reverse(false)
                .build();

        recyclerView = findViewById(R.id.recyclerView);
        //recyclerView.setHasFixedSize(true);
        //gridLayout = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
    }


}




