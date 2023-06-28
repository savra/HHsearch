CREATE TABLE vacancy
(
    id                  BIGSERIAL,
    vacancy_id          TEXT NOT NULL,
    name                TEXT,
    premium             BOOLEAN DEFAULT FALSE NOT NULL,
    description         TEXT,
    schedule            TEXT,
    accept_handicapped  BOOLEAN DEFAULT FALSE NOT NULL,
    accept_kids         BOOLEAN DEFAULT FALSE NOT NULL,
    experience          TEXT,
    alternate_url       TEXT,
    apply_alternate_url TEXT,
    code                TEXT,
    employment          TEXT,
    last_update         TIMESTAMP,
    CONSTRAINT vacancy_pk PRIMARY KEY(ID)
);

COMMENT ON TABLE vacancy IS 'Вакансия';
COMMENT ON COLUMN vacancy.id IS 'Идентификатор (vacancy_seq)';
COMMENT ON COLUMN vacancy.vacancy_id IS 'Идентификатор вакансии';
COMMENT ON COLUMN vacancy.name IS 'Название вакансии';
COMMENT ON COLUMN vacancy.premium IS 'Является ли данная вакансия премиум-вакансией';
COMMENT ON COLUMN vacancy.description IS 'Описание вакансии, содержит html';
COMMENT ON COLUMN vacancy.schedule IS 'График работы';
COMMENT ON COLUMN vacancy.accept_handicapped IS 'Указание, что вакансия доступна для соискателей с инвалидностью';
COMMENT ON COLUMN vacancy.accept_kids IS 'Указание, что вакансия доступна для соискателей от 14 лет';
COMMENT ON COLUMN vacancy.experience IS 'Требуемый опыт работы';
COMMENT ON COLUMN vacancy.alternate_url IS 'Ссылка на представление вакансии на сайте';
COMMENT ON COLUMN vacancy.apply_alternate_url IS 'Ссылка на отклик на вакансию на сайте';
COMMENT ON COLUMN vacancy.code IS 'Внутренний код вакансии работадателя';
COMMENT ON COLUMN vacancy.employment IS 'Тип занятости';
COMMENT ON COLUMN vacancy.last_update IS 'Дата и время последнего обновления';

CREATE SEQUENCE vacancy_seq START WITH 1 INCREMENT BY 1;

CREATE UNIQUE INDEX cuq_vacancy_id ON vacancy(vacancy_id);

CREATE TABLE key_skill
(
    name       TEXT,
    vacancy_id BIGINT NOT NULL,
    CONSTRAINT fk_key_skill_vacancy FOREIGN KEY (vacancy_id) REFERENCES vacancy (ID) ON DELETE CASCADE
);

COMMENT ON TABLE key_skill IS 'Ключевые навыки';
COMMENT ON COLUMN key_skill.name IS 'Название ключевого навыка';
COMMENT ON COLUMN key_skill.vacancy_id IS 'Вакансия';

CREATE SEQUENCE key_skill_seq START WITH 1 INCREMENT BY 1;

CREATE UNIQUE INDEX CUQ_KEY_SKILL_ID_VAC_ID ON key_skill (name, vacancy_id);

CREATE TABLE salary
(
    ID            BIGSERIAL,
    lower_boundary NUMERIC(19, 2),
    upper_boundary NUMERIC(19, 2),
    gross          BOOLEAN DEFAULT FALSE NOT NULL,
    currency       TEXT NOT NULL,
    vacancy_id     BIGINT         NOT NULL,
    CONSTRAINT pk_salary PRIMARY KEY (ID),
    CONSTRAINT fk_salary_vacancy FOREIGN KEY (vacancy_id) REFERENCES vacancy (ID) ON DELETE CASCADE
);

COMMENT ON TABLE salary IS 'Ключевые навыки';
COMMENT ON COLUMN salary.id IS 'Идентификатор (salary_seq)';
COMMENT ON COLUMN salary.lower_boundary IS 'Нижняя граница вилки оклада';
COMMENT ON COLUMN salary.upper_boundary IS 'Верняя граница вилки оклада';
COMMENT ON COLUMN salary.gross IS 'Признак того что оклад указан до вычета налогов. В случае если не указано - null.';
COMMENT ON COLUMN salary.currency IS 'Валюта';
COMMENT ON COLUMN salary.vacancy_id IS 'Вакансия';

CREATE SEQUENCE salary_seq START WITH 1 INCREMENT BY 1;