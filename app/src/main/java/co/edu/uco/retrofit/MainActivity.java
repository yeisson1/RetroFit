package co.edu.uco.retrofit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.retrofit.api.ServiceApi;
import co.edu.uco.retrofit.entity.Estudiante;
import co.edu.uco.retrofit.util.ConnectionRest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> datos = new ArrayList<String>();
    ListView listViewUsuario;
    ArrayAdapter<String> adaptador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewUsuario = findViewById(R.id.idListUsuarios);
        adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datos);
        listViewUsuario.setAdapter(adaptador);

        cargaData();

    }

    public void cargaData(){
        ServiceApi api = ConnectionRest.getConnection().create(ServiceApi.class);
        Call<List<Estudiante>> call = api.listaEstudiantes();
        call.enqueue(new Callback<List<Estudiante>>() {
            @Override
            public void onResponse(Call<List<Estudiante>> call, Response<List<Estudiante>> response) {
                if(response.isSuccessful()){
                    List<Estudiante> respuesta = response.body();

                    for(Estudiante estudiante: respuesta){
                        datos.add(estudiante.getIdEstudiante() + ".  " +  estudiante.getNumeroDocumento() + " " + estudiante.getNombres() + " " +  estudiante.getApellidos());
                    }
                    adaptador.notifyDataSetChanged();

                }else{
                    muestraMensaje("La respuesta no es correcta");
                }
            }

            @Override
            public void onFailure(Call<List<Estudiante>> call, Throwable t) {
                muestraMensaje("Error obteniendo la infomación");

            }
        });

    }

    public void muestraMensaje(String msg){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(msg);
        alert.show();

    }
}