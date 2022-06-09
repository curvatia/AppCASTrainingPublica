package com.castraining.app_castraining_publica.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.castraining.app_castraining_publica.R;
import com.castraining.app_castraining_publica.adapter.ConvocatoriaAdapter;
import com.castraining.app_castraining_publica.adapter.CursosAdapter;
import com.castraining.app_castraining_publica.api.Interface.ApiCasTraining;
import com.castraining.app_castraining_publica.api.convocatoria.AcfConvocatoria;
import com.castraining.app_castraining_publica.api.convocatoria.ConvocatoriaResponse;
import com.castraining.app_castraining_publica.api.cursos.AcfCursos;
import com.castraining.app_castraining_publica.api.cursos.CursosResponse;
import com.castraining.app_castraining_publica.databinding.ActivityPubApiBinding;
import com.castraining.app_castraining_publica.databinding.ActivityPubMainBinding;
import com.castraining.app_castraining_publica.model.CursoItinerarioBootcamp;
import com.castraining.app_castraining_publica.model.RcyViewDatosConvocatoria;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PubApi extends AppCompatActivity implements View.OnClickListener {

    public static final String URL_BASE = "https://cas-training.com/wp-json/wp/v2/";

    ActivityPubApiBinding pubApiBinding;

    //Adapter de los RecyclerView
    private RecyclerView.Adapter adapterConvocatoria = null;
    private RecyclerView.Adapter adapterCursos = null;

    //Retrofit
    private HttpLoggingInterceptor loggingInterceptor;
    private OkHttpClient.Builder httpClientBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_pub_api);
        pubApiBinding = ActivityPubApiBinding.inflate(getLayoutInflater());
        setContentView(pubApiBinding.getRoot());

        pubApiBinding.btnConvocatorias.setOnClickListener(this);
        pubApiBinding.btnCursos.setOnClickListener(this);
        pubApiBinding.btnBusquedaApi.setOnClickListener(this);

    }

    //METODOS
    private void getConvocatoria(){
        pubApiBinding.progressBar.setVisibility(View.VISIBLE);
        pubApiBinding.txtProgressBar.setVisibility(View.VISIBLE);
        pubApiBinding.rcyView.setVisibility(View.INVISIBLE);
        ArrayList<RcyViewDatosConvocatoria> listadoConvocatorias = new ArrayList<RcyViewDatosConvocatoria>();

        loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClientBuilder = new OkHttpClient.Builder().addInterceptor(loggingInterceptor);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClientBuilder.build())
                .build();
        ApiCasTraining apiCasTraining = retrofit.create(ApiCasTraining.class);
        Call<List<ConvocatoriaResponse>> call = apiCasTraining.getConvocatoria();
        call.enqueue(new Callback<List<ConvocatoriaResponse>>() {
            @Override
            public void onResponse(Call<List<ConvocatoriaResponse>> call, Response<List<ConvocatoriaResponse>> response) {
                List<ConvocatoriaResponse> convocatoriaResponses = response.body();

                for (int i = 0; i<response.body().size();i++){
                    ConvocatoriaResponse convocatoriaResponse = convocatoriaResponses.get(i);
                    AcfConvocatoria acf = convocatoriaResponse.getAcfConvocatoria();
                    CursoItinerarioBootcamp cursoItineriarioBootcamp = new CursoItinerarioBootcamp(acf);
                    String title = cursoItineriarioBootcamp.title();
                    int id = convocatoriaResponse.getId();
                    listadoConvocatorias.add(new RcyViewDatosConvocatoria(id, title));
                }
                if (response.isSuccessful()) {
                    pubApiBinding.progressBar.setVisibility(View.INVISIBLE);
                    pubApiBinding.txtProgressBar.setVisibility(View.INVISIBLE);
                    pubApiBinding.rcyView.setVisibility(View.VISIBLE);
                }
                adapterConvocatoria = new ConvocatoriaAdapter(listadoConvocatorias);
                pubApiBinding.rcyView.setLayoutManager(new LinearLayoutManager(PubApi.this));
                pubApiBinding.rcyView.setHasFixedSize(true);
                pubApiBinding.rcyView.setAdapter(adapterConvocatoria);

            }

            @Override
            public void onFailure(Call<List<ConvocatoriaResponse>> call, Throwable t) {
                Log.d("Response convocatoria:" ,t.getMessage());

            }
        });
    }
    private void getCursos() {
        pubApiBinding.progressBar.setVisibility(View.VISIBLE);
        pubApiBinding.txtProgressBar.setVisibility(View.VISIBLE);
        pubApiBinding.rcyView.setVisibility(View.INVISIBLE);
        //ArrayList<DatosLstview> listadoCursos = new ArrayList<DatosLstview>();
        ArrayList<RcyViewDatosConvocatoria> listadoCursos = new ArrayList<RcyViewDatosConvocatoria>();
        Retrofit retrofit = new Retrofit.Builder() //Creamos la instancia retrofit
                .baseUrl(URL_BASE) //Le indicamos la url base
                .addConverterFactory(GsonConverterFactory.create()) //Usamos la librería Gson para convertir la respuesta a un objeto JSON
                .build(); //Construimos el objeto retrofit

        ApiCasTraining apiCasTraining = retrofit.create(ApiCasTraining.class);

        Call<List<CursosResponse>> call = apiCasTraining.getCursos();

        call.enqueue(new Callback<List<CursosResponse>>() {
            @Override
            public void onResponse(Call<List<CursosResponse>> call, Response<List<CursosResponse>> response) {
                List<CursosResponse> cursosResponses = response.body();
                for (int i = 0; i < response.body().size(); i++) {
                    CursosResponse cursoResponse = cursosResponses.get(i);
                    AcfCursos acf = cursoResponse.getAcfCursos();
                    int id = cursoResponse.getId();
                    String title = cursoResponse.getTitle().getRendered();
                    listadoCursos.add(new RcyViewDatosConvocatoria(id, title));
                }
                if (response.isSuccessful()) {
                    pubApiBinding.progressBar.setVisibility(View.INVISIBLE);
                    pubApiBinding.txtProgressBar.setVisibility(View.INVISIBLE);
                    pubApiBinding.rcyView.setVisibility(View.VISIBLE);
                }
                adapterCursos = new CursosAdapter(listadoCursos);
                pubApiBinding.rcyView.setLayoutManager(new LinearLayoutManager(PubApi.this));
                pubApiBinding.rcyView.setAdapter(adapterCursos);



            }
            @Override
            public void onFailure(Call<List<CursosResponse>> call, Throwable t) {
                Log.d("Response curso: ",t.getMessage() );
            }
        });
    }
    private void getBusquedaApi(int id) {
        Intent intent = new Intent(PubApi.this, PubDetallesCurso.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnConvocatorias:
                getConvocatoria();
                break;
            case R.id.btnCursos:
                getCursos();
                break;
            case R.id.btnBusquedaApi:
                int id = Integer.parseInt(pubApiBinding.edtxtBusqueda.getText().toString());
                getBusquedaApi(id);
                break;
            default:
                break;
        }


    }
}