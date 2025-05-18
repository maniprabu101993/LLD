package com.example.splitwise.repositories;

import com.example.splitwise.models.GroupMember;
import com.example.splitwise.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {

    Optional<GroupMember> findByUser(User user);
    List<GroupMember> findAllByGroupId(Long groupId);
    Optional<GroupMember> findByGroup_IdAndUser_Id(Long groupId, Long userId);
}