package com.mrteknindo.sipk;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ProsesGaji extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>
{
    private AddBarangTask aksiSimpan = null;
    // UI references.
    private Spinner spNamen2;
    private EditText txtkode_gaji,txtperiode,txtperiode_kehadiran,txth_kerja,txth_cuti,txth_sakit,txth_izin_p,txth_tanpa_k,txtjam_minus,txtpotongan_pinjaman,txtkode_karyawan;
    private View mProgressView;
    private View mLoginFormView;
    private String  kode_gaji,periode,periode_kehadiran,h_kerja,h_cuti,h_sakit,h_izin_p,h_tanpa_k,jam_minus,potongan_pinjaman,kode_karyawan;

    private URL url;

    private String[] germanFeminine = {
            "Karin",
            "Ingrid", "Helga",
            "Renate",
            "Elke",
            "Ursula",
            "Erika",
            "Christa",
            "Gisela",
            "Monika"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proses_gaji);
        spNamen2 = (Spinner) findViewById(R.id.sp_name_2);

        // inisialiasi Array Adapter dengan memasukkan string array di atas
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, germanFeminine);


// Set up the login form.

        txtkode_gaji = (EditText) findViewById(R.id.kode_gaji);
        txtperiode = (EditText) findViewById(R.id.periode);
        txtperiode_kehadiran = (EditText) findViewById(R.id.periode_kehadiran);
        txth_kerja = (EditText) findViewById (R.id.hari_wajib_kerja);
        txth_cuti = (EditText) findViewById (R.id.cuti);
        txth_sakit = (EditText) findViewById(R.id.sakit);
        txth_izin_p = (EditText) findViewById(R.id.izin_gaji_dipotong);
        txth_tanpa_k = (EditText) findViewById (R.id.tanpa_keterangan);
        txtjam_minus = (EditText) findViewById(R.id.jam_minus);
        txtpotongan_pinjaman = (EditText) findViewById(R.id.potongan_pinjaman);
        txtkode_karyawan = (EditText) findViewById (R.id.kode_karyawan);
        Button btnSimpan = (Button) findViewById(R.id.btnsimpan);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpanData();
            }
        });
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }
    private void simpanData() {
        kode_gaji = txtkode_gaji.getText().toString();
        periode = txtperiode.getText().toString();
        periode_kehadiran = txtperiode_kehadiran.getText().toString();
        h_kerja = txth_kerja.getText().toString();
        h_cuti = txth_cuti.getText().toString();
        h_sakit= txth_sakit.getText().toString();
        h_izin_p = txth_izin_p.getText().toString();
        h_tanpa_k = txth_tanpa_k.getText().toString();
        jam_minus = txtjam_minus.getText().toString();
        potongan_pinjaman = txtpotongan_pinjaman.getText().toString();
        kode_karyawan = txtkode_karyawan.getText().toString();
        showProgress(true);
        aksiSimpan = new AddBarangTask(kode_gaji,periode, periode_kehadiran,h_kerja,h_cuti,h_sakit,h_izin_p,h_tanpa_k,jam_minus,potongan_pinjaman,kode_karyawan);
        aksiSimpan.execute((Void) null);
    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
// for very easy animations. If available, use these APIs to fade-in
// the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime =
                    getResources().getInteger(android.R.integer.config_shortAnimTime);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
// The ViewPropertyAnimator APIs are not available, so simply show
// and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
// Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY),
                ProfileQuery.PROJECTION,
// Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},
// Show primary email addresses first. Note that there won't be
// a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }
    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }
    }
    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
    }
    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };
        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }
    public class AddBarangTask extends AsyncTask<Void, Void, Boolean> {
        String mkode_gaji;
        String mperiode;
        String mperiode_kehadiran;
        String mh_kerja;
        String mh_cuti;
        String mh_sakit;
        String mh_izin;
        String mh_tanpa_k;
        String mjam_minus;
        String mpotongan_pinjaman;
        String mkode_karyawan;
        AddBarangTask(String kode_gaji_b,String periode_b, String periode_kehadiran_b,String h_kerja_b,String h_cuti_b,String  h_sakit_b,String  h_izin_p_b,String h_tanpa_k_b,String jam_minus_b,String potongan_pinjaman_b,String kode_karyawan_b) {
            mkode_gaji = kode_gaji_b;
            mperiode = periode_b;
            mperiode_kehadiran = periode_kehadiran_b;
            mh_kerja = h_kerja_b;
            mh_cuti = h_cuti_b;
            mh_sakit = h_sakit_b;
            mh_izin = h_izin_p_b;
            mh_tanpa_k = h_tanpa_k_b;
            mjam_minus = jam_minus_b;
            mpotongan_pinjaman = potongan_pinjaman_b;
            mkode_karyawan = kode_karyawan_b;

        }
        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
// Create data variable for sent values to server
            String data = null;
            try {
//Set Data Post
                data = URLEncoder.encode("periode", "UTF-8")
                        + "=" + URLEncoder.encode(mkode_gaji, "UTF-8");
                data = URLEncoder.encode("periode", "UTF-8")
                        + "=" + URLEncoder.encode(mperiode, "UTF-8");
                data += "&" + URLEncoder.encode("periode_kehadiran", "UTF-8") + "="
                        + URLEncoder.encode(mperiode_kehadiran, "UTF-8");
                data += "&" + URLEncoder.encode("h_kerja", "UTF-8")
                        + "=" + URLEncoder.encode(mh_kerja, "UTF-8");
                data = URLEncoder.encode("h_cuti", "UTF-8")
                        + "=" + URLEncoder.encode(mh_cuti, "UTF-8");
                data += "&" + URLEncoder.encode("h_sakit", "UTF-8") + "="
                        + URLEncoder.encode(mh_sakit, "UTF-8");
                data += "&" + URLEncoder.encode("h_izin_p", "UTF-8")
                        + "=" + URLEncoder.encode(mh_izin, "UTF-8");
                data = URLEncoder.encode("h_tanpa_k", "UTF-8")
                        + "=" + URLEncoder.encode(mh_tanpa_k, "UTF-8");
                data += "&" + URLEncoder.encode("jam_minus", "UTF-8") + "="
                        + URLEncoder.encode(mjam_minus, "UTF-8");
                data += "&" + URLEncoder.encode("potongan_pinjaman", "UTF-8")
                        + "=" + URLEncoder.encode(mpotongan_pinjaman, "UTF-8");
                data += "&" + URLEncoder.encode("kode_karyawan", "UTF-8")
                        + "=" + URLEncoder.encode(mkode_karyawan, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String text = "";
            BufferedReader reader=null;
// Send data
            try
            {
// Defined URL where to send data
                URL url = new URL("http://192.168.14.1/sipk_webservice/index.php/gaji");
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
            showProgress(false);
            if (success) {
                kembaliKeHome();
            } else {
                Toast.makeText(
                        getApplicationContext(),"gagal",Toast.LENGTH_LONG).show();
            }
        }
        @Override
        protected void onCancelled() {
            aksiSimpan = null;
            showProgress(false);
        }
    }
    protected void kembaliKeHome(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}