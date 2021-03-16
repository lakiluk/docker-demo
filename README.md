# docker-demo

docker network create cities-network

docker images
docker ps
docker ps -a
docker rm container_id

docker run --name cities-db -e POSTGRES_PASSWORD=skywalker -e POSTGRES_USER=luke -e POSTGRES_DB=cities -p 9999:5432 -v cities-volume:/var/lib/postgresql/data --network cities-network postgres:13.2

docker exec -it container_id bash

-----

docker build -t lukaszdworski/cities-app:1 .

docker run --name cities-app -p 4000:8080 --network cities-network -e CITIES_DB_HOST=cities-db -e CITIES_DB_PORT=5432  -d lukaszdworski/cities-app:1

-----

docker-compose up
docker-compose up -d
docker-compose down

----

docker push lukaszdworski/cities-app:1

----

kubectl apply -f kubernetes/db-config.yaml
kubectl apply -f kubernetes/app-config.yaml

kubectl rollout history deployment/cities-app-deployment
kubectl rollout history deployment/cities-app-deployment --revision 1
kubectl rollout undo deployment/cities-app-deployment --to-revision=1
