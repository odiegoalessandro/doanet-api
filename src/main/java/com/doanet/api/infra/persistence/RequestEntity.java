package com.doanet.api.infra.persistence;

import com.doanet.api.domain.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "request")
public class RequestEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "donation_point_id")
  private DonationPointEntity donationPoint;

  @ManyToOne(optional = false)
  @JoinColumn(name = "ong_id")
  private OngEntity ong;

  @Column(name = "created_at")
  private LocalDate createdAt;

  @OneToMany(mappedBy = "request", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<RequestItemEntity> items = new ArrayList<>();

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private RequestStatus status = RequestStatus.CREATED;

  public RequestEntity(DonationPointEntity donationPoint, OngEntity ong, LocalDate createdAt) {
    this.donationPoint = donationPoint;
    this.ong = ong;
    this.createdAt = createdAt;
  }
}
