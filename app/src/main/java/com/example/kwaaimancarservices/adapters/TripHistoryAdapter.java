package com.example.kwaaimancarservices.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kwaaimancarservices.rides.R;
import com.kwaaimancarservices.rides.models.Trip;

import java.util.List;

public class TripHistoryAdapter extends RecyclerView.Adapter<TripHistoryAdapter.TripViewHolder> {

    private List<Trip> trips;

    public TripHistoryAdapter(List<Trip> trips) {
        this.trips = trips;
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_trip_history, parent, false);
        return new TripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        Trip trip = trips.get(position);
        
        holder.textTripDate.setText(trip.getDate());
        holder.textTripPrice.setText(trip.getPrice());
        holder.textPickupLocation.setText(trip.getPickupLocation());
        holder.textDropoffLocation.setText(trip.getDropoffLocation());
        holder.textDistance.setText(trip.getDistance());
        holder.textDuration.setText(trip.getDuration());
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }

    static class TripViewHolder extends RecyclerView.ViewHolder {
        TextView textTripDate, textTripPrice, textPickupLocation, textDropoffLocation, textDistance, textDuration;

        public TripViewHolder(@NonNull View itemView) {
            super(itemView);
            
            textTripDate = itemView.findViewById(R.id.textTripDate);
            textTripPrice = itemView.findViewById(R.id.textTripPrice);
            textPickupLocation = itemView.findViewById(R.id.textPickupLocation);
            textDropoffLocation = itemView.findViewById(R.id.textDropoffLocation);
            textDistance = itemView.findViewById(R.id.textDistance);
            textDuration = itemView.findViewById(R.id.textDuration);
        }
    }
}