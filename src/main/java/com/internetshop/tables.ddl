create table category
(
	idcategory int(10) unsigned auto_increment
		primary key,
	name varchar(45) not null,
	constraint name_UNIQUE
		unique (name)
)
;

create table client
(
	idClient int(10) unsigned auto_increment,
	name varchar(45) not null,
	surname varchar(45) not null,
	birthdate date not null,
	email varchar(45) not null,
	password varchar(25) not null,
	phone varchar(45) null,
	order_counter int(10) unsigned default '0' not null,
	id_role int(10) unsigned not null,
	primary key (idClient, id_role),
	constraint `e-mail_UNIQUE`
		unique (email)
)
;

create index fk_client_role1_idx
	on client (id_role)
;

create table client_address
(
	idClient int(10) unsigned auto_increment
		primary key,
	country varchar(45) not null,
	city varchar(45) not null,
	post_code varchar(45) not null,
	street varchar(45) not null,
	house varchar(45) not null,
	flat varchar(45) not null,
	constraint client_address_client_idClient_fk
		foreign key (idClient) references client (idClient)
)
;

create index fk_client_address_client1_idx
	on client_address (idClient)
;

create table delivery_method
(
	method_id int auto_increment
		primary key,
	name varchar(20) null,
	constraint delivery_method_name_uindex
		unique (name)
)
;

create table goods
(
	idgoods int(10) unsigned auto_increment,
	name varchar(45) not null,
	price float unsigned not null,
	number_of_players tinyint not null,
	duration float not null,
	rule_id int not null,
	amount int(10) unsigned not null,
	visible tinyint(1) default '1' not null,
	description text null,
	id_category int(10) unsigned not null,
	primary key (idgoods, id_category),
	constraint name_UNIQUE
		unique (name),
	constraint fk_goods_category
		foreign key (id_category) references category (idcategory)
)
;

create index fk_goods_category_idx
	on goods (id_category)
;

create index goods___rulefk
	on goods (rule_id)
;

create table `order`
(
	idorder int(10) unsigned auto_increment,
	id_client int(10) unsigned not null,
	payment_type_id int not null,
	delivery_method_id int not null,
	date date not null,
	prim varchar(45) null,
	status_id int not null,
	pay_status tinyint(1) default '0' null,
	primary key (idorder, id_client),
	constraint fk_order_client1
		foreign key (id_client) references client (idClient),
	constraint order___deliveryfk
		foreign key (delivery_method_id) references delivery_method (method_id)
)
;

create index fk_order_client1_idx
	on `order` (id_client)
;

create index order__status_fk
	on `order` (status_id)
;

create index order___typefk
	on `order` (payment_type_id)
;

create index order___deliveryfk
	on `order` (delivery_method_id)
;

create table ordered_goods
(
	order_id int(10) unsigned not null,
	goods_id int(10) unsigned not null,
	primary key (order_id, goods_id),
	constraint fk_ordered_goods_order1
		foreign key (order_id) references `order` (idorder),
	constraint fk_ordered_goods_goods1
		foreign key (goods_id) references goods (idgoods)
)
;

create index fk_ordered_goods_goods1_idx
	on ordered_goods (goods_id)
;

create index fk_ordered_goods_order1_idx
	on ordered_goods (order_id)
;

create table payament_type
(
	type_id int auto_increment
		primary key,
	name varchar(20) null,
	constraint payament_type_name_uindex
		unique (name)
)
;

alter table `order`
	add constraint order___typefk
		foreign key (payment_type_id) references payament_type (type_id)
;

create table role
(
	id_role int(10) unsigned auto_increment
		primary key,
	name varchar(45) not null
)
;

alter table client
	add constraint fk_client_role1
		foreign key (id_role) references role (id_role)
;

create table rule
(
	rule_id int auto_increment
		primary key,
	name varchar(20) null,
	constraint rule_name_uindex
		unique (name)
)
;

alter table goods
	add constraint goods___rulefk
		foreign key (rule_id) references rule (rule_id)
;

create table status
(
	status_id int auto_increment
		primary key,
	name varchar(45) not null,
	constraint status_name_uindex
		unique (name)
)
;

alter table `order`
	add constraint order__status_fk
		foreign key (status_id) references status (status_id)
;

