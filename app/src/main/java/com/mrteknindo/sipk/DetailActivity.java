package com.mrteknindo.sipk;

/**
 * Created by Mursyid on 19/12/2016.
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by nelitaaas on 21/11/16.
 */

public class DetailActivity extends AppCompatActivity {

    String  nik,nama_karyawan,nama_perusahaan,jabatan,bank,norek,status;
    TextView  txtNik,txtnama,txtNama_perusahaan,txtjabatan,txtbank,txtnorek,txtstatus;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);


        nik = getIntent().getStringExtra("nik");
        nama_karyawan = getIntent().getStringExtra("nama_karyawan");
        nama_perusahaan = getIntent().getStringExtra("nama_perusahaan");
        jabatan = getIntent().getStringExtra("jabatan");
        bank = getIntent().getStringExtra("bank");
        norek = getIntent().getStringExtra("norek");
        status = getIntent().getStringExtra("status");


        txtNik = (TextView)findViewById(R.id.nik);
        txtNik.setText(nik);
        txtnama = (TextView)findViewById(R.id.nama_karyawan);
        txtnama.setText(nama_karyawan);
        txtNama_perusahaan = (TextView)findViewById(R.id.nama_perusahaan);
        txtNama_perusahaan.setText(nama_perusahaan);
        txtjabatan = (TextView)findViewById(R.id.jabatan);
        txtjabatan.setText(jabatan);
        txtbank = (TextView)findViewById(R.id.bank);
        txtbank.setText(bank);
        txtnorek = (TextView)findViewById(R.id.norek);
        txtnorek.setText(norek);
        txtstatus = (TextView)findViewById(R.id.status);
        txtstatus.setText(status);

    }
}

