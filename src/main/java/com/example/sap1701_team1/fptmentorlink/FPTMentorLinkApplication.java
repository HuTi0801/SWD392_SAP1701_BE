package com.example.sap1701_team1.fptmentorlink;

import com.example.sap1701_team1.fptmentorlink.enums.AppointmentStatus;
import com.example.sap1701_team1.fptmentorlink.enums.ProjectStatus;
import com.example.sap1701_team1.fptmentorlink.enums.Role;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.*;
import com.example.sap1701_team1.fptmentorlink.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RequiredArgsConstructor
@SpringBootApplication
public class FPTMentorLinkApplication {
	private final AppointmentRepo appointmentRepo;
	private final AccountRepo accountRepo;
	private final StudentRepo studentRepo;
	private final MentorRepo mentorRepo;
	private final LecturerRepo lecturerRepo;
	private final MajorRepo majorRepo;
	private final DepartmentRepo departmentRepo;
	private final ProjectRepo projectRepo;
	private final GroupRepo groupRepo;
	private final ReportRepo reportRepo;

	public static void main(String[] args) {
		SpringApplication.run(FPTMentorLinkApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = sdf.parse("2024-02-27 14:30:00");
		return args -> {
			//Thêm thông tin department
			Department department1 = Department.builder()
					.depName("Information Technology")
					.build();
			Department department2 = Department.builder()
					.depName("Business Administration")
					.build();
			departmentRepo.save(department1);
			departmentRepo.save(department2);

			//Thêm thông tin major
			Major major1 = Major.builder()
					.name("Software Engineering")
					.department(department1)
					.build();
			majorRepo.save(major1);
			Major major2 = Major.builder()
					.name("Artificial Intelligence")
					.department(department1)
					.build();
			majorRepo.save(major2);

			Major major3 = Major.builder()
					.name("International Business")
					.department(department2)
					.build();
			majorRepo.save(major3);

			//thêm thông tin account
			Account account1 = Account.builder()
					.username("huti")
					.password("1")
					.role(Role.LEADER)
					.email("huti@gmail.com")
					.fullname("Bui Huu Tien")
					.phone("0909330485")
					.is_active(true)
					.build();
			Account account2 = Account.builder()
					.username("nhatkhoa")
					.password("1")
					.role(Role.MEMBER)
					.email("nhatkhoa@gmail.com")
					.fullname("Tran Nguyen Nhat Khoa")
					.phone("0909330444")
					.is_active(true)
					.build();
			Account account3 = Account.builder()
					.username("putzien")
					.password("1")
					.role(Role.MEMBER)
					.email("putzien@gmail.com")
					.fullname("Nguyen Tram Phuc Duyen")
					.phone("0944949152")
					.is_active(true)
					.build();
			Account account4 = Account.builder()
					.username("datsung")
					.password("1")
					.role(Role.MEMBER)
					.email("datsung@gmail.com")
					.fullname("Nguyen Thanh Dat")
					.phone("0339315466")
					.is_active(true)
					.build();
			Account account5 = Account.builder()
					.username("quinh")
					.password("1")
					.role(Role.MEMBER)
					.email("quinh@gmail.com")
					.fullname("Pham Nhu Quynh")
					.phone("0909312345")
					.is_active(true)
					.build();
			Account account6 = Account.builder()
					.username("thao")
					.password("1")
					.role(Role.LECTURE)
					.email("thao@gmail.com")
					.fullname("Nguyen Thi Thanh Thao")
					.phone("0909452783")
					.is_active(true)
					.build();
			Account account7 = Account.builder()
					.username("an")
					.password("1")
					.role(Role.MENTOR)
					.email("an@gmail.com")
					.fullname("Phung Nhat An")
					.phone("0998766547")
					.is_active(true)
					.build();
			accountRepo.save(account1);
			accountRepo.save(account2);
			accountRepo.save(account3);
			accountRepo.save(account4);
			accountRepo.save(account5);
			accountRepo.save(account6);
			accountRepo.save(account7);

			Account account8 = Account.builder()
					.username("truongan")
					.password("1")
					.role(Role.LEADER)
					.email("andht@gmail.com")
					.fullname("Dao Huynh Truong An")
					.phone("0912347658")
					.is_active(true)
					.build();
			Account account9 = Account.builder()
					.username("thinh")
					.password("1")
					.role(Role.MEMBER)
					.email("thinhlp@gmail.com")
					.fullname("Le Phuoc Thinh")
					.phone("0334562345")
					.is_active(true)
					.build();
			Account account10 = Account.builder()
					.username("thien")
					.password("1")
					.role(Role.MEMBER)
					.email("thiennt@gmail.com")
					.fullname("Nguyen Trong Thien")
					.phone("0774538954")
					.is_active(true)
					.build();
			Account account11 = Account.builder()
					.username("tri")
					.password("1")
					.role(Role.MEMBER)
					.email("trith@gmail.com")
					.fullname("Tran Huu Tri")
					.phone("0339876597")
					.is_active(true)
					.build();
			accountRepo.save(account8);
			accountRepo.save(account9);
			accountRepo.save(account10);
			accountRepo.save(account11);

			//thêm thông tin Group
			Group group1 = Group.builder()
					.name("3cats")
					.build();
			groupRepo.save(group1);

			Group group2 = Group.builder()
					.name("wow")
					.build();
			groupRepo.save(group2);

			//thêm thông tin Student
			Student student1 = Student.builder()
					.account(account1)
					.major(major1)
					.jpa(9)
					.group(group1)
					.build();
			Student student2 = Student.builder()
					.account(account2)
					.major(major1)
					.jpa(8)
					.group(group1)
					.build();
			Student student3 = Student.builder()
					.account(account3)
					.major(major1)
					.jpa(9)
					.group(group1)
					.build();
			Student student4 = Student.builder()
					.account(account4)
					.major(major1)
					.jpa(7)
					.group(group1)
					.build();
			Student student5 = Student.builder()
					.account(account5)
					.major(major1)
					.jpa(9)
					.group(group1)
					.build();
			studentRepo.save(student1);
			studentRepo.save(student2);
			studentRepo.save(student3);
			studentRepo.save(student4);
			studentRepo.save(student5);

			Student student8 = Student.builder()
					.account(account8)
					.major(major1)
					.jpa(9)
					.group(group2)
					.build();
			Student student9 = Student.builder()
					.account(account9)
					.major(major1)
					.jpa(8)
					.group(group2)
					.build();
			Student student10 = Student.builder()
					.account(account10)
					.major(major1)
					.jpa(9)
					.group(group2)
					.build();
			Student student11 = Student.builder()
					.account(account11)
					.major(major1)
					.jpa(7)
					.group(group2)
					.build();
			studentRepo.save(student8);
			studentRepo.save(student9);
			studentRepo.save(student10);
			studentRepo.save(student11);

			//gán student1 làm leader
			group1.setLeader(student1);
			groupRepo.save(group1);

			group2.setLeader(student8);
			groupRepo.save(group2);

			//thêm thông tin Lecturer
			Lecturer lecturer6 = Lecturer.builder()
					.account(account6)
					.department(department1)
					.accademicRank("Master")
					.build();
			lecturerRepo.save(lecturer6);

			//thêm thông tin Mentor
			Mentor mentor7 = Mentor.builder()
					.account(account7)
					.expertise("BA")
					.rating(5)
					.build();
			mentorRepo.save(mentor7);

			//thêm thông tin meeting
			Appointment appointment1 = Appointment.builder()
					.mentor(mentor7)
					.student(student1)
					.date(date)
					.description("Report instructions")
					.appointmentStatus(AppointmentStatus.PENDING)
					.build();
			appointmentRepo.save(appointment1);

			Appointment appointment2 = Appointment.builder()
					.mentor(mentor7)
					.student(student8)
					.date(date)
					.description("Report instructions")
					.appointmentStatus(AppointmentStatus.PENDING)
					.build();
			appointmentRepo.save(appointment2);

			//thêm thông tin Project
			Project project1 = Project.builder()
					.lecturer(lecturer6)
					.group(group1)
					.topic("FPT Mentor Link")
					.description("FPT MentorLink is a mentorship platform to support students at FPT University" +
							" in their graduation projects and coursework.")
					.document("doc.com.vn")
					.projectStatus(ProjectStatus.PENDING)
					.build();
			projectRepo.save(project1);

			Project project2 = Project.builder()
					.lecturer(lecturer6)
					.group(group2)
					.topic("Pet care")
					.description("Our pet care website connects owners with vet services, grooming, training, " +
							"and a loving community for happier, healthier pets.")
					.document("doc22.com.vn")
					.projectStatus(ProjectStatus.PENDING)
					.build();
			projectRepo.save(project2);
		};
	}
}
