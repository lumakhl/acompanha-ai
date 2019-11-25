package com.ai.acompanha.acompanhaai.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ai.acompanha.acompanhaai.R;
import com.ai.acompanha.acompanhaai.data.RegistroRepository;
import com.ai.acompanha.acompanhaai.data.model.Registro;
import com.ai.acompanha.acompanhaai.data.shared.SharedUtils;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class FecharDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        final View content =  inflater.inflate(R.layout.dialog_fechar, null);
        builder.setView(content);

        builder.setTitle(R.string.fechar_titulo);

        TextView txtConsumo = content.findViewById(R.id.txtConsumoCV);
        TextView txtValorPrevisto = content.findViewById(R.id.txtEstimado);
        final EditText txtValorConta = content.findViewById(R.id.txtConta);
        final EditText date = content.findViewById(R.id.txtPeriodoCV);

        txtConsumo.setText(SharedUtils.getConsumo(getContext())+"m³");
        txtValorPrevisto.setText(String.format("R$%.02f",SharedUtils.getValor(getContext())));

        TextWatcher tw = new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                    String cleanC = current.replaceAll("[^\\d.]|\\.", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8) {
                        clean = clean + ddmmyyyy.substring(clean.length());
                    } else {
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day = Integer.parseInt(clean.substring(0, 2));
                        int mon = Integer.parseInt(clean.substring(2, 4));
                        int year = Integer.parseInt(clean.substring(4, 8));

                        mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                        cal.set(Calendar.MONTH, mon - 1);
                        year = (year < 1900) ? 1900 : (year > 2100) ? 2100 : year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE)) ? cal.getActualMaximum(Calendar.DATE) : day;
                        clean = String.format("%02d%02d%02d", day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    date.setText(current);
                    date.setSelection(sel < current.length() ? sel : current.length());
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        date.addTextChangedListener(tw);

        builder.setPositiveButton(R.string.salvar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                RegistroRepository registroRepository = new RegistroRepository(getContext());

                Registro registro = new Registro();
                registro.setConsumo(SharedUtils.getConsumo(getContext()));
                registro.setValorPrevisto(SharedUtils.getValor(getContext()));
                registro.setValorReal(Double.parseDouble(txtValorConta.getText().toString()));
                registro.setPeriodo(date.getText().toString());

                registroRepository.insert(registro);

                SharedUtils.setConsumo(getContext(),0);
                SharedUtils.setValor(getContext(), 0f);
            }
        });

        builder.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        return builder.create();
    }
}
