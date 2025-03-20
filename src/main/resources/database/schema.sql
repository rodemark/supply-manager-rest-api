create table supplier (
    ID serial primary key,
    name varchar(255) not null,
    contact varchar(255) not null
);

create table product (
    ID serial primary key,
    name varchar(255) not null,
    description varchar(255) not null,
    unit_of_measurement varchar(50) not null
);

create table delivery (
    ID serial primary key,
    supplier_id bigint not null,
    date date not null,
    foreign key (supplier_id) references supplier(id)
);

create table delivery_item (
    ID serial primary key,
    delivery_id bigint not null,
    product_id bigint not null,
    quantity float not null,
    price decimal(19,4) not null,
    foreign key (delivery_id) references delivery(id),
    foreign key (product_id) references product(id)
);

create table supplier_product_price (
    ID serial primary key,
    supplier_id bigint not null,
    product_id bigint not null,
    start_date date not null,
    end_date date not null,
    price decimal(19,4) not null,
    foreign key (supplier_id) references supplier(id),
    foreign key (product_id) references product(id)
);
