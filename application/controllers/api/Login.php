<?php

defined('BASEPATH') or exit('No direct script access allowed');

require APPPATH . '/libraries/REST_Controller.php';
require APPPATH . '/libraries/JWT.php';

use Restserver\Libraries\REST_Controller;
use \Firebase\JWT\JWT;

class Login extends REST_Controller
{
    private $secretkey = 'secretloginkey1234554321';

    public function index_post()
    {
        $this->load->library('form_validation');
        $this->form_validation->set_error_delimiters('','');

        $this->form_validation->set_rules('email', 'Email', 'required|trim|valid_email');
        $this->form_validation->set_rules('password', 'Password', 'required|trim');

        $date = new DateTime();

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
                $payload = array (
                    "uid"   => $users['id_user'],
                    "iat"   => $date->getTimeStamp(),
                );
                
                $token  = JWT::encode($payload, $this->secretkey, 'HS256');

                $this->response([
                    'error'   => false,
                    'message' => 'Successful login!',
                    'result'  => array (
                        'id_user'   => $users['id_user'],
                        'full_name' => $users['full_name'],
                        'token'     => $token, 
                    )
                ], 200);
            }
        }
    }
}