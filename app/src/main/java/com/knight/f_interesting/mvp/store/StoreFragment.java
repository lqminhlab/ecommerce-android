package com.knight.f_interesting.mvp.store;

import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.android.material.snackbar.Snackbar;
import com.knight.f_interesting.R;
import com.knight.f_interesting.adapters.CategoryStoreAdapter;
import com.knight.f_interesting.customs.RecyclerItemClickListener;
import com.knight.f_interesting.models.Category;
import com.knight.f_interesting.mvp.products.ProductsFragment;

import java.util.ArrayList;
import java.util.List;

public class StoreFragment extends Fragment implements StoreContract.View {

    private LinearLayout llLoading;
    private RecyclerView rvCategories;
    private ImageButton ivFilter;
    private DrawerLayout drawerFilter;

    private StoreContract.Presenter presenter;

    private CategoryStoreAdapter categoryAdapter;
    private List<Category> categories;
    private View view;
    private ProductsFragment fProducts;

    FragmentManager fm;
    FragmentTransaction ft;

    private void init(View view) {
        this.view = view;
        llLoading = view.findViewById(R.id.ll_load_store);
        rvCategories = view.findViewById(R.id.rv_categories_store);
        ivFilter = view.findViewById(R.id.ib_filter);
        drawerFilter = view.findViewById(R.id.drawer_store);

        fm = getFragmentManager();
        ft = fm.beginTransaction();

        categories = new ArrayList<>();
        categoryAdapter = new CategoryStoreAdapter(categories, view.getContext());
        rvCategories.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCategories.setAdapter(categoryAdapter);
        presenter = new StorePresenter(this);
        presenter.requestData();
    }

    private void listener(View view) {
        rvCategories.addOnItemTouchListener(new RecyclerItemClickListener(view.getContext(),
                rvCategories, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                categoryAdapter.changeIndex(position);
                fProducts.refresh(categories.get(position).getId());
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
        ivFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("CLE", "tr");
                drawerFilter.openDrawer(GravityCompat.END);
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_store, container, false);

        init(view);
        listener(view);

        return view;
    }

    @Override
    public void showProgress() {
        llLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        llLoading.setVisibility(View.GONE);
    }

    @Override
    public void setDataToView(List<Category> categories) {
        this.categories = categories;
        categoryAdapter.changeData(this.categories);
        categoryAdapter.notifyDataSetChanged();
        fProducts = new ProductsFragment( "", this.categories.get(0).getId(),
                0, 0, 0, 0, 0);
        ft.add(R.id.rl_products_store, fProducts);
        ft.commit();
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Snackbar.make(this.view.findViewById(R.id.fragment_store), getString(R.string.error_data),
                Snackbar.LENGTH_LONG).show();
    }
}