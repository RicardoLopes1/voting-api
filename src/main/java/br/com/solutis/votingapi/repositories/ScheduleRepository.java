package br.com.solutis.votingapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.solutis.votingapi.entities.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

}
