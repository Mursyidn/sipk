<?php

require APPPATH . '/libraries/REST_Controller.php';

class gaji extends REST_Controller {

    function __construct($config = 'rest') {
        parent::__construct($config);
		$this->load->model('mod_gaji');

    }

//show karyawan
    function get_karyawan() {
		$karyawan="SELECT * from karyawan";
		$data =  array("karyawan" => $this->db->query($karyawan)->result());
        $this->response($data, 200);    
    }


    // show data gaji

    function index_get() {
		$gaji="SELECT gaji.*, karyawan.nik, karyawan.nama_karyawan, perusahaan.nama_perusahaan from gaji,karyawan,perusahaan where gaji.kode_karyawan=karyawan.kode_karyawan AND karyawan.kode_perusahaan=perusahaan.kode_perusahaan";
		$data =  array("gaji" => $this->db->query($gaji)->result());
        $this->response($data, 200);    
    }


    // insert new data to gaji
    function index_post() {
       	$kode_karyawan = $this->input->post('kode_karyawan');

       	$data['tunjangan']=$this->db->get_where('tunjangan',array('kode_karyawan' => $kode_karyawan, 'status_tunjangan' => "Aktif"))->result();
        $data['upah_pokok']=$this->db->get_where('upah_pokok',array('kode_karyawan' => $kode_karyawan, 'status_upah_pokok' => "Aktif"))->result();
        $periode = $this->input->post('periode');
		$h_kerja = $this->input->post('h_kerja');
		$h_cuti = $this->input->post('h_cuti');
		$h_sakit = $this->input->post('h_sakit');
		$h_izin_p = $this->input->post('h_izin_p');
		$h_tanpa_k = $this->input->post('h_tanpa_k');
		$jam_minus = $this->input->post('jam_minus');
		$periode_kehadiran = $this->input->post('tgl_awal')." - ".$this->input->post('tgl_akhir');
		$potongan_pinjaman = $this->input->post('potongan_pinjaman');
		$ket_potongan = $this->input->post('ket_potongan');
		$data['tunjangan_lembur']=$this->db->get_where('tunjangan_lembur',array('kode_karyawan' => $kode_karyawan, 'periode_gajian' => $periode))->result();

		$tot_t_lembur=0;
		foreach ($data['tunjangan_lembur'] as $tl) {
			$tot_t_lembur = $tot_t_lembur + $tl->t_lembur;
			}
	
		foreach ($data['tunjangan'] as $t) {
	
			foreach ($data['upah_pokok'] as $up) {
				$kode_tunjangan = $t->kode_tunjangan;
				$kode_upah_pokok = $up->kode_upah_pokok;
				$upah_pokok = $up->upah_pokok;
				$upah_perjam = ($upah_pokok) / 173;
				$kekurangan_jam_kerja = $jam_minus + ($h_izin_p+$h_tanpa_k)*8;
				$potongan_jam_kerja = $kekurangan_jam_kerja*$upah_perjam;
				$t_makan = (($h_kerja)-($h_cuti+$h_sakit+$h_izin_p+$h_tanpa_k)) * $t->t_makan;
				$t_transport = (($h_kerja)-($h_cuti+$h_sakit+$h_izin_p+$h_tanpa_k)) * $t->t_transport;
				$t_bpjs_kesehatan = $t->t_bpjs_kesehatan;
				$t_bpjs_ketenagakerjaan = $t->t_bpjs_ketenagakerjaan;
				$t_pajak = $t->t_pajak;
				$t_keahlian = $t->t_keahlian;
				$t_jabatan = $t->t_jabatan;
				$t_produktivitas = $t->t_produktivitas;
				$t_disiplin = $t->t_disiplin;
				$tot_gaji = ($t_makan+$t_transport+$t_keahlian+$t_jabatan+$t_produktivitas+$t_disiplin+$upah_pokok+$tot_t_lembur) - ($potongan_pinjaman + $potongan_jam_kerja);
				$jumlah_pengeluaran = $tot_gaji + $t_bpjs_kesehatan + $t->t_bpjs_ketenagakerjaan + $t_pajak;
			}
		}

		$data = array(
		'kode_karyawan' => $kode_karyawan,
		'kode_tunjangan' => $kode_tunjangan,
		'kode_upah_pokok' => $kode_upah_pokok,
		'periode' => $periode,
		'periode_kehadiran' => $periode_kehadiran,
		'h_kerja' => $h_kerja,
		'h_cuti' => $h_cuti,
		'h_sakit' => $h_sakit,
		'h_izin_p' => $h_izin_p,
		'h_tanpa_k' => $h_tanpa_k,
		'jam_minus' => $jam_minus,
		'kekurangan_jam_kerja' => $kekurangan_jam_kerja,
		'potongan_pinjaman' => $potongan_pinjaman,
		'ket_potongan' => $ket_potongan,
		'potongan_jam_kerja' => $potongan_jam_kerja,
		'upah_pokok' => $upah_pokok,
		't_makan' => $t_makan,
		't_transport' => $t_transport,
		't_bpjs_kesehatan' => $t_bpjs_kesehatan,
		't_bpjs_ketenagakerjaan' => $t_bpjs_ketenagakerjaan,
		't_pajak' => $t_pajak,
		't_keahlian' => $t_keahlian,
		't_jabatan' => $t_jabatan,
		't_produktivitas' => $t_produktivitas,
		't_disiplin' => $t_disiplin,
		'tot_t_lembur' => $tot_t_lembur,
		'tot_gaji' => $tot_gaji,
		'jumlah_pengeluaran' => $jumlah_pengeluaran
			);

        $insert = $this->db->insert('gaji', $data);
        if ($insert) {
            $this->response($data, 200);
        } else {
            $this->response(array('status' => 'fail', 502));
        }
    }

    // update data gaji
    function index_put() {
        $kode_gaji = $this->put('kode_gaji');

       	$kode_karyawan = $this->put('kode_karyawan');

       	$data['tunjangan']=$this->db->get_where('tunjangan',array('kode_karyawan' => $kode_karyawan, 'status_tunjangan' => "Aktif"))->result();
        $data['upah_pokok']=$this->db->get_where('upah_pokok',array('kode_karyawan' => $kode_karyawan, 'status_upah_pokok' => "Aktif"))->result();
        $periode = $this->put('periode');
		$h_kerja = $this->put('h_kerja');
		$h_cuti = $this->put('h_cuti');
		$h_sakit = $this->put('h_sakit');
		$h_izin_p = $this->put('h_izin_p');
		$h_tanpa_k = $this->put('h_tanpa_k');
		$jam_minus = $this->put('jam_minus');
		$periode_kehadiran = $this->put('tgl_awal')." - ".$this->put('tgl_akhir');
		$potongan_pinjaman = $this->put('potongan_pinjaman');
		$ket_potongan = $this->put('ket_potongan');
		$data['tunjangan_lembur']=$this->db->get_where('tunjangan_lembur',array('kode_karyawan' => $kode_karyawan, 'periode_gajian' => $periode))->result();

		$tot_t_lembur=0;
		foreach ($data['tunjangan_lembur'] as $tl) {
			$tot_t_lembur = $tot_t_lembur + $tl->t_lembur;
			}

		foreach ($data['tunjangan'] as $t) {
	
			foreach ($data['upah_pokok'] as $up) {
				$kode_tunjangan = $t->kode_tunjangan;
				$kode_upah_pokok = $up->kode_upah_pokok;
				$upah_pokok = $up->upah_pokok;
				$upah_perjam = ($upah_pokok) / 173;
				$kekurangan_jam_kerja = $jam_minus + ($h_izin_p+$h_tanpa_k)*8;
				$potongan_jam_kerja = $kekurangan_jam_kerja*$upah_perjam;
				$t_makan = (($h_kerja)-($h_cuti+$h_sakit+$h_izin_p+$h_tanpa_k)) * $t->t_makan;
				$t_transport = (($h_kerja)-($h_cuti+$h_sakit+$h_izin_p+$h_tanpa_k)) * $t->t_transport;
				$t_bpjs_kesehatan = $t->t_bpjs_kesehatan;
				$t_bpjs_ketenagakerjaan = $t->t_bpjs_ketenagakerjaan;
				$t_pajak = $t->t_pajak;
				$t_keahlian = $t->t_keahlian;
				$t_jabatan = $t->t_jabatan;
				$t_produktivitas = $t->t_produktivitas;
				$t_disiplin = $t->t_disiplin;
				$tot_gaji = ($t_makan+$t_transport+$t_keahlian+$t_jabatan+$t_produktivitas+$t_disiplin+$upah_pokok+$tot_t_lembur) - ($potongan_pinjaman + $potongan_jam_kerja);
				$jumlah_pengeluaran = $tot_gaji + $t_bpjs_kesehatan + $t->t_bpjs_ketenagakerjaan + $t_pajak;
			}
		}



        $data = array(
		'kode_karyawan' => $kode_karyawan,
		'kode_tunjangan' => $kode_tunjangan,
		'kode_upah_pokok' => $kode_upah_pokok,
		'periode' => $periode,
		'periode_kehadiran' => $periode_kehadiran,
		'h_kerja' => $h_kerja,
		'h_cuti' => $h_cuti,
		'h_sakit' => $h_sakit,
		'h_izin_p' => $h_izin_p,
		'h_tanpa_k' => $h_tanpa_k,
		'jam_minus' => $jam_minus,
		'kekurangan_jam_kerja' => $kekurangan_jam_kerja,
		'potongan_pinjaman' => $potongan_pinjaman,
		'ket_potongan' => $ket_potongan,
		'potongan_jam_kerja' => $potongan_jam_kerja,
		'upah_pokok' => $upah_pokok,
		't_makan' => $t_makan,
		't_transport' => $t_transport,
		't_bpjs_kesehatan' => $t_bpjs_kesehatan,
		't_bpjs_ketenagakerjaan' => $t_bpjs_ketenagakerjaan,
		't_pajak' => $t_pajak,
		't_keahlian' => $t_keahlian,
		't_jabatan' => $t_jabatan,
		't_produktivitas' => $t_produktivitas,
		't_disiplin' => $t_disiplin,
		'tot_t_lembur' => $tot_t_lembur,
		'tot_gaji' => $tot_gaji,
		'jumlah_pengeluaran' => $jumlah_pengeluaran
                    );

        $this->db->where('kode_gaji', $kode_gaji);
        $update = $this->db->update('gaji', $data);
        if ($update) {
            $this->response($data, 200);
        } else {
            $this->response(array('status' => 'fail', 502));
        }
    }

    // delete gaji
    function index_delete() {
        $kode_gaji = $this->delete('kode_gaji');
        $this->db->where('kode_gaji', $kode_gaji);
        $delete = $this->db->delete('gaji');
        if ($delete) {
            $this->response(array('status' => 'success'), 201);
        } else {
            $this->response(array('status' => 'fail', 502));
        }
    }

}