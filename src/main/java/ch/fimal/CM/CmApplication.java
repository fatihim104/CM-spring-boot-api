package ch.fimal.CM;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ch.fimal.CM.model.Course;
import ch.fimal.CM.model.CourseStatus;
import ch.fimal.CM.model.Participant;
import ch.fimal.CM.model.User;
import ch.fimal.CM.repository.CourseRepository;
import ch.fimal.CM.repository.ParticipantRepository;
import ch.fimal.CM.repository.UserRepository;

@SpringBootApplication
public class CmApplication implements CommandLineRunner {

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	ParticipantRepository participantRepository;

	@Autowired
	UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(CmApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Course[] courses = new Course[] {
				new Course("English", "Zürich", LocalDate.parse(("2026-02-15")), CourseStatus.PLANNING),
				new Course("Math", "Luzern", LocalDate.parse(("2026-03-27")), CourseStatus.PLANNING)
		};

		for (int i = 0; i < courses.length; i++) {
			courseRepository.save(courses[i]);
		}

		Participant[] participants = new Participant[] {
				new Participant("Muhsin", "Imal", "mushin@gmail.com", bCryptPasswordEncoder().encode("123456"),
						LocalDate.parse(("1990-11-15"))),
				new Participant("Veli", "Dayi", "veli@gmail.com", bCryptPasswordEncoder().encode("123456"),
						LocalDate.parse(("1988-12-15")))
		};

		for (int i = 0; i < participants.length; i++) {
			participantRepository.save(participants[i]);
		}

		User[] users = new User[] {
				new User("Fatih", "Imal", "fatih@gmail.com", "123456")
		};

		for (int i = 0; i < users.length; i++) {
			userRepository.save(users[i]);
		}

	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
