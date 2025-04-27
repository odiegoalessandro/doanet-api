package com.doanet.api.controller;

import com.doanet.api.dto.CreateDonorDto;
import com.doanet.api.entity.Donor;
import com.doanet.api.service.CreateDonorService;
import com.doanet.api.service.FindDonorService;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/donor")
public class DonorController {
  private final CreateDonorService createDonorService;
  private final FindDonorService findDonorService;

  public DonorController(CreateDonorService createDonorService, FindDonorService findDonorService){
    this.createDonorService = createDonorService;
    this.findDonorService = findDonorService;
  }

  @PostMapping
  @Transactional
  public Donor create(@RequestBody CreateDonorDto donor){
    return this.createDonorService.create(donor.user(), donor.document(), donor.reasonSocial());
  }

  @GetMapping("/{id}")
  public Donor findById(@PathVariable("id") Long id){
    return this.findDonorService.findById(id);
  }

  @GetMapping("/document/{document}")
  public Donor findByDocument(@PathVariable("document") String document){
    return this.findDonorService.findByDocument(document);
  }

  @GetMapping("/reason-social/{reasonSocial}")
  public Donor findByReasonSocial(@PathVariable("reasonSocial") String reasonSocial){
    return this.findDonorService.findByReasonSocial(reasonSocial);
  }

  @GetMapping
  public Page<Donor> findAll(@RequestParam int page, @RequestParam int size){
    return this.findDonorService.findAll(page, size);
  }
}
