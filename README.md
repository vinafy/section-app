# SECTION APP
```
This API provides access to login, register, article, forum, and comment features.
```

## Codeigniter-RestServer
Check the recent version at https://github.com/chriskacerguis/codeigniter-restserver

My alternate version https://github.com/ardisaurus/old-rest-ci  

# Api Documentation
```
The following is the API documentation for the Application section.
```
## Endpoint
`http://34.101.137.177/section-app/index.php/api/endpoint`

## Register
- URL  
    - `/register`

- Method
    - POST
    
- Request Body
    | Nama      | Data Type | Description                   |
    | --------  | --------- | ----------------------------- |
    | full_name | string    | required filled               |
    | email     | email     | must be unique                |
    | Password  | string    | must be at least 8 characters |

- Respons
    ```json
    {
        "error": false,
        "message": "Registration is successful!"
    }
    ```
## Login
- URL
    - `/login`

- Method
    - POST

- Request Body
    | Nama     | Data Type | 
    | -------- | --------- | 
    | email    | email     | 
    | Password | string    | 

- Respons
    ```json
    {
        "error": false,
        "message": "Successful login!",
        "result": {
            "id_user": "1",
            "full_name": "Rohid",
            "profile_picture": "https://storage.googleapis.com/section-app-389109.appspot.com/default_image.png",
            "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiIxMiIsImlhdCI6MTY4NjY3MDA4M30.hScMENWotQ1wCsjYsmG53k94U9tShwuBcD_p5h1zS9g"
        }
    }
    ```

## Add New Forum Discussion
- URL
    - `/forum`

- Method
    - POST

- Header
    - `key` : `Authorization` 
    - `value` : `Api Key <token>`

- Request Body
    | Nama     | Data Type |
    | -------- | --------- | 
    | id_user  | int       |
    | theme_dcs | string   |
    | body_dcs | text      | 

- Respons
    ```json
    {
        "status": true,
        "message": "New forum added"
    }
    ```

##  GET All Forum Discussion
- URL
    - `/forum`

- Method
    - GET

- Header
    - `key` : `Authorization` 
    - `value` : `Api Key <token>`

- Request Body
    | Nama     | Data Type | Description |
    | -------- | --------- | ----------- |
    | page     | int       | optional, currently page for pagination |

- Respons
    ```json
    {
        "status": true,
        "data": [
            {
            "id_dcs": "1",
            "theme_dcs": "edu",
            "publish_date_dcs": "2023-06-06 20:05:36",
            "body_dcs": "example",
            "full_name": "Rohid",
            "profile_picture": "https://storage.googleapis.com/section-app-389109.appspot.com/default_image.png"
            },
        ]
    }
    ```

## Detail Forum Discussion
- URL
    - `/forum`

- Method
    - GET

- Header
    - `key` : `Authorization` 
    - `value` : `Api Key <token>`

- Request Body
    | Nama     | Data Type | Description |
    | -------- | --------- | ----------- |
    | id       | int       | requred     |

- Respons
    ```json
    {
        "status": true,
        "data": [
            {
            "id_dcs": "1",
            "theme_dcs": "edu",
            "publish_date_dcs": "2023-06-06 20:05:36",
            "body_dcs": "example",
            "full_name": "Rohid",
            "profile_picture": "https://storage.googleapis.com/section-app-389109.appspot.com/default_image.png"
            },
        ]
    }
    ```

## Edit Forum Discussion
- URL
    - `/forum`

- Method
    - PUT

- Header
    - `key` : `Authorization` 
    - `value` : `Api Key <token>`

- Request Body
    | Nama     | Data Type |
    | -------- | --------- | 
    | id_user  | int       |
    | theme_dcs | string   |
    | body_dcs | text      |
    | id        | int      | 

- Respons
    ```json
    {
        "status" : true,
        "message" : "Update forum successful"
    }
    ```

## Delete Forum Discussion
- URL
    - `/forum`

- Method
    - DELETE

- Header
    - `key` : `Authorization` 
    - `value` : `Api Key <token>`

- Request Body
    | Nama     | Data Type |
    | -------- | --------- | 
    | id        | int      | 

- Respons
    ```json
    {
        "status" : true,
        "data" : "1",
        "message" : "Deleted forum"
    }
    ```

## Add New Comment
- URL
    - `/api/comment`

- Parameters
    | Nama     | Data Type | 
    | -------- | --------- | 
    | id       | integer   |   

- Method
    - POST

- Headers
    - `Key` : `Authorization`
    - `Value` : `API Key <token>`

- Request Body
    | Nama     | Data Type | 
    | -------- | --------- | 
    | id_dcs   | integer   |   
    | id_user  | integer   |
    | comment  | string    |  

- Response
    ```json
    {
        "status": true,
        "message": "New comment added"
    }
    ```
## Delete Comment
- URL
    - `/api/comment`

- Parameters
    | Nama     | Data Type | 
    | -------- | --------- | 
    | id       | integer   |   

- Method
    - DELETE

- Headers
    - `Key` : `Authorization`
    - `Value` : `API Key <token>`

- Request Body
    | Nama     | Data Type | 
    | -------- | --------- | 
    | id       | integer   |   

- Response
    ```json
    {
        "status": true,
        "data": "7",
        "message": "Deleted Comment"
    }
    ```
## Change Comment
- URL
    - `/api/comment`

- Method
    - PUT

- Headers
    - `Key` : `Authorization`
    - `Value` : `API Key <token>`

- Request Body
    | Nama     | Data Type | 
    | -------- | --------- | 
    | id       | integer   |  
    | id_dcs   | integer   |   
    | id_user  | integer   |
    | comment  | string    |  

- Response
    ```json
    {
        "status": true,
        "message": "Update comment successful"
    }
    ```
## Get All Comments
- URL
    - `/api/comment`

- Parameters
    | Nama     | Data Type | Description |
    | -------- | --------- | ---------   |
    | page     | integer   | optional, currently page for pagination          |

- Method
    - GET

- Headers
    - `Key` : `Authorization`
    - `Value` : `API Key <token>`

- Response
    ```json
    {
        "status": true,
        "data": [
            {
                "comment": "Excellent!",
                "date_cmn": "2023-06-12 07:05:54",
                "full_name": "Vina Fujiyanti",
                "profile_picture": "https://storage.googleapis.com/section-app-389109.appspot.com/default_image.png"
            }
        ]
    }
    ```
## Get Detail Comment
- URL
    - `/api/comment`

- Parameters
    | Nama     | Data Type | Description |
    | -------- | --------- | ---------   |
    | page     | integer   | optional, currently page for pagination |
    | id       | integer   | to get specific comment                 |

- Method
    - GET

- Headers
    - `Key` : `Authorization`
    - `Value` : `API Key <token>`

- Response
    ```json
    {
        "status": true,
        "data": [
            {
                "comment": "Excellent!",
                "date_cmn": "2023-06-12 07:05:54",
                "full_name": "Vina Fujiyanti",
                "profile_picture": "https://storage.googleapis.com/section-app-389109.appspot.com/default_image.png"
            }
        ]
    }
    ```

## Detail Article
- URL
    - `/article`

- Method
    - GET

- Header
    - `key` : `Authorization` 
    - `value` : `Api Key <token>`

- Request Body
    | Nama     | Data Type | Description |
    | -------- | --------- | ----------- |
    | id       | int       | requred     |

- Respons
    ```json
    {
        "status": true,
        "data": [
            {
                "Article_id": "1",
                "Title": "4 Penyebab Sperma Encer dan Cara Mengatasinya",
                "Date": "2020-03-30",
                "Author": "Redaksi DokterSehat",
                "Reviewer": "dr. Jati Satriyo",
                "Article": "Lorem Ipsum",
                "Url": "https://doktersehat.com/informasi/kesehatan-reproduksi-pria/sperma-encer/"
            }
        ]
    }
    ```

## Detail Article
- URL
    - `/article`

- Method
    - GET

- Header
    - `key` : `Authorization` 
    - `value` : `Api Key <token>`

- Request Body
    | Nama     | Data Type | Description |
    | -------- | --------- | ----------- |
    | keyword       | string       | requred, keywords to search for articles based on title, Author, Reviewer, etc.    |
    | page     | int       | optional, currently page for pagination |

- Respons
    ```json
    {
        "status": true,
        "data": [
            {
                "Article_id": "1",
                "Title": "4 Penyebab Sperma Encer dan Cara Mengatasinya",
                "Date": "2020-03-30",
                "Author": "Redaksi DokterSehat",
                "Reviewer": "dr. Jati Satriyo",
                "Article": "Lorem Ipsum",
                "Url": "https://doktersehat.com/informasi/kesehatan-reproduksi-pria/sperma-encer/"
            }
        ]
    }
    ```

## Top 10 Recomendation Article
- URL Enpoint
    - `https://section-czc62lmnuq-et.a.run.app/api/user_id`

- Method
    - GET

- Respons
    ```json
    {
        "massage": "top 10 recommendations article for user 12",
        "title": [
            "Kanker Testis: Gejala, Penyebab, Diagnosis, dan Pengobatan",
            "Benarkah Penderita Asma Lebih Sulit Hamil? Cek Faktanya di Sini",
            "Mengenal Chasteberry dan Manfaatnya bagi Kesehatan",
            "7 Fakta Tentang Kutil Kelamin (HPV) yang Sering Terlewatkan",
            "Apakah Transplantasi Penis Secara Utuh Bisa Dilakukan?",
            "9 Cara Mengatasi Disfungsi Ereksi dengan Efektif dan Aman",
            "Tips Memilih Produk Perawatan Bayi yang Aman Bagi Kulit",
            "9 Penyakit Orang Kurus yang Harus Diwaspadai",
            "Ilmuwan Berhasil Menciptakan Sel Telur yang Berasal Dari Kulit Tikus",
            "Mengenal Hormon Progesteron, Salah Satu Hormon Seks pada Wanita"
        ]
    }
