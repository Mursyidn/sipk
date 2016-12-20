<?php 

class Mod_gaji extends CI_Model{
	function tampil_data($table,$number,$offset){
		return $query = $this->db->get($table,$number,$offset)->result();
	}

	function tampil_data_gaji(){
	if ($this->session->userdata('kode_perusahaan') == 'Semua'){
		$gaji="SELECT gaji.*, karyawan.nik, karyawan.nama_karyawan, perusahaan.nama_perusahaan from gaji,karyawan,perusahaan where gaji.kode_karyawan=karyawan.kode_karyawan AND karyawan.kode_perusahaan=perusahaan.kode_perusahaan";
	} else {
		$gaji="SELECT gaji.*, karyawan.nik, karyawan.nama_karyawan, perusahaan.nama_perusahaan from gaji,karyawan,perusahaan where karyawan.kode_perusahaan='".$this->session->userdata('kode_perusahaan')."' and gaji.kode_karyawan=karyawan.kode_karyawan AND karyawan.kode_perusahaan=perusahaan.kode_perusahaan";

	}
	return $data =  $this->db->query($gaji)->result();
	}

	function data_gaji($kode_gaji){
	$gaji="SELECT gaji.*, karyawan.*, perusahaan.nama_perusahaan, divisi.nama_divisi from gaji,karyawan,perusahaan,divisi where gaji.kode_gaji=".$kode_gaji." AND gaji.kode_karyawan=karyawan.kode_karyawan AND karyawan.kode_perusahaan=perusahaan.kode_perusahaan AND karyawan.kode_divisi=divisi.kode_divisi";
	return $data =  $this->db->query($gaji)->result();
	}

	function tampil_data_karyawan(){
		if ($this->session->userdata('kode_perusahaan') == 'Semua'){
		$karyawan="SELECT karyawan.*, divisi.nama_divisi, perusahaan.nama_perusahaan from karyawan,divisi,perusahaan where karyawan.kode_divisi=divisi.kode_divisi and karyawan.kode_perusahaan=perusahaan.kode_perusahaan";
		} else{
		$karyawan="SELECT karyawan.*, divisi.nama_divisi, perusahaan.nama_perusahaan from karyawan,divisi,perusahaan where karyawan.kode_perusahaan='".$this->session->userdata('kode_perusahaan')."' and karyawan.kode_divisi=divisi.kode_divisi and karyawan.kode_perusahaan=perusahaan.kode_perusahaan";
		}
		return $data =  $this->db->query($karyawan)->result();
	}

	function jumlah_data($table){
		return $this->db->get($table)->num_rows();
	}

	function ambil_data($table){
		return $this->db->get($table)->result();
	}

	function ambil_data2($table,$where){
		return $query = $this->db->get_where($table,$where)->result();
	}	

	function num($table,$where){
		return $this->db->get_where($table,$where)->num_rows()->result();
	}
	
	function tambah_data($data,$table){
		$this->db->insert($table,$data);
	}

	function edit_data($where,$table){		
		return $this->db->get_where($table,$where);
	}

	function update_data($where,$data,$table){
		$this->db->where($where);
		$this->db->update($table,$data);
	}

	function hapus_data($where,$table){
		$this->db->where($where);
		$this->db->delete($table);
	}
}