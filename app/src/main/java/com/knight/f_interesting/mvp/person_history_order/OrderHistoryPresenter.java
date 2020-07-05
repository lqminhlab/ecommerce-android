package com.knight.f_interesting.mvp.person_history_order;

import android.content.Context;

import com.knight.f_interesting.models.Order;

import java.util.List;

public class OrderHistoryPresenter implements OrderHistoryContract.Presenter, OrderHistoryContract.Model.OnFinishedListener {

    private OrderHistoryContract.View view;
    private OrderHistoryContract.Model model;

    public OrderHistoryPresenter(OrderHistoryContract.View view, Context context){
        this.view = view;
        this.model = new OrderHistoryModel(context);
    }

    @Override
    public void onFinished(List<Order> orders) {
        if(view != null){
            view.hideProgress();
            view.setData(orders);
        }
    }

    @Override
    public void onFailure(Throwable throwable) {
        if(view != null){
            view.hideProgress();
            view.onResponseFailure(throwable);
        }
    }

    @Override
    public void requestData() {
        if(view != null){
            view.showProgress();
            model.getData(this);
        }
    }

    @Override
    public void requestData(int offset) {
        if(view != null){
            view.showProgress();
            model.getData(this, offset);
        }
    }

    @Override
    public void requestData(int offset, int limit) {
        if(view != null){
            view.showProgress();
            model.getData(this, offset, limit);
        }
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}