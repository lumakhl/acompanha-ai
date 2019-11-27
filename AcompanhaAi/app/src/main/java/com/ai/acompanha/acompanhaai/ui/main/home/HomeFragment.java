package com.ai.acompanha.acompanhaai.ui.main.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.ai.acompanha.acompanhaai.R;
import com.ai.acompanha.acompanhaai.data.shared.SharedUtils;
import com.ai.acompanha.acompanhaai.service.ReloadListner;
import com.ai.acompanha.acompanhaai.ui.dialog.ConsumoDialog;
import com.ai.acompanha.acompanhaai.ui.dialog.ConsumoInicialDialog;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private TextView txtConsumo;
    private TextView txtValor;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        txtConsumo = root.findViewById(R.id.txtConsumo);
        txtValor = root.findViewById(R.id.txtValorPrevisto);
        final TextView btnManual = root.findViewById(R.id.txtInserirManualmente);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        btnManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inflarDialogConsumo();
            }
        });

        txtConsumo.setText(SharedUtils.getConsumo(getContext()) + "m³");
        txtValor.setText(String.format("R$%.02f", SharedUtils.getValor(getContext())));

        if (SharedUtils.getConsumoAnterior(getContext()) == 0) {
            inflarDialogConsumoinicial();
        }

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        reload();
    }

    @Override
    public void onPause() {
        super.onPause();
        reload();
    }

    @Override
    public void onStart() {
        super.onStart();
        reload();
    }

    private void inflarDialogConsumoinicial() {
        DialogFragment consumoInicialDialog = new ConsumoInicialDialog();
        consumoInicialDialog.show(getActivity().getSupportFragmentManager(), "ConsumoInicialDialogFragment");
    }

    private void inflarDialogConsumo() {
        ConsumoDialog consumoDialog = new ConsumoDialog();
        consumoDialog.setOnReloadListner(new ReloadListner() {
            @Override
            public void onReload() {
                reload();
            }
        });
        consumoDialog.show(getActivity().getSupportFragmentManager(), "ConsumoDialogFragment");
    }


    private void reload() {
        txtConsumo.setText(SharedUtils.getConsumo(getContext()) + "m³");
        txtValor.setText(String.format("R$%.02f", SharedUtils.getValor(getContext())));
    }

}