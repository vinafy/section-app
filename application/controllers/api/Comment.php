<?php
defined('BASEPATH') OR exit('No direct script access allowed');

require APPPATH . '/libraries/REST_Controller.php';
use Restserver\Libraries\REST_Controller;

class Comment extends REST_Controller 
{
    public function __construct(){
        parent::__construct();
        $this->load->model('Comment_model', 'comment');      
    }

    public function index_get()
    {
        if (!validateToken()) {
            return $this->aut_respon();
        }

        $this->load->library('pagination');

        $id = $this->get('id');
        $page = $this->get('page');

        $per_page = 10;
        $current_page = $page ? $page : 1; 
        
        if($id === null){
            $config['base_url'] = site_url('api/forum/index_get');
            $config['total_rows'] = $this->db->count_all('forum_discussion');
            $config['per_page'] = $per_page;

            $this->pagination->initialize($config);
            $comment = $this->comment->get_comment($id, $per_page, $current_page);
        } else {
            $comment = $this->comment->get_comment($id);
        }

        if($comment){
            $this->response([
                'status' => true,
                'data' => $comment
            ], REST_Controller::HTTP_OK);
        } else {
            $this->response([
                'status' => false,
                'message' => 'id not found'
            ], REST_Controller::HTTP_NOT_FOUND);
        }
    }
    
    public function index_delete()
    {
        if (!validateToken()) {
            return $this->aut_respon();
        } 

        $id = $this->delete('id');

        if($id === null) {
            $this->response([
                'status' => false,
                'message' => 'Provide an id!'
            ], REST_Controller::HTTP_BAD_REQUEST);
        } else {
            if($this->comment->delete_comment($id) > 0) {
                $this->response([
                    'status' => true,
                    'data' => $id,
                    'massage' => 'Deleted Comment'
                ], REST_Controller::HTTP_NO_CONTENT);
            } else {
                $this->response([
                    'status' => false,
                    'message' => 'id not found'
                ], REST_Controller::HTTP_NOT_FOUND);
            }
        }
    }

    public function index_post()
    {
        if (!validateToken()) {
            return $this->aut_respon();
        } 

        $data = [
            'id_dcs' => $this->post('id_dcs'), 
            'id_user' => $this->post('id_user'), 
            'comment' => $this->post('comment')
        ];

        if($this->comment->create_comment($data) > 0){
            $this->response([
                'status' => true,
                'message' => 'New comment added'
            ], REST_Controller::HTTP_CREATED);
        } else {
            $this->response([
                'status' => false,
                'message' => 'Failed to add comment'
            ], REST_Controller::HTTP_BAD_REQUEST);
        }
    }

    public function index_put()
    {
        if (!validateToken()) {
            return $this->aut_respon();
        } 

        $id = $this->put('id');
        $data = [
            'id_dcs' => $this->put('id_dcs'), 
            'id_user' => $this->put('id_user'), 
            'comment' => $this->put('comment')
        ];

        if($this->comment->update_comment($data, $id) > 0){
            $this->response([
                'status' => true,
                'message' => 'Update comment successful'
            ], REST_Controller::HTTP_OK);
        } else {
            $this->response([
                'status' => false,
                'message' => 'Failed update comment'
            ], REST_Controller::HTTP_BAD_REQUEST);
        }
    }

    private function aut_respon()
    {
        $this->response([
            'error'   => true,
            'message' => 'Unauthorized, access denied!'
        ], REST_CONTROLLER::HTTP_UNAUTHORIZED);
    }
    
}