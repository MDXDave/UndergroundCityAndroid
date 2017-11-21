package de.thkoeln.undergroundcity;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

class LayerAdapter extends RecyclerView.Adapter<LayerAdapter.LayerHolder> {

    private City city;
    private Context mContext;

    public LayerAdapter(Context context, City city) {
        this.city = city;
        this.mContext = context;
    }

    @Override
    public LayerHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_layer, viewGroup, false);
        return new LayerHolder(v);
    }

    @Override
    public void onBindViewHolder(LayerHolder layerHolder, int i) {

        Bauebene bauebene = city.getBauebenen().get(i);

        ArrayList<Bauwerk> buildings = city.getBauebenen().get(i).getBauwerke();
        BuildingAdapter buildingAdapter = new BuildingAdapter(mContext, buildings);

        layerHolder.headerTitle.setText(bauebene.toString());
        layerHolder.layerView.setHasFixedSize(true);
        layerHolder.layerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        layerHolder.layerView.setAdapter(buildingAdapter);

    }

    @Override
    public int getItemCount() {
        return city.getAnzahlEbenen();
    }

    public class LayerHolder extends RecyclerView.ViewHolder {

        RecyclerView layerView;
        TextView headerTitle;

        public LayerHolder(View view) {
            super(view);

            headerTitle = view.findViewById(R.id.headerTitle);
            layerView = view.findViewById(R.id.layerView);

        }

    }

}