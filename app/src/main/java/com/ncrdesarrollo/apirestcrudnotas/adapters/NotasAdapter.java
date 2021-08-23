package com.ncrdesarrollo.apirestcrudnotas.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ncrdesarrollo.apirestcrudnotas.R;
import com.ncrdesarrollo.apirestcrudnotas.models.Notas;

import java.util.List;

public class NotasAdapter extends RecyclerView.Adapter<NotasAdapter.viewHolder> {

    Context context;
    List<Notas> notasList;

    public NotasAdapter(Context context, List<Notas> notasList) {
        this.context = context;
        this.notasList = notasList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.item_nota, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, final int position) {
        holder.txtTitulo.setText(notasList.get(position).getTitulo());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("id", notasList.get(position).getId());
                Navigation.findNavController(view).navigate(R.id.detalleNotaFragment, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notasList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView txtTitulo;
        View view;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitulo = itemView.findViewById(R.id.txtTitulo);
            view = itemView;
        }
    }
}
