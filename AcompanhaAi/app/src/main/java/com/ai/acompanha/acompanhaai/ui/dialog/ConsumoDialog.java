package com.ai.acompanha.acompanhaai.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ai.acompanha.acompanhaai.R;
import com.ai.acompanha.acompanhaai.data.shared.SharedUtils;
import com.ai.acompanha.acompanhaai.service.BlumenauCalculoService;
import com.ai.acompanha.acompanhaai.service.ProcessImageService;
import com.ai.acompanha.acompanhaai.service.ReloadListner;
import com.ai.acompanha.acompanhaai.ui.main.home.HomeFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class ConsumoDialog extends DialogFragment {

    private static ReloadListner reloadListner;
    private String valorRelogio;

    public ConsumoDialog(){

    }

    public ConsumoDialog(String valorRelogio) {
        this.valorRelogio = valorRelogio;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final BlumenauCalculoService calculaService = BlumenauCalculoService.getInstance();

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        final View content = inflater.inflate(R.layout.dialog_consumo, null);
        builder.setView(content);

        final EditText txtConsumo = content.findViewById(R.id.txt_consumo_manual);

        if(valorRelogio != null) {
            txtConsumo.setText(valorRelogio);
        }
        builder.setTitle(R.string.fragment_consumo);
        builder.setPositiveButton(R.string.calcular, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (!txtConsumo.getText().toString().isEmpty()) {

                    try {
                        int consumo = Integer.parseInt(txtConsumo.getText().toString());
                        SharedUtils.setValor(getContext(), SharedUtils.getValor(getContext()) + calculaService.calcularValor(SharedUtils.getConsumoAnterior(getContext()), consumo));
                        SharedUtils.setConsumo(getContext(), SharedUtils.getConsumo(getContext()) + calculaService.calculaConsumo(SharedUtils.getConsumoAnterior(getContext()), consumo));
                        SharedUtils.setConsumoAnterior(getContext(), consumo);
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Por favor informar apenas números", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getContext(), R.string.msg_consumo_vazio, Toast.LENGTH_SHORT).show();
                }

                if (reloadListner != null) {
                    reloadListner.onReload();
                }

            }
        });

        builder.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getContext(), R.string.msg_consumo_vazio, Toast.LENGTH_SHORT).show();

                if (reloadListner != null) {
                    reloadListner.onReload();
                }
            }
        });

        return builder.create();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        reload();
    }

    private void reload() {
        Fragment frg = new HomeFragment();

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.detach(frg);
        fragmentTransaction.attach(frg);
        fragmentTransaction.commit();
    }

    public void setOnReloadListner(ReloadListner listner) {
        reloadListner = listner;
    }

}
