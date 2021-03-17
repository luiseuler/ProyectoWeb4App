package com.lagg.enfriamiento;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolder> {
    String[][] datos;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvId, tvFecha, tvTemp;
        public ViewHolder(@NonNull View v) {
            super(v);
            tvId = v.findViewById(R.id.tvId);
            tvTemp = v.findViewById(R.id.tvTemp);
            tvFecha = v.findViewById(R.id.tvFecha);
        }
    }

    public Adaptador(String[][] datos){
        this.datos = datos;
    }

    @NonNull
    @Override
    public Adaptador.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_datos, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptador.ViewHolder holder, int position) {
        holder.tvId.setText(datos[position][0]);
        holder.tvTemp.setText(datos[position][1]);
        holder.tvFecha.setText(datos[position][2]);
    }

    @Override
    public int getItemCount() {
        return datos.length;
    }


}
