FROM postgres
ENV POSTGRES_USER postgres
ENV POSTGRES_PASSWORD postgres
ENV POSTGRES_DB hrm_db
COPY tables.sql /docker-entrypoint-initdb.d/tables.sql
