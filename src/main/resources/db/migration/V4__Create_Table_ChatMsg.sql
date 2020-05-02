create table chat_msg
(
	id varchar(64) not null,
	send_user_id varchar(64) not null,
	accept_user_id varchar(64) not null,
	msg varchar(255) not null,
	sign_flag int not null,
	create_time datetime not null,
	constraint chat_msg_pk
		primary key (id)
);

