package com.megadev.easylist.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.megadev.easylist.R;
import com.megadev.easylist.activity.editor.MainListPresenter;
import com.megadev.easylist.activity.editor.MainListView;
import com.megadev.easylist.model.Item;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;
import java.util.Objects;

public class MainListActivity extends AppCompatActivity implements MainListView {

    private FirebaseAuth mAuth;

    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private ImageView backList;
    private ImageView shareList;
    private ImageView sharing;

    private int sessionID;

    MainListPresenter presenter;
    MainListAdapter adapter;
    MainListAdapter.ItemClickListener itemClickListener;

    List<Item> item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        mAuth = FirebaseAuth.getInstance();

        sessionID = Objects.requireNonNull(getIntent().getExtras()).getInt("EXTRA_SESSION_ID");
        String nmeLista = getIntent().getExtras().getString("EXTRA_LIST_NAME");
        boolean isSharing = getIntent().getExtras().getBoolean("EXTRA_SHARING");

        TextView toolbar_list_title = findViewById(R.id.toolbar_list_title);
        refreshLayout = findViewById(R.id.refreshLayout);
        recyclerView = findViewById(R.id.recycleView);

        toolbar_list_title.setText(nmeLista);

        refreshLayout.setColorSchemeResources(R.color.colorPrimaryDark);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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

        backList = findViewById(R.id.backList);
        backList.setOnClickListener(view -> {
            Intent intent = new Intent(MainListActivity.this, MainActivity.class);
            startActivity(intent);
        });

        shareList = findViewById(R.id.shareList);
        shareList.setOnClickListener(view -> {
            Intent intent = new Intent(this, shareActivity.class);
            intent.putExtra("EXTRA_SESSION_ID", sessionID);
            startActivity(intent);
        });

        sharing = findViewById(R.id.sharing);
        if (isSharing) {
            shareList.setVisibility(View.INVISIBLE);
            sharing.setVisibility(View.VISIBLE);
        }

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainListActivity.this, NewProductActivity.class);
            intent.putExtra("EXTRA_SESSION_ID", sessionID);
            startActivity(intent);
        });

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        itemClickListener = new MainListAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int ID_ITEM = item.get(position).getID_ITEM();
                int STA_CHECK = item.get(position).getSTA_CHECK();

                if (STA_CHECK == 1)
                    STA_CHECK = 0;
                else
                    STA_CHECK = 1;

                presenter.updateItem(ID_ITEM, STA_CHECK);

                Handler handler = new Handler();
                handler.postDelayed(() -> refreshPage(sessionID), 300);
            }

            @Override
            public void onLongItemClick(View view, int position) {
                alertDialog.setTitle("Deletar lista");
                alertDialog.setMessage("Você deseja deletar '" + item.get(position).getNME_PRODUTO() + "'?");
                alertDialog.setPositiveButton("Sim", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    presenter.deleteItem(item.get(position).getID_ITEM());

                    Handler handler = new Handler();
                    handler.postDelayed(() -> refreshPage(sessionID), 300);
                });
                alertDialog.setNegativeButton("Não",
                        (dialogInterface, i) -> dialogInterface.dismiss());
                alertDialog.show();
            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

        fab.show();

        int sessionID = Objects.requireNonNull(getIntent().getExtras()).getInt("EXTRA_SESSION_ID");

        refreshPage(sessionID);

    }

    @Override
    protected void onResume() {
        super.onResume();

        updateUI(mAuth.getCurrentUser());
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