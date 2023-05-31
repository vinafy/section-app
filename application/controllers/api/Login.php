<?php

defined('BASEPATH') or exit('No direct script access allowed');

require APPPATH . '/libraries/REST_Controller.php';

use Restserver\Libraries\REST_Controller;

class Login extends REST_Controller
{
    public function index_post()
    {
        $this->load->library('form_validation');
        $this->form_validation->set_error_delimiters('','');

        $this->form_validation->set_rules('email', 'Email', 'required|trim|valid_email');
        $this->form_validation->set_rules('password', 'Password', 'required|trim');

        if ($this->form_validation->run() == FALSE) 
        {
            $this->response([
                'error'   => true,
                'message' => validation_errors(),
                'message' => preg_replace('/[^A-Za-z0-9_~`\/@!$.%^#&*\\()+-=]/',' ', validation_errors()),
            ], 400);
        } 
        else {
            $email    = $this->input->post('email');
            $password = $this->input->post('password');

            $users = $this->db->get_where('users', ['email' => $email])->row_array();
            
            if (!$users) {
                $this->response([
                    'error'   => true,
                    'message' => 'This email is not registered!',
                ], 400);
            }

            elseif (!password_verify($password, $users['password'])) {
                $this->response([
                    'error'   => true,
                    'message' => 'Your password is wrong',
                ], 400);
            }

            else {
                $this->response([
                    'error'   => false,
                    'message' => 'Successful login!',
                ], 200);
            }
        }
    }
}