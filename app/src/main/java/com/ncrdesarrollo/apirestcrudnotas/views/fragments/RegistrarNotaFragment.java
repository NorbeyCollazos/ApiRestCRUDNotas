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

public class RegistrarNotaFragment extends Fragment implements INotasPresenter.View {

    private TextInputLayout etTitulo;
    private TextInputLayout etNota;
    private Button btnRegistrar;
    private ProgressBar progressBar;
    private NotasPresenter presenter;

    public RegistrarNotaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        presenter = new NotasPresenter(getActivity(), this);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registrar_nota, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etTitulo = view.findViewById(R.id.etTitulo);
        etNota = view.findViewById(R.id.etNota);
        progressBar = view.findViewById(R.id.progressBar2);
        btnRegistrar = view.findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.registrarDatos(
                        etTitulo.getEditText().getText().toString(),
                        etNota.getEditText().getText().toString()
                );
            }
        });
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

    }

    @Override
    public void putDataInRecycler(List<Notas> notasList) {

    }
}