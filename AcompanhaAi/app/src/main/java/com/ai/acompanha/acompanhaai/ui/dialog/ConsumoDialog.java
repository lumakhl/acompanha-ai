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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ConsumoDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final BlumenauCalculoService calculaService = BlumenauCalculoService.getInstance();

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        final View content = inflater.inflate(R.layout.dialog_consumo, null);
        builder.setView(content);

        final EditText txtConsumo = content.findViewById(R.id.txt_consumo_manual);
        builder.setTitle(R.string.fragment_consumo);
        builder.setPositiveButton(R.string.calcular, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (!txtConsumo.getText().toString().isEmpty()) {

                    int consumo = Integer.parseInt(txtConsumo.getText().toString());
                    SharedUtils.setValor(getContext(), SharedUtils.getValor(getContext()) + calculaService.calcularValor(SharedUtils.getConsumoAnterior(getContext()), consumo));
                    SharedUtils.setConsumo(getContext(), SharedUtils.getConsumo(getContext()) + calculaService.calculaConsumo(SharedUtils.getConsumoAnterior(getContext()), consumo));
                    SharedUtils.setConsumoAnterior(getContext(), consumo);

                } else {
                    Toast.makeText(getContext(), R.string.msg_consumo_vazio, Toast.LENGTH_SHORT).show();
                }


            }
        });

        builder.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getContext(), R.string.msg_consumo_vazio, Toast.LENGTH_SHORT).show();
            }
        });

        return builder.create();
    }
}
