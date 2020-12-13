
create table if not exists tblRoom(
	id int primary key auto_increment,
    roomNumber varchar(50),
    `type` varchar(50),
    price float,
    amountPeople int
);

create table if not exists tblStudent(
	id int primary key auto_increment,
    studentID varchar(50),
    studentName varchar(50),
    idCard varchar(50),
    dob date,
    classroom varchar(50),
    studentAddress varchar(50),
    roomid int,
    foreign key (roomid) references tblroom(id)
);

create table if not exists tblGuest(
	id int primary key auto_increment,
    idCard varchar(20),
    `name` varchar(20),
    dob date,
    studentid int,
    foreign key (studentid) references tblstudent(id)
);

create table if not exists tblService(
	id int primary key auto_increment,
    `name` varchar(50),
    price float,
    `time` float
);

create table if not exists tblStudentService(
	id int primary key auto_increment,
    quantity int,
    `date` Date,
    studentid int,
    serviceid int,
    foreign key (serviceid) references tblService(id),
    foreign key (studentid) references tblStudent(id)
);

create table if not exists tblMotorbike(
	id int primary key auto_increment,
    motorbikeName varchar(50),
    price float,
    licensePlates varchar(50),
    studentid int,
    foreign key (studentid) references tblStudent(id)
);

create table if not exists tblCheckin(
	id int primary key auto_increment,
    `time` datetime,
    motorBikeid int,
    foreign key (motorBikeid) references tblMotorbike(id)
);

create table if not exists tblCheckout(
	id int primary key auto_increment,
    `time` datetime,
    checkinid int,
    foreign key (checkinid) references tblCheckin(id)
);

create table if not exists tblMonthlyTicket(
	id int primary key auto_increment,
	studentid int,
    `month` int,
    motorBikeid int,
    foreign key (motorBikeid) references tblMotorBike(id),
    foreign key (studentid) references tblStudent(id)
);

