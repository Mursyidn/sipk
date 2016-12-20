package com.mrteknindo.sipk;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static com.mrteknindo.sipk.R.id.harga;

public class DetailGaji extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_gaji);


        String setKode_karyawan = getIntent().getStringExtra("kode_karyawan");
        String setNama_karyawan = getIntent().getStringExtra("nama_karyawan");
        String setNama_perusahaan = getIntent().getStringExtra("nama_perusahaan");

        EditText kode_karyawan = (EditText) findViewById(R.id.kode_barang_ed);
        EditText nama_karyawan = (EditText) findViewById(R.id.nama_barang_ed);
        EditText nama_perusahaan = (EditText) findViewById(R.id.harga_ed);

        assert kode_karyawan != null;
        kode_karyawan.setText(setKode_karyawan); //kode.setVisibility(View.INVISIBLE);
        assert nama_karyawan != null;
        nama_karyawan.setText(setNama_karyawan); //nama.setVisibility(View.INVISIBLE);
        assert nama_perusahaan != null;
        nama_perusahaan.setText(setNama_perusahaan); //harga.setVisibility(View.INVISIBLE);

        Button btnEdit = (Button) findViewById(R.id.btnEdit);
        assert btnEdit != null;
        btnEdit.setVisibility(View.GONE);
    }
}
