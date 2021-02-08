package com.fastcampus.javaallinone.project2.mycontact.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Block {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String name;

    private String Reason;

    private LocalDate startDate;

    private LocalDate endDate;
}
