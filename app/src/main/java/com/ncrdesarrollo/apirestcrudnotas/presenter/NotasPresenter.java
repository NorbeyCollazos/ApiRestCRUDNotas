package com.ncrdesarrollo.apirestcrudnotas.presenter;

import android.content.Context;
import android.util.Log;

import com.ncrdesarrollo.apirestcrudnotas.api.APIRest;
import com.ncrdesarrollo.apirestcrudnotas.api.AdaptadorRetrofit;
import com.ncrdesarrollo.apirestcrudnotas.models.Notas;

import java.util.ArrayList;

import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class NotasPresenter implements INotasPresenter.Presenter {

    Context context;
    INotasPresenter.View view;
    private List<Notas> notasList;
    Retrofit retrofit;
    APIRest api;

    public NotasPresenter(Context context, INotasPresenter.View view) {
        this.context = context;
        this.view = view;
        notasList = new ArrayList<>();
        retrofit = new AdaptadorRetrofit().getAdapter();
        api = retrofit.create(APIRest.class);
    }

    @Override
    public void listarDatos() {
        view.showProgress();
        Call<List<Notas>> call = api.obtenerNotas();
        call.enqueue(new Callback<List<Notas>>() {
            @Override
            public void onResponse(Call<List<Notas>> call, retrofit2.Response<List<Notas>> response) {
                view.hideProgress();
                notasList = new ArrayList<Notas>(response.body());
                view.putDataInRecycler(notasList);

            }

            @Override
            public void onFailure(Call<List<Notas>> call, Throwable t) {

            }
        });

    }

    @Override
    public void registrarDatos(String titulo, String nota, String imagen) {
        if (titulo.isEmpty() || nota.isEmpty()){
            view.msgError("Por favor completa los campos");
        }else {
            view.showProgress();

            Notas notas = new Notas();
            notas.setTitulo(titulo);
            notas.setNota(nota);
            notas.setImagen(imagen);

            Call<Void> call = api.agregarNota(notas);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                    switch (response.code()){
                        case 400:
                            view.msgError("Campos incompletos");
                            break;

                        case 200:
                            view.hideProgress();
                            view.msgSucces("Se registró con éxito");
                            break;
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        }
    }

    @Override
    public void verDatos(String id) {
        Log.i("datos", id);
        view.showProgress();

        Call<List<Notas>> call = api.obtenerNota(id);
        Log.i("datos", call.toString());
        call.enqueue(new Callback<List<Notas>>() {
            @Override
            public void onResponse(Call<List<Notas>> call, retrofit2.Response<List<Notas>> response) {

                view.hideProgress();
                notasList = new ArrayList<Notas>(response.body());
                Log.i("datos", notasList.get(0).getTitulo());
                view.mostrarDatos(
                        notasList.get(0).getTitulo(),
                        notasList.get(0).getNota(),
                        notasList.get(0).getImagen()
                );


            }

            @Override
            public void onFailure(Call<List<Notas>> call, Throwable t) {
                view.hideProgress();
                Log.i("error", t.getMessage());
            }
        });

    }

    @Override
    public void modificarDatos(String id, String titulo, String nota, String imagen) {

        if (titulo.isEmpty() || nota.isEmpty()){
            view.msgError("Por favor completa los campos");
        }else {
            view.showProgress();

            Notas notas = new Notas();
            notas.setId(id);
            notas.setTitulo(titulo);
            notas.setNota(nota);
            if (!imagen.isEmpty()){
                notas.setImagen(imagen);
            }

            Call<Void> call = api.editarNota(notas);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                    switch (response.code()){
                        case 400:
                            view.msgError("Campos incompletos");
                            break;

                        case 200:
                            view.hideProgress();
                            view.msgSucces("Se editó el registro");
                            break;
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        }

    }

    @Override
    public void eliminarDatos(String id) {
        view.showProgress();

        Call<Void> call = api.eliminarNota(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                view.hideProgress();
                switch (response.code()) {
                    case 200:
                        view.msgSucces("Se eliminó la nota");
                        break;
                    case 204:
                        view.msgError("No se puedo eliminar");
                        break;

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

}
