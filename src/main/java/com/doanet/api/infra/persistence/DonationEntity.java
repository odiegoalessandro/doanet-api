package com.doanet.api.infra.persistence;


import com.doanet.api.domain.enums.DonationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "donation")
public class DonationEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "donor_id")
  private DonorEntity donor;

  @ManyToOne(optional = false)
  @JoinColumn(name = "donation_point_id")
  private DonationPointEntity donationPoint;

  @Column(name = "created_at")
  private LocalDate createdAt;

  @OneToMany(mappedBy = "donation", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<DonationItemEntity> donationItems = new ArrayList<>();

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private DonationStatus status = DonationStatus.CREATED;

  public DonationEntity(Long id,
                        DonorEntity donor,
                        DonationPointEntity donationPoint,
                        LocalDate createdAt,
                        List<DonationItemEntity> donationItems,
                        DonationStatus status) {
    this.id = id;
    this.donor = donor;
    this.donationPoint = donationPoint;
    this.createdAt = createdAt;
    this.donationItems = donationItems;
    this.status = status;
  }
}
