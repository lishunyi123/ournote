package com.lishunyi.ournote.member.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author 李顺仪
 * @version 1.0
 * @since 2020/9/11 17:17
 **/
@Entity
@Table(name = "t_member")
@Data
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private Boolean enabled;
}
