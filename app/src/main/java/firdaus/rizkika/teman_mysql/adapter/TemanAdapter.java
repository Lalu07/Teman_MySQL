package firdaus.rizkika.teman_mysql.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import firdaus.rizkika.teman_mysql.MainActivity;
import firdaus.rizkika.teman_mysql.R;
import firdaus.rizkika.teman_mysql.app.AppController;
import firdaus.rizkika.teman_mysql.database.Teman;
import firdaus.rizkika.teman_mysql.edit_teman;

public class TemanAdapter extends RecyclerView.Adapter<TemanAdapter.TemanViewHolder> {
    private ArrayList<Teman>listdata;

    public TemanAdapter(ArrayList<Teman> listdata) {
        this.listdata = listdata;
    }

    @Override
    public TemanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_data_teman,parent,false);
        return new TemanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TemanViewHolder holder, int position) {
        String id,nm,tlp;
        id = listdata.get(position).getId();
        nm = listdata.get(position).getNama();
        tlp = listdata.get(position).getTelpon();

        holder.namaText.setText(nm);
        holder.telponText.setText(tlp);
        holder.cardku.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu pm = new PopupMenu(view.getContext(),view);
                pm.inflate(R.menu.popup1);
                pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.edit:
                                Bundle bundle = new Bundle();

                                bundle.putString("kunci1",id);
                                bundle.putString("kunci2",nm);
                                bundle.putString("kunci3",tlp);

                                Intent intent = new Intent(view.getContext(), edit_teman.class);
                                intent.putExtras(bundle);
                                view.getContext().startActivity(intent);
                                break;

                            case R.id.hapus:
                                AlertDialog.Builder alertdb = new AlertDialog.Builder(view.getContext());
                                alertdb.setTitle("Yakin" + nm + "akan dihapus?");
                                alertdb.setMessage("Tekan Ya untuk hapus");
                                alertdb.setCancelable(false);
                                alertdb.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        HapusData(id);
                                        Toast.makeText(view.getContext(),"Data" + id + "telah dihapus",Toast.LENGTH_SHORT).show();
                                        Intent intent1 = new Intent(view.getContext(), MainActivity.class);
                                        view.getContext().startActivity(intent1);
                                    }
                                });
                                alertdb.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        dialog.cancel();
                                    }
                                });
                                AlertDialog adlg = alertdb.create();
                                adlg.show();
                                break;
                        }
                        return true;
                    }
                });
                pm.show();
                return true;
            }
        });
    }

    private void HapusData(final String idx){
        String url_delete ="http://10.0.2.2/teman_mysql/deletetm.php";
        final String TAG = MainActivity.class.getSimpleName();
        final String TAG_SUCCESS = "succes";
        final int[] success =  new int[1];

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_delete, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG,"Respon:" + response.toString());

                try {
                    JSONObject jobj = new JSONObject(response);
                    success[0] = jobj.getInt(TAG_SUCCESS);
                }catch (JSONException e){
                    e.printStackTrace();
                }}
            },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG,"Error :" + error.getMessage());
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("id",idx);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    @Override
    public int getItemCount() {
        return(listdata != null)?listdata.size() : 0;
    }

    public class TemanViewHolder extends RecyclerView.ViewHolder {
        private CardView cardku;
        private TextView namaText,telponText;

        public TemanViewHolder(View view) {
            super(view);
            cardku = view.findViewById(R.id.CV1);
            namaText = view.findViewById(R.id.textNama);
            telponText = view.findViewById(R.id.textTelpon);
        }
    }
}
