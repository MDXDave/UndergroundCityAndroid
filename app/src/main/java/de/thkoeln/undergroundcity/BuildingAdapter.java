package de.thkoeln.undergroundcity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class BuildingAdapter extends RecyclerView.Adapter<BuildingAdapter.BuildingHolder> {

    private ArrayList<Bauwerk> buildingList;
    private Context mContext;

    public BuildingAdapter(Context context, ArrayList<Bauwerk> itemsList) {
        this.buildingList = itemsList;
        this.mContext = context;
    }

    @Override
    public BuildingHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_building, null);
        return new BuildingHolder(v);
    }

    @Override
    public void onBindViewHolder(BuildingHolder buildingHolder, int i) {

        Bauwerk bauwerk = buildingList.get(i);

        Log.i("BAUWERK", bauwerk.type);

        buildingHolder.buildingTitle.setText(bauwerk.type);
    }

    @Override
    public int getItemCount() {
        return (null != buildingList ? buildingList.size() : 0);
    }

    public class BuildingHolder extends RecyclerView.ViewHolder {

        TextView buildingTitle;

        public BuildingHolder(View view) {
            super(view);

            buildingTitle = view.findViewById(R.id.buildingTitle);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });


        }

    }

}

