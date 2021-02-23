 package com.ynsdrnks.simplejpaonetoone.security.repository;


 import com.ynsdrnks.simplejpaonetoone.security.domain.Role;
 import org.springframework.data.jpa.repository.JpaRepository;

 public interface RoleRepository extends JpaRepository<Role, Integer> {

     Role findByRole(String role);
 }
