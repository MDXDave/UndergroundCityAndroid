package de.thkoeln.undergroundcity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class BuildingAdapter extends RecyclerView.Adapter<BuildingAdapter.BuildingHolder> {

    private ArrayList<Bauwerk> buildingList;
    private Context mContext;

    BuildingAdapter(Context context, ArrayList<Bauwerk> itemsList) {
        this.buildingList = itemsList;
        this.mContext = context;
    }

    @Override
    public BuildingHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_building, viewGroup, false);
        return new BuildingHolder(v);
    }

    @Override
    public void onBindViewHolder(BuildingHolder buildingHolder, int i) {

        Bauwerk bauwerk = buildingList.get(i);

        switch (bauwerk.type) {
            case "V":
                buildingHolder.buildingImage.setImageResource(R.drawable.building_house);
                break;
            case "H":
                buildingHolder.buildingImage.setImageResource(R.drawable.building_skyscraper);
                break;
            case "P":
                buildingHolder.buildingImage.setImageResource(R.drawable.building_park);
                break;
            case "S":
                buildingHolder.buildingImage.setImageResource(R.drawable.building_supermarket);
                break;
        }

        Log.i("BAUWERK", bauwerk.type);

        buildingHolder.buildingTitle.setText(mContext.getResources().getString(R.string.required_slots, bauwerk.benoetigteSlots()));
    }

    @Override
    public int getItemCount() {
        return (null != buildingList ? buildingList.size() : 0);
    }

    class BuildingHolder extends RecyclerView.ViewHolder {

        TextView buildingTitle;
        ImageView buildingImage;

        BuildingHolder(View view) {
            super(view);

            buildingTitle = view.findViewById(R.id.buildingTitle);
            buildingImage = view.findViewById(R.id.buildingImage);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

    }

}

