<?php
defined('BASEPATH') OR exit('No direct script access allowed');

require APPPATH . '/libraries/REST_Controller.php';
use Restserver\Libraries\REST_Controller;


class Article extends REST_Controller 
{
    public function __construct()
    {
        parent::__construct();
        $this->load->model('Article_model', 'article');      
    }

    public function index_get()
    {
        if (!validateToken()) {
            return $this->aut_respon();
        }

        $this->load->library('pagination');

        $keyword = $this->get('keyword');
        $id = $this->get('id');

        $page = $this->get('page');

        $per_page = 10;
        $current_page = $page ? $page : 1; 
        
        if($id !== null){
            $article = $this->article->article_by_id($id);
        } else if($keyword !== null) {
            $config['base_url'] = site_url('api/forum/index_get');
            $config['total_rows'] = $this->db->count_all('forum_discussion');
            $config['per_page'] = $per_page;

            $this->pagination->initialize($config);

            $article = $this->article->search($keyword, $per_page, $current_page);
        }

        if($article){
            $this->response([
                'status' => true,
                'data' => $article
            ], REST_Controller::HTTP_OK);
        } else if($id === null){
            $this->response([
                'status' => false,
                'data' => 'id not found'
            ], REST_Controller::HTTP_NOT_FOUND);
        } else {
            $this->response([
                'status' => false,
                'message' => 'not found'
            ], REST_Controller::HTTP_NOT_FOUND);
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