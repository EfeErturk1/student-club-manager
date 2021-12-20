package com.example.cs319project.repository;

import com.example.cs319project.model.Club;
import com.example.cs319project.model.Role;
import com.example.cs319project.model.Student;
import com.example.cs319project.model.clubstrategy.ClubRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClubRoleRepository extends JpaRepository<ClubRole, Integer> {
    @Query("SELECT c from ClubRole c where c.club.id = :clubId")
    List<ClubRole> findByClubId(Integer clubId);

   @Query("SELECT c from ClubRole c where c.student.id = :studentId")
    List<ClubRole> findByStudentId(Integer studentId);


}
