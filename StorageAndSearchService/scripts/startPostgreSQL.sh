#bin/zsh

docker run -d \
    --name UDD-postgres \
    -p 5432:5432 \
    -e POSTGRES_PASSWORD=admin \
    -v /home/nemanja/Documents/FTN/Master/UDD/project/data/postgres:/var/lib/postgresql/data \
    postgres
