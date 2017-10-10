delete from profile_views where view_date <= DATE_SUB(NOW(),INTERVAL 1 YEAR);
delete from profile_views where from_user_id = 37;
delete from profile_views where to_user_id = 37;
delete from profile_views where from_user_id = 0 or to_user_id = 0;

delete from flirts where date <= DATE_SUB(NOW(),INTERVAL 1 YEAR);

delete from favorites where date <= DATE_SUB(NOW(),INTERVAL 1 YEAR);

delete from messages where from_user_id = 0 or to_user_id = 0;
delete from messages where from_user_id = 38 or to_user_id = 38;

delete from emails;

alter table users add column has_profile_photo varchar(1) default 'N';
update users u
JOIN photos p ON p.profile_id = u.id AND p.is_profile_photo = 'Y'
set u.has_profile_photo = 'Y';


ALTER TABLE users
    MODIFY birth_date date NULL,
    MODIFY height_inch varchar(2) NULL,
    MODIFY height_feet varchar(2) NULL,
    MODIFY smoke varchar(2) NULL,
    MODIFY drink varchar(2) NULL,
    MODIFY hair_color varchar(2) NULL,
    MODIFY religion varchar(2) NULL,
    MODIFY ethnicity varchar(255) NULL,
    MODIFY zip varchar(10) NULL;

ALTER TABLE photos add version INT DEFAULT 0;