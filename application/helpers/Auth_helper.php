<?php

defined('BASEPATH') or exit('No direct script access allowed');

require APPPATH . '/libraries/JWT.php';
require APPPATH . '/libraries/Key.php';
require APPPATH . '/libraries/SignatureInvalidException.php';
require APPPATH . '/libraries/BeforeValidException.php';
require APPPATH . '/libraries/CachedKeySet.php';

use Firebase\JWT\JWT;
use Firebase\JWT\Key;

function validateToken()
{
    // $CI =& get_instance(); untuk memanggil variable di luar controller
    $CI =& get_instance();
    $CI->load->model('Token_model');
        
    // token yang diinput di header Authorization
    $header = $CI->input->get_request_header('Authorization');
        
    if (!$header) {
        return false;
    }

    // Validasi token
    try {
        $secretkey = 'dgdsfokdofkopditeurweygwyegwxckosoekmdgsjl'; 
        $decoded   = JWT::decode($header, new Key ($secretkey, 'HS256'));
        $id        = $decoded->uid;

        // Cek apakah token yang diinput sesuai dengan token dalam database
        $user  = $CI->Token_model->get_token($id);
        $token = $user['token']; 
        if ($user && $token == $header) {
            return true;
        } else {
            return false;
        }
    } catch (Exception $e) {
        // Token invalid
        return false;
    }
}

