CREATE TABLE IF NOT EXISTS app_user (
    id integer NOT NULL AUTO_INCREMENT,
    name varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    email varchar(255) NOT NULL,
    CONSTRAINT pk_app_user PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS spaceship (
    id integer NOT NULL AUTO_INCREMENT,
    name varchar(255) NOT NULL,
    passengers_amount integer NOT NULL,
    creation_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_spaceship PRIMARY KEY (id)
);