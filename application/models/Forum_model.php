<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class forum_model extends CI_Model
{
    public function get_forum($id = null, $per_page, $current_page)
    {
        $this->db->select('f.id_dcs, f.theme_dcs, f.publish_date_dcs, f.body_dcs, u.full_name, p.profile_picture');
        $this->db->from('forum_discussion AS f');
        $this->db->join('profile AS p', 'f.id_user = p.id_user');
        $this->db->join('users AS u', 'f.id_user = u.id_user');
        if($id === null){
            $this->db->limit($per_page, ($current_page - 1) * $per_page);
            $query = $this->db->get()->result_array();
        } else {
            $this->db->where('id_dcs', $id);
            $query = $this->db->get()->result_array();
        }

        return $query;
    }

    public function delete_forum($id) 
    {
        $this->db->where('id_dcs', $id);
        $this->db->delete('forum_discussion');
        return $this->db->affected_rows();
    }

    public function create_forum($data)
    {
        $this->db->insert('forum_discussion', $data);
        return $this->db->affected_rows();
    } 

    public function update_forum($data, $id)
    {
        $this->db->where('id_dcs', $id);
        $this->db->update('forum_discussion', $data);
        return $this->db->affected_rows();
    } 
    
}