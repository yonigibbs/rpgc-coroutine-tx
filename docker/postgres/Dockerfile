FROM postgres
ENV POSTGRES_DB postgres
ENV POSTGRES_USER postgres
ENV POSTGRES_PASSWORD postgres
RUN apt-get update
COPY create-db.sql /docker-entrypoint-initdb.d/