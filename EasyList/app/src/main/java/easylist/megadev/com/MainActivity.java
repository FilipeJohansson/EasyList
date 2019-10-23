package easylist.megadev.com;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    final EditText taskEditText = new EditText(this);
    private LoginButton loginButton;
    private TextView txtName, txtEmail;
    private CallbackManager callbackManager;

    AlertDialog dialog;
        public MainActivity() {
            dialog = new AlertDialog.Builder(this)
                    .setTitle("Adicione uma nova lista")
                    .setMessage("Nome da Lista:")
                    .setView(taskEditText)
                    .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String task = String.valueOf(taskEditText.getText());
                            Log.d(TAG, "Task to Adicionar: " + task);
                        }
                    })
                    .setNegativeButton("Cancelar", null)
                    .create();
                    dialog.show();

            loginButton = findViewById(R.id.login_button);
            txtName = findViewById(R.id.profile_name);
            txtEmail = findViewById((R.id.profile_email));
            callbackManager = CallbackManager.Factory.create();


            callbackManager = CallbackManager.Factory.create();

            LoginManager.getInstance().registerCallback(callbackManager,
                    new FacebookCallback<LoginResult>() {
                        @Override
                        public void onSuccess(LoginResult loginResult) {
                            // App code
                        }

                        @Override
                        public void onCancel() {
                            // App code
                        }

                        @Override
                        public void onError(FacebookException exception) {
                            // App code
                        }
                    });


        }

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


            loginButton = (LoginButton) findViewById(R.id.login_button);
            loginButton.setReadPermissions("email");
            // If using in a fragment
            //loginButton.setFragment(this);

            // Callback registration
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    // App code
                }

                @Override
                public void onCancel() {
                    // App code
                }

                @Override
                public void onError(FacebookException exception) {
                    // App code
                }
            });


            AccessToken accessToken = AccessToken.getCurrentAccessToken();
            boolean isLoggedIn = accessToken != null && !accessToken.isExpired();



            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
        }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_task) {
            Log.d(TAG, "Adicionar nova lista");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
