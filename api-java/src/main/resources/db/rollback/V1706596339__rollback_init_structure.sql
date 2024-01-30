-- -----------------------------------------
-- WARNING: THIS IS A DATABASE ROLLBACK SCRIPT!
-- -----------------------------------------
-- -------------------------------
-- Table public.users
-- -------------------------------
ALTER SEQUENCE IF EXISTS public.users_seq RESTART WITH 1;

DROP TABLE IF EXISTS public.users;

-- -------------------------------
-- Table public.permission
-- -------------------------------
ALTER SEQUENCE IF EXISTS public.permission_seq RESTART WITH 1;

DROP TABLE IF EXISTS public.permission;

-- -------------------------------
-- Table public.user_permission
-- -------------------------------
ALTER SEQUENCE IF EXISTS public.user_permission_seq RESTART WITH 1;

DROP TABLE IF EXISTS public.user_permission;