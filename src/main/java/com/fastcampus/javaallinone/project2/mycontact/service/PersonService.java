package com.fastcampus.javaallinone.project2.mycontact.service;

import com.fastcampus.javaallinone.project2.mycontact.domain.Block;
import com.fastcampus.javaallinone.project2.mycontact.domain.Person;
import com.fastcampus.javaallinone.project2.mycontact.repository.BlockRepository;
import com.fastcampus.javaallinone.project2.mycontact.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
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
        return people.stream().filter(person -> person.getBlock() == null).collect(Collectors.toList());
    }
}
