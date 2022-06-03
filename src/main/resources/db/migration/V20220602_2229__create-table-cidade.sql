create table cidade (
    id bigint not null auto_increment,
    nome varchar(255) not null,
    estado_id bigint not null,

    primary key (id)
) engine=InnoDB default charset=utf8;

alter table cidade add constraint FK_cidade_estado foreign key (estado_id) references estado (id)