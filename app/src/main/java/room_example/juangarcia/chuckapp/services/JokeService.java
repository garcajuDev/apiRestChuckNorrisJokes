package room_example.juangarcia.chuckapp.services;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import room_example.juangarcia.chuckapp.domain.Joke;

public interface JokeService {

    String BASE_URL = "http://api.icndb.com/jokes/";

    @GET("random")
    Call<Joke> getJoke();
}
