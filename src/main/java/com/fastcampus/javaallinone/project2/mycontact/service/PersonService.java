package com.fastcampus.javaallinone.project2.mycontact.service;

import com.fastcampus.javaallinone.project2.mycontact.domain.Block;
import com.fastcampus.javaallinone.project2.mycontact.domain.Person;
import com.fastcampus.javaallinone.project2.mycontact.repository.BlockRepository;
import com.fastcampus.javaallinone.project2.mycontact.repository.PersonRepository;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PersonService {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private BlockRepository blockRepository;

    public List<Person> getPeopleExcludeBlocks(){
        List<Person> people = personRepository.findAll();
        // @OneToOne 지정하지 않았을 때 사용
        //List<Block> blocks = blockRepository.findAll();
        //List<String> blockNames = blocks.stream().map(Block::getName).collect(Collectors.toList()); // blocks에 있는 모든 요소들에 대해 getName() 실행
        //return people.stream().filter(person -> !blockNames.contains(person.getName())).collect(Collectors.toList());

        // @OneToOnE 지정했을 때 사용
        //return people.stream().filter(person -> person.getBlock() == null).collect(Collectors.toList());

        // Query Method 사용했을 때
        return personRepository.findByBlockIsNull();
    }

    public List<Person> getPeopleByName(String name){
        //List<Person> people = personRepository.findAll();

        //return people.stream().filter(person -> person.getName().equals(name)).collect(Collectors.toList());
        // Query method 활용하면
        return personRepository.findByName(name);
    }

    @Transactional(readOnly = true)
    public Person getPerson(Long id){
        Person person = personRepository.findById(id).get();

        log.info("person : {}", person); // production에 배포됐을 때 log출력 제한할 수 있는 장점

        return person;
    }
}