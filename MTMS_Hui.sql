/* This SQL file is used for CSCI 6230 group project. */
/* Created by Hui Guo on Mar 24th, 2013               */

/* drop the existing tables to avoid collision. */
drop table if exists Test;
drop table if exists Patient;
drop table if exists User;

/* UserID's are consistent with those in other systems. */
/* UserType can be GUEST, LABTEC, DOCTOR, ADMIN. */
create table User(
	UserID		varchar(15),
	Password	varchar(30),
	FirstName	varchar(20),
	LastName	varchar(20),
	BirthDate	date,
	EnrollDate	date,
	Address		text,
	Zipcode		char(5),
	PhoneNo		char(12),
	UserType	varchar(6),
	primary key (UserID));

/* PatientID's are consistent with those in other systems. */
create table Patient(
	PatientID	int,
	FirstName	varchar(20),
	LastName	varchar(20),
	BirthDate	date,
	Gender		tinyint(1),
	Address		varchar(50),
	Zipcode		char(5),
	PhoneNo		char(12),
	DoctorID	varchar(15),
	primary key (PatientID),
	foreign key (DoctorID) references User(UserID));

/* Status can be ORDERED, FINISHED, REPORTED, CANCELED */
create table Test(
	TestID		int,
	PatientID	int,
	DoctorID	varchar(15),
	TestDate	date,
	TestType	varchar(20),
	TestDes		varchar(50),
	TestResult	blob,
	Report		blob,
	Comment		text,
	UpdatedBy	varchar(15),
	Status		char(8),
	primary key (TestID),
	foreign key (PatientID) references Patient(PatientID),
	foreign key (UpdatedBy) references User(UserID));

insert into User values (
	'admin', 'admin', 'Group2', 'CSCI6230', '2013-02-01', '2013-04-01',
	'East Carolina University;Greenville, NC', '27858', '252-000-1234','ADMINS');