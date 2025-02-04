package com.lrin.project.entity.member;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Entity
@NoArgsConstructor
@Data
@Table(name = "usermember")
public class MemberEntity {
    @Id
    @Column
    private String id;

    @Column
    private String pw;

    @Column
    private String name;

    @Column
    private String addr;

    @Column
    private String streetaddr;

    @Column
    private String detailaddr;

    @Column
    private String tel;

    @Builder
    public MemberEntity(String id, String pw, String name, String addr, String streetaddr, String detailaddr, String tel) {
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.addr = addr;
        this.streetaddr = streetaddr;
        this.detailaddr = detailaddr;
        this.tel = tel;
    }
}