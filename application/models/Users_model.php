<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Users_model extends CI_Model {
    public function add_users($data) {
        $this->db->insert('users', $data);
        return $this->db->insert_id();
    }

    public function add_image($photo) {
        return $this->db->insert('profile', $photo);
    }

    public function get_profile($id) {
        return $this->db->get_where('profile', ['id_user' => $id])->row_array();
    }
}