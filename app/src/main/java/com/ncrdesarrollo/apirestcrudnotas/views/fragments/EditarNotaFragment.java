package com.ncrdesarrollo.apirestcrudnotas.views.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.ncrdesarrollo.apirestcrudnotas.R;
import com.ncrdesarrollo.apirestcrudnotas.models.Notas;
import com.ncrdesarrollo.apirestcrudnotas.presenter.INotasPresenter;
import com.ncrdesarrollo.apirestcrudnotas.presenter.NotasPresenter;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class EditarNotaFragment extends Fragment implements INotasPresenter.View {

    private TextInputLayout etTitulo;
    private TextInputLayout etNota;
    private Button btnEditar;
    private String sImagen = "";
    private ImageView imgFoto;
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
        imgFoto = view.findViewById(R.id.imgFoto);
        progressBar = view.findViewById(R.id.progressBar4);
        btnEditar = view.findViewById(R.id.btnEditar);

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.modificarDatos(
                        id,
                        etTitulo.getEditText().getText().toString(),
                        etNota.getEditText().getText().toString(),
                        sImagen
                );
            }
        });

        imgFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            100);
                }else {
                    seleccionarImagen();
                }
            }
        });

        presenter.verDatos(id);

    }

    private void seleccionarImagen() {
        /*Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent,"Seleccionar imagen"),100);*/

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, 100);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==100 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            seleccionarImagen();
        }else{
            Toast.makeText(getActivity(), "Permiso denegado", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100 && resultCode==RESULT_OK && data != null){
            Uri uri = data.getData();
            imgFoto.setImageURI(uri);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
                byte[] bytes = stream.toByteArray();
                sImagen = Base64.encodeToString(bytes,Base64.DEFAULT);
                Log.i("imagen", sImagen);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

        etTitulo.getEditText().setText(titulo);
        etNota.getEditText().setText(nota);
        if (!imagen.isEmpty()) {
            Picasso.with(getActivity()).load(imagen).into(imgFoto);
            //sImagen = imagen;
        }
    }

    @Override
    public void putDataInRecycler(List<Notas> notasList) {

    }
}