package com.megadev.easylist.activity.editor;

import com.megadev.easylist.model.Item;

import java.util.List;

public interface MainListView {

    void showLoading();
    void hideLoading();
    void onGetResult(List<Item> itens);
    void onErrorLoading(String message);

    void onAddSuccess(String message);
    void onAddError(String message);

}
