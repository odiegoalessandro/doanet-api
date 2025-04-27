package com.doanet.api.controller;

import com.doanet.api.dto.CreateDonationPointDto;
import com.doanet.api.entity.DonationPoint;
import com.doanet.api.service.CreateDonationPointService;
import com.doanet.api.service.FindDonationPointService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/donation-point")
public class DonationPointController {
  private final CreateDonationPointService createDonationPointService;
  private final FindDonationPointService findDonationPointService;

  public DonationPointController(
      CreateDonationPointService createDonationPointService,
      FindDonationPointService findDonationPointService
  ) {
    this.createDonationPointService = createDonationPointService;
    this.findDonationPointService = findDonationPointService;
  }

  @PostMapping
  @Transactional
  public DonationPoint create(@RequestBody @Valid CreateDonationPointDto donationPoint) {
    return this.createDonationPointService.create(donationPoint.user(), donationPoint.description());
  }

  @GetMapping("/description/{description}")
  public Page<DonationPoint> findByDescription(
      @PathVariable("description") String description,
      @RequestParam Integer pageNumber,
      @RequestParam Integer pageSize
  ){
    return this.findDonationPointService.findByDescription(description, pageNumber, pageSize);
  }

  @GetMapping("/{id}")
  public DonationPoint findById(@PathVariable("id") Long id){
    return this.findDonationPointService.findById(id);
  }

  @GetMapping
  public Page<DonationPoint> findAll(@RequestParam Integer pageNumber, @RequestParam Integer pageSize){
    return this.findDonationPointService.findAll(pageNumber, pageSize);
  }
}


