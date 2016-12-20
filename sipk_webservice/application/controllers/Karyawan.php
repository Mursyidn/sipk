<?php

require APPPATH . '/libraries/REST_Controller.php';

class karyawan extends REST_Controller {

    function __construct($config = 'rest') {
        parent::__construct($config);

    }

    // show data karyawan

    function index_get() {
		$karyawan="SELECT * from karyawan";
		$data_karyawan = array("karyawan" => $this->db->query($karyawan)->result());
        $this->response($data_karyawan, 200);    
    }


}