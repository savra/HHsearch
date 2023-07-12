create table vacancy
(
    id                    BIGSERIAL,
    name                  TEXT,
    experience            TEXT,
    url                   TEXT,
    lower_boundary_salary NUMERIC(19, 2),
    upper_boundary_salary NUMERIC(19, 2),
    key_skill             TEXT
);

COMMENT ON TABLE vacancy IS 'Вакансии';
COMMENT ON COLUMN vacancy.id IS 'Идентификатор (vacancy_seq)';
COMMENT ON COLUMN vacancy.name IS 'Название вакансии';
COMMENT ON COLUMN vacancy.experience IS 'Требуемый опыт работы';
COMMENT ON COLUMN vacancy.url IS 'Ссылка на представление вакансии на сайте';
COMMENT ON COLUMN vacancy.lower_boundary_salary IS 'Нижняя граница вилки оклада в рублях по курсу ЦБ';
COMMENT ON COLUMN vacancy.upper_boundary_salary IS 'Верняя граница вилки оклада в рублях по курсу ЦБ';
COMMENT ON COLUMN vacancy.key_skill IS 'Список ключевых навыков';

CREATE SEQUENCE vacancy_seq START WITH 1 INCREMENT BY 1;

CREATE UNIQUE INDEX CUQ_vacancy_url ON vacancy (url);