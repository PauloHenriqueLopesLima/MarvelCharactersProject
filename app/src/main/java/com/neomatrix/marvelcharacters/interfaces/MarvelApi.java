package com.neomatrix.marvelcharacters.interfaces;

import com.neomatrix.marvelcharacters.models.CharactersApi;
import com.neomatrix.marvelcharacters.models.Data;
import com.neomatrix.marvelcharacters.models.RespostaApi;
import com.neomatrix.marvelcharacters.models.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface MarvelApi {

    public static final String BASE_URL = "http://gateway.marvel.com/v1/public/";
    public static final int LIMIT = 20;
    public static final long TS = 1581772705783L;
        public static final String API_KEY = "9df87fa1d8b665f808852975ceb42844&hash=b3b889dc48b0069c81668384b28c2e31";


    @GET("characters?limit=20&offset=0&ts=1581772705783&apikey=9df87fa1d8b665f808852975ceb42844&hash=b3b889dc48b0069c81668384b28c2e31")
    Call<CharactersApi> getCharacters();

    @GET
    Call<CharactersApi> getMoreCharacters(@Url String url);

    @GET
    Call<RespostaApi> getComics(@Url String url);



}
