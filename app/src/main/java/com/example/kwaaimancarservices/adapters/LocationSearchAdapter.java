package com.example.kwaaimancarservices.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kwaaimancarservices.R;
import com.example.kwaaimancarservices.models.Location;

import java.util.ArrayList;
import java.util.List;

public class LocationSearchAdapter extends RecyclerView.Adapter<LocationSearchAdapter.LocationViewHolder> {

    private Context context;
    private List<Location> locations;
    private OnLocationClickListener listener;

    public interface OnLocationClickListener {
        void onLocationClick(Location location);
    }

    public LocationSearchAdapter(Context context, OnLocationClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.locations = new ArrayList<>();
    }

    public void updateLocations(List<Location> newLocations) {
        this.locations.clear();
        this.locations.addAll(newLocations);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_location_search, parent, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        Location location = locations.get(position);
        holder.bind(location);
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    class LocationViewHolder extends RecyclerView.ViewHolder {
        private ImageView iconImageView;
        private TextView nameTextView;
        private TextView addressTextView;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            iconImageView = itemView.findViewById(R.id.location_icon);
            nameTextView = itemView.findViewById(R.id.location_name);
            addressTextView = itemView.findViewById(R.id.location_address);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onLocationClick(locations.get(position));
                }
            });
        }

        public void bind(Location location) {
            nameTextView.setText(location.getName());
            addressTextView.setText(location.getAddress());

            // Set appropriate icon based on location type
            if (location.getName().toLowerCase().contains("current") ||
                    location.getName().toLowerCase().contains("gps")) {
                iconImageView.setImageResource(R.drawable.ic_my_location);
            } else if (location.getName().toLowerCase().contains("airport")) {
                iconImageView.setImageResource(R.drawable.ic_flight);
            } else if (location.getName().toLowerCase().contains("mall") ||
                    location.getName().toLowerCase().contains("shop")) {
                iconImageView.setImageResource(R.drawable.ic_shopping);
            } else if (location.getName().toLowerCase().contains("station") ||
                    location.getName().toLowerCase().contains("train")) {
                iconImageView.setImageResource(R.drawable.ic_train);
            } else if (location.getName().toLowerCase().contains("university") ||
                    location.getName().toLowerCase().contains("school")) {
                iconImageView.setImageResource(R.drawable.ic_school);
            } else {
                iconImageView.setImageResource(R.drawable.ic_location_on);
            }
        }
    }
}