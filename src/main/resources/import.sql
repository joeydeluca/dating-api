DROP TABLE IF EXISTS `Countries`;
CREATE TABLE IF NOT EXISTS `Countries` (
  `CountryId` smallint(6) NOT NULL auto_increment,
  `Country` varchar(50) NOT NULL,
  `FIPS104` varchar(2) NOT NULL,
  `ISO2` varchar(2) NOT NULL,
  `ISO3` varchar(3) NOT NULL,
  `ISON` varchar(4) NOT NULL,
  `Internet` varchar(2) NOT NULL,
  `Capital` varchar(25) default NULL,
  `MapReference` varchar(50) default NULL,
  `NationalitySingular` varchar(35) default NULL,
  `NationalityPlural` varchar(35) default NULL,
  `Currency` varchar(30) default NULL,
  `CurrencyCode` varchar(3) default NULL,
  `Population` bigint(20) default NULL,
  `Title` varchar(50) default NULL,
  `Comment` varchar(255) default NULL,
  PRIMARY KEY  (`CountryId`)
);

DROP TABLE IF EXISTS `Regions`;
CREATE TABLE IF NOT EXISTS `Regions` (
  `RegionID` smallint(6) NOT NULL auto_increment,
  `CountryID` smallint(6) NOT NULL,
  `Region` varchar(45) NOT NULL,
  `Code` varchar(8) NOT NULL,
  `ADM1Code` char(4) NOT NULL,
  PRIMARY KEY  (`RegionID`)
);

DROP TABLE IF EXISTS `Cities`;
CREATE TABLE IF NOT EXISTS `Cities` (
  `CityId` int(11) NOT NULL auto_increment,
  `CountryID` smallint(6) NOT NULL,
  `RegionID` smallint(6) NOT NULL,
  `City` varchar(45) NOT NULL,
  `Latitude` float NOT NULL,
  `Longitude` float NOT NULL,
  `TimeZone` varchar(10) NOT NULL,
  `DmaId` smallint(6) default NULL,
  `Code` varchar(4) default NULL,
  PRIMARY KEY  (`CityId`)
);

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL auto_increment,
  `site_id` int(11) NOT NULL,
  `email` varchar(50) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(100) NOT NULL,
  `is_paid` varchar(1) NOT NULL default 'N',
  `gender` varchar(5) NOT NULL,
  `gender_seeking` varchar(5) NOT NULL,
  `birth_date` date NOT NULL,
  `country_id` varchar(10) NULL,
  `state_id` varchar(10) NULL,
  `city_id` varchar(10) NULL,
  `zip` varchar(10) NOT NULL,
  `height_inch` varchar(2) NOT NULL,
  `height_feet` varchar(2) NOT NULL,
  `body_type` varchar(2) default NULL,
  `smoke` varchar(2) NOT NULL,
  `drink` varchar(2) NOT NULL,
  `hair_color` varchar(2) NOT NULL,
  `religion` varchar(2) NOT NULL,
  `pet` varchar(50) default NULL,
  `language` varchar(50) default NULL,
  `ethnicity` varchar(50) NOT NULL,
  `occupation` varchar(2) default NULL,
  `salary` varchar(2) default NULL,
  `children_status` varchar(2) default NULL,
  `astro_sign` varchar(2) default NULL,
  `education` varchar(2) default NULL,
  `eye_color` varchar(2) default NULL,
  `about_me` varchar(4000) default NULL,
  `partner_description` varchar(4000) default NULL,
  `perfect_date` varchar(4000) default NULL,
  `partner_ethnicity` varchar(64) default NULL,
  `partner_body_type` varchar(64) default NULL,
  `partner_eye_color` varchar(64) default NULL,
  `partner_hair_color` varchar(64) default NULL,
  `partner_pet` varchar(64) default NULL,
  `partner_language` varchar(64) default NULL,
  `partner_religion` varchar(64) default NULL,
  `partner_occupation` varchar(64) default NULL,
  `partner_salary` varchar(64) default NULL,
  `partner_children_status` varchar(64) default NULL,
  `partner_astro_sign` varchar(64) default NULL,
  `partner_education` varchar(64) default NULL,
  `partner_smoke` varchar(64) default NULL,
  `partner_drink` varchar(64) default NULL,
  `is_subscribed_new_mail` varchar(1) default NULL,
  `is_subscribed_new_flirt` varchar(1) default NULL,
  `is_subscribed_favorited_me_alert` varchar(1) default NULL,
  `completion_status` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `is_deleted` varchar(1) NOT NULL default 'N',
  `ref_id` varchar(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `GENDER_SEEKING` (`gender_seeking`),
  KEY `GENDER` (`gender`),
  KEY `COUNTRY_STATE` (`country_id`,`state_id`),
  KEY `CITY` (`city_id`),
  KEY `IS_DELETED` (`is_deleted`)
);
alter table users add column has_profile_photo varchar(1) default 'N';

DROP TABLE IF EXISTS `photos`;
CREATE TABLE IF NOT EXISTS `photos` (
  `id` int(11) NOT NULL auto_increment,
  `profile_id` int(11) NOT NULL,
  `original_filename` varchar(200) default NULL,
  `medium_filename` varchar(200) default NULL,
  `thumb_filename` varchar(200) default NULL,
  `large_filename` varchar(200) default NULL,
  `is_profile_photo` varchar(1) NOT NULL,
  `insert_date` date NOT NULL,
  `version` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY  (`id`)
);

DROP TABLE IF EXISTS `messages`;
CREATE TABLE IF NOT EXISTS `messages` (
  `id` int(11) NOT NULL auto_increment,
  `from_user_id` int(11) NOT NULL,
  `to_user_id` int(11) NOT NULL,
  `subject` varchar(100) NOT NULL,
  `message` varchar(4000) NOT NULL,
  `send_date` datetime NOT NULL,
  `read_date` datetime default NULL,
  PRIMARY KEY  (`id`)
);

DROP TABLE IF EXISTS `product_prices`;
CREATE TABLE IF NOT EXISTS `product_prices` (
  `id` int(11) NOT NULL auto_increment,
  `durration_text` varchar(100) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `currency` varchar(3) NOT NULL,
  `durration_days` int(11) NOT NULL,
  `position_weight` int(11) NOT NULL,
  `is_featured` varchar(1) NOT NULL,
  `is_enabled` varchar(1) NOT NULL,
  PRIMARY KEY  (`id`)
);

DROP TABLE IF EXISTS `subscriptions`;
CREATE TABLE IF NOT EXISTS `subscriptions` (
  `id` int(11) NOT NULL auto_increment,
  `user_id` int(11) NOT NULL,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  `processor_subscription_id` varchar(100) default NULL,
  PRIMARY KEY  (`id`)
);

DROP TABLE IF EXISTS `transactions`;
CREATE TABLE IF NOT EXISTS `transactions` (
  `id` int(11) NOT NULL auto_increment,
  `user_id` int(11) NOT NULL,
  `product_price_id` int(11) NOT NULL,
  `date` datetime NOT NULL,
  `processor_transaction_id` varchar(50) NOT NULL,
  `subscription_id` int(11) NOT NULL,
  `amount_paid` int(11) NOT NULL,
  `currency` varchar(3) NOT NULL,
  PRIMARY KEY  (`id`)
);