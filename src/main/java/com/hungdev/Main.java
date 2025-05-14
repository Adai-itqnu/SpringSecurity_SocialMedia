package com.hungdev;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.context.annotation.ApplicationScope;

import com.hungdev.configs.DatabaseConfig;
import com.hungdev.entities.User;

import com.hungdev.repositories.UserRepositoryImp;
import com.hungdev.services.JwtService;
import com.hungdev.services.UserService;

@ApplicationScope
public class Main {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DatabaseConfig.class);

		DatabaseConfig dbConfig = context.getBean(DatabaseConfig.class);
		DataSource dataSource = dbConfig.dataSource();

		UserRepositoryImp userRepository = new UserRepositoryImp(dataSource);
		UserService userService = new UserService(userRepository);

		List<User> users = userService.findPaged(0, 10, 1);
		for (User user : users) {
			System.out.println(user.getUsername());
		}

		context.close();
	}

}
