create table vacancy
(
    id                    BIGSERIAL,
    name                  TEXT,
    experience            TEXT,
    url                   TEXT,
    lower_boundary_salary NUMERIC(19, 2),
    upper_boundary_salary NUMERIC(19, 2),
    key_skills            TEXT,
    currency              TEXT,
    gross                 boolean DEFAULT FALSE
);

COMMENT ON TABLE vacancy IS 'Вакансии';
COMMENT ON COLUMN vacancy.id IS 'Идентификатор';
COMMENT ON COLUMN vacancy.name IS 'Название вакансии';
COMMENT ON COLUMN vacancy.experience IS 'Требуемый опыт работы';
COMMENT ON COLUMN vacancy.url IS 'Ссылка на представление вакансии на сайте';
COMMENT ON COLUMN vacancy.lower_boundary_salary IS 'Нижняя граница вилки оклада в рублях по курсу ЦБ';
COMMENT ON COLUMN vacancy.upper_boundary_salary IS 'Верняя граница вилки оклада в рублях по курсу ЦБ';
COMMENT ON COLUMN vacancy.key_skills IS 'Список ключевых навыков';
COMMENT ON COLUMN vacancy.gross IS 'Признак того что оклад указан до вычета налогов. В случае если не указано - null.';
COMMENT ON COLUMN vacancy.currency IS 'Валюта';

CREATE UNIQUE INDEX CUQ_vacancy_url ON vacancy (url);