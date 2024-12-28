-- 테이블 삭제
DROP TABLE IF EXISTS `Chat`;
DROP TABLE IF EXISTS `Match`;
DROP TABLE IF EXISTS `MatchCriteria`;
DROP TABLE IF EXISTS `Report`;
DROP TABLE IF EXISTS `UserActivityLog`;
DROP TABLE IF EXISTS `UserHobby`;
DROP TABLE IF EXISTS `Preference`;
DROP TABLE IF EXISTS `YoutubeSubscription`;
DROP TABLE IF EXISTS `TimeTable`;
DROP TABLE IF EXISTS `Profile`;
DROP TABLE IF EXISTS `User`;
DROP TABLE IF EXISTS `Major`;
DROP TABLE IF EXISTS `College`;
DROP TABLE IF EXISTS `Hobby`;
DROP TABLE IF EXISTS `Tier`;

-- 테이블 생성
CREATE TABLE `User` (
                        `user_id` INTEGER NOT NULL,
                        `password` VARCHAR(255) NOT NULL,
                        `phone` VARCHAR(255) NOT NULL,
                        `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        `closed_at` TIMESTAMP NULL,
                        `tier_id` INTEGER NOT NULL,
                        `role` VARCHAR(10) NOT NULL DEFAULT "USER" COMMENT 'USER, ADMIN',
                        `email` VARCHAR(50) NULL,
                        PRIMARY KEY (`user_id`)
);

CREATE TABLE `Tier` (
                        `tier_id` INTEGER NOT NULL,
                        `tier_name` VARCHAR(10) NOT NULL,
                        `min_score` INTEGER NOT NULL,
                        `max_score` INTEGER NOT NULL,
                        PRIMARY KEY (`tier_id`)
);

CREATE TABLE `College` (
                           `college_id` INTEGER NOT NULL,
                           `college_name` VARCHAR(20) NOT NULL,
                           PRIMARY KEY (`college_id`)
);

CREATE TABLE `Major` (
                         `major_id` INTEGER NOT NULL,
                         `major_name` VARCHAR(20) NOT NULL,
                         PRIMARY KEY (`major_id`)
);

CREATE TABLE `Profile` (
                           `user_profile_id` INTEGER NOT NULL,
                           `user_id` INTEGER NOT NULL,
                           `college_id` INTEGER NOT NULL,
                           `major_id` INTEGER NULL,
                           `nickname` VARCHAR(20) NOT NULL,
                           `rating` INTEGER NULL COMMENT '당근온도, 매칭 후에 남기는 리뷰',
                           `gender` VARCHAR(20) NULL,
                           `mbti` VARCHAR(4) NULL,
                           `height` INTEGER NULL,
                           `weight` INTEGER NULL,
                           PRIMARY KEY (`user_profile_id`)
);

CREATE TABLE `TimeTable` (
                             `timetable_id` INTEGER NOT NULL,
                             `user_id` INTEGER NOT NULL,
                             `term` VARCHAR(20) NULL,
                             `day` VARCHAR(20) NOT NULL,
                             `start_time` TIMESTAMP NOT NULL,
                             `end_time` TIMESTAMP NOT NULL,
                             PRIMARY KEY (`timetable_id`)
);

CREATE TABLE `YoutubeSubscription` (
                                       `subscription_id` INTEGER NOT NULL,
                                       `channel_name` VARCHAR(50) NOT NULL,
                                       `user_id` INTEGER NOT NULL,
                                       PRIMARY KEY (`subscription_id`)
);

CREATE TABLE `Preference` (
                              `preference_id` INTEGER NOT NULL,
                              `user_profile_id` INTEGER NOT NULL,
                              `mbti` VARCHAR(4) NULL,
                              `height_min` INTEGER NULL,
                              `height_max` INTEGER NULL,
                              `weight_min` INTEGER NULL,
                              `weight_max` INTEGER NULL,
                              `hobby_id` INTEGER NULL,
                              PRIMARY KEY (`preference_id`)
);

CREATE TABLE `Hobby` (
                         `hobby_id` INTEGER NOT NULL,
                         `hobby_name` VARCHAR(30) NOT NULL,
                         PRIMARY KEY (`hobby_id`)
);

CREATE TABLE `UserHobby` (
                             `userhobby_id` INTEGER NOT NULL,
                             `hobby_id` INTEGER NOT NULL,
                             `user_profile_id` INTEGER NOT NULL,
                             PRIMARY KEY (`userhobby_id`)
);

CREATE TABLE `MatchCriteria` (
                                 `match_score_id` INTEGER NOT NULL,
                                 `user_id` INTEGER NOT NULL,
                                 `user_id2` INTEGER NOT NULL,
                                 `youtube_score` FLOAT NULL,
                                 `preference_score` FLOAT NULL,
                                 `total_score` FLOAT NOT NULL COMMENT '유튜브 유사도 점수 + 선호도 점수',
                                 `created_at` TIMESTAMP NOT NULL,
                                 PRIMARY KEY (`match_score_id`)
);

CREATE TABLE `Report` (
                          `report_id` INTEGER NOT NULL,
                          `reported_id` INTEGER NOT NULL,
                          `reporter_id` INTEGER NOT NULL,
                          `reason` VARCHAR(100) NOT NULL,
                          `reported_at` TIMESTAMP NOT NULL,
                          PRIMARY KEY (`report_id`)
);

CREATE TABLE `Match` (
                         `match_id` INTEGER NOT NULL,
                         `match_score_id` INTEGER NOT NULL,
                         `matched_at` TIMESTAMP NOT NULL,
                         `status` VARCHAR(20) NOT NULL DEFAULT "PROGRESS" COMMENT 'READY: 1,  PROGRESS: 2, COMPLETE',
                         PRIMARY KEY (`match_id`)
);

CREATE TABLE `Chat` (
                        `chat_id` INTEGER NOT NULL,
                        `match_id` INTEGER NOT NULL,
                        `sender_id` INTEGER NOT NULL,
                        `message` VARCHAR(255) NOT NULL,
                        `send_at` TIMESTAMP NOT NULL,
                        PRIMARY KEY (`chat_id`)
);

CREATE TABLE `UserActivityLog` (
                                   `log_id` INTEGER NOT NULL,
                                   `user_id` INTEGER NOT NULL,
                                   `activity_type` VARCHAR(10) NOT NULL COMMENT 'LOGIN, LOGOUT, MATCH_REQUEST, BLOCKED',
                                   `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                   PRIMARY KEY (`log_id`)
);
