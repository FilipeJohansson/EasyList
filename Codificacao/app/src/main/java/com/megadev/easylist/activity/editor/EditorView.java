package com.megadev.easylist.activity.editor;

import com.google.firebase.auth.FirebaseUser;

public interface EditorView {

    void showProgress();
    void hideProgress();
    void onAddSuccess(String message, FirebaseUser user);
    void onAddError(String message, FirebaseUser user);

}
