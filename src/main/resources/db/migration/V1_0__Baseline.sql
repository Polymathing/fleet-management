CREATE TABLE IF NOT EXISTS truck
(
    "id"                   BIGINT NOT NULL,
    "license_plate"        VARCHAR(8) UNIQUE NOT NULL,
    "manufacturer"         VARCHAR(255) NOT NULL,
    "model"                VARCHAR(255) NOT NULL,
    "kilometers_per_liter" FLOAT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS location
(
    "id"        BIGINT NOT NULL,
    "name"      VARCHAR(255) NOT NULL,
    "latitude"  DECIMAL(8,6) NOT NULL,
    "longitude" DECIMAL(8,6) NOT NULL,
    PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS delivery_order
(
    "id"                   BIGINT AUTO_INCREMENT NOT NULL,
    "truck_id"             BIGINT NOT NULL,
    "origin_id"   BIGINT NOT NULL,
    "destination_id" BIGINT NOT NULL,
    "distance"             FLOAT NOT NULL,
    "date_time"            TIMESTAMP NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (truck_id) REFERENCES truck(id),
    FOREIGN KEY (origin_id) REFERENCES location(id),
    FOREIGN KEY (destination_id) REFERENCES location(id)
);