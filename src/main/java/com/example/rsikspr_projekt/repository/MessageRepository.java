package com.example.rsikspr_projekt.repository;

import com.example.rsikspr_projekt.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    //you can add custome methodes here
    //besides save(), findById(), findAll(), deleteById()...
}
