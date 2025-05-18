package com.example.bmsbookticket.repositories;

import com.example.bmsbookticket.models.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

}
