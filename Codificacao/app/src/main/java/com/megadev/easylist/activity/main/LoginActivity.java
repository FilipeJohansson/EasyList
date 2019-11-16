package com.megadev.easylist.activity.main;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.megadev.easylist.R;
import com.megadev.easylist.activity.editor.EditorPresenter;
import com.megadev.easylist.activity.editor.EditorView;

public class LoginActivity extends AppCompatActivity implements EditorView {

    static final int GOOGLE_SIGN = 779;
    FirebaseAuth mAuth;
    Button btn_login;
    ProgressBar progressBar;
    GoogleSignInClient mGoogleSignInClient;

    EditorPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter = new EditorPresenter(this);

        btn_login = findViewById(R.id.login);
        progressBar = findViewById(R.id.progress_circular);

        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        btn_login.setOnClickListener(v -> SignInGoogle());

        if (mAuth.getCurrentUser() != null) {
            FirebaseUser user = mAuth.getCurrentUser();
            updateUI(user);

        }

    }

    void SignInGoogle() {
        progressBar.setVisibility(View.VISIBLE);
        Intent intent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(intent, GOOGLE_SIGN);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_SIGN) {
            Task<GoogleSignInAccount> task = GoogleSignIn
                    .getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);

                if (account != null) firebaseAuthWithGoogle(account);

            } catch (ApiException e) {
                Log.w("TAG", "Google sign in failed", e);

            }

        }

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d("TAG", "firebaseAuthWithGoogle: " + account.getId());

        AuthCredential credential = GoogleAuthProvider
                .getCredential(account.getIdToken(), null);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Log.d("TAG", "signin success");

                        FirebaseUser user = mAuth.getCurrentUser();

                        String UID_USUARIO = mAuth.getUid().trim();

                        presenter.saveUser(UID_USUARIO, user);

                    } else {
                        progressBar.setVisibility(View.INVISIBLE);
                        Log.w("TAG", "signin failure", task.getException());

                        Toast.makeText(this, "Erro ao entrar", Toast.LENGTH_SHORT).show();
                        updateUI(null);

                    }

                });

    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);

        }

    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onAddSuccess(String message, FirebaseUser user) {
        updateUI(user);
    }

    @Override
    public void onAddError(String message, FirebaseUser user) {
        user.delete();

        Toast.makeText(LoginActivity.this,
                message,
                Toast.LENGTH_SHORT).show();
        // if error, still in this activity
    }

}
