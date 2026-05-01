package com.example.UBAKA.repository;

import com.example.UBAKA.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByJobId(Long jobId);

    List<Message> findBySenderId(Long senderId);

    List<Message> findByReceiverId(Long receiverId);
}