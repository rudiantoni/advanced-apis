-- -----------------------------------------
-- WARNING: THIS IS A DATABASE ROLLBACK SCRIPT!
-- -----------------------------------------
-- -------------------------------
-- Table public.product
-- -------------------------------
ALTER SEQUENCE IF EXISTS public.product_seq RESTART WITH 1;

DROP TABLE IF EXISTS public.product;

-- -------------------------------
-- Table public.users
-- -------------------------------
ALTER SEQUENCE IF EXISTS public.users_seq RESTART WITH 1;

DROP TABLE IF EXISTS public.users;
