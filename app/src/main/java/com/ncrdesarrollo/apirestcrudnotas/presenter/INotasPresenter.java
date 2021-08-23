package com.ncrdesarrollo.apirestcrudnotas.presenter;

import com.ncrdesarrollo.apirestcrudnotas.models.Notas;

import java.util.List;

public interface INotasPresenter {

    interface View{
        void msgSucces(String mensaje);
        void msgError(String mensaje);
        void showProgress();
        void hideProgress();
        void mostrarDatos(String titulo, String nota);
        void putDataInRecycler(List<Notas> notasList);
    }

    interface Presenter{
        void listarDatos();
        void registrarDatos(String titulo, String nota);
        void verDatos(String id);
        void modificarDatos(String id, String titulo, String nota);
        void eliminarDatos(String id);
    }
}
