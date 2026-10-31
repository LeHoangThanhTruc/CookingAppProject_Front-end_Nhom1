package com.example.cookingapp;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

public final class ProfileImageLoader {
    private ProfileImageLoader() {
    }

    public static void load(ImageView imageView, String avatarUrl) {
        String resolvedUrl = ApiClient.resolveUrl(avatarUrl);
        if (resolvedUrl == null) {
            Glide.with(imageView).clear(imageView);
            imageView.setImageResource(R.drawable.ic_person);
            imageView.setColorFilter(imageView.getContext().getColor(R.color.panda_green));
            return;
        }

        imageView.clearColorFilter();
        Glide.with(imageView)
                .load(resolvedUrl)
                .placeholder(R.drawable.ic_person)
                .error(R.drawable.ic_person)
                .circleCrop()
                .into(imageView);
    }
}
