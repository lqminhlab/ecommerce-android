package com.knight.clothes.mvp.collection;

import com.knight.clothes.api.APIInterface;
import com.knight.clothes.api.AppClient;
import com.knight.clothes.models.Product;
import com.knight.clothes.models.ResponseList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollectionModel implements CollectionContract.Model {
    @Override
    public void getData(final OnFinishedListener onFinishedListener) {
        APIInterface api = AppClient.client().create(APIInterface.class);
        Call<ResponseList<Product>> call = api.collection(AppClient.headers());

        call.enqueue(new Callback<ResponseList<Product>>() {
            @Override
            public void onResponse(Call<ResponseList<Product>> call, Response<ResponseList<Product>> response) {
                if (response.body() != null && response.body().getStatus() == 1)
                    onFinishedListener.onFinished(response.body().getData());
                else
                    onFinishedListener.onFinished(new ArrayList<Product>());
            }

            @Override
            public void onFailure(Call<ResponseList<Product>> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }

    @Override
    public void getData(final OnFinishedListener onFinishedListener, int offset) {
        APIInterface api = AppClient.client().create(APIInterface.class);
        Call<ResponseList<Product>> call = api.collection(AppClient.headers(), offset);

        call.enqueue(new Callback<ResponseList<Product>>() {
            @Override
            public void onResponse(Call<ResponseList<Product>> call, Response<ResponseList<Product>> response) {
                if (response.body() != null && response.body().getStatus() == 1)
                    onFinishedListener.onGetMoreFinished(response.body().getData());
                else
                    onFinishedListener.onGetMoreFinished(new ArrayList<Product>());
            }

            @Override
            public void onFailure(Call<ResponseList<Product>> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }
}
