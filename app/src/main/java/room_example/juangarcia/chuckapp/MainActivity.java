package room_example.juangarcia.chuckapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import room_example.juangarcia.chuckapp.domain.Joke;
import room_example.juangarcia.chuckapp.domain.Value;
import room_example.juangarcia.chuckapp.services.JokeService;

public class MainActivity extends AppCompatActivity {

    TextView jokeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        jokeView = findViewById(R.id.txtJoke);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofitObject = getRetrofitObject();
                JokeService api = retrofitObject.create(JokeService.class);
                callRemoteApi(api);
            }
        });
    }

    private Retrofit getRetrofitObject() {
        return new Retrofit.Builder().baseUrl(JokeService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private void callRemoteApi(JokeService api) {
        Call<Joke> call = api.getJoke();
        doEnqueue(call);
    }

    private void doEnqueue(Call<Joke> call) {
        call.enqueue(new Callback<Joke>() {
            @Override
            public void onResponse(Call<Joke> call, Response<Joke> response) {
                Joke jokeObject = response.body();
                Value val = jokeObject.getValue();
                String joke = val.getJoke();

                jokeView.setText(joke);
            }

            @Override
            public void onFailure(Call<Joke> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
