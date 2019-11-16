package com.megadev.easylist.activity.editor;

import com.megadev.easylist.model.Lista;

import java.util.List;

public interface MainView {

    void showLoading();
    void hideLoading();
    void onGetResult(List<Lista> listas);
    void onErrorLoading(String message);

    void onAddSuccess(String message);

}
