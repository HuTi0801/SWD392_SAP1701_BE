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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
	private final CheckpointRepo checkpointRepo;
	private final MentorAvailabilityRepo mentorAvailabilityRepo;
	private final PasswordEncoder passwordEncoder;
	private final CheckpointReviewRepo checkpointReviewRepo;

	public static void main(String[] args) {
		SpringApplication.run(FPTMentorLinkApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date date = sdf.parse("2024-02-27 14:30:00");
		Date date = new Date();
		return args ->
			{
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
						.password(passwordEncoder.encode("12345"))
						.role(Role.STUDENT)
						.email("huti@gmail.com")
						.fullname("Bui Huu Tien")
						.phone("0909330485")
						.is_active(true)
						.userCode("SE100001")
						.build();
				Account account2 = Account.builder()
						.username("nhatkhoa")
						.password(passwordEncoder.encode("12345"))
						.role(Role.STUDENT)
						.email("nhatkhoa@gmail.com")
						.fullname("Tran Nguyen Nhat Khoa")
						.phone("0909330444")
						.is_active(true)
						.userCode("SE100002")
						.build();
				Account account3 = Account.builder()
						.username("putzien")
						.password(passwordEncoder.encode("12345"))
						.role(Role.STUDENT)
						.email("putzien@gmail.com")
						.fullname("Nguyen Tram Phuc Duyen")
						.phone("0944949152")
						.is_active(true)
						.userCode("SE100003")
						.build();
				Account account4 = Account.builder()
						.username("datsung")
						.password(passwordEncoder.encode("12345"))
						.role(Role.STUDENT)
						.email("datsung@gmail.com")
						.fullname("Nguyen Thanh Dat")
						.phone("0339315466")
						.is_active(true)
						.userCode("SE100004")
						.build();
				Account account5 = Account.builder()
						.username("quinh")
						.password(passwordEncoder.encode("12345"))
						.role(Role.STUDENT)
						.email("quinh@gmail.com")
						.fullname("Pham Nhu Quynh")
						.phone("0909312345")
						.is_active(true)
						.userCode("SE100005")
						.build();
				Account account6 = Account.builder()
						.username("binhnn")
						.password(passwordEncoder.encode("12345"))
						.role(Role.LECTURE)
						.email("binhnn@gmail.com")
						.fullname("Nguyen Nguyen Binh")
						.phone("0909xxxxxx")
						.is_active(true)
						.userCode("LE000001")
						.build();
				Account account7 = Account.builder()
						.username("quocndk")
						.password(passwordEncoder.encode("12345"))
						.role(Role.MENTOR)
						.email("quocndk@gmail.com")
						.fullname("Nguyen Dang Kien Quoc")
						.phone("0998766547")
						.is_active(true)
						.userCode("ME000001")
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
						.password(passwordEncoder.encode("12345"))
						.role(Role.STUDENT)
						.email("andht@gmail.com")
						.fullname("Dao Huynh Truong An")
						.phone("0912347658")
						.is_active(true)
						.userCode("SE100008")
						.build();
				Account account9 = Account.builder()
						.username("thinh")
						.password(passwordEncoder.encode("12345"))
						.role(Role.STUDENT)
						.email("thinhlp@gmail.com")
						.fullname("Le Phuoc Thinh")
						.phone("0334562345")
						.is_active(true)
						.userCode("SE100009")
						.build();
				Account account10 = Account.builder()
						.username("thien")
						.password(passwordEncoder.encode("12345"))
						.role(Role.STUDENT)
						.email("thiennt@gmail.com")
						.fullname("Nguyen Trong Thien")
						.phone("0774538954")
						.is_active(true)
						.userCode("SE100010")
						.build();
				Account account11 = Account.builder()
						.username("tri")
						.password(passwordEncoder.encode("12345"))
						.role(Role.STUDENT)
						.email("trith@gmail.com")
						.fullname("Tran Huu Tri")
						.phone("0339876597")
						.is_active(true)
						.userCode("SE100011")
						.build();
				accountRepo.save(account8);
				accountRepo.save(account9);
				accountRepo.save(account10);
				accountRepo.save(account11);

				Account account12 = Account.builder()
						.username("thainguyen")
						.password(passwordEncoder.encode("12345"))
						.role(Role.STUDENT)
						.email("thai@gmail.com")
						.fullname("Nguyen Viet Thai")
						.phone("091xxxxxx")
						.is_active(true)
						.userCode("SE100012")
						.build();
				Account account13 = Account.builder()
						.username("duynn")
						.password(passwordEncoder.encode("12345"))
						.role(Role.STUDENT)
						.email("duynn@gmail.com")
						.fullname("Nguyen Nhut Duy")
						.phone("033xxxxxxx")
						.is_active(true)
						.userCode("SS100013")
						.build();
				Account account14 = Account.builder()
						.username("thao")
						.password(passwordEncoder.encode("12345"))
						.role(Role.STUDENT)
						.email("thaont@gmail.com")
						.fullname("Nguyen Thi Thao")
						.phone("077xxxxxxx")
						.is_active(true)
						.userCode("SS100014")
						.build();
				Account account15 = Account.builder()
						.username("khang")
						.password(passwordEncoder.encode("12345"))
						.role(Role.STUDENT)
						.email("khangba@gmail.com")
						.fullname("Bach An Khang")
						.phone("034xxxxxxx")
						.is_active(true)
						.userCode("SS100015")
						.build();
				Account account16 = Account.builder()
						.username("tai")
						.password(passwordEncoder.encode("12345"))
						.role(Role.STUDENT)
						.email("tainv@gmail.com")
						.fullname("Nguyen Van Tai")
						.phone("031xxxxxxx")
						.is_active(true)
						.userCode("SS100016")
						.build();
				Account account17 = Account.builder()
						.username("hoangnt")
						.password(passwordEncoder.encode("12345"))
						.role(Role.LECTURE)
						.email("hoangnt@gmail.com")
						.fullname("Nguyen The Hoang")
						.phone("031xxxxxxx")
						.is_active(true)
						.userCode("LE000002")
						.build();
				Account account18 = Account.builder()
						.username("duongttt2")
						.password(passwordEncoder.encode("12345"))
						.role(Role.LECTURE)
						.email("duongttt2@gmail.com")
						.fullname("To Thi Thuy Duong")
						.phone("031xxxxxxx")
						.is_active(true)
						.userCode("LE000003")
						.build();
				Account account19 = Account.builder()
						.username("khangpbd")
						.password(passwordEncoder.encode("12345"))
						.role(Role.ADMIN)
						.email("khangpbd@gmail.com")
						.fullname("Phan Ba Dinh Khang")
						.phone("031xxxxxxx")
						.is_active(true)
						.userCode("AD000001")
						.build();
				Account account20 = Account.builder()
						.username("hoantm")
						.password(passwordEncoder.encode("12345"))
						.role(Role.MENTOR)
						.email("hoantm@gmail.com")
						.fullname("Nguyen Thi My Hoa")
						.phone("0998******")
						.is_active(true)
						.userCode("ME000002")
						.build();
				Account account21 = Account.builder()
						.username("mypnn")
						.password(passwordEncoder.encode("12345"))
						.role(Role.MENTOR)
						.email("mypnn@gmail.com")
						.fullname("Phung Nguyen Ngoc My")
						.phone("099*******")
						.is_active(true)
						.userCode("ME000003")
						.build();
				accountRepo.save(account12);
				accountRepo.save(account13);
				accountRepo.save(account14);
				accountRepo.save(account15);
				accountRepo.save(account16);
				accountRepo.save(account17);
				accountRepo.save(account18);
				accountRepo.save(account19);
				accountRepo.save(account20);
				accountRepo.save(account21);

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
						.id(account1.getUserCode())
						.account(account1)
						.major(major1)
						.jpa(9)
						.group(group1)
						.build();
				Student student2 = Student.builder()
						.id(account2.getUserCode())
						.account(account2)
						.major(major1)
						.jpa(8)
						.group(group1)
						.build();
				Student student3 = Student.builder()
						.id(account3.getUserCode())
						.account(account3)
						.major(major1)
						.jpa(9)
						.group(group1)
						.build();
				Student student4 = Student.builder()
						.id(account4.getUserCode())
						.account(account4)
						.major(major1)
						.jpa(7)
						.group(group1)
						.build();
				Student student5 = Student.builder()
						.id(account5.getUserCode())
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
						.id(account8.getUserCode())
						.account(account8)
						.major(major1)
						.jpa(9)
						.group(group2)
						.build();
				Student student9 = Student.builder()
						.id(account9.getUserCode())
						.account(account9)
						.major(major1)
						.jpa(8)
						.group(group2)
						.build();
				Student student10 = Student.builder()
						.id(account10.getUserCode())
						.account(account10)
						.major(major1)
						.jpa(9)
						.group(group2)
						.build();
				Student student11 = Student.builder()
						.id(account11.getUserCode())
						.account(account11)
						.major(major1)
						.jpa(7)
						.group(group2)
						.build();
				studentRepo.save(student8);
				studentRepo.save(student9);
				studentRepo.save(student10);
				studentRepo.save(student11);

				Student student12 = Student.builder()
						.id(account12.getUserCode())
						.account(account12)
						.major(major1)
						.jpa(9)
						.build();
				Student student13 = Student.builder()
						.id(account13.getUserCode())
						.account(account13)
						.major(major3)
						.jpa(8)
						.build();
				Student student14 = Student.builder()
						.id(account14.getUserCode())
						.account(account14)
						.major(major3)
						.jpa(9)
						.build();
				Student student15 = Student.builder()
						.id(account15.getUserCode())
						.account(account15)
						.major(major3)
						.jpa(7)
						.build();
				Student student16 = Student.builder()
						.id(account16.getUserCode())
						.account(account16)
						.major(major3)
						.jpa(7)
						.build();
				studentRepo.save(student12);
				studentRepo.save(student13);
				studentRepo.save(student14);
				studentRepo.save(student15);
				studentRepo.save(student16);
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

				Lecturer lecturer17 = Lecturer.builder()
						.account(account17)
						.department(department1)
						.accademicRank("Master")
						.build();
				lecturerRepo.save(lecturer17);

				Lecturer lecturer18 = Lecturer.builder()
						.account(account18)
						.department(department2)
						.accademicRank("Master")
						.build();
				lecturerRepo.save(lecturer18);

				//thêm thông tin Mentor
				Mentor mentor1 = Mentor.builder()
						.account(account7)
						.expertise(List.of("BA", "Project Management", "Agile"))
						.rating(5)
						.build();
				Mentor mentor2 = Mentor.builder()
						.account(account20)
						.expertise(List.of("DA", "Project Management", "Agile"))
						.rating(5)
						.build();
				Mentor mentor3 = Mentor.builder()
						.account(account21)
						.expertise(List.of("BA", "Project Management", "Agile"))
						.rating(5)
						.build();
				mentorRepo.save(mentor1);
				mentorRepo.save(mentor2);
				mentorRepo.save(mentor3);

				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				// Chuyển đổi String thành Date
				Date startTime1 = dateFormat.parse("2025-04-07 09:00:00");
				Date endTime1 = dateFormat.parse("2025-04-07 11:00:00");

				Date startTime2 = dateFormat.parse("2025-04-07 13:00:00");
				Date endTime2 = dateFormat.parse("2025-04-07 15:00:00");

				Date startTime3 = dateFormat.parse("2025-04-07 17:00:00");
				Date endTime3 = dateFormat.parse("2025-04-07 19:00:00");

				Date startTime4 = dateFormat.parse("2025-04-07 19:00:00");
				Date endTime4 = dateFormat.parse("2025-04-07 21:00:00");

				MentorAvailability mentorAvailability1 = MentorAvailability.builder()
						.mentor(mentor1)
						.year(2025)
						.startTime(startTime1)
						.endTime(endTime1)
						.isBooked(true)
						.build();
				mentorAvailabilityRepo.save(mentorAvailability1);

				MentorAvailability mentorAvailability2 = MentorAvailability.builder()
						.mentor(mentor1)
						.year(2025)
						.startTime(startTime2)
						.endTime(endTime2)
						.isBooked(true)
						.build();
				mentorAvailabilityRepo.save(mentorAvailability2);

				MentorAvailability mentorAvailability3 = MentorAvailability.builder()
						.mentor(mentor1)
						.year(2025)
						.startTime(startTime3)
						.endTime(endTime3)
						.isBooked(false)
						.build();
				mentorAvailabilityRepo.save(mentorAvailability3);

				MentorAvailability mentorAvailability4 = MentorAvailability.builder()
						.mentor(mentor1)
						.year(2025)
						.startTime(startTime4)
						.endTime(endTime4)
						.isBooked(false)
						.build();
				mentorAvailabilityRepo.save(mentorAvailability4);

				Appointment appointment1 = Appointment.builder()
						.mentor(mentor1)
						.student(student1)
						.date(date)
						.description("Report instructions")
						.appointmentStatus(AppointmentStatus.PENDING)
						.mentorAvailability(mentorAvailability1)
						.build();
				appointmentRepo.save(appointment1);

				Appointment appointment2 = Appointment.builder()
						.mentor(mentor1)
						.student(student8)
						.date(date)
						.description("Report instructions")
						.appointmentStatus(AppointmentStatus.PENDING)
						.mentorAvailability(mentorAvailability2)
						.build();
				appointmentRepo.save(appointment2);

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
						.projectStatus(ProjectStatus.IN_PROGRESS)
						.build();
				projectRepo.save(project2);

				Checkpoint cp1_1 = Checkpoint.builder()
						.project(project1)
						.cpName("Checkpoint 1")
						.deadline("2025-03-07")
						.document("https://docs.google.com/document/d/1txZgWcG8Aj1bqYdfGoBumDNXua68nUtR/edit?usp=sharing&ouid=107505895634068400897&rtpof=true&sd=true")
						.build();

				Checkpoint cp1_2 = Checkpoint.builder()
						.project(project1)
						.cpName("Checkpoint 2")
						.deadline("2025-04-07")
						.build();

				Checkpoint cp1_3 = Checkpoint.builder()
						.project(project1)
						.cpName("Checkpoint 3")
						.deadline("2025-05-07")
						.build();

				Checkpoint cp1_4 = Checkpoint.builder()
						.project(project1)
						.cpName("Checkpoint 4")
						.deadline("2025-06-07")
						.build();

				Checkpoint cp2_1 = Checkpoint.builder()
						.project(project1)
						.cpName("Checkpoint 1")
						.deadline("2025-05-07")
						.build();

				Checkpoint cp2_2 = Checkpoint.builder()
						.project(project1)
						.cpName("Checkpoint 2")
						.deadline("2025-06-07")
						.build();

				Checkpoint cp2_3 = Checkpoint.builder()
						.project(project1)
						.cpName("Checkpoint 3")
						.deadline("2025-06-07")
						.build();

				Checkpoint cp2_4 = Checkpoint.builder()
						.project(project1)
						.cpName("Checkpoint 4")
						.deadline("2025-07-07")
						.build();
				checkpointRepo.save(cp1_1);
				checkpointRepo.save(cp1_2);
				checkpointRepo.save(cp1_3);
				checkpointRepo.save(cp1_4);
				checkpointRepo.save(cp2_1);
				checkpointRepo.save(cp2_2);
				checkpointRepo.save(cp2_3);
				checkpointRepo.save(cp2_4);

				Date review_at_1 = dateFormat.parse("2025-04-07 09:00:00");

				CheckpointReview checkpointReview1_1 = CheckpointReview.builder()
						.checkpoint(cp1_1)
						.lecturer(lecturer6)
						.score(9F)
						.reviewedAt(review_at_1)
						.feedback("Good structure")
						.build();
				checkpointReviewRepo.save(checkpointReview1_1);
		};
	}
}
