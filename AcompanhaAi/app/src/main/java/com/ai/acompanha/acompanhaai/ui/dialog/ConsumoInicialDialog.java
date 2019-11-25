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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ConsumoInicialDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        final View content =  inflater.inflate(R.layout.dialog_consumo_inicial, null);
        builder.setView(content);

        final EditText consumoInicial = content.findViewById(R.id.txt_consumo_inicial);
        builder.setTitle(R.string.con_inic);
        builder.setPositiveButton(R.string.salvar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedUtils.setConsumoAnterior(getContext(),Integer.parseInt(consumoInicial.getText().toString()));
            }
        });

        builder.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getActivity(), R.string.msg_cancel_inicial,
                        Toast.LENGTH_SHORT).show();
            }
        });

        return builder.create();
    }
}
