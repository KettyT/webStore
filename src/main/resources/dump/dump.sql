
CREATE TABLE Countries(
	id int primary key auto_increment,
	iso_code int NOT NULL,
	name varchar(50) NULL
);

create table detail_group(

    id integer primary key auto_increment unique not null,
    name text not null,
    code text not null,
    parent_id integer
        references details_groups(id)
);


create or replace view  v_group_detail_info as (
    select
        #CAST(dg.id AS CHAR) || '_' || CAST(d.id AS CHAR) as id,
           concat(dg.id, '_', d.id) as id,
        dg.id as detail_group_id,
        dg.name,
        dg.code,
        sd.id as detail_id,
        d.name as detail_name
     from details_groups dg
        join details d on d.group_id = dg.id
        join producer_details pd on d.id = pd.detail_id
        join stored_details sd on sd.producer_detail_id = pd.id
);

create or replace view  v_detail_info as (
    select
        sd.id,
        sd.price,
        sd.quantity,
        dgl.id as detail_group_list_id,
        dgl.text_title as detail_group_list_text_title,
        pd.article,
        pd.code as producer_detail_code,
        pd.amount_in_package,
        d.id as detail_id,
        d.name as detail_name,
        p.name as producer_name,
        c.name as country_name

    from stored_details sd
        join producer_details pd on pd.id = sd.producer_detail_id
        join details d on d.id = pd.detail_id
        join producers p on p.id = pd.producer_id
        left join countries c on p.country_id = c.id
        left join detail_group_list dgl on dgl.id = d.detail_group_list_id
);

create or replace view  v_cart_info as (
    select
        cd.id,
       cd.quantity,
       sd.quantity as store_quantity,
       sd.price as price,
       sd.amount_in_package,
       d.name as item_name

    from cart c
        join cart_details cd on c.id = cd.cart_id
        join stored_details sd on cd.stored_detail_id = sd.id
        join producer_details pd on sd.producer_detail_id = pd.id
        join details d on pd.detail_id = d.id

);

alter table producer_details
    add constraint producer_details_producers_id_fk
        foreign key (producer_id) references producers (id);

alter table producers
drop foreign key producers_countries_id_fk;

alter table producers
	add constraint producers_countries_id_fk
		foreign key (country_id) references countries (id);