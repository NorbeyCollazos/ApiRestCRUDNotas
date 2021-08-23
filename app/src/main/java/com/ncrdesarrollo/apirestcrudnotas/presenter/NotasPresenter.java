package com.ncrdesarrollo.apirestcrudnotas.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ncrdesarrollo.apirestcrudnotas.models.Notas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotasPresenter implements INotasPresenter.Presenter {

    Context context;
    INotasPresenter.View view;
    private String URL = "http://apirestcrudnotas.ncrdesarrollo.com/notas";
    private RequestQueue requestQueue;
    private List<Notas> notasList;

    public NotasPresenter(Context context, INotasPresenter.View view) {
        this.context = context;
        this.view = view;
        notasList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(context);
    }

    @Override
    public void listarDatos() {
        view.showProgress();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        view.hideProgress();

                        try {
                            int size = response.length();
                            for (int i = 0; i < size; i++){
                                JSONObject jsonObject = new JSONObject(response.get(i).toString());
                                Notas model = new Notas();
                                model.setId(jsonObject.getString("id"));
                                model.setTitulo(jsonObject.getString("titulo"));
                                notasList.add(model);
                                view.putDataInRecycler(notasList);


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.i("lista", e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        view.hideProgress();
                        if (error instanceof ServerError){
                            view.msgError("Error en el servidor");
                        }
                        if (error instanceof NoConnectionError){
                            view.msgError("No hay conexión a internet");
                        }

                        Log.i("error", error.getMessage());
                    }
                }
        );

        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void registrarDatos(String titulo, String nota) {
        if (titulo.isEmpty() || nota.isEmpty()){
            view.msgError("Por favor completa los campos");
        }else {
            view.showProgress();
            JSONObject jsonBody = new JSONObject();
            try {
                jsonBody.put("type", "aplication/json");
                jsonBody.put("titulo", titulo);
                jsonBody.put("nota", nota);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    view.hideProgress();
                    view.msgSucces("Se registró con éxito");
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    view.hideProgress();
                    view.hideProgress();
                    if (error instanceof ServerError){
                        view.msgError("Error en el servidor");
                    }
                    if (error instanceof NoConnectionError){
                        view.msgError("No hay conexión a internet");
                    }
                }
            });

            Volley.newRequestQueue(context).add(request);
        }
    }

    @Override
    public void verDatos(String id) {
        view.showProgress();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL+"?id="+id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                view.hideProgress();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                    view.mostrarDatos(jsonObject.getString("titulo"),jsonObject.getString("nota"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                view.hideProgress();
                if (error instanceof ServerError){
                    view.msgError("Error en el servidor");
                }
                if (error instanceof NoConnectionError){
                    view.msgError("No hay conexión a internet");
                }
            }
        });
        Volley.newRequestQueue(context).add(stringRequest);
    }

    @Override
    public void modificarDatos(String id, String titulo, String nota) {
        if (titulo.isEmpty() || nota.isEmpty()) {
            view.msgError("Campos incompletos");
        }else{

            view.showProgress();

            JSONObject jsonBody = new JSONObject();
            try {
                jsonBody.put("type", "aplication/json");
                jsonBody.put("id", id);
                jsonBody.put("titulo", titulo);
                jsonBody.put("nota", nota);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    view.hideProgress();
                    view.msgSucces("Se modificó el registro");
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    view.hideProgress();
                    if (error instanceof ServerError){
                        view.msgError("Error en el servidor");
                    }
                    if (error instanceof NoConnectionError){
                        view.msgError("No hay conexión a internet");
                    }

                }
            });

            Volley.newRequestQueue(context).add(request);
        }
    }

    @Override
    public void eliminarDatos(String id) {
        view.showProgress();
        StringRequest request = new StringRequest(Request.Method.DELETE, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                view.hideProgress();
                view.msgSucces("Se eliminó el registro");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                view.hideProgress();
                if (error instanceof ServerError){
                    view.msgError("Error en el servidor");
                }
                if (error instanceof NoConnectionError){
                    view.msgError("No hay conexión a internet");
                }

            }
        }){
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                return params;
            }
        };

        Volley.newRequestQueue(context).add(request);
    }

}
