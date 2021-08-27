package com.ncrdesarrollo.apirestcrudnotas.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ncrdesarrollo.apirestcrudnotas.R;
import com.ncrdesarrollo.apirestcrudnotas.adapters.NotasAdapter;
import com.ncrdesarrollo.apirestcrudnotas.models.Notas;
import com.ncrdesarrollo.apirestcrudnotas.presenter.INotasPresenter;
import com.ncrdesarrollo.apirestcrudnotas.presenter.NotasPresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListaNotasFragment extends Fragment implements INotasPresenter.View {

    private RecyclerView recyclerViewNotas;
    private FloatingActionButton floatingAdd;
    private ProgressBar progressBar;
    private NotasPresenter presenter;


    public ListaNotasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_lista_notas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewNotas = view.findViewById(R.id.recyclerNotas);
        progressBar = view.findViewById(R.id.progressBar);
        floatingAdd = view.findViewById(R.id.floatingAdd);
        floatingAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.registrarNotaFragment);
            }
        });

        presenter = new NotasPresenter(getActivity(), this);
        presenter.listarDatos();


    }

    @Override
    public void msgSucces(String mensaje) {
        Toast.makeText(getActivity(), mensaje, Toast.LENGTH_SHORT).show();
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

    }

    @Override
    public void putDataInRecycler(List<Notas> notasList) {
        NotasAdapter notasAdapter = new NotasAdapter(getActivity(), notasList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewNotas.setLayoutManager(linearLayoutManager);
        recyclerViewNotas.setAdapter(notasAdapter);
    }
}