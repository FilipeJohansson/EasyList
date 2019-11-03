package com.megadev.easylist.activity;

import androidx.annotation.NonNull;
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
import com.megadev.easylist.api.ApiClient;
import com.megadev.easylist.api.ApiInterface;
import com.megadev.easylist.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    static final int GOOGLE_SIGN = 779;
    FirebaseAuth mAuth;
    Button btn_login;
    ProgressBar progressBar;
    GoogleSignInClient mGoogleSignInClient;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

                        saveUser(UID_USUARIO, user);

                    } else {
                        progressBar.setVisibility(View.INVISIBLE);
                        Log.w("TAG", "signin failure", task.getException());

                        Toast.makeText(this, "Erro ao entrar", Toast.LENGTH_SHORT).show();
                        updateUI(null);

                    }

                });

    }

    private void saveUser(final String UID_USUARIO, final FirebaseUser user) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<User> call = apiInterface.saveUser(UID_USUARIO);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {

                if (response.isSuccessful() && response.body() != null) {
                    Boolean success = response.body().getSuccess();

                    if (success) {
                        updateUI(user);

                    } else {
                        user.delete();

                        Toast.makeText(LoginActivity.this,
                                response.body().getMessage(),
                                Toast.LENGTH_SHORT).show();
                        // if error, still in this activity

                    }

                }

            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {

                user.delete();

                Toast.makeText(LoginActivity.this,
                        t.getLocalizedMessage(),
                        Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);

        }

    }

}
