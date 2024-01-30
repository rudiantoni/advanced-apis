-- -------------------------------
-- Table public.users
-- -------------------------------
CREATE TABLE IF NOT EXISTS public.users (
    id INT8 NOT NULL,
    email VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL,
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
-- Table public.permission
-- -------------------------------
CREATE TABLE IF NOT EXISTS public.permission (
    id INT8 NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    CONSTRAINT pk_authority PRIMARY KEY (id),
    CONSTRAINT uk_authority_name UNIQUE (name)
);

CREATE SEQUENCE IF NOT EXISTS public.permission_seq
    AS INT8
    INCREMENT 1
    MINVALUE 1
    NO MAXVALUE
    NO CYCLE
    START WITH 1
    OWNED BY public.permission.id;

ALTER TABLE public.permission
    ALTER COLUMN id SET DEFAULT nextval('public.permission_seq'::regclass);

-- -------------------------------
-- Table public.user_permission
-- -------------------------------
CREATE TABLE IF NOT EXISTS public.user_permission (
    id INT8 NOT NULL,
    user_id INT8 NOT NULL,
    permission_id INT8 NOT NULL,
    CONSTRAINT pk_user_permission PRIMARY KEY (id),
    CONSTRAINT uk_user_permission_user_id_permission_id UNIQUE (user_id, permission_id),
    CONSTRAINT fk_user_permission_on_user FOREIGN KEY (user_id) REFERENCES public.users(id),
    CONSTRAINT fk_user_permission_on_permission FOREIGN KEY (permission_id) REFERENCES public.permission(id)
);

CREATE SEQUENCE IF NOT EXISTS public.user_permission_seq
    AS INT8
    INCREMENT 1
    MINVALUE 1
    NO MAXVALUE
    NO CYCLE
    START WITH 1
    OWNED BY public.user_permission.id;

ALTER TABLE public.user_permission
    ALTER COLUMN id SET DEFAULT nextval('public.user_permission_seq'::regclass);