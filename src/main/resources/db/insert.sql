SET FOREIGN_KEY_CHECKS = 0;

truncate table blog_post;
truncate table author;
truncate table comment;
truncate table author_posts;
truncate table blog_post_comments;

INSERT INTO blog_post(id, title, content, date_published)
VALUES(41, 'Title post 1', 'Post Content 1', current_timestamp),
            (42, 'Title post 2', 'Post Content 2', '2021-06-03 11:33:06.000000'),
            (43, 'Title post 3', 'Post Content 3', '2021-06-03 11:32:06.000000'),
            (44, 'Title post 4', 'Post Content 4', '2021-06-03 11:31:06.000000'),
            (45, 'Title post 5', 'Post Content 5', '2021-06-03 11:30:06.000000');
SET FOREIGN_KEY_CHECKS = 1;
