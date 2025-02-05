package com.steel.product.application.controller;

import com.steel.product.application.dto.material.MaterialRequestDto;
import com.steel.product.application.dto.material.MaterialResponseDetailsDto;
import com.steel.product.application.dto.material.MaterialResponseDto;
import com.steel.product.application.entity.Material;
import com.steel.product.application.service.MaterialDescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(origins = {"http://localhost:3001"})
//@CrossOrigin(origins = {"http://rubrum-frontend.s3-website.ap-south-1.amazonaws.com"})
@CrossOrigin
@RequestMapping({"/api/material"})
public class MaterialDescriptionController {
  @Autowired
  private MaterialDescriptionService matDescSvc;
  

  
  @PostMapping({"/save"})
  public ResponseEntity<Object> saveMatDesc(@RequestBody MaterialRequestDto materialRequestDto) {
    try{
      matDescSvc.saveMatDesc(materialRequestDto);
      return new ResponseEntity<>("Material saved successfully", HttpStatus.OK);
    }catch (Exception e){
      e.printStackTrace();
      return new ResponseEntity<>("Unknown error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping({"/update"})
  public ResponseEntity<Object> updateMaterial(@RequestBody MaterialRequestDto materialRequestDto) {
    try{
      matDescSvc.saveMatDesc(materialRequestDto);
      return new ResponseEntity<>("Material saved successfully", HttpStatus.OK);
    }catch (Exception e){
      e.printStackTrace();
      return new ResponseEntity<>("Unknown error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  
  @GetMapping({"/list"})
  public List<MaterialResponseDetailsDto> getAllMatDesc() {
    return this.matDescSvc.getAllMatDesc();
  }
  
  @GetMapping({"/getById/{matId}"})
  public MaterialResponseDetailsDto getMatById(@PathVariable int matId) {
    return Material.valueOfMat(this.matDescSvc.getMatById(matId));
  }
}
