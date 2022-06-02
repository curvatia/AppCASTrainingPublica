package com.castraining.app_castraining_publica.view;

import static com.castraining.app_castraining_publica.view.PubApi.URL_BASE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.castraining.app_castraining_publica.api.Interface.ApiCasTraining;
import com.castraining.app_castraining_publica.adapter.ExpanableAdapter;
import com.castraining.app_castraining_publica.api.cursos.AcfCursos;
import com.castraining.app_castraining_publica.api.DetallesCurso;
import com.castraining.app_castraining_publica.databinding.ActivityPubDetallesCursoBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PubDetallesCurso extends AppCompatActivity {

    private ActivityPubDetallesCursoBinding pubDetalleCursoBinding;

    /** Declaraciones e inicializaciones para la lista Expanable*/
    //Adaptador de la ExpanableList
    private ExpanableAdapter adapter;
    //Declaramos e inicializamos la lista de categoiras
    private ArrayList<String> listaCategoria = new ArrayList<>();
    //Declaramos e inicializamos el mapa de los child
    private Map<String, String> mapChild;
    /**----------------- Fin lista Expanable --------------*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_pub_detalles_curso);
        pubDetalleCursoBinding = ActivityPubDetallesCursoBinding.inflate(getLayoutInflater());
        setContentView(pubDetalleCursoBinding.getRoot());

        Intent i = getIntent();
        int id = i.getIntExtra("id", 0);

        mapChild = new HashMap<>();

        Retrofit retrofit = new Retrofit.Builder() //Creamos la instancia retrofit
                .baseUrl(URL_BASE) //Le indicamos la url base
                .addConverterFactory(GsonConverterFactory.create()) //Usamos la librería Gson para convertir la respuesta a un objeto JSON
                .build(); //Construimos el objeto retrofit

        ApiCasTraining apiCasTraining = retrofit.create(ApiCasTraining.class);
        Call<DetallesCurso> call = apiCasTraining.getCursosid(id);
        call.enqueue(new Callback<DetallesCurso>() {
            @Override
            public void onResponse(Call<DetallesCurso> call, Response<DetallesCurso> response) {
                DetallesCurso cursosResponses = response.body();
                AcfCursos acf = cursosResponses.getDatosCurso();
                String title = cursosResponses.getTitulo().getRendered();

                mapChild.put("Dirigido a", acf.getAudienciaCurso());
                mapChild.put("Objetivos", acf.getObjetivosCurso());
                mapChild.put("Requisitos", acf.getRequisitosCurso());
                mapChild.put("Material del curso", acf.getMaterialCurso());
                mapChild.put("Perfil Docente", acf.getDocenteCurso());
                mapChild.put("Certificación", acf.getCertificacionCurso());
                mapChild.put("Contenidos", acf.getContenidosCurso());

                String duracion = acf.getDuracionCurso();
                List<Integer> modalidad = cursosResponses.getModalidad();
                String mod = "";
                switch (modalidad.size()){
                    case 1:
                        if (modalidad.get(0)==2381){
                            mod = "Presencial";
                        }else mod = "Aula Virtual";
                        break;
                    case 2:
                        mod = "Presencial \n Aula Virtual";
                        break;
                    default:
                        break;
                }
                pubDetalleCursoBinding.txtTitulo.setText(title);
                pubDetalleCursoBinding.txtViewDuracion.setText(duracion);
                pubDetalleCursoBinding.txtViewLugar.setText("Madrid/Online");
                pubDetalleCursoBinding.txtViewModalidad.setText(mod);
                pubDetalleCursoBinding.txtViewInicio.setText("Próximamente");
                cargaDatos ();
                adapter = new ExpanableAdapter(listaCategoria, mapChild,PubDetallesCurso.this);
                pubDetalleCursoBinding.expLstView.setAdapter(adapter);

            }
            @Override
            public void onFailure(Call<DetallesCurso> call, Throwable t) {
                Log.d("Response CursosID: ", t.getMessage());
            }
        });
    }
    //Creamos un método para cargar datos en la lista Expanable
    public void cargaDatos () {
        //Definimos los elementos de la categoría
        listaCategoria.add("Dirigido a");
        listaCategoria.add("Objetivos");
        listaCategoria.add("Requisitos");
        listaCategoria.add("Material del curso");
        listaCategoria.add("Perfil Docente");
        listaCategoria.add("Certificación");
        listaCategoria.add("Contenidos");


    }
}