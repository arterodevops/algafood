create table produto (
    id bigint not null auto_increment,
    ativo bit not null,
    descricao varchar(255) not null,
    nome varchar(255) not null,
    preco decimal(19,2) not null,
    restaurante_id bigint not null,

    primary key (id)
) engine=InnoDB default charset=utf8;

alter table produto add constraint FK_produto_restaurante foreign key (restaurante_id) references restaurante (id);