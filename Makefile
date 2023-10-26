all: network mysql build run

network:
	sudo docker network create springboot-mysql-net || true

build:
	sudo docker build --network springboot-mysql-net -t spring-boot-docker:spring-docker .
run:
	sudo docker run --rm --network springboot-mysql-net --name backend -d -p 8080:8080 spring-boot-docker:spring-docker .

mysql:
	sudo docker run -d --rm -p 3306:3306 --name mysql-docker-container --network springboot-mysql-net -e MYSQL_ROOT_PASSWORD=toor -e MYSQL_DATABASE=amigo -e MYSQL_USER=user -e MYSQL_PASSWORD=user mysql/mysql-server:latest
stop:
	sudo docker stop backend
