package com.limbertlopez.ventas;

import android.app.ProgressDialog;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    String Url = "Tu url a Insertar";
    private RecyclerView reciclador;
    private LinearLayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    List<Venta> listaVentas;
    Button btnInsertar;
    EditText monto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reciclador = (RecyclerView) findViewById(R.id.reciclador);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        reciclador.setLayoutManager(layoutManager);
        if (isNetworkAvailable()) {
            loadRecyclerviewdata();
        } else {
            Toast.makeText(MainActivity.this, "No tienes acceso a INTERNET.", Toast.LENGTH_LONG).show();
        }

        btnInsertar = (Button) findViewById(R.id.btnInsert);
        monto = (EditText) findViewById(R.id.txtMontoInsert);
        btnInsertar.setOnClickListener((View.OnClickListener) this);

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void loadRecyclerviewdata() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando Datos");
        progressDialog.show();
        JsonArrayRequest req = new JsonArrayRequest(Url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                listaVentas = new ArrayList<>();

                try {
                    for (int i = 0; i < response.length(); i++) {

                        JSONObject person = null;
                        person = (JSONObject) response.get(i);
                        String cod = person.getString("pk");
                        String total = person.getString("total");
                        String fecha = person.getString("fecha");
                        Venta item = null;
                        item = new Venta(cod, total, fecha);
                        listaVentas.add(item);
                    }
                    Venta.VENTAS = listaVentas;
                    adapter = new VentaAdaptador();
                    adapter.setHasStableIds(true);
                    reciclador.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_LONG).show();

            }
        });
        progressDialog.dismiss();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(req);
    }

    private void insertarVentas() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                monto.setText("");
                Toast.makeText(MainActivity.this, "Venta Agregada.!", Toast.LENGTH_LONG).show();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Ocurrio un error al registrar.", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map params = new HashMap<>();
                String monto1 = monto.getText().toString().trim();
                params.put("total", monto1);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnInsert) {
            if (isNetworkAvailable()) {
                if (monto.getText().toString().trim().length() > 1) {
                    insertarVentas();
                    loadRecyclerviewdata();
                } else {
                    Toast.makeText(MainActivity.this, "Debe Introducir datos mayor a 1 digito.", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "No tienes acceso a INTERNET. Para poder agregar necesitar tener INTERNET." +
                        "" +
                        "" +
                        "", Toast.LENGTH_LONG).show();
            }

        }
    }
}
