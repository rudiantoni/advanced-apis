-- -------------------------------
-- Table public.product
-- -------------------------------
CREATE TABLE IF NOT EXISTS public.product (
    id BIGINT NOT NULL,
    name VARCHAR(255),
    description VARCHAR(255),
    amount INTEGER,
    price REAL,

    CONSTRAINT pk_id PRIMARY KEY (id)
);

CREATE SEQUENCE IF NOT EXISTS public.product_seq
    AS BIGINT
    INCREMENT 1
    MINVALUE 1
    NO MAXVALUE
    NO CYCLE
    START WITH 1
    OWNED BY public.product.id;

ALTER TABLE public.product
    ALTER COLUMN id SET DEFAULT nextval('public.product_seq'::regclass);