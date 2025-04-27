package com.HeartStream.heartStreamBack.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ExperienceShare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"groupMemberships", "experienceShares", "punchRecords"})
    private User user;

    @ManyToOne
    @JoinColumn(name = "group_id")
    @JsonIgnoreProperties({"groupMembers", "experienceShares", "punchRecords"})
    private UserGroup group;

    private String content;
    private Date shareTime;
}
