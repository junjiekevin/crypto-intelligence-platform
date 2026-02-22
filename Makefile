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
	@# For now, verify database tables exist
	docker exec crypto-postgres psql -U postgres -d crypto_intel -c "\dt"

clean:
	docker-compose down -v

migrate:
	@echo "Running manual migrations..."
	docker exec -i crypto-postgres psql -U postgres -d crypto_intel < api/src/main/resources/db/migration/V1__Initial_Schema.sql

seed:
	@echo "Seeding database..."
	docker exec -i crypto-postgres psql -U postgres -d crypto_intel < scripts/seed_db.sql
