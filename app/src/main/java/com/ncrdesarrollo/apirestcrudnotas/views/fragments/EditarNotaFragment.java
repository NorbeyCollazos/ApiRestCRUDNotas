package com.ncrdesarrollo.apirestcrudnotas.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.ncrdesarrollo.apirestcrudnotas.R;
import com.ncrdesarrollo.apirestcrudnotas.models.Notas;
import com.ncrdesarrollo.apirestcrudnotas.presenter.INotasPresenter;
import com.ncrdesarrollo.apirestcrudnotas.presenter.NotasPresenter;

import java.util.List;

public class EditarNotaFragment extends Fragment implements INotasPresenter.View {

    private TextInputLayout etTitulo;
    private TextInputLayout etNota;
    private Button btnEditar;
    private ProgressBar progressBar;
    private NotasPresenter presenter;

    public EditarNotaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_editar_nota, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String id = getArguments().getString("id");
        presenter = new NotasPresenter(getActivity(),this);
        etTitulo = view.findViewById(R.id.etTitulo);
        etNota = view.findViewById(R.id.etNota);
        progressBar = view.findViewById(R.id.progressBar4);
        btnEditar = view.findViewById(R.id.btnEditar);

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.modificarDatos(
                        id,
                        etTitulo.getEditText().getText().toString(),
                        etNota.getEditText().getText().toString()
                );
            }
        });

        presenter.verDatos(id);

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
    public void mostrarDatos(String titulo, String nota) {
        etTitulo.getEditText().setText(titulo);
        etNota.getEditText().setText(nota);

    }

    @Override
    public void putDataInRecycler(List<Notas> notasList) {

    }
}