package com.experience_kafka.repository;

import com.experience_kafka.model.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepository extends JpaRepository<Messages, Long> {
}
