package com.casasolarctpi.prosolar2.models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.casasolarctpi.prosolar2.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class AdapterListInicio extends RecyclerView.Adapter<AdapterListInicio.Holder> {

    private List<String> listaCorriente = new ArrayList<>();
    private List<String> listaVoltaje = new ArrayList<>();

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inicio, parent,false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.connectData(listaCorriente.get(position), listaVoltaje.get(position));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView txtCorriente;
        TextView txtVoltaje;
        private Holder(@NonNull View itemView) {
            super(itemView);
            txtCorriente = itemView.findViewById(R.id.txtCorriente);
            txtVoltaje = itemView.findViewById(R.id.txtVoltaje);

        }

        private void connectData(String s, String s1) {
            txtCorriente.setText(s);
            txtVoltaje.setText(s1);
        }
    }
}
