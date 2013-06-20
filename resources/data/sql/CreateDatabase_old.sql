/*Author: Ly Viet Dung
  Name of DBMS: MySQL version 5.6.6
*/
/* Create relation 'User'*/

create table User (
Id int auto_increment not null,
Name varchar(50) not null,
Password varchar(50) not null,
Email varchar(50),
Address varchar(100),
constraint Id primary key(Id) 
);
/* Create relation 'Place'*/

create table Place (
Id int auto_increment not null,
Name varchar(150) not null,
Address varchar(150) not null,
Description text,
constraint Id primary key(Id)
);
/* Create relation 'Type'*/

create table Type (
Id int auto_increment not null,
Name varchar(150) not null,
Description text,
constraint Id primary key(Id) 
);
/* Create relation 'Activity'*/

create table Activity (
Id int auto_increment not null,
PlaceId int not null,
TypeId int not null,
Note text,
RingStatus bit not null default 0,
constraint Id primary key(Id),
constraint fk1 foreign key(PlaceId) references Place(Id),
constraint fk2 foreign key(TypeId) references Type(Id)
    on update cascade
    on delete cascade
);
/* Create relation 'Time'*/

create table Time (
Id int auto_increment not null,
ActId int not null,
Time datetime not null,
Note text,
constraint Id primary key(Id),
constraint fk3 foreign key(ActId) references Activity(Id)
    on update cascade
    on delete cascade
);
/* Create relation  'User_Activity'*/

create table User_Activity (
UserId int not null,
ActId int not null,
TimeRegister timestamp not null default current_timestamp,
constraint Id primary key(UserId, ActId),
constraint fk4 foreign key(UserId) references User(Id) 
    on update cascade
    on delete cascade,
constraint fk5 foreign key(ActId) references Activity(Id)
    on update cascade
    on delete cascade
);
