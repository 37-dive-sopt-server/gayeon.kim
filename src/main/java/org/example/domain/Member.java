package org.example.domain;

import java.time.LocalDate;

public class Member {

    private Long id;
    private String name;
    /**
     * 신규 필드: 생년월일, 이메일, 성별(Gender enum)
     */
    private LocalDate birthDate;
    private String email;
    private Gender gender;

    public Member(Long id, String name, LocalDate birthDate, String email, Gender gender) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.email = email;
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getEmail() {
        return email;
    }

    public Gender getGender() {
        return gender;
    }
}
