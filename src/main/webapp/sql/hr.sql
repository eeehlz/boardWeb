-- Structured Query Language
select *
from tab;

-- 게시판(tbl_board)
-- 게시글번호, 제목, 내용, 작성자, 작성일시, 조회수
create table tbl_board (
 board_no number primary key, -- 키역할.
 title    varchar2(100) not null, -- 제목.
 content  varchar2(1000) not null, -- 내용.
 writer   varchar2(100) not null, -- 작성자.
 write_date date default sysdate, -- 작성일시.
 view_cnt number default 0 -- 조회수.
);
--시퀀스
create sequence board_seq;
insert into tbl_board (board_no, title, content, writer)
values(board_seq.nextval, '첫번째 글', '서블릿 재밌네요', 'user01');
insert into tbl_board (board_no, title, content, writer)
values(board_seq.nextval, '열심히', 'jsp를 공부합시다', 'user02');
--조회.
select *
from tbl_board;

select board_seq.nextval
from dual;

select student_no
      ,student_name
      ,phone
      ,address
from tbl_student
order by student_no;

select *
from TBL_EMPLOYEES
where emp_name = nvl('', emp_name)
order by emp_no;

-- insert into 테이블 values ();
insert into tbl_employees (emp_no, emp_name, tel_no, hire_date, salary)
values(1003, '김과장', '654-0103', '2025-02-20', 400);
commit;

-- update 테이블 set 컬럼=값;
update tbl_employees
set    tel_no = nvl('', tel_no),
       hire_date = case '' when '1900-01-01' then hire_date
                           else '' 
                   end,-- to_date('20250109','yyyymmdd'), -- 문자열 -> Date 변경.
       salary = case '' when '0' then salary
                        else ''
                end
where emp_no = 1003;

update tbl_employees 
set    tel_no = nvl('', tel_no),
       hire_date = case ' Mon Jan 01 00:00:00 KST 1900 ' when '1900-01-01' then hire_date
                   else 'Mon Jan 01 00:00:00 KST 1900' 
                   end,-- to_date('20250109','yyyymmdd'), -- 문자열 -> Date 변경.
       salary = case 350 when 0 then salary
                else 350
                end
where emp_no = 1003;


update tbl_employees 
set    tel_no = nvl('', tel_no),
       hire_date = case to_date('1900-01-01','yyyy-mm-dd') when to_date('1900-01-01','yyyy-mm-dd') then hire_date
                   else to_date('1900-01-01','yyyy-mm-dd')
                   end,-- to_date('20250109','yyyymmdd'), -- 문자열 -> Date 변경.
       salary = case 234 when 0 then salary
                else 234                
                end 
where emp_no = 1003;

select *
from tbl_employees
order by 1;

-- delete from 테이블;
delete from tbl_employees
where emp_no = 1003;
rollback;

select *
from employees
where department_id = 80
and salary >= 10000
and employee_id >= 150
order by first_name desc; --Executive
-- SA_REP
select *
from jobs;
--90
select *
from departments;

--사원정보 테이블.
create table tbl_employees (
  emp_no number primary key, --중복제약조건.
  emp_name varchar2(100) not null, --제약조건.
  tel_no varchar2(20),
  hire_date date,
  salary number
);

insert into tbl_employees
values (1001, '박사장', '654-0101', sysdate, 500);

insert into tbl_employees
values (1002, '최이사', '654-0102', sysdate, 450);
commit;

select *
from tbl_employees;


--테이블 생성.
create table tbl_student (
  student_no varchar2(10),
  student_name varchar2(100),
  phone varchar2(20),
  address varchar2(100)
);

--생성. insert
insert into tbl_student(student_no, student_name)
values ('S001', '홍길동');
-- 학번:S002, 이름:김민서, 연락처:010-1234-2323, 주소:X
insert into tbl_student(student_no, student_name, phone)
values('S002', '김민서', '010-1234-2323');
-- 학번:S003, 이름:박항서, 연락처:010-9191-1010, 주소:대구 북구 200
insert into tbl_student(student_no, student_name, address, phone)
values('S003', '박항서', '대구 북구 200', '010-9191-1010');
commit; --db 반영.

--삭제.
delete from tbl_student
where student_name = '김민서';

--수정 update
update tbl_student
set    student_no = 'S004'
where student_name = '박항서'
and   phone = '010-6789-6789';

-- 학생번호: S005, 이름:신용권 추가.
-- 신용권의 연락처: 010-0987-1234, 주소: 대구 서구 400 로 수정.
--추가 insert
insert into tbl_student(student_no, phone)
values ('S006', '010-1212-3434');

--수정 update
update tbl_studentset    phone = '010-0987-1234',  address = '대구 서구 400' where student_no = 'S005';

select * --컬럼명.
from tbl_student
order by student_no; --테이블명.

--제약조건. student_no컬럼에 중복값을 허용하지 않음.
alter table tbl_student add constraint student_pk primary key (student_no); 
--제약조건. null 값을 허용하지 않음.
alter table tbl_student modify student_name not null;

