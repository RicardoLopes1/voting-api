package br.com.solutis.votingapi.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_voting")
public class Voting {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "session_id")
  private Long sessionId;

  @Column(name = "schedule_id")
  private Long scheduleId;

  @Column(name = "associate_id")
  private Long associateId;

  @Column(name = "vote")
  private String vote;

  @Column(name = "vote_date")
  private LocalDateTime voteDate;
  
}
