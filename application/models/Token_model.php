<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Token_model extends CI_Model {
    public function get_token($id) {
        return $this->db->get_where('key', ['id_user' => $id])->row_array();
    }

    public function get_user($header) {
        return $this->db->get_where('key', ['token' => $header])->row_array();
    }

    public function add_token($id, $token) {
        $data = [
            'id_user'   => $id,
            'token'     => $token
        ];

        $this->db->insert('key', $data);
    }

    public function update_token($id, $token) {
        $this->db->where('id_user', $id);
        $this->db->from('key');
        $this->db->update('key', ['token' => $token]);
    }
}