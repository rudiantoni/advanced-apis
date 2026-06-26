-- -------------------------------
-- Table public.product
-- -------------------------------
CREATE TABLE IF NOT EXISTS public.product (
    id                       BIGINT NOT NULL,
    name                     VARCHAR(256) NOT NULL,
    description              VARCHAR(512) NOT NULL,
    price                    NUMERIC(19, 2) NOT NULL,
    reference                VARCHAR(128),
    stock_quantity           INTEGER,
    image_url                VARCHAR(1024),
    CONSTRAINT pk_product    PRIMARY KEY (id)
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

-- -------------------------------
-- Table public.users
-- -------------------------------
CREATE TABLE IF NOT EXISTS public.users (
    id                           BIGINT NOT NULL,
    email                        VARCHAR(128) NOT NULL,
    username                     VARCHAR(128) NOT NULL,
    password                     VARCHAR(512) NOT NULL,
    CONSTRAINT pk_users          PRIMARY KEY (id),
    CONSTRAINT uk_users_email    UNIQUE (email)
);

CREATE SEQUENCE IF NOT EXISTS public.users_seq
    AS BIGINT
    INCREMENT 1
    MINVALUE 1
    NO MAXVALUE
    NO CYCLE
    START WITH 1
    OWNED BY public.users.id;

ALTER TABLE public.users
    ALTER COLUMN id SET DEFAULT nextval('public.users_seq'::regclass);
