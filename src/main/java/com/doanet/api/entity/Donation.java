package com.doanet.api.entity;


import com.doanet.api.dto.CreateDonationDto;
import com.doanet.api.enums.Status;
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
@Table(name = "donation")
public class Donation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "donor_id")
  private Donor donor;

  @ManyToOne(optional = false)
  @JoinColumn(name = "donation_point_id")
  private DonationPoint donationPoint;

  @Column(name = "created_at")
  private LocalDate createdAt;

  @OneToMany(mappedBy = "donation", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<DonationItem> donationItems = new ArrayList<>();

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Status status = Status.CREATED;

  public Donation(Donor donor, DonationPoint donationPoint, LocalDate createdAt) {
    this.donor = donor;
    this.donationPoint = donationPoint;
    this.createdAt = createdAt;
  }
}
