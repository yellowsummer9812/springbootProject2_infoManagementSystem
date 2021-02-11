package com.fastcampus.javaallinone.project2.mycontact.repository;

import com.fastcampus.javaallinone.project2.mycontact.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByName(String name);

    // 차단 안 된 사람 가져오기
    List<Person> findByBlockIsNull();

    List<Person> findByBloodType(String bloodType);

    List<Person> findByBirthdayBetween(LocalDate startDate,LocalDate endDate);
}
