package com.megadev.easylist.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements MainView {

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private ImageView logout;

    private String UID_USUARIO;

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

        String UID_USUARIO = Objects.requireNonNull(mAuth.getUid()).trim();

        logout = findViewById(R.id.logout);
        refreshLayout = findViewById(R.id.refreshLayout);
        recyclerView = findViewById(R.id.recycleView);

        refreshLayout.setColorSchemeResources(R.color.colorPrimaryDark);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        presenter = new MainPresenter(this);
        presenter.getData(UID_USUARIO);

        refreshLayout.setOnRefreshListener(
                () -> presenter.getData(UID_USUARIO)
        );

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

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, NewListActivity.class);
            startActivity(i);
        });

        Intent intent = new Intent(this, MainListActivity.class);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this, R.style.MyDialogTheme);

        itemClickListener = new MainAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int sessionId = lista.get(position).getID_LISTA();
                String nmeLista = lista.get(position).getNME_LISTA();

                intent.putExtra("EXTRA_SESSION_ID", sessionId);
                intent.putExtra("EXTRA_LIST_NAME", nmeLista);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {
                alertDialog.setTitle("Deletar lista");
                alertDialog.setMessage("Você deseja deletar '" + lista.get(position).getNME_LISTA() + "'?");
                alertDialog.setPositiveButton("Sim", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    presenter.deleteLista(lista.get(position).getID_LISTA());

                    Handler handler = new Handler();
                    handler.postDelayed(() -> refreshPage(UID_USUARIO), 300);
                });
                alertDialog.setNegativeButton("Não",
                        (dialogInterface, i) -> dialogInterface.dismiss());
                alertDialog.show();
            }
        };

        logout.setOnClickListener(view -> Logout());

    }

    @Override
    protected void onStart() {
        super.onStart();

        updateUI(mAuth.getCurrentUser());

        fab.show();

        UID_USUARIO = Objects.requireNonNull(mAuth.getUid()).trim();
        refreshPage(UID_USUARIO);

    }

    @Override
    protected void onResume() {
        super.onResume();

        updateUI(mAuth.getCurrentUser());
        refreshPage(UID_USUARIO);
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

    @Override
    public void onAddSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void refreshPage(String UID_USUARIO) {

        presenter = new MainPresenter(this);
        presenter.getData(UID_USUARIO);

        refreshLayout.setOnRefreshListener(
                () -> presenter.getData(UID_USUARIO)
        );

    }

    void Logout() {
        FirebaseAuth.getInstance().signOut();
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this,
                        task -> updateUI(null));

    }

}