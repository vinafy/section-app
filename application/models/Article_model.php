<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Article_model extends CI_Model
{
    public function article_by_id($id){
        return $this->db->get_where('article', ['Article_id' => $id])->result_array();
    }

    public function search($keyword, $per_page, $current_page){
        $this->db->like('Article_id',$keyword);
		$this->db->or_like('Title',$keyword);
		$this->db->or_like('Date',$keyword);
		$this->db->or_like('Author',$keyword);
		$this->db->or_like('Reviewer',$keyword);
		
        $this->db->limit($per_page, ($current_page - 1) * $per_page);
        return $this->db->get('article')->result_array();
        
    }
}