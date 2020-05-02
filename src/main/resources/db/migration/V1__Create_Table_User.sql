create table users
(
	id varchar(64) not null,
	username varchar(20) not null,
	password varchar(64) not null,
	face_image varchar(255) not null,
	face_image_big varchar(255) not null,
	nickname varchar(64) not null,
	qrcode varchar(255) not null,
	cid varchar(64) null
);

create unique index users_id_uindex
	on users (id);

alter table users
	add constraint users_pk
		primary key (id);

