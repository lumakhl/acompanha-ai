package com.ai.acompanha.acompanhaai.ui.main.gallery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ai.acompanha.acompanhaai.R;
import com.ai.acompanha.acompanhaai.data.model.Registro;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class RegistroAdapter extends RecyclerView.Adapter<RegistroAdapter.RegistroViewHolder> {

    List<Registro> registros;
    public static class RegistroViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView consumo;
        TextView valorEstimado;
        TextView valorReal;
        TextView periodo;

        public RegistroViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cv_registros);
            consumo = itemView.findViewById(R.id.txtPeriodoCV);
            valorEstimado = itemView.findViewById(R.id.txtValorEstimadoCV);
            valorReal = itemView.findViewById(R.id.txtValorRealCV);
            periodo = itemView.findViewById(R.id.txtPeriodoCV);
        }
    }


    public RegistroAdapter(List<Registro> registros) {
        this.registros = registros;
    }


    @NonNull
    @Override
    public RegistroAdapter.RegistroViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_historico, viewGroup, false);
        RegistroViewHolder rvh = new RegistroViewHolder(v);
        return rvh;

    }

    @Override
    public void onBindViewHolder(@NonNull RegistroViewHolder holder, int i) {
        holder.consumo.setText(String.valueOf(registros.get(i).getConsumo()));
        holder.valorReal.setText(String.valueOf(registros.get(i).getValorReal()));
        holder.valorEstimado.setText(String.valueOf(registros.get(i).getValorPrevisto()));
        holder.periodo.setText(registros.get(i).getPeriodo().toString());
    }


    @Override
    public int getItemCount() {
        return registros.size();
    }
}
