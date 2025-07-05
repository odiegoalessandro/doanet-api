package com.doanet.api.entity;

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
public class Request {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "donation_point_id")
  private DonationPoint donationPoint;

  @ManyToOne(optional = false)
  @JoinColumn(name = "ong_id")
  private Ong ong;

  @Column(name = "created_at")
  private LocalDate createdAt;

  @OneToMany(mappedBy = "request", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<RequestItem> requestItems = new ArrayList<>();

  public Request(DonationPoint donationPoint, Ong ong, LocalDate createdAt) {
    this.donationPoint = donationPoint;
    this.ong = ong;
    this.createdAt = createdAt;
  }
}
