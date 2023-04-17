-- -------------------------------
-- Table public.users
-- -------------------------------
CREATE TABLE IF NOT EXISTS public.users (
    id INT8 NOT NULL,
    email VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,

    CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT uk_users_email UNIQUE (email),
    CONSTRAINT uk_users_username UNIQUE (username)
);

CREATE SEQUENCE IF NOT EXISTS public.users_seq
    AS INT8
    INCREMENT 1
    MINVALUE 1
    NO MAXVALUE
    NO CYCLE
    START WITH 1
    OWNED BY public.users.id;

ALTER TABLE public.users
    ALTER COLUMN id SET DEFAULT nextval('public.users_seq'::regclass);

-- -------------------------------
-- Table public.product
-- -------------------------------
CREATE TABLE IF NOT EXISTS public.product (
    id INT8 NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    price FLOAT4,

    CONSTRAINT pk_product PRIMARY KEY (id)
);

CREATE SEQUENCE IF NOT EXISTS public.product_seq
    AS INT8
    INCREMENT 1
    MINVALUE 1
    NO MAXVALUE
    NO CYCLE
    START WITH 1
    OWNED BY public.product.id;

ALTER TABLE public.product
    ALTER COLUMN id SET DEFAULT nextval('public.product_seq'::regclass);

