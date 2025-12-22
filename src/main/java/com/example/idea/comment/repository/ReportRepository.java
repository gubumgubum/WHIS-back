package com.example.idea.comment.repository;

import com.example.idea.comment.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}