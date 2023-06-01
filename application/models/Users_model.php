<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Users_model extends CI_Model {
    public function add_users($data) {
        $this->db->insert('users', $data);
        return $this->db->insert_id();
    }
}