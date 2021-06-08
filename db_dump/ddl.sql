create table countries
(
    id       int unsigned auto_increment
        primary key,
    iso_code int unsigned not null,
    name     varchar(50)  not null
);

create table producers
(
    id         int unsigned auto_increment
        primary key,
    name       varchar(255) not null,
    country_id int unsigned not null,
    constraint producers_countries_id_fk
        foreign key (country_id) references countries (id)
);

create table purchases
(
    id          int unsigned not null
        primary key,
    produser_id int unsigned not null,
    number      int unsigned not null,
    date        timestamp    not null,
    constraint purchases_producers_id_fk
        foreign key (produser_id) references producers (id)
);

create table users
(
    id       int unsigned auto_increment
        primary key,
    name     varchar(50)  not null,
    surname  varchar(50)  not null,
    password varchar(255) not null,
    email    varchar(255) not null,
    phone    varchar(20)  not null
);

create table orders
(
    id            int unsigned auto_increment
        primary key,
    user_id       int unsigned                                                             not null,
    number        int unsigned                                                             not null,
    date_delivery timestamp                                                                not null,
    date_create   timestamp                                                                not null,
    status        enum ('created', 'delivered', 'completed', 'refunded') default 'created' not null,
    constraint orders_users_id_fk
        foreign key (user_id) references users (id)
);

create table sessions
(
    id           int unsigned auto_increment
        primary key,
    session_code text         not null,
    user_id      int unsigned null,
    constraint sessions_users_id_fk
        foreign key (user_id) references users (id)
);

create table cart
(
    id         int unsigned auto_increment
        primary key,
    session_id int unsigned  not null,
    sum        decimal(8, 2) null,
    user_id    int           null,
    constraint cart_user_id_uindex
        unique (user_id),
    constraint cart_sessions_id_fk
        foreign key (session_id) references sessions (id)
);

create definer = root@localhost view v_cart_info_session as
select `cd`.`id`                AS `id`,
       `cd`.`quantity`          AS `quantity`,
       `sd`.`quantity`          AS `store_quantity`,
       `sd`.`price`             AS `price`,
       `sd`.`amount_in_package` AS `amount_in_package`,
       `d`.`name`               AS `item_name`,
       `c`.`session_id`         AS `session_id`,
       `c`.`id`                 AS `cart_id`,
       `sd`.`id`                AS `detail_id`,
       `s`.`session_code`       AS `session_code`
from ((((((`auto_market`.`sessions` `s` left join `auto_market`.`users` `u` on ((`s`.`user_id` = `u`.`id`))) join `auto_market`.`cart` `c` on ((
        ((`u`.`id` is not null) and (`c`.`user_id` = `u`.`id`)) or
        ((`u`.`id` is null) and (`s`.`id` = `c`.`session_id`))))) join `auto_market`.`cart_details` `cd` on ((`c`.`id` = `cd`.`cart_id`))) join `auto_market`.`stored_details` `sd` on ((`cd`.`stored_detail_id` = `sd`.`id`))) join `auto_market`.`producer_details` `pd` on ((`sd`.`producer_detail_id` = `pd`.`id`)))
         join `auto_market`.`details` `d` on ((`pd`.`detail_id` = `d`.`id`)));

create definer = root@localhost view v_cart_info_user as
select `cd`.`id`                AS `id`,
       `cd`.`quantity`          AS `quantity`,
       `sd`.`quantity`          AS `store_quantity`,
       `sd`.`price`             AS `price`,
       `sd`.`amount_in_package` AS `amount_in_package`,
       `d`.`name`               AS `item_name`,
       `c`.`session_id`         AS `session_id`,
       `c`.`id`                 AS `cart_id`,
       `sd`.`id`                AS `detail_id`,
       `u`.`id`                 AS `user_id`
from (((((`auto_market`.`users` `u` join `auto_market`.`cart` `c` on (((`c`.`user_id` = `u`.`id`) or (`u`.`id` is null)))) join `auto_market`.`cart_details` `cd` on ((`c`.`id` = `cd`.`cart_id`))) join `auto_market`.`stored_details` `sd` on ((`cd`.`stored_detail_id` = `sd`.`id`))) join `auto_market`.`producer_details` `pd` on ((`sd`.`producer_detail_id` = `pd`.`id`)))
         join `auto_market`.`details` `d` on ((`pd`.`detail_id` = `d`.`id`)));

create definer = root@localhost view v_detail_info as
select `sd`.`id`                AS `id`,
       `sd`.`price`             AS `price`,
       `sd`.`quantity`          AS `quantity`,
       `dgl`.`id`               AS `detail_group_list_id`,
       `dgl`.`text_title`       AS `detail_group_list_text_title`,
       `pd`.`article`           AS `article`,
       `pd`.`code`              AS `producer_detail_code`,
       `pd`.`amount_in_package` AS `amount_in_package`,
       `d`.`id`                 AS `detail_id`,
       `d`.`name`               AS `detail_name`,
       `p`.`name`               AS `producer_name`,
       `c`.`name`               AS `country_name`
from (((((`auto_market`.`stored_details` `sd` join `auto_market`.`producer_details` `pd` on ((`pd`.`id` = `sd`.`producer_detail_id`))) join `auto_market`.`details` `d` on ((`d`.`id` = `pd`.`detail_id`))) join `auto_market`.`producers` `p` on ((`p`.`id` = `pd`.`producer_id`))) left join `auto_market`.`countries` `c` on ((`p`.`country_id` = `c`.`id`)))
         left join `auto_market`.`detail_group_list` `dgl` on ((`dgl`.`id` = `d`.`detail_group_list_id`)));

create definer = root@localhost view v_group_detail_info as
select concat(`dg`.`id`, '_', `d`.`id`) AS `id`,
       `dg`.`id`                        AS `detail_group_id`,
       `dg`.`name`                      AS `name`,
       `dg`.`code`                      AS `code`,
       `sd`.`id`                        AS `detail_id`,
       `d`.`name`                       AS `detail_name`
from (((`auto_market`.`details_groups` `dg` join `auto_market`.`details` `d` on ((`d`.`group_id` = `dg`.`id`))) join `auto_market`.`producer_details` `pd` on ((`d`.`id` = `pd`.`detail_id`)))
         join `auto_market`.`stored_details` `sd` on ((`sd`.`producer_detail_id` = `pd`.`id`)));

-- comment on view v_group_detail_info not supported: View 'auto_market.v_group_detail_info' references invalid table(s) or column(s) or function(s) or definer/invoker of view lack rights to use them

create definer = root@localhost view v_order_detail as
select `od`.`id`                AS `id`,
       `od`.`quantity`          AS `quantity`,
       `od`.`price`             AS `price`,
       `od`.`order_id`          AS `order_id`,
       `od`.`refunded_quantity` AS `refunded_quantity`,
       `od`.`stored_detail_id`  AS `stored_detail_id`,
       `d`.`name`               AS `name`
from (((`auto_market`.`order_details` `od` join `auto_market`.`stored_details` `sd` on ((`od`.`stored_detail_id` = `sd`.`id`))) join `auto_market`.`producer_details` `pd` on ((`pd`.`id` = `sd`.`producer_detail_id`)))
         join `auto_market`.`details` `d` on ((`d`.`id` = `pd`.`detail_id`)));

-- Cyclic dependencies found

create table cart_details
(
    id               int unsigned auto_increment
        primary key,
    cart_id          int unsigned not null,
    stored_detail_id int unsigned not null,
    quantity         int unsigned not null,
    constraint cart_details_cart_id_stored_detail_id_uindex
        unique (cart_id, stored_detail_id),
    constraint cart_details_cart_id_fk
        foreign key (cart_id) references cart (id),
    constraint cart_details_stored_details_id_fk
        foreign key (stored_detail_id) references stored_details (id)
);

-- Cyclic dependencies found

create table details
(
    id                   int unsigned auto_increment,
    detail_group_list_id int          null,
    name                 varchar(255) not null,
    article_original     text         null,
    code                 text         null,
    constraint Details_id_uindex
        unique (id),
    constraint details_detail_group_list_id_fk
        foreign key (detail_group_list_id) references detail_group_list (id)
);

alter table details
    add primary key (id);

create table producer_details
(
    id                int unsigned auto_increment,
    detail_id         int unsigned           not null,
    producer_id       int unsigned           not null,
    code              text                   not null,
    article           varchar(100)           null,
    price             decimal(8, 2)          not null,
    amount_in_package int unsigned default 1 not null,
    constraint Producer_details_id_uindex
        unique (id),
    constraint producer_details_details_id_fk
        foreign key (detail_id) references details (id),
    constraint producer_details_producers_id_fk
        foreign key (producer_id) references producers (id)
);

alter table producer_details
    add primary key (id);

create table stored_details
(
    id                 int unsigned auto_increment
        primary key,
    producer_detail_id int unsigned           not null,
    price              decimal(8, 2)          not null,
    quantity           int unsigned           not null,
    amount_in_package  int unsigned default 1 not null,
    constraint stored_details_producer_details_id_fk
        foreign key (producer_detail_id) references producer_details (id)
);

create table order_details
(
    id                int unsigned auto_increment
        primary key,
    order_id          int unsigned           null,
    stored_detail_id  int unsigned           null,
    price             decimal(8, 2) unsigned not null,
    quantity          int unsigned           not null,
    refunded_quantity int unsigned default 0 not null,
    constraint order_details_order_id_fk
        foreign key (order_id) references orders (id),
    constraint order_details_stored_details_id_fk
        foreign key (stored_detail_id) references stored_details (id)
);

create table order_refund_request
(
    id              int auto_increment
        primary key,
    user_id         int unsigned                                                  not null,
    order_detail_id int unsigned                                                  not null,
    text            text                                                          null,
    date_create     timestamp                                                     not null,
    date_closed     timestamp                                                     null,
    status          enum ('created', 'waiting_delivery', 'completed', 'declined') null,
    quantity        int                                                           not null,
    constraint order_refund_request_order_details_id_fk
        foreign key (order_detail_id) references order_details (id),
    constraint order_refund_request_users_id_fk
        foreign key (user_id) references users (id)
);

create table purchase_details
(
    id                int unsigned auto_increment
        primary key,
    purchases_id      int unsigned           not null,
    stored_detail_id  int unsigned           not null,
    amount_in_package int unsigned default 1 not null,
    price             decimal(8, 2) unsigned not null,
    quantity          int unsigned           not null,
    constraint purchase_details_purchases_id_fk
        foreign key (purchases_id) references purchases (id),
    constraint purchase_details_stored_details_id_fk
        foreign key (stored_detail_id) references stored_details (id)
);

create table synonyms
(
    id        int unsigned auto_increment
        primary key,
    name      varchar(255) not null,
    detail_id int unsigned not null,
    constraint synonyms_details_id_fk
        foreign key (detail_id) references details (id)
);

-- Cyclic dependencies found

create table detail_image_position
(
    id                   int auto_increment
        primary key,
    x                    double       not null,
    y                    double       not null,
    width                double       not null,
    height               double       not null,
    detail_id            int unsigned not null,
    detail_group_list_id int          not null,
    constraint detail_image_position_detail_group_list_id_fk
        foreign key (detail_group_list_id) references detail_group_list (id),
    constraint detail_image_position_details_id_fk
        foreign key (detail_id) references details (id)
);

-- Cyclic dependencies found

create table details_groups
(
    id        int unsigned auto_increment,
    name      varchar(100) not null,
    code      varchar(100) not null,
    parent_id int unsigned null,
    constraint id
        unique (id),
    constraint details_groups_details_groups_id_fk
        foreign key (parent_id) references details_groups (id)
);

alter table details_groups
    add primary key (id);

create table detail_group_list
(
    id               int auto_increment
        primary key,
    url              text         null,
    text_title       text         null,
    number           int          null,
    details_group_id int unsigned not null,
    constraint detail_group_list_details_groups_id_fk
        foreign key (details_group_id) references details_groups (id)
);

