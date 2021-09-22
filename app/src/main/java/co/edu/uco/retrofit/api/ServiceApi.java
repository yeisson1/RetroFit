package co.edu.uco.retrofit.api;

import java.util.List;

import co.edu.uco.retrofit.entity.Estudiante;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ServiceApi {

    @GET("estudiantes")
    public abstract Call<List<Estudiante>> listaEstudiantes();


}
