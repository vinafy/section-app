<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Profile_model extends CI_Model {
    public function add_profile($data) {
        $this->db->insert('profile', $data);
        return $this->db->insert_id();
    }
}