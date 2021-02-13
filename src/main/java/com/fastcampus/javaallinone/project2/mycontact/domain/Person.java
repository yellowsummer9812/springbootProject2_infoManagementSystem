package com.fastcampus.javaallinone.project2.mycontact.domain;

import com.fastcampus.javaallinone.project2.mycontact.domain.dto.Birthday;
import lombok.*;



import javax.persistence.*;
import javax.validation.Valid;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@RequiredArgsConstructor
@Builder
public class Person {

    @Id // identifier
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동적으로 생성
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private int age;

    private String hobby;

    @NonNull
    private String bloodType;

    private String address;

    @Valid
    @Embedded
    private Birthday birthday;

    private String job;

    @ToString.Exclude
    private String phoneNumber;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Block block;
}
