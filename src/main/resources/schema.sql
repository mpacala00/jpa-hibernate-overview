CREATE TABLE IF NOT EXISTS public.product
(
    id integer NOT NULL DEFAULT 'nextval('product_id_seq'::regclass)',
    name character varying(45) COLLATE pg_catalog."default",
    CONSTRAINT product_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.employee
(
    id bigint NOT NULL,
    name character varying COLLATE pg_catalog."default",
    address character varying COLLATE pg_catalog."default",
    CONSTRAINT employee_pkey PRIMARY KEY (id)
);