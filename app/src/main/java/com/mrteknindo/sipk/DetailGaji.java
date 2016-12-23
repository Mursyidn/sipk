package com.mrteknindo.sipk;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class DetailGaji extends AppCompatActivity{

    //buat variable data
    String  kode_gaji,nama_karyawan,periode,upah_pokok,tot_t_lembur,tot_gaji;
    TextView txtKode,txtnama,txtperiode,txtupah_pokok,txtlembur,txtgaji;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_gaji);

//menghubungkan dengan id yang ada pada layout
        kode_gaji = getIntent().getStringExtra("kode_gaji");
        nama_karyawan = getIntent().getStringExtra("nama_karyawan");
        periode = getIntent().getStringExtra("periode");
        upah_pokok = getIntent().getStringExtra("upah_pokok");
        tot_gaji = getIntent().getStringExtra("tot_gaji");


//tampilan textview
        txtKode = (TextView)findViewById(R.id.kode_gaji);
        txtKode.setText(kode_gaji);
        txtperiode = (TextView)findViewById(R.id.periode);
        txtperiode.setText(periode);
        txtnama = (TextView)findViewById(R.id.nama_karyawan);
        txtnama.setText(nama_karyawan);
        txtupah_pokok = (TextView)findViewById(R.id.upah_pokok);
        txtupah_pokok.setText(upah_pokok);
        txtgaji= (TextView)findViewById(R.id.tot_gaji);
        txtgaji.setText(tot_gaji);


    }
}
