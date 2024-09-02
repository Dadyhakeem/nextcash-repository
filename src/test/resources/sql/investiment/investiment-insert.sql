insert into tb_users(id,email,username,password,role) values(5, 'amor@gmail.com', 'Dady', '$2a$10$BSpCrGtFdNeGR2/m5mYWG.vV41P2zcwljIjpGIYNMbrXCLpqYFxAa', 'ROLE_ADMIN');
insert into tb_users(id,email,username,password,role) values(11, 'user1@gmail.com', 'Dady', '$2a$10$Xi9tPuWiKspBfQVP4AKTuO44mDr.OX0Mu7DikV5cdkQZbFL8Wco86', 'ROLE_CLIENT');
insert into tb_users(id,email,username,password,role) values(12, 'user2@gmail.com', 'Dady', '$2a$10$ae7eyiYWBf9K8PyON1s38OMvh2vZEDtIro7Lv1v9ypf6Gl/TW3O/e', 'ROLE_CLIENT');
insert into tb_users(id,email,username,password,role) values(13, 'user3@gmail.com', 'Dady', '$2a$10$.eb478hx6S6CDjCY4CoALeghyFIuUZ6IW79vkqkaWjxWr.vKGPvVe', 'ROLE_CLIENT');



insert into tb_investment(id,name,amount,investment_type,start_date,end_date ,user_id)values(10,'aponsentadoria',15000,'FUNDOS','2024-01-01','2024-12-01',11);
insert into tb_investment(id,name,amount,investment_type,start_date,end_date ,user_id)values(20,'aponsentadoria',19000,'TITULOS','2024-01-01','2024-12-01',13);
insert into tb_investment(id,name,amount,investment_type,start_date,end_date ,user_id)values(30,'aponsentadoria',17000,'ACOES','2024-01-01','2024-12-01',12);