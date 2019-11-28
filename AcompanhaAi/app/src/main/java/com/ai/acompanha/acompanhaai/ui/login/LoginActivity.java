package com.ai.acompanha.acompanhaai.ui.login;


import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ai.acompanha.acompanhaai.R;
import com.ai.acompanha.acompanhaai.data.shared.SharedUtils;
import com.ai.acompanha.acompanhaai.ui.main.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getName();

    private LoginViewModel loginViewModel;

    private FirebaseAuth mAuth;

    private ProgressBar loadingProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.btn_login);
        loadingProgressBar = findViewById(R.id.progressBar);
        final TextView registrar = findViewById(R.id.txtRegistrar);


        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                // login(usernameEditText.getText().toString(),
                //         passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loadingProgressBar.setVisibility(View.VISIBLE);
                    login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                criarUsuario(usernameEditText.getText().toString(), passwordEditText.getText().toString());


            }
        });

        if(SharedUtils.getLogado(this)) {
            updateUiWithUser();
        }
    }

    private void criarUsuario(String username, String password) {
        if (!username.isEmpty() && !password.isEmpty()) {
            mAuth.createUserWithEmailAndPassword(username,
                    password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in sucess
                        loadingProgressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(LoginActivity.this, "Usuário criado com sucesso", Toast.LENGTH_SHORT).show();
                        updateUiWithUser();


                    } else {
                        loadingProgressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(LoginActivity.this, "Não foi possível criar um novo usuário",
                                Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
        loadingProgressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(this, "É necessário preencher todos os campos para fazer cadastro", Toast.LENGTH_SHORT).show();
    }

    private void login(String username, String password) {
        if (!username.isEmpty() && !password.isEmpty()) {
            mAuth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUiWithUser();
                            } else {
                                loadingProgressBar.setVisibility(View.INVISIBLE);
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                SharedUtils.setLogado(getBaseContext(), false);
                                Toast.makeText(LoginActivity.this, "Usuário ou senha incorretos",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        } else {
            loadingProgressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "É necessário preencher todos os campos para fazer login", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loadingProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        //TODO fazer o upload do login
        // updateUiWithUser(currentUser);
    }

    private void updateUiWithUser() {

        // TODO : initiate successful logged in experience
        SharedUtils.setLogado(this, true);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }

}
