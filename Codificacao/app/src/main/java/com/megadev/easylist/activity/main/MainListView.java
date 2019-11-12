package com.megadev.easylist.activity.main;

import com.megadev.easylist.model.Item;

import java.util.List;

public interface MainListView {

    void showLoading();
    void hideLoading();
    void onGetResult(List<Item> itens);
    void onErrorLoading(String message);

}
