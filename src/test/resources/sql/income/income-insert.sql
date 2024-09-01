insert into tb_users(id,email,username,password,role) values(5, 'amor@gmail.com', 'Dady', '$2a$10$BSpCrGtFdNeGR2/m5mYWG.vV41P2zcwljIjpGIYNMbrXCLpqYFxAa', 'ROLE_ADMIN');
insert into tb_users(id,email,username,password,role) values(1, 'user1@gmail.com', 'Dady', '$2a$10$Xi9tPuWiKspBfQVP4AKTuO44mDr.OX0Mu7DikV5cdkQZbFL8Wco86', 'ROLE_CLIENT');
insert into tb_users(id,email,username,password,role) values(12, 'user2@gmail.com', 'Dady', '$2a$10$ae7eyiYWBf9K8PyON1s38OMvh2vZEDtIro7Lv1v9ypf6Gl/TW3O/e', 'ROLE_CLIENT');
insert into tb_users(id,email,username,password,role) values(15, 'user5@gmail.com', 'Dady', '$2a$10$.eb478hx6S6CDjCY4CoALeghyFIuUZ6IW79vkqkaWjxWr.vKGPvVe', 'ROLE_CLIENT');



insert into tb_account(id,balance,financial_institution,account_type,user_id)values(2,15000,'Banco Santader','CONTA_POUPANCA',1);
insert into tb_account(id,balance,financial_institution,account_type,user_id)values(3,15000,'Banco Itau','CONTA_CORRENTE',1);


insert into incomes(id,category_income,amount,descricao,created_at,account_id) values (10,'SALARIO',1500,'salario de mes','2024-08-01',2);
insert into incomes(id,category_income,amount,descricao,created_at,account_id) values (11,'SALARIO',12500,'salario de mes','2024-08-01',3);
