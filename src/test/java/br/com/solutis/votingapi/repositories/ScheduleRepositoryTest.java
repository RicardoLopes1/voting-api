package br.com.solutis.votingapi.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.util.Assert;

import br.com.solutis.votingapi.entities.Schedule;

@DataJpaTest
public class ScheduleRepositoryTest {
  
  @Autowired
  private ScheduleRepository scheduleRepository;

  @Test
  public void shouldReturnScheduleByName() {
    Optional<Schedule> schedule = scheduleRepository.findByName("schedule 001");
    Assert.isTrue(schedule.isPresent(), "Should return a schedule by name.");
  }

  @Test
  public void shouldNotReturnScheduleByName() {
    Optional<Schedule> schedule = scheduleRepository.findByName("");
    Assert.isTrue(schedule.isEmpty(), "Should not return a schedule by name.");
  }
  
}
