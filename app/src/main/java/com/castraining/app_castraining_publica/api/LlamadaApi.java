package com.castraining.app_castraining_publica.api;

import static com.castraining.app_castraining_publica.view.PubApi.URL_BASE;

import android.util.Log;

import com.castraining.app_castraining_publica.api.Interface.ApiCasTraining;
import com.castraining.app_castraining_publica.api.convocatoria.AcfConvocatoria;
import com.castraining.app_castraining_publica.api.convocatoria.ConvocatoriaResponse;
import com.castraining.app_castraining_publica.model.CursoItinerarioBootcamp;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LlamadaApi {


    private ArrayList<DatosLstview> listado = new ArrayList<DatosLstview>();
    private Retrofit retrofit;
    private HttpLoggingInterceptor loggingInterceptor;
    private OkHttpClient.Builder httpClientBuilder;
    private List<ConvocatoriaResponse> resConvocatoria;

    public ArrayList<DatosLstview> getListado() {
        return listado;
    }
    public void setListado(ArrayList<DatosLstview> listado) {
        this.listado = listado;
    }
    public LlamadaApi() {}

    public LlamadaApi(ArrayList<DatosLstview> listado) {
        this.listado = listado;
    }

    public void RespuestaApiConvocatoria (){
        loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClientBuilder = new OkHttpClient.Builder().addInterceptor(loggingInterceptor);
        retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClientBuilder.build())
                .build();
        ApiCasTraining apiCasTraining = retrofit.create(ApiCasTraining.class);
        Call<List<ConvocatoriaResponse>> call = apiCasTraining.getConvocatoria();
        call.enqueue(new Callback<List<ConvocatoriaResponse>>() {
            @Override
            public void onResponse(Call<List<ConvocatoriaResponse>> call, Response<List<ConvocatoriaResponse>> response) {
                resConvocatoria = response.body();
            }
            @Override
            public void onFailure(Call<List<ConvocatoriaResponse>> call, Throwable t) {
                Log.d("LlamadaApi respuesta:", t.getMessage());
            }
        });
    }
    public ArrayList<DatosLstview> Callconvocatoria() {
        for (int i = 0; i < resConvocatoria.size(); i++) {
            ConvocatoriaResponse convocatoriaResponse = resConvocatoria.get(i);
            AcfConvocatoria acf = convocatoriaResponse.getAcfConvocatoria();
            CursoItinerarioBootcamp cursoItineriarioBootcamp = new CursoItinerarioBootcamp(acf);
            String title = cursoItineriarioBootcamp.title();
            int id = convocatoriaResponse.getId();
            listado.add(new DatosLstview(title, id));
        }
        return listado;

    }

}
