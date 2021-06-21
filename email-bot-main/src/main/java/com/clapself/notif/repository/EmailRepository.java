package com.clapself.notif.repository;

import com.clapself.notif.entities.EmailData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<EmailData, Long >{

}