 package com.ynsdrnks.simplejpaonetoone.security.repository;

 import com.ynsdrnks.simplejpaonetoone.security.domain.User;
 import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.data.jpa.repository.Query;
 import org.springframework.data.repository.query.Param;

 import java.util.List;

 public interface UserRepository extends JpaRepository<User, Integer> {

     User findByUsername(String username);
     User findByEmail(String email);

     @Query("select u from User u Where u.email= :email")
     List<User> getUserByEmail(@Param("email") String email);
 }
