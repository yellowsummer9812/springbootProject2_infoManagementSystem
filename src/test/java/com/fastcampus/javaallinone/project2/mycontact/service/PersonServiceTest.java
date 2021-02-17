package com.fastcampus.javaallinone.project2.mycontact.service;

import com.fastcampus.javaallinone.project2.mycontact.controller.dto.PersonDto;
import com.fastcampus.javaallinone.project2.mycontact.domain.Person;
import com.fastcampus.javaallinone.project2.mycontact.domain.dto.Birthday;
import com.fastcampus.javaallinone.project2.mycontact.repository.PersonRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @InjectMocks // 테스트의 대상이 되는 클래스에 붙여줌
    private PersonService personService;
    @Mock // 대상이 되는 클래스에서 Autowired되는 클래스에 붙여줌
    private PersonRepository personRepository;

    @Test
    void getPeopleByName(){
        when(personRepository.findByName("martin"))
                .thenReturn(Lists.newArrayList(new Person("martin")));

        List<Person> result = personService.getPeopleByName("martin");

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getName()).isEqualTo("martin");
    }

    @Test
    void getPerson(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("martin")));
        Person person = personService.getPerson(1L);

        assertThat(person.getName()).isEqualTo("martin");
    }

    @Test
    void getPersonIfNotFound(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.empty());

        Person person = personService.getPerson(1L);

        assertThat(person).isNull();
    }

    @Test
    void put() {

        personService.put(mockPersonDto());

        // personRepository에 대해서 check하는데, save라는 action이 한번만 실행이 되었는지, save에 대한 parameter가 맞는지
        verify(personRepository, times(1)).save(argThat(new IsPersonWillBeInserted()));
    }

    @Test
    void modifyIfPersonNotFound(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.empty());

        // RuntimeException이 발생할 것이라고 검증하는 logic
        assertThrows(RuntimeException.class, () -> personService.modify(1L, mockPersonDto()));
    }

    @Test
    void modifyIfNameIsDifferent(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("tony")));

        assertThrows(RuntimeException.class, () -> personService.modify(1L, mockPersonDto()));

    }

    @Test
    void modify(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("martin")));

        personService.modify(1L, mockPersonDto());

        //verify(personRepository, times(1)).save(any(Person.class));
        verify(personRepository, times(1)).save((argThat(new IsPersonWillBeUpdated())));
    }

    @Test
    void modifyByNameIfPersonNotFound(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> personService.modify(1L, "deniel"));
    }

    @Test
    void modifyByName(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("martin")));

        personService.modify(1L,"daniel");

        verify(personRepository, times(1)).save(argThat(new IsNameWillBeUpdated()));
    }

    @Test
    void deleteIfPersonNotFound(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> personService.delete(1L));
    }

    @Test
    void delete(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("martin")));

        personService.delete(1L);

        verify(personRepository, times(1)).save(argThat(new IsPersonWillBeDeleted()));
    }

    private PersonDto mockPersonDto(){
        return PersonDto.of("martin","programming", "판교", LocalDate.now(), "programmer", "010-1111-2222");
    }

    private static class IsPersonWillBeInserted implements ArgumentMatcher<Person>{

        @Override
        public boolean matches(Person person) {
            return equals(person.getName(),"martin")
                    && equals(person.getHobby(), "programming")
                    && equals(person.getAddress(),"판교")
                    && equals(person.getBirthday(), Birthday.of(LocalDate.now()))
                    && equals(person.getJob(),"programmer")
                    && equals(person.getPhoneNumber(),"010-1111-2222");
        }
        private boolean equals(Object actual, Object expected){
            return expected.equals(actual);
        }
    }

    // save될 person에 대해서 검증 (set이 잘 수행되었는지 검증)
    private static class IsPersonWillBeUpdated implements ArgumentMatcher<Person> {

        @Override
        public boolean matches(Person person) {
            return equals(person.getName(),"martin")
                    && equals(person.getHobby(), "programming")
                    && equals(person.getAddress(),"판교")
                    && equals(person.getBirthday(), Birthday.of(LocalDate.now()))
                    && equals(person.getJob(),"programmer")
                    && equals(person.getPhoneNumber(),"010-1111-2222");
        }

        private boolean equals(Object actual, Object expected){
            return expected.equals(actual);
        }
    }

    private static  class IsNameWillBeUpdated implements ArgumentMatcher<Person>{

        @Override
        public boolean matches(Person person) {
            return person.getName().equals("daniel");
        }
    }

    private static class IsPersonWillBeDeleted implements ArgumentMatcher<Person>{

        @Override
        public boolean matches(Person person) {
            return person.isDeleted();
        }
    }
}