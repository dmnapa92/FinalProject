package com.example.tjgaming.finalproject.Api.TVShows;

import com.example.tjgaming.finalproject.Model.TVMaze.TVMazeResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by TJ on 10/15/2018.
 */
public interface TVShowsApiInterface {
    @Headers("X-Mashape-Key: RbXPrJzxBdmshZaGCvdsD0APshi3p1spBKJjsns8OGdx2NP64O")
    @GET("schedule?country=US")
    Call<List<TVMazeResult>> getTvMazeResults();
}
