package com.fastcampus.javaallinone.project2.mycontact.service;

import com.fastcampus.javaallinone.project2.mycontact.domain.Block;
import com.fastcampus.javaallinone.project2.mycontact.domain.Person;
import com.fastcampus.javaallinone.project2.mycontact.repository.BlockRepository;
import com.fastcampus.javaallinone.project2.mycontact.repository.PersonRepository;
import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonServiceTest {

    @Autowired
    private PersonService personService;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private BlockRepository blockRepository;

    @Test
    void getPeopleExcludeBlocks(){
        givenPeople();

        List<Person> result = personService.getPeopleExcludeBlocks();

        //System.out.println(result);

        result.forEach(System.out::println);
    }

    @Test
    void getPeopleByName(){
        givenPeople();

        List<Person> result = personService.getPeopleByName("martin");
        result.forEach(System.out::println);
    }

    @Test
    void cascadeTest(){
        givenPeople();

        List<Person> result = personRepository.findAll();
        result.forEach(System.out::println);

        Person person = result.get(3);
        person.getBlock().setStartDate(LocalDate.now());
        person.getBlock().setEndDate(LocalDate.now());

        personRepository.save(person);
        personRepository.findAll().forEach(System.out::println);

        //personRepository.delete(person);
        //personRepository.findAll().forEach(System.out::println);
        //blockRepository.findAll().forEach(System.out::println);

        person.setBlock(null); // 블럭 해제
        personRepository.save(person);
        personRepository.findAll().forEach(System.out::println);
        blockRepository.findAll().forEach(System.out::println);
    }

    private void givenBlockPerson(String name, int age, String bloodType){
        Person blockPerson = new Person(name, age, bloodType);
        blockPerson.setBlock(new Block(name));

        personRepository.save(blockPerson);
    }

    @Test
    void getPerson(){
        givenPeople();

        Person person = personService.getPerson((long) 3);

        System.out.println(person);
    }

    private void givenPeople() {
        givenPerson("martin", 10, "A");
        givenPerson("david", 9, "B");
        givenBlockPerson("dennis", 7, "O");
        givenBlockPerson("martin", 11, "AB");
    }

    private void givenPerson(String name, int age, String bloodType, LocalDate birthday) {
        Person person = new Person(name, age, bloodType);
        person.setBirthday(birthday);
        personRepository.save(person);
    }

    private void givenPerson(String name, int age, String bloodType){
        givenPerson(name, age, bloodType, null);
    }

    @Test
    void findByBloodType(){
        givenPerson("martin", 10, "A");
        givenPerson("david", 9, "B");
        givenPerson("dennis", 8, "O");
        givenPerson("sophia", 7, "AB");
        givenPerson("benny", 6, "A");
        givenPerson("john", 5, "A");

        List<Person> result = personRepository.findByBloodType("A");
        result.forEach(System.out::println);

    }

    @Test
    void findByBirthdayBetween(){
        givenPerson("martin", 10, "A",LocalDate.of(1991,8,15));
        givenPerson("david", 9, "B",LocalDate.of(1992,7,10));
        givenPerson("dennis", 8, "O",LocalDate.of(1993,1,5));
        givenPerson("sophia", 7, "AB",LocalDate.of(1994,6,30));
        givenPerson("benny", 6, "A",LocalDate.of(1995,8,30));

        List<Person> result = personRepository.findByBirthdayBetween(LocalDate.of(1991, 8, 1), LocalDate.of(1991, 8, 31));
        result.forEach(System.out::println);

    }
}