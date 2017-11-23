package de.thkoeln.undergroundcity;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton startButton;
    LayerAdapter layerAdaper;
    City city;

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
            case R.id.layer_small:
                city.baueEbene(1000, 5); break;
            case R.id.layer_medium:
                city.baueEbene(2000, 10); break;
            case R.id.layer_large:
                city.baueEbene(2850, 15); break;
            case R.id.building_high:
                if(!city.baueHochhaus())
                    Toast.makeText(this, "Zuwenig Slots!", Toast.LENGTH_LONG).show();
                break;
            case R.id.building_house:
                if(!city.baueVilla())
                    Toast.makeText(this, "Zuwenig Slots!", Toast.LENGTH_LONG).show();
                break;
            case R.id.building_park:
                if(!city.bauePark())
                    Toast.makeText(this, "Zuwenig Slots!", Toast.LENGTH_LONG).show();
                break;
            case R.id.building_supermarket:
                if(!city.baueSupermarkt())
                    Toast.makeText(this, "Zuwenig Slots!", Toast.LENGTH_LONG).show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        updateUi();

        return true;
    }

    private void updateUi() {

        layerAdaper.notifyDataSetChanged();
        getSupportActionBar().setSubtitle("Guthaben: "+city.aktuellesGuthaben+" € ");
    }
}
