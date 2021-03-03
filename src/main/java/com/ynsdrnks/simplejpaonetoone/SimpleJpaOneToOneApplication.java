package com.ynsdrnks.simplejpaonetoone;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ynsdrnks.simplejpaonetoone.converter.Converter;
import com.ynsdrnks.simplejpaonetoone.dto.CountryDto;
import com.ynsdrnks.simplejpaonetoone.entity.Country;
import com.ynsdrnks.simplejpaonetoone.security.domain.Role;
import com.ynsdrnks.simplejpaonetoone.security.repository.RoleRepository;
import com.ynsdrnks.simplejpaonetoone.service.impl.CountryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@EnableJpaRepositories
@SpringBootApplication
public class SimpleJpaOneToOneApplication implements CommandLineRunner{
	@Autowired
	RoleRepository rolerepository;


	public static void main(String[] args)
	{
				SpringApplication.run(SimpleJpaOneToOneApplication.class, args);

	}


	@Override
	public void run(String... args) throws Exception {
		Role role1=new Role();
		role1.setRole("ADMIN");
		role1.setRole_id(1);
		Role role2=new Role();
		role2.setRole("USER");
		role2.setRole_id(2);
		rolerepository.save(role1);
		rolerepository.save(role2);
	}

	}


