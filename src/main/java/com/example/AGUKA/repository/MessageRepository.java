package com.example.AGUKA.repository;

import com.example.AGUKA.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByJobId(Long jobId);

    List<Message> findBySenderId(Long senderId);

    List<Message> findByReceiverId(Long receiverId);
}