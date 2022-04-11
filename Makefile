PROJECT = beauty-space
VOLUME = beauty-space

dev-env-up:
	docker volume create --name=$(VOLUME)
	docker compose -p $(PROJECT) -f docker/docker-compose.yaml up -d --build --remove-orphans

run-tests:
	mvn surefire:test

dev-env-down:
	docker compose -p $(PROJECT) -f docker/docker-compose.yaml down -v --remove-orphans

dev-env-clean: dev-env-down
	docker volume remove $(VOLUME)

start-app-dev:
	spring-boot:start -Dspring-boot.run.profiles=dev -f pom.xml

stop:
	mvn spring-boot:stop
