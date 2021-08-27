package com.ncrdesarrollo.apirestcrudnotas.views.fragments;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ncrdesarrollo.apirestcrudnotas.R;
import com.ncrdesarrollo.apirestcrudnotas.models.Notas;
import com.ncrdesarrollo.apirestcrudnotas.presenter.INotasPresenter;
import com.ncrdesarrollo.apirestcrudnotas.presenter.NotasPresenter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetalleNotaFragment extends Fragment implements INotasPresenter.View {

    private TextView txtTitulo;
    private TextView txtNota;
    private Button btnEditar;
    private Button btnEliminar;
    private ImageView imgFoto;
    private ProgressBar progressBar;
    private NotasPresenter presenter;

    public DetalleNotaFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detalle_nota, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String id = getArguments().getString("id");
        presenter = new NotasPresenter(getActivity(),this);
        txtTitulo = view.findViewById(R.id.txtTitulo);
        txtNota = view.findViewById(R.id.txtNota);
        imgFoto = view.findViewById(R.id.imgFoto);
        progressBar = view.findViewById(R.id.progressBar3);
        btnEditar = view.findViewById(R.id.btnEditar);
        btnEliminar = view.findViewById(R.id.btnEliminar);

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                Navigation.findNavController(view).navigate(R.id.editarNotaFragment, bundle);
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertEliminar(id);
            }
        });

        presenter.verDatos(id);



    }

    private void alertEliminar(String id) {
        final AlertDialog.Builder pruebaaler = new AlertDialog.Builder(getActivity());
        pruebaaler.setMessage("¿Seguro desea eliminar este registro?");
        pruebaaler.setTitle("Eliminar registro");
        pruebaaler.setIcon(android.R.drawable.ic_menu_delete);

        pruebaaler.setPositiveButton("Si, Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                presenter.eliminarDatos(id);
            }
        });
        pruebaaler.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog dialog = pruebaaler.create();
        dialog.show();
    }

    @Override
    public void msgSucces(String mensaje) {
        Toast.makeText(getActivity(), mensaje, Toast.LENGTH_SHORT).show();
        getActivity().onBackPressed();
    }

    @Override
    public void msgError(String mensaje) {
        Toast.makeText(getActivity(), mensaje, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void mostrarDatos(String titulo, String nota, String imagen) {
        txtTitulo.setText(titulo);
        txtNota.setText(nota);
        if (!imagen.isEmpty()) {
            Picasso.with(getActivity()).load(imagen).into(imgFoto);
        }
    }

    @Override
    public void putDataInRecycler(List<Notas> notasList) {

    }
}