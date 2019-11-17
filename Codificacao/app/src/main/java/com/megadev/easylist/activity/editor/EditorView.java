package com.megadev.easylist.activity.editor;

import com.google.firebase.auth.FirebaseUser;
import com.megadev.easylist.model.User;

import java.util.List;

public interface EditorView {

    void showProgress();
    void hideProgress();
    void onAddSuccess(String message, FirebaseUser user);
    void onAddError(String message, FirebaseUser user);

    void onGetResult(List<User> users);

}
