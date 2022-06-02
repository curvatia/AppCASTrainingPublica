package com.castraining.app_castraining_publica.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.castraining.app_castraining_publica.R;
import com.castraining.app_castraining_publica.databinding.ActivityPubDetallesCursoBinding;
import com.castraining.app_castraining_publica.databinding.CardviewPubDetallesBinding;
import com.castraining.app_castraining_publica.model.RcyViewDatosConvocatoria;
import com.castraining.app_castraining_publica.view.PubDetallesCurso;

import java.util.List;

public class ConvocatoriaAdapter extends RecyclerView.Adapter<ConvocatoriaAdapter.ConvocatoriaAdapterHolder> {

    private List<RcyViewDatosConvocatoria> datosConvocatoria;

    public ConvocatoriaAdapter (List<RcyViewDatosConvocatoria> datosConvocatoria){
        this.datosConvocatoria = datosConvocatoria;
    }

    @NonNull
    @Override
    public ConvocatoriaAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ConvocatoriaAdapterHolder(CardviewPubDetallesBinding
                .inflate(LayoutInflater.from(parent.getContext()),parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ConvocatoriaAdapterHolder holder, int position) {
        RcyViewDatosConvocatoria datoString = datosConvocatoria.get(position);
        String tit = datoString.getTitle();
        int id = datoString.getId();
        holder.binding.txtTituloConvocatoria.setText(tit);
        holder.binding.imgFechaConvocatoria.setImageResource(R.drawable.ic_launcher);
        holder.binding.cvConvocatoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), PubDetallesCurso.class);
                intent.putExtra("id", id);
                intent.putExtra("title", tit);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datosConvocatoria.size();
    }

    public class ConvocatoriaAdapterHolder extends RecyclerView.ViewHolder{

        CardviewPubDetallesBinding binding;

        public ConvocatoriaAdapterHolder(@NonNull CardviewPubDetallesBinding b) {
            super(b.getRoot());
            binding = b;
        }
    }

}
