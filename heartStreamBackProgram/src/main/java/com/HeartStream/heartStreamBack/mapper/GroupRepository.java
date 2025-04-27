package com.HeartStream.heartStreamBack.mapper;

import com.HeartStream.heartStreamBack.model.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<UserGroup, Long> {
}
