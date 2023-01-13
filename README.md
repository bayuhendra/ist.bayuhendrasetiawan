# ist.bayuhendrasetiawan

Api Document di Postman

List API
http://localhost:8081/api/ist/user/save-user
http://localhost:8081/api/ist/user/get-list
http://localhost:8081/api/ist/user/{id}
http://localhost:8081/api/ist/auth/signin
http://localhost:8081/api/ist/user/get-data-integration
POST
save-user
http://localhost:8081/api/ist/user/save-user
Save data user

Bodyraw (json)
json
{
  "username": "bayu",
  "password": "bayu"
}
GET
get-list
http://localhost:8081/api/ist/user/get-list
Get list data user

Bodyraw (json)
json
{}
GET
get-data-integration
http://localhost:8081/api/ist/user/get-data-integration
Get data from https://swapi.py4e.com/api/people/ with api rest.

Bodyraw (json)
json
{}
PUT
edit-user
http://localhost:8081/api/ist/user/11
edit data user

Bodyraw (json)
json
{
  "username": "bayuhendra",
  "password": "bayuhendra"
}
POST
signin
http://localhost:8081/api/ist/auth/signin
Login user

Bodyraw (json)
json
{
  "username": "bayuhendra",
  "password": "bayuhendra"
} 