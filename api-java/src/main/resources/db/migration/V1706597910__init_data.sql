-- -------------------------------
-- Table public.users
-- -------------------------------
INSERT INTO public.users (email, username, password, created_at, updated_at)
VALUES
    ('usuario.teste@meuemail.com', 'Usuário Teste', 'master', NOW(), NOW());

-- -------------------------------
-- Table public.permission
-- -------------------------------
INSERT INTO public.permission (name, description, type)
VALUES
    ('ROLE_ADMIN', 'Administradores', 'ROLE'),
    ('ROLE_MANAGER', 'Gerentes', 'ROLE'),
    ('ROLE_EMPLOYEE', 'Funcionários', 'ROLE'),
    ('ACCESS_ALL_USER_DATA', 'Acessar todos os dados dos usuários', 'AUTHORITY'),
    ('MODIFY_SYSTEM_SETTINGS', 'Modificar configurações do sistema', 'AUTHORITY'),
    ('VIEW_TEAM_REPORTS', 'Visualizar relatórios de equipes', 'AUTHORITY'),
    ('APPROVE_LEAVE_REQUESTS', 'Aprovação de licenças variadas', 'AUTHORITY'),
    ('ACCESS_SELF_RECORDS', 'Acessar os próprios registros', 'AUTHORITY'),
    ('SUBMIT_SERVICE_REQUEST', 'Enviar uma requisição de serviços', 'AUTHORITY');

-- -------------------------------
-- Table public.user_permission
-- -------------------------------
INSERT INTO public.user_permission (user_id, permission_id)
VALUES
    (
        (SELECT id FROM public.users u WHERE u.email = 'usuario.teste@meuemail.com'),
        (SELECT id FROM public.permission p WHERE p.name = 'ROLE_ADMIN')
    ),
    (
        (SELECT id FROM public.users u WHERE u.email = 'usuario.teste@meuemail.com'),
        (SELECT id FROM public.permission p WHERE p.name = 'ACCESS_ALL_USER_DATA')
    ),
    (
        (SELECT id FROM public.users u WHERE u.email = 'usuario.teste@meuemail.com'),
        (SELECT id FROM public.permission p WHERE p.name = 'MODIFY_SYSTEM_SETTINGS')
    ),
    (
        (SELECT id FROM public.users u WHERE u.email = 'usuario.teste@meuemail.com'),
        (SELECT id FROM public.permission p WHERE p.name = 'VIEW_TEAM_REPORTS')
    );
