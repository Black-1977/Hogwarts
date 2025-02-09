create table persons
(
    id         int primary key,
    name       text,
    age        int,
    is_license boolean,
    car_id     int references cars (id)
);

create table cars
(
    id    int primary key,
    brand text,
    model text,
    cost  bigint,
);