package com.fastcampus.javaallinone.project2.mycontact.service;

import com.fastcampus.javaallinone.project2.mycontact.controller.dto.PersonDto;
import com.fastcampus.javaallinone.project2.mycontact.domain.Block;
import com.fastcampus.javaallinone.project2.mycontact.domain.Person;
import com.fastcampus.javaallinone.project2.mycontact.domain.dto.Birthday;
import com.fastcampus.javaallinone.project2.mycontact.repository.BlockRepository;
import com.fastcampus.javaallinone.project2.mycontact.repository.PersonRepository;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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
        //Person person = personRepository.findById(id).get();

        Person person = personRepository.findById(id).orElse(null); // 값이 없으면 null 리턴, 아니면 get


        log.info("person : {}", person); // production에 배포됐을 때 log출력 제한할 수 있는 장점

        return person;
    }

    @Transactional
    public void put(Person person){
        personRepository.save(person);
    }

    @Transactional
    public void modify(Long id, PersonDto personDto) {
        Person person = personRepository.findById(id).orElseThrow(() -> new RuntimeException("아이디가 존재하지 않습니다."));

        // 이름에 대해서 Validation
        if(!person.getName().equals(personDto.getName())){
            throw new RuntimeException("이름이 다릅니다.");
        }

        // person entity에 set
        person.set(personDto);

        // db에 저장
        personRepository.save(person);
    }

    @Transactional
    public void modify(Long id, String name){
        Person person = personRepository.findById(id).orElseThrow(() -> new RuntimeException("아이디가 존재하지 않습니다."));

        person.setName(name);

        personRepository.save(person);
    }

    @Transactional
    public void delete(Long id){
        Person person = personRepository.findById(id).orElseThrow(() -> new RuntimeException("아이디가 존재하지 않습니다."));

        person.setDeleted(true);

        personRepository.save(person);

        // DB에서 바로 지우는 방법은 위험하기 때문에 위의 soft한 방식으로 delete
        //personRepository.deleteById(id);


    }
}
