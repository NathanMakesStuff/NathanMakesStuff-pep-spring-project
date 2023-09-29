package com.example.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.Column;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.Message;


public interface MessageRepository extends JpaRepository<Message, Integer>{

    @Modifying
    @Query("DELETE FROM Message a WHERE a.message_id = :messageId")
    Integer deleteMessageByID(@Param("messageId") Integer id);

    @Query("SELECT a FROM Message a WHERE a.posted_by = :postedBy")
    List<Message> findByPostedBy(@Param("postedBy") Integer id);

    @Modifying
    @Query("UPDATE Message a SET a.message_text = :newText WHERE a.message_id = :messageId")
    int updateMessage(@Param("newText") String newtext, @Param("messageId") Integer id);
}
