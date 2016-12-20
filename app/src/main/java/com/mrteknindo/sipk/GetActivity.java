package com.mrteknindo.sipk;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Mursyid on 18/12/2016.
 */

public class GetActivity extends ListActivity {

    private static String url = "http://192.168.14.1/sipk_webservice/index.php/karyawan";
    // JSON
    private static String TAG_STUDENTINFO = "karyawan";
    private static String TAG_KODE_KARYAWAN = "kode_karyawan";
    private static String TAG_NIK = "nik";
    private static String TAG_NAMA_KARYAWAN = "nama_karyawan";
    private static String TAG_TANGGAL_LAHIR = "tanggal_lahir";
    private static String TAG_KODE_PERUSAHAAN = "kode_perusahaan";
    private static String TAG_JABATAN = "jabatan";
    private static String TAG_BANK = "bank";
    private static String TAG_NOREK = "norek";
    private static String TAG_STATUS_KARYAWAN = "status_karyawan";


    ArrayList<HashMap<String, String>> studentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_getactivity);

        new GetStudents().execute();
    }

    private class GetStudents extends AsyncTask<Void, Void, Void> {

        ArrayList<HashMap<String, String>> studentList;
        ProgressDialog pDialog;
        ListView lv = getListView();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(GetActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
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
                    GetActivity.this, studentList,
                    R.layout.list_item, new String[]{ TAG_NIK,TAG_NAMA_KARYAWAN,
                    TAG_TANGGAL_LAHIR,TAG_KODE_PERUSAHAAN,TAG_STATUS_KARYAWAN}, new int[]{R.id.nik,
                    R.id.nama_karyawan, R.id.tanggal_lahir,R.id.kode_perusahaan,R.id.status_karyawan});
            setListAdapter(adapter);
        }
    }

    private ArrayList<HashMap<String, String>> ParseJSON(String json) {
        if (json != null) {
            try {
                // Hashmap for ListView
                JSONObject jsonObj = new JSONObject(json);
                JSONArray students = jsonObj.getJSONArray(TAG_STUDENTINFO);
                // looping through All Students
                for (int i = 0; i < students.length(); i++) {
                    JSONObject c = students.getJSONObject(i);
                    String kode_karyawan = c.getString(TAG_KODE_KARYAWAN);
                    String nik = c.getString(TAG_NIK);
                    String nama_karyawan = c.getString(TAG_NAMA_KARYAWAN);
                    String kode_perusahaan = c.getString(TAG_KODE_PERUSAHAAN);
                    String jabatan = c.getString(TAG_JABATAN);
                    String bank = c.getString(TAG_BANK);
                    String norek = c.getString(TAG_NOREK);
                    String status_karyawan = c.getString(TAG_STATUS_KARYAWAN);



                    // tmp hashmap for single student
                    HashMap<String, String> student = new HashMap<>();

                    // adding each child node to HashMap key => value
                    student.put(TAG_KODE_KARYAWAN, kode_karyawan.replace("-", " "));
                    student.put(TAG_NIK, nik);
                    student.put(TAG_NAMA_KARYAWAN, nama_karyawan);
                    student.put(TAG_KODE_PERUSAHAAN, kode_perusahaan);
                    student.put(TAG_JABATAN, jabatan);
                    student.put(TAG_BANK, bank);
                    student.put(TAG_NOREK, norek);
                    student.put(TAG_STATUS_KARYAWAN, status_karyawan.replace("-", " "));

                    // adding student to students list
                    studentList.add(student);
                }
                return studentList;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
            return null;
        }
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        HashMap<String, String> data = studentList.get(position);
        String kode_karyawan = data.get(TAG_KODE_KARYAWAN);
        String nik = data.get(TAG_NIK);
        String nama_karyawan = data.get(TAG_NAMA_KARYAWAN);
        String kode_perusahaan = data.get(TAG_KODE_PERUSAHAAN);
        String jabatan = data.get(TAG_JABATAN);
        String bank = data.get(TAG_BANK);
        String norek = data.get(TAG_NOREK);
        String status = data.get(TAG_STATUS_KARYAWAN);

        Intent detail = new Intent(getApplicationContext(), DetailActivity.class);
        detail.putExtra("kode_karyawan",kode_karyawan);
        detail.putExtra("nik", nik);
        detail.putExtra("nama_karyawan", nama_karyawan);
        detail.putExtra("kode_perusahaan", kode_perusahaan);
        detail.putExtra("jabatan", jabatan);
        detail.putExtra("bank", bank);
        detail.putExtra("norek", norek);
        detail.putExtra("status", status);
        startActivity(detail);
    }
}
