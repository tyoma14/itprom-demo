insert into profession (id, name, note) values (2, 'Разработчик', 'Отвечает за разработку ПО');
insert into profession (id, name, note) values (3, 'QA-инженер', 'Отвечает за качество ПО и процессов его разработки');
insert into profession (id, name, note) values (4, 'Аналитик', 'Отвечает за анализ требований к ПО и составление ТЗ');
insert into profession (id, name, note) values (5, 'Системный администратор', 'Отвечает за корректную работу оборудования');
insert into profession (id, name, note) values (6, 'HR-менеджер', 'Отвечает за развитие персонала компании');

insert into department (id, name, note, parent_id) values (2, 'Отдел разработки', 'Занимается разработкой ПО', null);
insert into department (id, name, note, parent_id) values (3, 'Отдел бэкенд-разработки', 'Занимается разработкой бэкенда', 2);
insert into department (id, name, note, parent_id) values (4, 'Отдел фронтенд-разработки', 'Занимается разработкой фронтенда', 2);
insert into department (id, name, note, parent_id) values (5, 'Отдел контроля качества', 'Занимается контролем качества ПО', null);
insert into department (id, name, note, parent_id) values (6, 'Аналитический отдел', 'Занимается анализом требований к ПО и составлением ТЗ', null);
insert into department (id, name, note, parent_id) values (7, 'Отдел тех. поддержки', 'Занимается поддержкой внутренних тех. процессов', null);
insert into department (id, name, note, parent_id) values (8, 'Отдел развития персонала', 'Занимается развитием и расширением персонала', null);

insert into employee (id, full_name, profession_id, department_id, note)
values (2, 'Абрамов Лавр Аристархович', 2, 3, '-');
insert into employee (id, full_name, profession_id, department_id, note)
values (3, 'Сидоров Рудольф Матвеевич', 5, 7, '-');
insert into employee (id, full_name, profession_id, department_id, note)
values (4, 'Зыкова Ася Денисовна', 4, 6, '-');
insert into employee (id, full_name, profession_id, department_id, note)
values (5, 'Евдокимова Надежда Семеновна', 6, 8, '-');