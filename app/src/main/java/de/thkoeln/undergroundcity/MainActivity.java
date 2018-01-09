package de.thkoeln.undergroundcity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView;
    FloatingActionButton startButton;
    LayerAdapter layerAdaper;
    City city;
    boolean isRunning = false;
    private Runnable runnable;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        startButton = findViewById(R.id.startButton);
        city = new City();
        city.aktuellesGuthaben = 25000;
        layerAdaper = new LayerAdapter(this, city);

        initializeRecyclerView();
        updateUi();

        startButton.setOnClickListener(this);

    }

    private void initializeRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(layerAdaper);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.layers:
            case R.id.buildings:
                break;
            case R.id.layer_small:
                city.baueEbene(1000, 10); break;
            case R.id.layer_medium:
                city.baueEbene(2000, 25); break;
            case R.id.layer_large:
                city.baueEbene(3500, 50); break;
        }

        if(city.aktuelleEbene >= 0) {
            switch (item.getItemId()) {
                case R.id.building_high:
                    if (!city.baueHochhaus())
                        Toast.makeText(this, "Zuwenig Slots!", Toast.LENGTH_LONG).show();
                    break;
                case R.id.building_house:
                    if (!city.baueVilla())
                        Toast.makeText(this, "Zuwenig Slots!", Toast.LENGTH_LONG).show();
                    break;
                case R.id.building_park:
                    if (!city.bauePark())
                        Toast.makeText(this, "Zuwenig Slots!", Toast.LENGTH_LONG).show();
                    break;
                case R.id.building_supermarket:
                    if (!city.baueSupermarkt())
                        Toast.makeText(this, "Zuwenig Slots!", Toast.LENGTH_LONG).show();
                    break;
            }
        }else{
            Toast.makeText(this, "Es ist keine Ebene ausgewählt!", Toast.LENGTH_LONG).show();
        }

        updateUi();

        return true;
    }

    private void updateUi() {

        layerAdaper.notifyDataSetChanged();
        try {
            getSupportActionBar().setSubtitle(getResources().getString(R.string.money, city.aktuellesGuthaben));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /* start round */
    @Override
    public void onClick(View view) {
        if(!isRunning) {

            handler = new Handler();
            final int delay = 1000; // delay between rounds in ms

            startButton.setImageResource(R.drawable.ic_stop);
            isRunning = true;

            runnable = new Runnable() {
                @Override
                public void run() {
                    city.spielrunden(1);
                    getSupportActionBar().setTitle(getResources().getString(R.string.round, city.aktuelleSpielrunde));
                    getSupportActionBar().setSubtitle(getResources().getString(R.string.money, city.aktuellesGuthaben));
                    layerAdaper.notifyDataSetChanged();
                    handler.postDelayed(runnable, delay);
                }
            };

            handler.postDelayed(runnable, delay);
        }else{
            handler.removeCallbacks(runnable);
            startButton.setImageResource(R.drawable.ic_play);
            isRunning = false;
        }

    }
}
