package com.example.appcoronavirus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    val listapais = ArrayList<Pais>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val url = "https://corona-virus-stats.herokuapp.com/api/v1/cases/countries-search"

        val busRequest = Volley.newRequestQueue(this)
        val solicitudJson = JsonObjectRequest(
                url, null,
                {
                    respuestaJson ->
                    println("Exito")
                    println(respuestaJson)
                    val datos = respuestaJson.getJSONObject("data")
                    val estado = respuestaJson.getString("status")
                    val ultima = datos.getString("last_update")
                    val paginacion = datos.getJSONObject("paginationMeta")
                    val totalpag = paginacion.getInt("totalPages")
                    val rows = datos.getJSONArray("rows")

                    for ( i in 0 until rows.length()){
                        val pais = rows.getJSONObject(i)
                        val nombrePais = pais.getString("country")
                        val bandera = pais.getString("flag")
                        val objPais = Pais(nombrePais,bandera)
                        listapais.add(objPais)
                    }

/*
                    val datos2 = respuestaJson[
                    "data"] as JSONObject
                    val estado2 = respuestaJson["status"] as String

 */
                },
                {
                    error ->
                    println("Error")
                    println(error)
                }
        )
        busRequest.add(solicitudJson)
    }
}