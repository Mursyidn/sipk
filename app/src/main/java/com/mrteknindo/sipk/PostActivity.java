package com.mrteknindo.sipk;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Mursyid on 18/12/2016.
 */

public class PostActivity extends ListActivity implements View.OnClickListener {
    private AddBarangTask aksiSimpan = null;
    // URL to get contacts JSON
    private static String url = "http://192.168.14.1/sipk_webservice/index.php/gaji";
    // JSON Node names
    private static final String TAG_BARANGINFO = "gaji";
    private static String TAG_PERIODE = "periode";
    private static String TAG_PERIODE_KEHADIRAN= "periode_kehadiran";
    private static String TAG_H_KERJA = "h_kerja";
    private static String TAG_H_CUTI = "h_cuti";
    private static String TAG_H_SAKIT = "h_sakit";
    private static String TAG_H_IZIN_P = "h_izin_p";
    private static String TAG_H_TANPA_K= "h_tanpa_k";
    private static String TAG_JAM_MINUS= "jam_minus";
    private static String TAG_POTONGAN_PINJAMAN = "potongan_pinjaman";
    private static String TAG_KODE_GAJI = "kode_gaji";
    private static String TAG_KODE_KARYAWAN = "kode_karyawan";
    private static String TAG_NAMA_KARYAWAN = "nama_karyawan";
    private static String TAG_NAMA_PERUSAHAAN = "nama_perusahaan";



    // private ListAdapter daftar;
    private String item;
    private ListView listView;
    Button btnTambah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_postactivity);
        btnTambah = (Button) findViewById(R.id.btnTambahBarang);
        btnTambah.setOnClickListener(this);
// Calling async task to get json
        new GetBarang().execute();

    }

    @Override
    public void onClick(View v) {
        Intent proses = new Intent(this, ProsesGaji.class);
        startActivity(proses);
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetBarang extends AsyncTask<Void, Void, Void> {
        // Hashmap for ListView
        ArrayList<HashMap<String, String>> studentList;
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
// Showing progress dialog
            pDialog = new ProgressDialog(PostActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
// Creating service handler class instance
            Koneksi webreq = new Koneksi();
// Making a request to url and getting response
            String jsonStr = webreq.makeWebServiceCall(url, Koneksi.GET);
            Log.d("Response: ", "> " + jsonStr);
            studentList = ParseJSON(jsonStr);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
// Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
/**
 * Updating parsed JSON data into ListView
 * */
            ListAdapter adapter = new SimpleAdapter(
                    PostActivity.this, studentList,
                    R.layout.list_item_gaji, new String[]{TAG_PERIODE, TAG_PERIODE_KEHADIRAN, TAG_NAMA_KARYAWAN,TAG_NAMA_PERUSAHAAN}, new int[]{R.id.periode, R.id.periode_kehadiran, R.id.nama_karyawan,R.id.nama_perusahaan,});
            setListAdapter(adapter);
            listView = getListView();
            listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int
                        position, long id) {
// item = (String) parent.getItemAtPosition(position);
                    HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
                    final String periode = map.get(TAG_PERIODE);
                    final String periode_kehadiran = map.get(TAG_PERIODE_KEHADIRAN);
                    final String h_kerja = map.get(TAG_H_KERJA);
                    final String h_cuti = map.get(TAG_H_CUTI);
                    final String h_sakit = map.get(TAG_H_SAKIT);
                    final String h_izin_p = map.get(TAG_H_IZIN_P);
                    final String h_tanpa_k = map.get(TAG_H_TANPA_K);
                    final String jam_minus = map.get(TAG_JAM_MINUS);
                    final String potongan_pinjaman = map.get(TAG_POTONGAN_PINJAMAN);
                    final String kode_gaji = map.get(TAG_KODE_GAJI);
                    final String kode_karyawan = map.get(TAG_KODE_KARYAWAN);
                    final String nama_karyawan = map.get(TAG_NAMA_KARYAWAN);
                    final String nama_perusahaan = map.get(TAG_NAMA_PERUSAHAAN);



                /*Intent update = new Intent(MainActivity.this, UpdateBarang.class);
                update.putExtra("kode", kode);
                update.putExtra("nama", nama);
                update.putExtra("harga", harga);
                startActivity(update);*/
                    final CharSequence[] dialogItem = {"Detail", "Update", "Delete"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(PostActivity.this);
                    builder.setTitle("Pilihan");
                    builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, final int item) {
                            switch (item){
                                case 0:
                                    Intent iLihat = new Intent(PostActivity.this, DetailGaji.class);
                                    iLihat.putExtra("periode", periode);
                                    iLihat.putExtra("periode_kehadiran", periode_kehadiran);
                                    iLihat.putExtra("h_kerja", h_kerja);
                                    iLihat.putExtra("h_cuti", h_cuti);
                                    iLihat.putExtra("h_sakit", h_sakit);
                                    iLihat.putExtra("h_izin_p", h_izin_p);
                                    iLihat.putExtra("h_tanpa_k", h_tanpa_k);
                                    iLihat.putExtra("jam_minus", jam_minus);
                                    iLihat.putExtra("potongan_pinjaman", potongan_pinjaman);
                                    iLihat.putExtra("kode_gaji", kode_gaji);
                                    iLihat.putExtra("kode_karyawan", kode_karyawan);
                                    iLihat.putExtra("nama_karyawan", nama_karyawan);
                                    iLihat.putExtra("nama_perusahaan", nama_perusahaan);
                                    startActivity(iLihat);
                                    break;

                                case 1:
                                    Intent iUpdate = new Intent(PostActivity.this, UpdateGaji.class);
                                    iUpdate.putExtra("periode", periode);
                                    iUpdate.putExtra("periode_kehadiran", periode_kehadiran);
                                    iUpdate.putExtra("h_kerja", h_kerja);
                                    iUpdate.putExtra("h_cuti", h_cuti);
                                    iUpdate.putExtra("h_sakit", h_sakit);
                                    iUpdate.putExtra("h_izin_p", h_izin_p);
                                    iUpdate.putExtra("h_tanpa_k", h_tanpa_k);
                                    iUpdate.putExtra("jam_minus", jam_minus);
                                    iUpdate.putExtra("potongan_pinjaman", potongan_pinjaman);
                                    iUpdate.putExtra("kode_gaji", kode_gaji);
                                    iUpdate.putExtra("kode_karyawan", kode_karyawan);
                                    iUpdate.putExtra("nama_karyawan", nama_karyawan);
                                    iUpdate.putExtra("nama_perusahaan", nama_perusahaan);
                                    startActivity(iUpdate);
                                    break;

                                case 2:
                                    new AlertDialog.Builder(PostActivity.this)
                                            .setIcon(R.drawable.ic_warning)
                                            .setTitle("Hapus Data")
                                            .setMessage("Anda yakin akan Menghapus " + kode_karyawan + " ?")
                                            .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    aksiSimpan = new AddBarangTask(kode_gaji);
                                                    aksiSimpan.execute((Void) null);
                                                }
                                            })
                                            .setNegativeButton("Tidak", null)
                                            .show();
                                    break;
                            }
                        }
                    });
                    builder.create().show();
                }
            });
            ;
        }
    }

    private ArrayList<HashMap<String, String>> ParseJSON(String json) {
        if (json != null) {
            try {
// Hashmap for ListView
                ArrayList<HashMap<String, String>> barangList = new
                        ArrayList<HashMap<String, String>>();
                JSONObject jsonObj = new JSONObject(json);
// Getting JSON Array node
                JSONArray dataBarang = jsonObj.getJSONArray(TAG_BARANGINFO);
// looping through All Students
                for (int i = 0; i < dataBarang.length(); i++) {
                    JSONObject c = dataBarang.getJSONObject(i);
                    String periode = "periode : " + c.getString(TAG_PERIODE);
                    String periode_kehadiran = "periode_kehadiran : " + c.getString(TAG_PERIODE_KEHADIRAN);
                    String nama_karyawan  = "nama_karyawan :  " + c.getString(TAG_NAMA_KARYAWAN);
                    String nama_perusahaan = "nama_perusahaan :  " + c.getString(TAG_NAMA_PERUSAHAAN);
// tmp hashmap for single student
                    HashMap<String, String> barang = new HashMap<String, String>();
// adding each child node to HashMap key => value
                    barang.put(TAG_PERIODE, periode);
                    barang.put(TAG_PERIODE_KEHADIRAN, periode_kehadiran);
                    barang.put(TAG_NAMA_KARYAWAN, nama_karyawan);
                    barang.put(TAG_NAMA_PERUSAHAAN, nama_perusahaan);
                    barangList.add(barang);
                }
                return barangList;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
            return null;
        }
    }

    private class AddBarangTask extends AsyncTask<Void, Void, Boolean>{
        String mkode_gaji;

        public AddBarangTask(String kode) {
            mkode_gaji= kode;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
// Create data variable for sent values to server
            String data = null;
            try {
//Set Data Post
                data = URLEncoder.encode("kode", "UTF-8")
                        + "=" + URLEncoder.encode(mkode_gaji, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String text = "";
            BufferedReader reader=null;
// Send data
            try
            {
// Defined URL where to send data
                URL url = new URL("http://dthan.net/delete_barang.php");
// Send POST data request
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new
                        OutputStreamWriter(conn.getOutputStream());
                wr.write( data );
                wr.flush();
// Get the server response
                reader = new BufferedReader(new
                        InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;
// Read Server Response
                while((line = reader.readLine()) != null)
                {
// Append server response in string
                    sb.append(line + "\n");
                }
            }
            catch(Exception ex)
            {
            }
            finally
            {
                try
                {
                    reader.close();
                }
                catch(Exception ex) {}
            }
// TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            aksiSimpan = null;
            if (success) {
                finish();
                Toast.makeText(PostActivity.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(
                        getApplicationContext(),"gagal",Toast.LENGTH_LONG).show();
            }
        }
        @Override
        protected void onCancelled() {
            aksiSimpan = null;
        }
    }
}