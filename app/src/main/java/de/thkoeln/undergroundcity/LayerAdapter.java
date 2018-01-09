package de.thkoeln.undergroundcity;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

class LayerAdapter extends RecyclerView.Adapter<LayerAdapter.LayerHolder> {

    private City city;
    private Context mContext;

    LayerAdapter(Context context, City city) {
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

        layerHolder.itemView.setBackgroundColor(city.aktuelleEbene == i ? 0xFF66BB6A : android.R.attr.selectableItemBackground);

        layerHolder.inhabitants.setText(mContext.getResources().getString(R.string.inhabitants, bauebene.getEinwohner()));
        layerHolder.income.setText(mContext.getResources().getString(R.string.income, bauebene.getEinnahmen()));
        layerHolder.costs.setText(mContext.getResources().getString(R.string.costs, bauebene.getAusgaben()));
        layerHolder.quality.setText(mContext.getResources().getString(R.string.quality, bauebene.getLebensqualitaet()));

        layerHolder.headerTitle.setText(mContext.getResources().getString(R.string.layer_free_slots, bauebene.getFreieSlots()));
        layerHolder.layerView.setHasFixedSize(true);
        layerHolder.layerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        layerHolder.layerView.setAdapter(buildingAdapter);
        layerHolder.layer = i;

    }

    @Override
    public int getItemCount() {
        return city.getAnzahlEbenen();
    }

    public class LayerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout layerLayout;
        RecyclerView layerView;
        TextView headerTitle;
        TextView inhabitants;
        TextView income;
        TextView costs;
        TextView quality;

        int layer;

        LayerHolder(View view) {
            super(view);

            headerTitle = view.findViewById(R.id.headerTitle);
            layerView = view.findViewById(R.id.layerView);
            layerLayout = view.findViewById(R.id.layerLayout);

            inhabitants = view.findViewById(R.id.inhabitants);
            income = view.findViewById(R.id.income);
            costs = view.findViewById(R.id.costs);
            quality = view.findViewById(R.id.quality);

            itemView.setOnClickListener(this);
            layerView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            if (getAdapterPosition() == RecyclerView.NO_POSITION) return;

            // Updating old as well as new positions
            notifyItemChanged(city.aktuelleEbene);
            city.aktuelleEbene = getAdapterPosition();
            notifyItemChanged(city.aktuelleEbene);
        }
    }

}