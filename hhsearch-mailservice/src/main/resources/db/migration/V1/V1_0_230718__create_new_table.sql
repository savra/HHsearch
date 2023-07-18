create table report
(
    id                    BIGSERIAL,
    name                  TEXT not null,
    report_file           bytea not null,
    report_date           date not null
);

COMMENT ON TABLE report IS 'Отчеты';
COMMENT ON COLUMN report.id IS 'Идентификатор';
COMMENT ON COLUMN report.name IS 'Название отчета';
COMMENT ON COLUMN report.report_file IS 'Файл отчета в формате excel';
COMMENT ON COLUMN report.report_date IS 'Дата выпуска отчета';