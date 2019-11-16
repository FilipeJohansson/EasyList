package com.megadev.easylist.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.megadev.easylist.R;
import com.megadev.easylist.activity.editor.MainPresenter;
import com.megadev.easylist.activity.editor.MainView;
import com.megadev.easylist.model.Lista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView {

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    private TextView toolbar;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;

    MainPresenter presenter;
    MainAdapter adapter;
    MainAdapter.ItemClickListener itemClickListener;

    List<Lista> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        String UID_USUARIO = mAuth.getUid().trim();

        toolbar = findViewById(R.id.toolbar_title);
        refreshLayout = findViewById(R.id.refreshLayout);
        recyclerView = findViewById(R.id.recycleView);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    if (fab.isShown()) {
                        fab.hide();
                    }
                }
                else if (dy < 0) {
                    if (!fab.isShown()) {
                        fab.show();
                    }
                }
            }
        });

        refreshLayout.setColorSchemeResources(R.color.colorPrimaryDark);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, NewListActivity.class);
            startActivity(i);
        });

        presenter = new MainPresenter(this);
        presenter.getData(UID_USUARIO);

        refreshLayout.setOnRefreshListener(
                () -> presenter.getData(UID_USUARIO)
        );

        itemClickListener = ((view, position) -> {
            Intent intent = new Intent(this, MainListActivity.class);

            int sessionId = lista.get(position).getID_LISTA();
            String nmeLista = lista.get(position).getNME_LISTA();

            intent.putExtra("EXTRA_SESSION_ID", sessionId);
            intent.putExtra("EXTRA_LIST_NAME", nmeLista);
            startActivity(intent);

        });

        toolbar.setOnClickListener(view -> Logout());

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

        fab.show();

        String UID_USUARIO = mAuth.getUid().trim();
        presenter = new MainPresenter(this);
        presenter.getData(UID_USUARIO);

        refreshLayout.setOnRefreshListener(
                () -> presenter.getData(UID_USUARIO)
        );

    }

    private void updateUI(FirebaseUser currentUser) {

        if (currentUser == null) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);

        }

    }

    @Override
    public void showLoading() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onGetResult(List<Lista> listas) {
        adapter = new MainAdapter(this, listas, itemClickListener);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        lista = listas;
    }

    @Override
    public void onErrorLoading(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    void Logout() {
        FirebaseAuth.getInstance().signOut();
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this,
                        task -> updateUI(null));

    }

}