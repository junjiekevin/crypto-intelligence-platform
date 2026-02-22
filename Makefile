.PHONY: up down build logs test clean seed

up:
	docker-compose up -d

down:
	docker-compose down

build:
	docker-compose build

logs:
	docker-compose logs -f

test:
	@echo "Running tests..."
	@# TODO: Add test commands for api and rag-service

clean:
	docker-compose down -v
	@# TODO: Add more clean logic

seed:
	@echo "Seeding database..."
	@# TODO: Link to Phase 1 seed script
