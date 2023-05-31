<?php

defined('BASEPATH') or exit('No direct script access allowed');

require APPPATH . '/libraries/REST_Controller.php';

use Restserver\Libraries\REST_Controller;

class Register extends REST_Controller
{

    public function __construct()
    {
        // Construct the parent class
        parent::__construct();
        $this->load->model('Users_model');
    }

    public function index_post()
    {
        $this->load->library('form_validation');
        $this->form_validation->set_error_delimiters('','');

        $this->form_validation->set_rules('full_name', 'Full Name', 'required|trim');
        $this->form_validation->set_rules('email', 'Email', 'required|trim|valid_email|is_unique[users.email]');
        $this->form_validation->set_rules('password', 'Password', 'required|trim|min_length[8]');

        if ($this->form_validation->run() == FALSE) 
        {
            $this->response([
                'error'   => true,
                'message' => validation_errors(),
                'message' => preg_replace('/[^A-Za-z0-9_~`\/@!$.%^#&*\\()+-=]/',' ', validation_errors()),
            ], 400);
        } 
        
        else {
            $data = [
                'full_name'  => $this->input->post('full_name', true),
                'email'      => $this->input->post('email', true),
                'password'   => password_hash($this->input->post('password'), PASSWORD_DEFAULT),
            ];

            $this->Users_model->add_users($data);

            $this->response([
                'error'   => false,
                'message' => 'Registration is successful!'
            ], 201);
        }
    }
}
