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


insert into tblRoom(id, roomNumber, `type`, price, amountPeople)
values(1, '105', 'phong thuong', 500000, 6),
(2, '106', 'phong thuong', 300000, 8),
(3, '107', 'phong thuong', 700000, 4),
(4, '201', 'phong vip', 1000000, 8),
(5, '202', 'phong vip', 2000000, 6),
(6, '203', 'phong vip', 3000000, 4),
(7, '304', 'phong thuong', 800000, 6),
(8, '305', 'phong thuong', 900000, 4),
(9, '306', 'phong thuong', 500000, 6);

insert into tblStudent(id, studentID, studentName, idCard, dob, classroom, studentAddress, roomid)
values(1, 'B17DCCN335', 'Hoàng Tăng Khải', '123456789', 19990128,'CNPM05', 'Hà Nội', 1),
(2, 'B17DCCN359', 'Phạm Trung Kiên', '123456987', 19990127,'CNPM05', 'Quảng Ninh', 1),
(3, 'B17DCCN563', 'Nguyễn Tất Thắng', '123458769', 19990126,'CNPM05', 'Thái Bình', 2),
(4, 'B17DCCN335', 'Hoàng Minh Tâm', '123476589', 19990125,'CNPM05', 'Hà Nội', 2),
(5, 'B17DCCN335', 'Bùi Xuân Quang', '125436789', 19990124,'CNPM05', 'Thái Bình', 3);


insert into tblGuest(idCard, `name`, dob, studentid)
values('123459768', 'Nguyễn Quang Huy', 19991208, 1),
('123459687', 'Nguyễn Ngọc Tuấn', 19990819, 2),
('123453454', 'Nguyễn Đức Thủy', 19950819, 3),
('123454325', 'Nguyễn Tuấn Nghĩa', 19990615, 4),
('123475674', 'Hoàng Tùng Lâm', 20000820, 2),
('543453214', 'Hoàng Hải An', 20010919, 1),
('543459687', 'Nguyễn Ngọc Ánh', 19990819, 3),
('876459768', 'Quách Gia Huy', 19990708, 5);

insert into tblService(id, `name`, price, `time`)
values(1, 'Trông xe', 3000, 8),
(2, 'Thuê xe', 100000, 24),
(3, 'Giặt là', 200000, 2),
(4, 'Thuê phòng ăn', 50000, 2);

insert into tblMotorBike(id, motorbikename, price, licensePlates, studentid)
values(1, 'Wave', 12000000, '29Y5 12345', 1),
(2, 'Exciter', 22000000, '14Y5 03739', 2),
(3, 'AirBlade', 42000000, '17Y5 12134', 3),
(4, 'Lead', 32000000, '29Z6 7481', 4),
(5, 'Liberty', 52000000, '17Y5 84353', 5);


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

-- insert into tblCheckin(id, motorBikeid, `time`)
-- values(1, 1, '2020-12-01'),
-- (2, 2, '2020-12-02'),
-- (3, 3, '2020-12-03'),
-- (4, 4, '2020-12-04');

-- insert into tblCheckout(checkinid, `time`)
-- values(2, '2020-12-01'),
-- (4, 20201119080000),
-- (5, 20201114070000);
