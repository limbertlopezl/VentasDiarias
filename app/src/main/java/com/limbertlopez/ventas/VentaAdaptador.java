package com.limbertlopez.ventas;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limbert on 06/10/2017.
 */

public class VentaAdaptador extends RecyclerView.Adapter<VentaAdaptador.ViewHolder> {

    private OnItemClickListener listener;

    public OnItemClickListener getOnItemClickListener() {
        return listener;
    }


    public interface OnItemClickListener {
        void onItemClick(ViewHolder item, int position);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lista, viewGroup, false);
        return new ViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Venta item = Venta.VENTAS.get(position);

        String cod = item.getCod();
        String monto = item.getMonto();
        String fecha = item.getFecha();

        viewHolder.monto.setText(monto + " Bs");
        viewHolder.cod.setText(cod);
        viewHolder.fecha.setText(fecha);
    }

    @Override
    public int getItemCount() {
        return Venta.VENTAS.size();
    }

    @Override
    public long getItemId(int position) {
        int o= Integer.parseInt(Venta.VENTAS.get(position).getCod());
        return o;
    }

    //------------------ Clase auxiliar de Adaptador -------------------------------------
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView monto, cod, fecha;


        private VentaAdaptador padre = null;

        public ViewHolder(View v, VentaAdaptador padre) {
            super(v);

            v.setOnClickListener(this);
            this.padre = padre;
            monto = (TextView) v.findViewById(R.id.txtMonto);
            cod = (TextView) v.findViewById(R.id.txtCod);
            fecha = (TextView) v.findViewById(R.id.txtFecha);

        }

        @Override
        public void onClick(View v) {
            final OnItemClickListener listener = padre.getOnItemClickListener();
            if (listener != null) {
                listener.onItemClick(this, getAdapterPosition());
            }
        }
    }

}
