package com.api.swagger3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.swagger3.model.Entity.Team;
@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

}