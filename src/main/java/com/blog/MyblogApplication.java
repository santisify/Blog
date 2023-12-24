package com.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyblogApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyblogApplication.class, args);
		System.out.println(
				"     _ _     _             \n"
				+ "  | | |__ | | ___   __ _ \n"
				+ "  | | '_ \\| |/ _ \\ / _` |\n"
				+ "  | | |_) | | (_) | (_| |\n"
				+ "  |_|_.__/|_|\\___/ \\__, |\n"
				+ "                   |___/ \n"
				+ " (#^.^#) 启动成功!"
		);
	}

}
