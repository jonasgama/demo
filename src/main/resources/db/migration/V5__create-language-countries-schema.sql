create table languages_countries(
  used_languages_id uuid REFERENCES languages(id),
  spoken_countries_id bigint REFERENCES countries(id)
);