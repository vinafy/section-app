<?php
defined('BASEPATH') OR exit('No direct script access allowed');

require APPPATH . '/libraries/REST_Controller.php';
use Restserver\Libraries\REST_Controller;

class Forum extends REST_Controller 
{
    public function __construct(){
        parent::__construct();
        $this->load->model('Forum_model', 'forum');
        $this->load->helper('Auth_helper');       
    }

    public function index_get()
    { 
        if (!validateToken()) {
            return $this->aut_respon();
        }
        $this->load->library('pagination');

        $page = $this->get('page');
        $id = $this->get('id');

        $per_page = 10;
        $current_page = $page ? $page : 1; 
        
        if($id === null){
            $config['base_url'] = site_url('api/forum/index_get');
            $config['total_rows'] = $this->db->count_all('forum_discussion');
            $config['per_page'] = $per_page;

            $this->pagination->initialize($config);
            $forum = $this->forum->get_forum($id, $per_page, $current_page);
        } else {
            $forum = $this->forum->get_forum($id);
        }

        if($forum){
            $this->response([
                'status' => true,
                'data' => $forum,
            ], REST_Controller::HTTP_OK);
        } else {
            $this->response([
                'status' => false,
                'message' => 'Not found'
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
            if($this->forum->delete_forum($id) > 0) {
                $this->response([
                    'status' => true,
                    'data' => $id,
                    'massage' => 'Deleted forum'
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
            'id_user' => $this->post('id_user'), 
            'theme_dcs' => $this->post('theme_dcs'), 
            'body_dcs' => $this->post('body_dcs')
        ];

        if($this->forum->create_forum($data) > 0){
            $this->response([
                'status' => true,
                'message' => 'New forum added'
            ], REST_Controller::HTTP_CREATED);
        } else {
            $this->response([
                'status' => false,
                'message' => 'Failed to add forum'
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
            'id_user' => $this->put('id_user'), 
            'theme_dcs' => $this->put('theme_dcs'), 
            'body_dcs' => $this->put('body_dcs')
        ];

        if($this->forum->update_forum($data, $id) > 0){
            $this->response([
                'status' => true,
                'message' => 'Update forum successful'
            ], REST_Controller::HTTP_OK);
        } else {
            $this->response([
                'status' => false,
                'message' => 'Failed update forum'
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
