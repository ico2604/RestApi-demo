package com.api.swagger3.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.swagger3.model.Entity.score;

public interface ScoreRepository extends JpaRepository<score, Long> {

}