package com.megadev.easylist.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.megadev.easylist.R;
import com.megadev.easylist.activity.editor.MainListPresenter;
import com.megadev.easylist.activity.editor.MainListView;
import com.megadev.easylist.model.Item;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

public class MainListActivity extends AppCompatActivity implements MainListView {

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private TextView toolbar_list_title;

    MainListPresenter presenter;
    MainListAdapter adapter;
    MainListAdapter.ItemClickListener itemClickListener;

    List<Item> item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        int sessionID = getIntent().getExtras().getInt("EXTRA_SESSION_ID");
        String nmeLista = getIntent().getExtras().getString("EXTRA_LIST_NAME");

        toolbar_list_title = findViewById(R.id.toolbar_list_title);
        refreshLayout = findViewById(R.id.refreshLayout);
        recyclerView = findViewById(R.id.recycleView);

        toolbar_list_title.setText(nmeLista);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainListActivity.this, NewProductActivity.class);
            intent.putExtra("EXTRA_SESSION_ID", sessionID);
            startActivity(intent);
        });

        itemClickListener = ((view, position) -> {
            int ID_ITEM = item.get(position).getID_ITEM();
            int STA_CHECK = item.get(position).getSTA_CHECK();

            if (STA_CHECK == 1)
                STA_CHECK = 0;
            else
                STA_CHECK = 1;

            presenter.updateItem(ID_ITEM, STA_CHECK);

            Handler handler = new Handler();
            handler.postDelayed(() -> refreshPage(sessionID), 100);

        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

        int sessionID = getIntent().getExtras().getInt("EXTRA_SESSION_ID");

        refreshPage(sessionID);

    }

    private void updateUI(FirebaseUser currentUser) {

        if (currentUser == null) {
            Intent intent = new Intent(MainListActivity.this, LoginActivity.class);
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
    public void onGetResult(List<Item> itens) {
        adapter = new MainListAdapter(this, itens, itemClickListener);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        item = itens;
    }

    @Override
    public void onErrorLoading(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddSuccess(String message) {

    }

    @Override
    public void onAddError(String message) {

    }

    private void refreshPage(int sessionID) {

        presenter = new MainListPresenter(this);
        presenter.getData(sessionID);

        refreshLayout.setOnRefreshListener(
                () -> presenter.getData(sessionID)
        );

    }

}