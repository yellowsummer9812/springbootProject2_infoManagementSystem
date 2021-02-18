package com.fastcampus.javaallinone.project2.mycontact.service;

import com.fastcampus.javaallinone.project2.mycontact.controller.dto.PersonDto;
import com.fastcampus.javaallinone.project2.mycontact.domain.Person;
import com.fastcampus.javaallinone.project2.mycontact.exception.PersonNotFoundException;
import com.fastcampus.javaallinone.project2.mycontact.exception.RenameNotPermittedException;
import com.fastcampus.javaallinone.project2.mycontact.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public List<Person> getPeopleByName(String name){
        //List<Person> people = personRepository.findAll();

        //return people.stream().filter(person -> person.getName().equals(name)).collect(Collectors.toList());
        // Query method 활용하면
        return personRepository.findByName(name);
    }

    @Transactional(readOnly = true)
    public Person getPerson(Long id){
        return personRepository.findById(id).orElse(null); // 값이 없으면 null 리턴, 아니면 get
    }

    @Transactional
    public void put(PersonDto personDto){
        Person person = new Person();
        person.set(personDto);
        person.setName(personDto.getName());

        personRepository.save(person);
    }

    @Transactional
    public void modify(Long id, PersonDto personDto) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);

        // 이름에 대해서 Validation
        if(!person.getName().equals(personDto.getName())){
            throw new RenameNotPermittedException();
        }

        // person entity에 set
        person.set(personDto);

        // db에 저장
        personRepository.save(person);
    }

    @Transactional
    public void modify(Long id, String name){
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);

        person.setName(name);

        personRepository.save(person);
    }

    @Transactional
    public void delete(Long id){
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);

        person.setDeleted(true);

        personRepository.save(person);

        // DB에서 바로 지우는 방법은 위험하기 때문에 위의 soft한 방식으로 delete
        //personRepository.deleteById(id);


    }
}
