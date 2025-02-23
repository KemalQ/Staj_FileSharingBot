package com.staj.staj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StajApplication {
	public static void main(String[] args)
	{
		SpringApplication.run(StajApplication.class, args);
	}
//	@Autowired
//	MailSenderService mailSenderService;
//	@EventListener(ApplicationReadyEvent.class)
//	public void doSomethingAfterStartup() {
//		MailParams mailParams = new MailParams();
//		mailParams.setId("YWGYTH");
//		mailParams.setEmailTo("kemal@yopmail.com");
//		mailSenderService.send(mailParams);
//	}
}
