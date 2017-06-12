
insert into `users` ( `username`, `password`, `enabled`) values ( 'test', '$2a$10$hTGtbco/ZqgvosoQVWdOdeKYBcJSspdPpVtfqyF7uUUBVz0UtBXWy', '1');

insert into `authorities` ( `username`, `authority`) values ( 'test', 'ROLE_MEMBER');

insert into `oauth_client_details` ( `client_id`, `resource_ids`, `client_secret`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`) values ( 'sample-client', 'oauth2-resource', '$2a$10$hTGtbco/ZqgvosoQVWdOdeKYBcJSspdPpVtfqyF7uUUBVz0UtBXWy', 'read,write,openid', 'password,authorization_code,refresh_token', 'http://localhost:9999/login', 'ROLE_CLIENT', '1800', null, null, null);
