package com.ai.acompanha.acompanhaai.ui.main;

import android.Manifest;
import android.animation.Animator;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import com.ai.acompanha.acompanhaai.R;
import com.ai.acompanha.acompanhaai.data.shared.SharedUtils;
import com.ai.acompanha.acompanhaai.service.ProcessImageService;
import com.ai.acompanha.acompanhaai.ui.dialog.ConsumoInicialDialog;
import com.ai.acompanha.acompanhaai.ui.dialog.FecharDialog;
import com.ai.acompanha.acompanhaai.ui.login.LoginActivity;
import com.ai.acompanha.acompanhaai.ui.main.camera.CameraActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.ai.acompanha.acompanhaai.ui.main.gallery.GalleryFragment;
import com.ai.acompanha.acompanhaai.ui.main.home.HomeFragment;
import com.ai.acompanha.acompanhaai.ui.main.slideshow.SlideshowFragment;
import com.ai.acompanha.acompanhaai.ui.main.tools.ToolsFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.DialogFragment;

import android.view.Menu;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private FloatingActionButton fab;
    private Button btnFechar;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int CAMERA_REQUEST_CODE = 100;

    private ProcessImageService imageService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = findViewById(R.id.fab);
        btnFechar = findViewById(R.id.btnFechar);

        imageService = imageService.getInstance();

        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                callCameraActivity();
            }
        });

        btnFechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment fecharDialog = new FecharDialog();
                fecharDialog.show(getSupportFragmentManager(), "FecharDialogFragment");

            }
        });

//        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem menuItem) {
//                if(menuItem.getItemId() == R.id.action_settings){
//                    Toast.makeText(getBaseContext(), "TESTE", Toast.LENGTH_SHORT).show();
//                }
//                return false;
//            }
//        });

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                switch (menuItem.getItemId()) {
                    case (R.id.nav_gallery):
                        fragment = new GalleryFragment();
                        showButtons(false);
                        toolbar.setTitle(R.string.histo);
                        break;
                    case (R.id.nav_slideshow):
                        fragment = new SlideshowFragment();
                        showButtons(false);
                        toolbar.setTitle(R.string.prev);
                        break;
                    case (R.id.nav_tools):
                        fragment = new ToolsFragment();
                        showButtons(false);
                        toolbar.setTitle(R.string.config);
                        break;
                    default:
                        fragment = new HomeFragment();
                        showButtons(true);
                        toolbar.setTitle(R.string.home);
                        break;
                }

                FragmentManager fM = getSupportFragmentManager();
                fM.beginTransaction().replace(R.id.content_main, fragment).commit();
                drawer.closeDrawers();
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_REQUEST_CODE) {

            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "É preciso permitir o uso da camera para continuar.", Toast.LENGTH_LONG).show();
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            imageService.process(photo, this);
        }
    }

    private void callCameraActivity() {
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.action_settings){
            SharedUtils.setLogado(getApplicationContext(), false);
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();

        }
        return super.onOptionsItemSelected(item);

    }

    private void showButtons(boolean visible) {
        if (visible) {
            btnFechar.setVisibility(View.VISIBLE);
            fab.show();
        } else {
            btnFechar.setVisibility(View.GONE);
            fab.hide();
        }
    }
}