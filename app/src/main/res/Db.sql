

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+03:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--

-- SESSION DIMENSIONS

CREATE TABLE `users` (
  `user_id` varchar(40) NOT NULL default "",
  `user_name` varchar(40) NOT NULL default "",
  `user_profile_pic` varchar(128) NOT NULL default "",
  `mobile_number` varchar(24) NOT NULL default "",
  `timestamp_millis_registered` int(13) unsigned NOT NULL default 0,
  PRIMARY KEY (`user_id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `users_blocked` (
  `record_id` varchar(40) NOT NULL default "",
  `user_id_blocked` varchar(40) NOT NULL default "",
  `user_id_that_blocked` varchar(40) NOT NULL default "",
  `timestamp_millis_blocked` int(13) unsigned NOT NULL default 0,
  PRIMARY KEY (`user_id_blocked`,user_id_that_blocked),
  UNIQUE KEY(`record_id`),
  FOREIGN KEY(`user_id_blocked`) REFERENCES `users`(`user_id`),
  FOREIGN KEY(`user_id_that_blocked`) REFERENCES `users`(`user_id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `trips_uploaded` (
  `trip_id` varchar(40) NOT NULL default "",
  `timestamp_created_millis` int(13) unsigned NOT NULL default 0,
  `for_user_id` varchar(40) NOT NULL default "",
  `origin_name` varchar(128) NOT NULL default "",
  `destination_name` varchar(128) NOT NULL default "",
  `trip_date` date NOT NULL default "1970-01-01",
  `trip_time` time NOT NULL default "00:00:00",
  `fuel_charges_ugx` int(5) unsigned NOT NULL default 0,
  `car_model` varchar(64) NOT NULL default "",
  `car_reg_number` varchar(32) NOT NULL default "",
  `max_concurrent_marriages` int(3) unsigned NOT NULL default 1,

  `from_country_name` varchar(32) NOT NULL default "",
  `from_admin_area` varchar(32) NOT NULL default "",
  `from_sub_admin_area` varchar(32) NOT NULL default "",
  `from_locality` varchar(32) NOT NULL default "",
  `from_latitude` double NOT NULL default 0,
  `from_longtitude` double NOT NULL default 0,

  `to_country_name` varchar(32) NOT NULL default "",
    `to_admin_area` varchar(32) NOT NULL default "",
    `to_sub_admin_area` varchar(32) NOT NULL default "",
    `to_locality` varchar(32) NOT NULL default "",
    `to_latitude` double NOT NULL default 0,
    `to_longtitude` double NOT NULL default 0,



  PRIMARY KEY (`trip_id`),
  FOREIGN KEY(`for_user_id`) REFERENCES `users`(`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `families` (
  `family_id` varchar(40) NOT NULL default "",
  `driver_trip_id` varchar(40) NOT NULL default "",
  `timestamp_created_millis` int(13) unsigned NOT NULL default 0,

  PRIMARY KEY (`family_id`),
  FOREIGN KEY(`driver_trip_id`) REFERENCES `trips_uploaded`(`trip_id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `trips_completed` (
  `record_id` varchar(40) NOT NULL default "",
  `family_id_completed` varchar(40) NOT NULL default "",
  `user_id_that_completed` varchar(40) NOT NULL default "",
  `timestamp_completed_millis` int(13) unsigned NOT NULL default 0,
  PRIMARY KEY (`family_id_completed`,user_id_that_completed),
  UNIQUE KEY(`record_id`),
  FOREIGN KEY(`family_id_completed`) REFERENCES `families`(`family_id`),
  FOREIGN KEY(`user_id_that_completed`) REFERENCES `users`(`user_id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `family_members` (
  `member_id` varchar(40) NOT NULL default "",
  `family_id` varchar(40) NOT NULL default "",
  `trip_id` varchar(40) NOT NULL default "",
  `timestamp_added_millis` int(13) unsigned NOT NULL default 0,
  PRIMARY KEY (`family_id`,trip_id),
  UNIQUE KEY(`member_id`),
  FOREIGN KEY(`family_id`) REFERENCES `families`(`family_id`),
  FOREIGN KEY(`trip_id`) REFERENCES `trips_uploaded`(`trip_id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `family_member_ratings` (
  `record_id` varchar(40) NOT NULL default "",
  `member_id_rated` varchar(40) NOT NULL default "",
  `member_id_that_rated` varchar(40) NOT NULL default "",
  `timestamp_rated_millis` int(13) unsigned NOT NULL default 0,

  `rating_time_keeping` tinyint(1) unsigned NOT NULL default 0,
  `rating_friendliness` tinyint(1) unsigned NOT NULL default 0,
  `rating_safe_driving` tinyint(1) unsigned NOT NULL default 0,
  `rating_other_factors` tinyint(1) unsigned NOT NULL default 0,

  PRIMARY KEY (`member_id_rated`,`member_id_that_rated`),
  UNIQUE KEY(`record_id`),
  FOREIGN KEY(`member_id_rated`) REFERENCES `family_members`(`member_id`),
  FOREIGN KEY(`member_id_that_rated`) REFERENCES `family_members`(`member_id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `mutual_proposal` (
  `record_id` varchar(40) NOT NULL default "",
  `trip_id_first` varchar(40) NOT NULL default "",
  `trip_id_second` varchar(40) NOT NULL default "",
  `timestamp_created_millis` int(13) unsigned NOT NULL default 0,

  PRIMARY KEY (`trip_id_first`,`trip_id_second`),
  UNIQUE KEY(`record_id`),
  FOREIGN KEY(`trip_id_first`) REFERENCES `trips_uploaded`(`trip_id`),
  FOREIGN KEY(`trip_id_second`) REFERENCES `trips_uploaded`(`trip_id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sessions` (
  `session_id` varchar(40) NOT NULL default "",
  `user_id` varchar(40) NOT NULL default "",
  `timestamp_created_millis` int(13) unsigned NOT NULL default 0,

  PRIMARY KEY (`session_id`),
  FOREIGN KEY(`user_id`) REFERENCES `users`(`user_id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `trip_message` (
  `message_id` varchar(40) NOT NULL default "",
  `to_trip_id` varchar(40) NOT NULL default "",
  `from_trip_id` varchar(40) NOT NULL default "",
  `transaction_id` varchar(40) NOT NULL default "",
  `message_type` enum("","TRIP_SCHEDULED") NOT NULL default "",
  `message_text` varchar(1000) NOT NULL default "",
  `is_system_message` boolean NOT NULL default false,
  `is_seen` boolean NOT NULL default false,
  `timestamp_created_millis` int(13) unsigned NOT NULL default 0,

  PRIMARY KEY (`message_id`),
  FOREIGN KEY(`to_trip_id`) REFERENCES `trips_uploaded`(`trip_id`),
  FOREIGN KEY(`from_trip_id`) REFERENCES `trips_uploaded`(`trip_id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `family_message` (
  `message_id` varchar(40) NOT NULL default "",
  `from_user_id` varchar(40) NOT NULL default "",
  `to_family_id` varchar(40) NOT NULL default "",
  `for_user_id` varchar(40) NOT NULL default "",
  `transaction_id` varchar(40) NOT NULL default "",
  `message_type` enum("","TEXT_PLAIN") NOT NULL default "",
  `message_text` varchar(1000) NOT NULL default "",
  `message_status` enum("","SENT","FAILED","DELIVERED","SEEN") NOT NULL default "",
  `is_system_message` boolean NOT NULL default false,
  `timestamp_created_millis` int(13) unsigned NOT NULL default 0,

  PRIMARY KEY (`message_id`),
  FOREIGN KEY(`from_user_id`) REFERENCES `users`(`user_id`),
  FOREIGN KEY(`to_family_id`) REFERENCES `families`(`family_id`),
  FOREIGN KEY(`for_user_id`) REFERENCES `users`(`user_id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `proposal` (
  `proposal_id` varchar(40) NOT NULL default "",
  `from_trip_id` varchar(40) NOT NULL default "",
  `to_trip_id` varchar(40) NOT NULL default "",
  `timestamp_created_millis` int(13) unsigned NOT NULL default 0,

  PRIMARY KEY (`from_trip_id`,`to_trip_id`),
  UNIQUE KEY (`proposal_id`),
  FOREIGN KEY(`to_trip_id`) REFERENCES `trips_uploaded`(`trip_id`),
  FOREIGN KEY(`from_trip_id`) REFERENCES `trips_uploaded`(`trip_id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `user_login_details` (
  `user_id` varchar(40) NOT NULL default "",
  `mobile_no` int(16) unsigned NOT NULL default 0,
  `password_sha1` varchar(64) NOT NULL default "",
  PRIMARY KEY (`user_id`),
  FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
