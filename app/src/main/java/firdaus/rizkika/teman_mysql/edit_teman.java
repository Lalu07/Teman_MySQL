package firdaus.rizkika.teman_mysql;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class edit_teman extends AppCompatActivity {

    TextView idText;
    EditText edNama,edTelpon;
    Button edButton;
    String id,nm,tlp,namaEd,telponEd;
    int success;

    private static String url_update = "http://10.0.2.2/teman_mysql/updatetm.php";
    private static String TAG = TambahTeman.class.getSimpleName();
    private static String TAG_SUCCES = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_teman);

        edNama = findViewById(R.id.upNama);
        edTelpon = findViewById(R.id.upTelpon);
        idText = findViewById(R.id.placeholder);
        edButton = findViewById(R.id.buttonUpdate);

        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("kunci1");
        nm = bundle.getString("kunci2");
        tlp = bundle.getString("kunci3");

        idText.setText("id:" + id);
        edNama.setText(nm);
        edTelpon.setText(tlp);

        edButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditData();
            }
        });

    }

    public void EditData(){
        namaEd = edNama.getText().toString();
        telponEd = edTelpon.getText().toString();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_update, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Respond:" + response.toString());
                try {
                    JSONObject jobj = new JSONObject(response);
                    success = jobj.getInt(TAG_SUCCES);
                    if (success == 1) {
                        Toast.makeText(edit_teman.this, "sukses mengedit data", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(edit_teman.this, "gagal", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG,"Error :" + error.getMessage());
                Toast.makeText(edit_teman.this,"Gagal Edit Data",Toast.LENGTH_SHORT).show();
            }
        })
                {
                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<>();

                        params.put("id",id);
                        params.put("nama",namaEd);
                        params.put("telpon",telponEd);

                        return params;
                    }
                };
        requestQueue.add(stringRequest);
        CallHomeActivity();
    }

    public void CallHomeActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

}
