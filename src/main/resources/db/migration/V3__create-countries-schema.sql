CREATE SEQUENCE countries_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;

CREATE TABLE countries (
    id bigint DEFAULT nextval('countries_id_seq') NOT NULL,
    name varchar(255) NOT NULL,
    CONSTRAINT "countries_pkey" PRIMARY KEY ("id")
)