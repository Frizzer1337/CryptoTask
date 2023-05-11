create table client
(
    id                int auto_increment
        primary key,
    price_change      decimal(15, 5) not null,
    symbol            varchar(45)    not null,
    username          varchar(45)    not null,
    registration_time timestamp      not null on update CURRENT_TIMESTAMP,
    start_price       decimal(15, 2) not null,
    constraint id
        unique (id)
);

create table coin
(
    id        int auto_increment
        primary key,
    symbol    varchar(45)    not null,
    price     decimal(15, 2) not null,
    crypto_id int            not null,
    constraint cryptoId
        unique (crypto_id),
    constraint id
        unique (id),
    constraint symbol
        unique (symbol)
);
