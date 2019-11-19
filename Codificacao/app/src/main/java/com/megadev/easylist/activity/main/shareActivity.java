package com.megadev.easylist.activity.main;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.megadev.easylist.R;
import com.megadev.easylist.activity.editor.EditorPresenter;
import com.megadev.easylist.activity.editor.EditorView;
import com.megadev.easylist.model.User;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

public class shareActivity extends Activity implements EditorView {

    private FirebaseAuth mAuth;

    private EditText etEmail;

    private int sessionId;

    ProgressDialog progressDialog;

    EditorPresenter presenter = new EditorPresenter(this);;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        mAuth = FirebaseAuth.getInstance();

        sessionId = Objects.requireNonNull(getIntent().getExtras()).getInt("EXTRA_SESSION_ID");

        /* PopUp activity */
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.5));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);
        /* /PopUp activity/ */

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Compartilhando lista");

        /* e-mail label */
        etEmail = findViewById(R.id.etEmail);
        /* /e-mail label/ */

        /* button salvar */
        Button btnSalvar = findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(view -> {
            String EMAIL = etEmail.getText().toString();
            /*presenter.shareList(
                    etEmail.getText().toString(),
                    sessionId,
                    mAuth.getCurrentUser());*/
            presenter.getUser(EMAIL, mAuth.getCurrentUser());
        });
        /* /button salvar/ */

    }

    @Override
    protected void onStart() {
        super.onStart();
        updateUI(mAuth.getCurrentUser());

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI(mAuth.getCurrentUser());
    }

    private void updateUI(FirebaseUser currentUser) {

        if (currentUser == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);

        }

    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void onAddSuccess(String message, FirebaseUser user) {
        Toast.makeText(this,
                message,
                Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onAddError(String message, FirebaseUser user) {
        Toast.makeText(this,
                message,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetResult(List<User> users) {
        if (!users.isEmpty()) {
            User user = users.get(0);
            shareList(user.getID_USUARIO(), sessionId);
        } else {
            Toast.makeText(this, "Nenhum usuário com esse e-mail foi encontrado", Toast.LENGTH_SHORT).show();
        }
    }

    private void shareList(int ID_USUARIO, int ID_LISTA) {
        presenter.shareList(ID_LISTA, ID_USUARIO, mAuth.getCurrentUser());
        finish();
    }

}
