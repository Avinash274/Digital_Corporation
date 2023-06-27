-- phpMyAdmin SQL Dump
-- version 3.4.5
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Aug 27, 2022 at 08:25 AM
-- Server version: 5.5.16
-- PHP Version: 5.3.8

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `petscorner`
--

-- --------------------------------------------------------

--
-- Table structure for table `account_creation_otp`
--

CREATE TABLE IF NOT EXISTS `account_creation_otp` (
  `email_id` varchar(500) NOT NULL,
  `otp` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `account_creation_otp`
--

INSERT INTO `account_creation_otp` (`email_id`, `otp`) VALUES
('shashankdivate19@gmail.com', '897026'),
('poojapattar97@gmail.com', '345730');

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE IF NOT EXISTS `category` (
  `category_name` varchar(500) NOT NULL,
  `category_image` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`category_name`, `category_image`) VALUES
('Dogs', 'dogsecond.png'),
('Cats', 'catSecond.png'),
('Birds', 'birdSecond.png'),
('Hens', 'henSecond.png'),
('Fishes', 'fishSecond.png');

-- --------------------------------------------------------

--
-- Table structure for table `user_events`
--

CREATE TABLE IF NOT EXISTS `user_events` (
  `user_id` varchar(500) NOT NULL,
  `event_id` varchar(500) NOT NULL,
  `user_email_id` varchar(500) NOT NULL,
  `event_title` varchar(500) NOT NULL,
  `event_date` varchar(500) NOT NULL,
  `event_time` varchar(500) NOT NULL,
  `event_desc` varchar(500) NOT NULL,
  `event_location` varchar(500) NOT NULL,
  `event_state` varchar(500) NOT NULL,
  `event_contact` varchar(500) NOT NULL,
  `event_link` varchar(500) NOT NULL,
  `event_photo` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_events`
--

INSERT INTO `user_events` (`user_id`, `event_id`, `user_email_id`, `event_title`, `event_date`, `event_time`, `event_desc`, `event_location`, `event_state`, `event_contact`, `event_link`, `event_photo`) VALUES
('724202', '131971', 'shashankdivate19@gmail.com', 'Dog Show ðŸ¶', '08/15/2022', '18:30', 'Dog show provides the information of dog show event details which are going to be held in india.', 'Hubli', 'Karnataka', '6361681074', 'http://www.takshak-2k22.co.in/', 'EVENTIMAGE17830.jpg'),
('724202', '963975', 'shashankdivate19@gmail.com', 'Animal Antics', '08/31/2022', '3:15', 'The French dog show take place in August every year at the dharwad. This year a there gone be variety dogs and other animals. ', 'Dharwad', 'Karnataka', '6361681078', 'https://www.takshak-2k22.co.in', 'EVENTIMAGE23241.jpg'),
('724202', '902696', 'shashankdivate19@gmail.com', 'Pet Play Day', '09/04/2022', '6:30', 'The French dog show take place in August every year at the dharwad. This year a there gone be variety dogs and other animals. ', 'Hubli', 'Karnataka', '6361681074', 'https://www.takshak-2k22.co.in', 'EVENTIMAGE20210.jpg'),
('724202', '175428', 'shashankdivate19@gmail.com', 'Pooch Parade', '10/01/2022', '18:15', 'The French dog show take place in August every year at the dharwad. This year a there gone be variety dogs and other animals. ', 'Bangalore', 'Karnataka', '6361681074', 'https://www.takshak-2k22.co.in', 'EVENTIMAGE3008.jpg'),
('724202', '819742', 'shashankdivate19@gmail.com', 'Santa Paws', '09/30/2022', '6:30', 'The French dog show take place in August every year at the dharwad. This year a there gone be variety dogs and other animals. ', 'Bangalore', 'Karnataka', '6361681074', 'https://www.takshak-2k22.co.in', 'EVENTIMAGE31976.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `user_login`
--

CREATE TABLE IF NOT EXISTS `user_login` (
  `email_id` varchar(500) NOT NULL,
  `password` varchar(500) NOT NULL,
  `otp` varchar(500) NOT NULL,
  PRIMARY KEY (`email_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_login`
--

INSERT INTO `user_login` (`email_id`, `password`, `otp`) VALUES
('poojapattar97@gmail.com', 'poo@2022', 'null'),
('shashankdivate19@gmail.com', 'Pooja@123', 'null');

-- --------------------------------------------------------

--
-- Table structure for table `user_pets`
--

CREATE TABLE IF NOT EXISTS `user_pets` (
  `user_id` varchar(500) NOT NULL,
  `pet_id` varchar(500) NOT NULL,
  `email_id` varchar(500) NOT NULL,
  `petCategory` varchar(500) NOT NULL,
  `petName` varchar(500) NOT NULL,
  `petAge` varchar(500) NOT NULL,
  `pGender` varchar(500) NOT NULL,
  `pBreed` varchar(500) NOT NULL,
  `pABout` varchar(500) NOT NULL,
  `pPrice` varchar(500) NOT NULL,
  `pPriceType` varchar(500) NOT NULL,
  `pLocation` varchar(500) NOT NULL,
  `pImage` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_pets`
--

INSERT INTO `user_pets` (`user_id`, `pet_id`, `email_id`, `petCategory`, `petName`, `petAge`, `pGender`, `pBreed`, `pABout`, `pPrice`, `pPriceType`, `pLocation`, `pImage`) VALUES
('724202', '610812', 'shashankdivate19@gmail.com', 'Dogs', 'Bella', '15', 'Female', 'Bulldog', 'The bulldog is a British of dog of mastiff type. It may also be known as the English bulldog or british bulldog it is muscular, well built dog with wrinkled face and a pushed in nose. ', '2500', 'Fixed', 'Hubli', 'PETIMAGE31114.jpg'),
('724202', '972086', 'shashankdivate19@gmail.com', 'Dogs', 'Luna', '13', 'Male', 'German Shepherd', 'The German Shepherd is a German breed ðŸ¥³ of working dog of medium size. The breed was developed by max von using various traditional German herding dogs in 1899.', '1890', 'Negotiable', 'Hubli', 'PETIMAGE29720.jpg'),
('724202', '147949', 'shashankdivate19@gmail.com', 'Dogs', 'Lucy', '10', 'Male', 'Golden Retriever', 'The golden retriever is a German breed ðŸ¥³ of working dog of medium size. The breed was developed by max von using various traditional German herding dogs in 1899.', '3999', 'Negotiable', 'Hubli', 'PETIMAGE13935.jpg'),
('724202', '440863', 'shashankdivate19@gmail.com', 'Dogs', 'Charlie', '18', 'Female', 'Poodle', 'The German Shepherd is a German breed ðŸ¥³ of working dog of medium size. The breed was developed by max von using various traditional German herding dogs in 1899.', '1900', 'Fixed', 'Hubli', 'PETIMAGE12293.jpg'),
('724202', '391330', 'shashankdivate19@gmail.com', 'Dogs', 'Rocky', '25', 'Male', 'French Bulldog', 'The German Shepherd is a German breed ðŸ¥³ of working dog of medium size. The breed was developed by max von using various traditional German herding dogs in 1899.', '9999', 'Negotiable', 'Hubli', 'PETIMAGE19083.jpg'),
('724202', '606987', 'shashankdivate19@gmail.com', 'Dogs', 'Tucker', '17', 'Select Gender', 'Pomeranian', 'The German Shepherd is a German breed ðŸ¥³ of working dog of medium size. The breed was developed by max von using various traditional German herding dogs in 1899.', '1960', 'Negotiable', 'Hubli', 'PETIMAGE18305.jpg'),
('724202', '231011', 'shashankdivate19@gmail.com', 'Cats', 'Luna', '12', 'Male', 'Persian Cat', 'The German Shepherd is a German breed ðŸ¥³ of working dog of medium size. The breed was developed by max von using various traditional German herding dogs in 1899.', '599', 'Negotiable', 'Hubli', 'PETIMAGE16941.jpg'),
('724202', '245713', 'shashankdivate19@gmail.com', 'Cats', 'Bella', '12', 'Female', 'Maine Coon', 'The German Shepherd is a German breed ðŸ¥³ of working dog of medium size. The breed was developed by max von using various traditional German herding dogs in 1899.', '3680', 'Fixed', 'Hubli', 'PETIMAGE19699.jpg'),
('724202', '983940', 'shashankdivate19@gmail.com', 'Cats', 'Lily', '25', 'Male', 'Siamese Cat', 'The German Shepherd is a German breed ðŸ¥³ of working dog of medium size. The breed was developed by max von using various traditional German herding dogs in 1899.', '9633', 'Fixed', 'Hubli', 'PETIMAGE21545.jpg'),
('724202', '136664', 'shashankdivate19@gmail.com', 'Cats', 'Nala', '10', 'Female', 'Bengal Cat', 'The German Shepherd is a German breed ðŸ¥³ of working dog of medium size. The breed was developed by max von using various traditional German herding dogs in 1899.', '2580', 'Negotiable', 'Hubli', 'PETIMAGE17743.jpg'),
('672254', '232774', 'poojapattar97@gmail.com', 'Dogs', 'Juno ', '10', 'Male', 'Labrador Retriever ', 'The Labrador Retriever or Labrador is a British breed of retriever gun dog. It was developed in the United Kingdom from fishing dogs imported from the colony of Newfoundland, and was named after the Labrador region of that colony. ', '4999', 'Fixed', 'Dharwad ', 'PETIMAGE11226.jpg'),
('672254', '916558', 'poojapattar97@gmail.com', 'Dogs', 'Rinku', '10', 'Male', 'Golden Retriever ', 'Ho gaya re the late ayt al adk enu ðŸ¤©ðŸ‘€ hai u have been in a day or night ðŸŒƒ you are.', '8600', 'Negotiable', 'Hubli', 'PETIMAGE23173.jpg'),
('672254', '997070', 'poojapattar97@gmail.com', 'Cats', 'Cat', '10', 'Male', 'Tabby Cat ', 'Cat is available ', '2999', 'Fixed', 'Dharwad', 'PETIMAGE6717.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `user_pet_request`
--

CREATE TABLE IF NOT EXISTS `user_pet_request` (
  `user_id` varchar(500) NOT NULL,
  `request_id` varchar(500) NOT NULL,
  `email_id` varchar(500) NOT NULL,
  `petDescription` varchar(500) NOT NULL,
  `petLocation` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_pet_request`
--

INSERT INTO `user_pet_request` (`user_id`, `request_id`, `email_id`, `petDescription`, `petLocation`) VALUES
('724202', '184543', 'shashankdivate19@gmail.com', 'Hello pet lovers ðŸ˜\nI am pooja and I love pets. I was searching for a golden retriever dog. If any one have a dog and interested in giving /selling it then please do contact me. \n\nContact Number : 6361681078', 'Hubli'),
('672254', '904134', 'poojapattar97@gmail.com', 'We want dog', 'Hubli');

-- --------------------------------------------------------

--
-- Table structure for table `user_profile`
--

CREATE TABLE IF NOT EXISTS `user_profile` (
  `user_id` varchar(500) NOT NULL,
  `email_id` varchar(500) NOT NULL,
  `firstName` varchar(500) NOT NULL,
  `lastName` varchar(500) NOT NULL,
  `gender` varchar(500) NOT NULL,
  `contactNumber` varchar(500) NOT NULL,
  `password` varchar(500) NOT NULL,
  `profile_pic` varchar(500) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_profile`
--

INSERT INTO `user_profile` (`user_id`, `email_id`, `firstName`, `lastName`, `gender`, `contactNumber`, `password`, `profile_pic`) VALUES
('672254', 'poojapattar97@gmail.com', 'Pooja', 'Pattar', 'Female', '7892501345', 'poo@2022', 'PROFILEIMAGE22503.jpg'),
('724202', 'shashankdivate19@gmail.com', 'Pooja', 'Pattar', 'Female', '6361681074', 'Pooja@123', 'PROFILEIMAGE28273.jpg');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
