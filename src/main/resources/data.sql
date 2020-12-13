delete from tblMonthlyTicket;
delete from tblcheckout;
delete from tblcheckin;
delete from tblMotorBike;
delete from tblGuest;
delete from tblStudentService;
delete from tblStudent;
delete from tblRoom;
delete from tblService;

ALTER TABLE tblRoom AUTO_INCREMENT = 1;
ALTER TABLE tblStudent AUTO_INCREMENT = 1;
ALTER TABLE tblGuest AUTO_INCREMENT = 1;
ALTER TABLE tblStudentService AUTO_INCREMENT = 1;
ALTER TABLE tblMotorBike AUTO_INCREMENT = 1;
ALTER TABLE tblcheckin AUTO_INCREMENT = 1;
ALTER TABLE tblcheckout AUTO_INCREMENT = 1;
ALTER TABLE tblMonthlyTicket AUTO_INCREMENT = 1;
ALTER TABLE tblService AUTO_INCREMENT = 1;


insert into tblRoom(roomNumber, `type`, price, amountPeople)
values('105', 'phong thuong', 500000, 6),
('106', 'phong thuong', 300000, 8),
('107', 'phong thuong', 700000, 4),
('201', 'phong vip', 1000000, 8),
('202', 'phong vip', 2000000, 6),
('203', 'phong vip', 3000000, 4),
('304', 'phong thuong', 800000, 6),
('305', 'phong thuong', 900000, 4),
('306', 'phong thuong', 500000, 6);

insert into tblStudent(studentID, studentName, idCard, dob, classroom, studentAddress, roomid)
values('B17DCCN335', 'Hoàng Tăng Khải', '123456789', 19990128,'CNPM05', 'Hà Nội', 1),
('B17DCCN359', 'Phạm Trung Kiên', '123456987', 19990127,'CNPM05', 'Quảng Ninh', 1),
('B17DCCN563', 'Nguyễn Tất Thắng', '123458769', 19990126,'CNPM05', 'Thái Bình', 2),
('B17DCCN335', 'Hoàng Minh Tâm', '123476589', 19990125,'CNPM05', 'Hà Nội', 2),
('B17DCCN335', 'Bùi Xuân Quang', '125436789', 19990124,'CNPM05', 'Thái Bình', 3);


insert into tblGuest(idCard, `name`, dob, studentid)
values('123459768', 'Nguyễn Quang Huy', 19991208, 1),
('123459687', 'Nguyễn Ngọc Tuấn', 19990819, 2),
('123453454', 'Nguyễn Đức Thủy', 19950819, 3),
('123454325', 'Nguyễn Tuấn Nghĩa', 19990615, 4),
('123475674', 'Hoàng Tùng Lâm', 20000820, 2),
('543453214', 'Hoàng Hải An', 20010919, 1),
('543459687', 'Nguyễn Ngọc Ánh', 19990819, 3),
('876459768', 'Quách Gia Huy', 19990708, 5);

insert into tblService(`name`, price, `time`)
values('Trông xe', 3000, 8),
('Thuê xe', 100000, 24),
('Giặt là', 200000, 2),
('Thuê phòng ăn', 50000, 2);

insert into tblMotorBike(motorbikename, price, licensePlates, studentid)
values('Wave', 12000000, '29Y5 12345', 1),
('Exciter', 22000000, '14Y5 03739', 2),
('AirBlade', 42000000, '17Y5 12134', 3),
('Lead', 32000000, '29Z6 7481', 4),
('Liberty', 52000000, '17Y5 84353', 5);


insert into tblStudentService(quantity, studentid, serviceid, `date`)
values(1, 1, 1, '2020-12-01'),
(2, 2, 2, '2020-12-02'),
(1, 3, 2, '2020-12-03'),
(1, 1, 3, '2020-12-04'),
(1, 4, 4, '2020-12-05'),
(1, 2, 4, '2020-11-05');

insert into tblMonthlyTicket(studentid, `month`, motorBikeid)
values(1, 11, 1),
(2, 11, 2),
(3, 12, 3),
(4, 12, 4);

insert into tblCheckin(motorBikeid, `time`)
values(1, 20201119080000),
(2, 20201111070000),
(3, 20201119090000),
(4, 20201116100000),
(5, 20201113160000);

insert into tblCheckout(checkinid, `time`)
values(2, 20201117070000),
(4, 20201119080000),
(5, 20201114070000);
