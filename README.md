# Vending Demo App with Spring-boot MySQL React Docker NGINX

## Project overview

There are 2 pages for the project which are home page and admin page. There is no link for the admin page. 
The url for admin page is _/admin_. 

## How to install

### 1. Pre-requests
This project is configured for running on a docker image. Docker must be installed on the server.
Docker can be installed:

```bash
sudo apt install docker.io
```

In order to make things easier, I created _Makefile_. Make can be installed:
```bash
sudo apt install make
```

### 2. The steps are as follows

* Creating network for docker images
* Running MySQL database inside docker
* Building docker image of Spring-boot app
* Running our image

```bash
make network
make mysql
make build
make run
```

---
That's all. The backend runs on port _8080_. For the api documentation please visit _::8080/swagger-ui.html_

## Installing Frontend App

### 1. Clone the project

https://github.com/race46/vending-frontend

### 2. Run Makefile

```bash
make build
make run
```

---
Frontend project is also include nginx as a reverse proxy for the backend.

<h3 style="color:red;">Warning</h3>
The database does not have any record at first. Go to admin page and tap the _RESET_ button for initializing project. Username is <b>admin</b> and password( default: admin123 ) is read from application.properties.