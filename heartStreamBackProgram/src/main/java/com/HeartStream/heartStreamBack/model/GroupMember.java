package com.HeartStream.heartStreamBack.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "group_member")
@AllArgsConstructor
@NoArgsConstructor
public class GroupMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("groupMemberships")
    private User user;

    @ManyToOne
    @JoinColumn(name = "group_id")
    @JsonIgnoreProperties({"groupMembers", "experienceShares", "punchRecords"})
    private UserGroup group;

}
