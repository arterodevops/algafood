create table restaurante_forma_pagamento (
    restaurante_id bigint not null,
    forma_pagamento_id bigint not null
) engine=InnoDB default charset=utf8;

alter table restaurante_forma_pagamento add constraint FK_restaurante_forma_pagamento_forma_pagamento foreign key (forma_pagamento_id) references forma_pagamento (id);
alter table restaurante_forma_pagamento add constraint FK_restaurante_forma_pagamento_restaurante foreign key (restaurante_id) references restaurante (id);