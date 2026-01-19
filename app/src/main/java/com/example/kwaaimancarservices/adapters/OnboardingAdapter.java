package com.example.kwaaimancarservices.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kwaaimancarservices.R;
import com.example.kwaaimancarservices.models.OnboardingItem;

import java.util.List;

public class OnboardingAdapter extends RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder> {

    private List<OnboardingItem> onboardingItems;

    public OnboardingAdapter(List<OnboardingItem> onboardingItems) {
        this.onboardingItems = onboardingItems;
    }

    @NonNull
    @Override
    public OnboardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_onboarding, parent, false);
        return new OnboardingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OnboardingViewHolder holder, int position) {
        OnboardingItem item = onboardingItems.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return onboardingItems.size();
    }

    static class OnboardingViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView titleView;
        private TextView descriptionView;

        public OnboardingViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.onboarding_image);
            titleView = itemView.findViewById(R.id.onboarding_title);
            descriptionView = itemView.findViewById(R.id.onboarding_description);
        }

        public void bind(OnboardingItem item) {
            imageView.setImageResource(item.getImageResource());
            titleView.setText(item.getTitle());
            descriptionView.setText(item.getDescription());
        }
    }
}