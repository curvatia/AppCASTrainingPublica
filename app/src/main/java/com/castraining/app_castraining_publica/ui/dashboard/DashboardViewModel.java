package com.castraining.app_castraining_publica.ui.dashboard;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.castraining.app_castraining_publica.adapter.ConvocatoriaAdapter;
import com.castraining.app_castraining_publica.api.Interface.ApiCasTraining;
import com.castraining.app_castraining_publica.api.convocatoria.AcfConvocatoria;
import com.castraining.app_castraining_publica.api.convocatoria.ConvocatoriaResponse;
import com.castraining.app_castraining_publica.model.CursoItinerarioBootcamp;
import com.castraining.app_castraining_publica.model.RcyViewDatosConvocatoria;
import com.castraining.app_castraining_publica.view.PubApi;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DashboardViewModel extends ViewModel {

    public static final String URL_BASE = "https://cas-training.com/wp-json/wp/v2/";

    //Adapter de los RecyclerView
    private RecyclerView.Adapter adapterConvocatoria = null;
    private RecyclerView.Adapter adapterCursos = null;

    //Retrofit
    private HttpLoggingInterceptor loggingInterceptor;
    private OkHttpClient.Builder httpClientBuilder;

    public MutableLiveData <RecyclerView.Adapter> adapter;

    public DashboardViewModel() {
        adapter = new MutableLiveData<>();
    }

    private void getConvocatoria(){

        loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClientBuilder = new OkHttpClient.Builder().addInterceptor(loggingInterceptor);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClientBuilder.build())
                .build();
        ApiCasTraining apiCasTraining = retrofit.create(ApiCasTraining.class);
        Call<List<ConvocatoriaResponse>> call = apiCasTraining.getConvocatoria();
        return call.enqueue(new Callback<List<ConvocatoriaResponse>>() {
            @Override
            public void onResponse(Call<List<ConvocatoriaResponse>> call, Response<List<ConvocatoriaResponse>> response) {
                List<ConvocatoriaResponse> convocatoriaResponses = response.body();

                List<RcyViewDatosConvocatoria> listadoConvocatorias;
                for (int i = 0; i < response.body().size(); i++) {
                    ConvocatoriaResponse convocatoriaResponse = convocatoriaResponses.get(i);
                    AcfConvocatoria acf = convocatoriaResponse.getAcfConvocatoria();
                    CursoItinerarioBootcamp cursoItineriarioBootcamp = new CursoItinerarioBootcamp(acf);
                    String title = cursoItineriarioBootcamp.title();
                    int id = convocatoriaResponse.getId();
                    listadoConvocatorias.add(new RcyViewDatosConvocatoria(id, title));
                    final DashboardConvocatoriaRV convocatoriaRV = new DashboardConvocatoriaRV(id,title);
                }

                adapterConvocatoria = new ConvocatoriaAdapter(listadoConvocatorias);


            }

            @Override
            public void onFailure(Call<List<ConvocatoriaResponse>> call, Throwable t) {

            }

        });
    }}