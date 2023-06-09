<?php

defined('BASEPATH') or exit('No direct script access allowed');

require APPPATH . '/libraries/REST_Controller.php';

use Restserver\Libraries\REST_Controller;
use Firebase\JWT\JWT;

class Login extends REST_Controller
{
    public function __construct()
    {
        // Construct the parent class
        parent::__construct();
        // $this->load->helper('auth_helper');
        $this->load->model('Users_model');
        $this->load->model('Token_model');
    }

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
            ], REST_CONTROLLER::HTTP_BAD_REQUEST);
        } 

        else 
        {
            $email    = $this->input->post('email');
            $password = $this->input->post('password');

            $users = $this->db->get_where('users', ['email' => $email])->row_array();
            
            if (!$users) {
                $this->response([
                    'error'   => true,
                    'message' => 'This email is not registered!',
                ], REST_CONTROLLER::HTTP_BAD_REQUEST);
            }

            elseif (!password_verify($password, $users['password'])) {
                $this->response([
                    'error'   => true,
                    'message' => 'Your password is wrong',
                ], REST_CONTROLLER::HTTP_BAD_REQUEST);
            }

            else {
                $id    = $users['id_user'];
                $image = $this->Users_model->get_profile($id);
                $token = $this->generateToken($id);

                $this->response([
                    'error'   => false,
                    'message' => 'Successful login!',
                    'result'  => array (
                        'id_user'         => $id,
                        'full_name'       => $users['full_name'],
                        'profile_picture' => $image['profile_picture'],
                        'token'           => $token,
                    )
                ], REST_CONTROLLER::HTTP_OK);

                $token_exist = $this->Token_model->get_token($id);
                
                if ($token_exist) {
                    $this->Token_model->update_token($id, $token);
                }

                else {
                    $this->Token_model->add_token($id, $token);
                }
            }
        }
    }

    private function generateToken($id) 
    {
        $date = new DateTime();
        $secretkey = 'dgdsfokdofkopditeurweygwyegwxckosoekmdgsjl';
        $payload = array (
            "uid"   => $id,
            "iat"   => $date->getTimeStamp(),
        );
    
        return JWT::encode($payload, $secretkey, 'HS256');
    }

    // Logout Function
    public function index_get()
    {
        $header = $this->input->get_request_header('Authorization');

        // Helper yang digunakan di seluruh controller
        if (!validateToken()) {
            return $this->response([
                'error'   => true,
                'message' => 'Unauthorized, access denied!'
            ], REST_CONTROLLER::HTTP_UNAUTHORIZED);
        }

        // Response atau semua kondisi jika berhasil autorisasi
        $userid  = $this->Token_model->get_user($header);
        $id      = $userid['id_user'];
        $null    = $this->Token_model->update_token($id, null);

        return $this->response([
            'error'   => false,
            'message' => 'Logout successful!'
        ], REST_CONTROLLER::HTTP_OK);
    }
}