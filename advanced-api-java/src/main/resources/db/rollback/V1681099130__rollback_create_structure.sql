-- -----------------------------------------
-- WARNING: THIS IS A DATABASE ROLLBACK SCRIPT!
-- -----------------------------------------
-- -------------------------------
-- Table public.product
-- -------------------------------
ALTER SEQUENCE IF EXISTS public.product_seq RESTART WITH 1;

DROP TABLE IF EXISTS public.product;