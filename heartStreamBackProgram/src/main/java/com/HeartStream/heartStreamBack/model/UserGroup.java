package com.HeartStream.heartStreamBack.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "user_group")
@AllArgsConstructor
@NoArgsConstructor
public class UserGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String goal;

    @OneToMany(mappedBy = "group")
    @JsonIgnoreProperties("group")
    private List<GroupMember> groupMembers = new ArrayList<>();

    @OneToMany(mappedBy = "group")
    @JsonIgnoreProperties("group")
    private List<ExperienceShare> experienceShares = new ArrayList<>();

    @OneToMany(mappedBy = "group")
    @JsonIgnoreProperties("group")
    private List<PunchRecord> punchRecords = new ArrayList<>();
}
