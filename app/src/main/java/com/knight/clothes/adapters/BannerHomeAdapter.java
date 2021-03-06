package com.knight.clothes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.knight.clothes.R;
import com.knight.clothes.api.AppClient;
import com.knight.clothes.models.Banner;

import java.util.ArrayList;
import java.util.List;

public class BannerHomeAdapter extends PagerAdapter {

    private List<Banner> banners;
    private Context context;

    public BannerHomeAdapter(Context context){
        this.context = context;
        this.banners = new ArrayList<>();
    }

    public void changeData(List<Banner> banners){
        this.banners = banners;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return banners.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_banner_home, null);
        ImageView imageView = view.findViewById(R.id.iv_banner_home);
        ProgressBar progressBar = view.findViewById(R.id.pb_load_banner_home);
        TextView textView = view.findViewById(R.id.txt_banner_home);

        Glide.with(view).load(AppClient.url()
                + banners.get(position).getImage())
                .placeholder(R.drawable.border_image_brand_home)
                .into(imageView);
        textView.setText(banners.get(position).getSlogan());
        textView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }
}
