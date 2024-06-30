package com.company.dms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.company.dms.service.RoleInfoService;
import com.company.dms.service.UserInfoService;

/**
 * Main application class for the User Service.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableAspectJAutoProxy
public class UserServiceApplication implements CommandLineRunner {
	private final RoleInfoService roleInfoService;
	private final UserInfoService userInfoService;

    @Autowired
    public UserServiceApplication(RoleInfoService roleInfoService, UserInfoService userInfoService) {
        this.roleInfoService = roleInfoService;
        this.userInfoService = userInfoService;
    }

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Call the roleEntry and adminEntry methods after the application context is initialized
		roleInfoService.roleEntry();
		userInfoService.adminEntry();
	}
}

