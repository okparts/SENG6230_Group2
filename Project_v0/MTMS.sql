drop table if exists Reports;
drop table if exists People;
drop table if exists Roles;

create table Roles(
    Type          char(10),
    CanSeeIntro   int,
    CanSeeOwn     int,
    CanSeeAll     int,
    CanRequest    int,
    CanFillBlank  int,
    CanChange     int,
    primary key (Type));
create table People(
    PID           char(5),
    Pwd           char(20),
    Name          char(30),
    BirthDate     date,
    Type          char(10),
    primary key (PID),
    foreign key (Type) references Roles(Type));
create table Reports(
    RID           char(10),
    ReportType    char(20),
    RequestDate   date,
    Status        char(10),
    RequestedBy   char(5),
    LastChangedBy char(5),
    Detail        char(255),
    primary key (RID),
    foreign key (RequestedBy) references People(PID),
    foreign key (LastChangedBy) references People(PID));

insert into Roles values (
    'Guest',1,0,0,0,0,0);
insert into Roles values (
    'Patient',1,1,0,1,0,0);
insert into Roles values (
    'Nurse',1,1,1,1,1,0);
insert into Roles values (
    'Doctor',1,1,1,1,1,1);

insert into People values (
    '00000','','Guest','1990-01-01','Guest');
insert into People values (
    '00001','123','Hui Guo','1987-03-08','Patient');
insert into People values (
    '00002','123','Yuan He','1989-01-06','Patient');
insert into People values (
    '00003','123','Shweta Mahendrakar','1986-01-01','Nurse');
insert into People values (
    '00004','123','Tim Adams','1970-01-01','Doctor');
insert into People values (
    '00005','123','Chauncey Perry','1988-01-01','Patient');
insert into People values (
    '00006','123','Matt Strapko','1980-01-01','Doctor');
insert into People values (
    '00007','123','Atif Ghafoor','1980-01-01','Doctor');

insert into Reports values (
    '10001','Blood', '2013-02-02','Requested','00001','00001','');
insert into Reports values (
    '10002','Blood', '2013-01-28','Finished','00001','00003','Normal');
insert into Reports values (
    '10003','Blood', '2013-01-25','Analyzed','00002','00006','Normal');
