package com.example.sap1701_team1.fptmentorlink.repositories;

import com.example.sap1701_team1.fptmentorlink.models.entity_models.Group;
import com.example.sap1701_team1.fptmentorlink.models.entity_models.Student;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepo extends JpaRepository<Student, String> {
    @Query("SELECT s FROM Student s WHERE s.account.userCode = :userCode")
    Optional<Student> findLeaderByUserCode(@Param("userCode") String userCode);
    List<Student> findByAccountUserCodeIn(List<String> userCodes);

    @Modifying
    @Transactional
    @Query("UPDATE Student s SET s.group.id = :groupId WHERE s.id = :studentId")
    void updateGroupIdForStudent(@Param("studentId") String studentId, @Param("groupId") Integer groupId);
}
