package firdaus.rizkika.teman_mysql;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TambahTeman extends AppCompatActivity {
    private EditText editNama,editTelpon;
    private Button simpanButton;
    String nm,tlp;
    int success;

    private static String url_insert = "http://10.0.2.2/teman_mysql/insertteman.php";
    private static String TAG = TambahTeman.class.getSimpleName();
    private static String TAG_SUCCES = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_teman);

        editNama = findViewById(R.id.edNama);
        editTelpon = findViewById(R.id.edTelpon);
        simpanButton = findViewById(R.id.buttonSimpan);

        simpanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editNama.getText().toString().equals("") || editTelpon.getText().toString().equals("")){
                    Toast.makeText(TambahTeman.this,"Semua data harus diisi",Toast.LENGTH_SHORT).show();
                }
                else {
                        nm = editNama.getText().toString();
                        tlp = editTelpon.getText().toString();
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                        StringRequest strReq = new StringRequest(Request.Method.POST, url_insert, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d(TAG, "Response :" + response.toString());

                                try {
                                    JSONObject jboj = new JSONObject(response);
                                    success = jboj.getInt(TAG_SUCCES);
                                    if (success == 1) {
                                        Toast.makeText(TambahTeman.this, "Sukses Simpan Data", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(TambahTeman.this, "gagal", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e(TAG,"Error" + error.getMessage());
                                Toast.makeText(TambahTeman.this,"Gagal meyimpan data",Toast.LENGTH_SHORT).show();
                            }

                }){
                            @Override
                            protected Map<String,String> getParams(){
                                Map<String,String> params = new HashMap<>();

                                params.put("nama",nm);
                                params.put("telpon",tlp);

                                return params;
                            }
                        };
                        requestQueue.add(strReq);
                }
            }
        });
    }
}