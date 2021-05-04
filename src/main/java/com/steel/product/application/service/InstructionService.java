package com.steel.product.application.service;

import com.steel.product.application.entity.Instruction;

import java.util.List;

public interface InstructionService {

	public List<Instruction> getAll();

	public List<Instruction> getAllWIP();

	public List<Instruction> getAllWIPList();
	
	public Instruction getById(int theId);
	
	public void save(Instruction instruction);
	
	public void deleteById(int id);

	public void updateInstructionWithDeliveryRemarks(int deliveryId, String remarks,int instructionId);

}
