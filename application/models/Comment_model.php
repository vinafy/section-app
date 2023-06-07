<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Comment_model extends CI_Model
{
    public function get_comment($id = null, $per_page, $current_page)
    {
        $this->db->select('c.id_cmn, c.comment, c.date_cmn, u.full_name, p.profile_picture');
        $this->db->from('comment_discussion AS c');
        $this->db->join('profile AS p', 'c.id_user = p.id_user');
        $this->db->join('users AS u', 'c.id_user = u.id_user');
        if($id === null){
            $this->db->limit($per_page, ($current_page - 1) * $per_page);
            $query = $this->db->get()->result_array();
        }  else {
            $this->db->where('id_cmn', $id);
            $query = $this->db->get()->result_array();
        }

        return $query;
    }

    public function delete_comment($id) 
    {
        $this->db->where('id_cmn', $id);
        $this->db->delete('comment_discussion');
        return $this->db->affected_rows();
    }

    public function create_comment($data)
    {
        $this->db->insert('comment_discussion', $data);
        return $this->db->affected_rows();
    } 

    public function update_comment($data, $id)
    {
        $this->db->where('id_cmn', $id);
        $this->db->update('comment_discussion', $data);
        return $this->db->affected_rows();
    } 
    
}