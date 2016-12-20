<?php

require APPPATH . '/libraries/REST_Controller.php';

class login extends REST_Controller {

    function __construct($config = 'rest') {
        parent::__construct($config);

    }

    // show data 

    function index_get() {
        $username = $this->get('username');
        $password = $this->get('password');
        $this->db->where('username', $username);
        	$data['admin']= $this->db->get_where('admin',array('kode_perusahaan' => 'Semua'))->result();
        foreach ($data['admin'] as $adm) {
			if (password_verify($password,$adm->password)){
			    $this->response($data['admin'], 200);
    	    }
    	else{
				$this->response(array('status' => 'failed'));			
    		}
		}
	}

}