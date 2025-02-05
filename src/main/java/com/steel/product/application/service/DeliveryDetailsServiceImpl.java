package com.steel.product.application.service;

import com.steel.product.application.dao.DeliveryDetailsRepository;
import com.steel.product.application.dto.delivery.DeliveryDto;
import com.steel.product.application.dto.delivery.DeliveryItemDetails;
import com.steel.product.application.dto.delivery.DeliveryPacketsDto;
import com.steel.product.application.entity.DeliveryDetails;
import com.steel.product.application.entity.Instruction;
import com.steel.product.application.entity.InwardEntry;
import com.steel.product.application.entity.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DeliveryDetailsServiceImpl implements DeliveryDetailsService{

    private final static Logger LOGGER = LoggerFactory.getLogger(DeliveryDetails.class);

    private DeliveryDetailsRepository deliveryDetailsRepo;

    private InstructionService instructionService;

    private StatusService statusService;

    private InwardEntryService inwardEntryService;

    @Autowired
    public DeliveryDetailsServiceImpl(DeliveryDetailsRepository deliveryDetailsRepo, InstructionService instructionService, StatusService statusService, InwardEntryService inwardEntryService) {
        this.deliveryDetailsRepo = deliveryDetailsRepo;
        this.instructionService = instructionService;
        this.statusService = statusService;
        this.inwardEntryService = inwardEntryService;
    }

    @Override
    public List<Instruction> getAll() {
        return deliveryDetailsRepo.deliveredItems();
    }

    @Override
    public List<Instruction> getInstructionsByDeliveryId(int deliveryId) {
        return deliveryDetailsRepo.deliveredItemsById(deliveryId);
    }

    @Override
    public DeliveryDetails getById(int theId) {

        Optional<DeliveryDetails> result = deliveryDetailsRepo.findById(theId);

        DeliveryDetails delivery = null;

        if (result.isPresent()) {
            delivery = result.get();
        }
        else {
            // we didn't find the employee
            throw new RuntimeException("Did not find delivery id - " + theId);
        }

        return delivery;
    }

    @Override
    @Transactional
    public DeliveryDetails save(DeliveryDto deliveryDto) {
        LOGGER.info("in save delivery api");
        List<DeliveryItemDetails> deliveryItemDetails;
        DeliveryDetails delivery;
            if(deliveryDto.getDeliveryId() != null){
                LOGGER.info("Updating delivery with id "+deliveryDto.getDeliveryId());
                delivery = getById(deliveryDto.getDeliveryId());
                if(deliveryDto.getCustomerInvoiceNo() != null) {
                    delivery.setCustomerInvoiceNo(deliveryDto.getCustomerInvoiceNo());
                }
                if(deliveryDto.getCustomerInvoiceDate() != null){
                    delivery.setCustomerInvoiceDate(deliveryDto.getCustomerInvoiceDate());
                }
                deliveryDetailsRepo.save(delivery);
                return delivery;
            }
            LOGGER.info("adding new delivery with id");
            delivery = new DeliveryDetails();

            delivery.setCreatedBy(1);
            delivery.setUpdatedBy(1);
        delivery.setVehicleNo(deliveryDto.getVehicleNo());

        deliveryItemDetails = deliveryDto.getDeliveryItemDetails();

        float inStockWeight = 0f, weightToDeliver = 0f, parentWeight = 0f;
        Integer deliveredStatusId = 4;
        Status deliveredStatus = statusService.getStatusById(deliveredStatusId);
        Integer readyToDeliverStatusId = 3;
        Map<Integer, String> instructionRemarksMap = deliveryItemDetails.stream().filter(d -> d.getRemarks() != null)
                .collect(Collectors.toMap(d -> d.getInstructionId(), d -> d.getRemarks()));
        List<Instruction> instructions = instructionService.findAllByInstructionIdInAndStatus(deliveryItemDetails.stream()
                .map(d -> d.getInstructionId()).collect(Collectors.toList()), readyToDeliverStatusId);
        instructions.forEach(ins -> ins.setStatus(deliveredStatus));
        instructions = instructionService.saveAll(instructions);

        InwardEntry inwardEntry;
        Instruction parentInstruction;
        List<Instruction> groupInstructions = null;
        Set<Instruction> parentGroupInstructions = null;
        Set<Instruction> childrenInstructions;
            Set<InwardEntry> inwardEntryList = new HashSet<>();
            Integer parentGroupId;
            boolean isAnyInstructionNotDelivered = true;
            try {
                for (Instruction instruction : instructions) {
                    inwardEntry = instruction.getInwardId();
                    parentInstruction = instruction.getParentInstruction();
                    if (inwardEntry == null) {
                        inwardEntry = parentInstruction.getInwardId();
                    }
                    parentGroupId = instruction.getParentGroupId();
                    inStockWeight = inwardEntry.getInStockWeight();
                    if (parentGroupId != null) {
                        LOGGER.info("instruction has inward id,parentGroupId " + inwardEntry.getInwardEntryId() + " " + parentGroupId);
                        if(parentGroupInstructions == null || !parentGroupInstructions.contains(instruction)) {
                            parentGroupInstructions = new HashSet<>();
                            parentGroupInstructions.addAll(instructionService.findAllByParentGroupId(parentGroupId));
                            LOGGER.info("total parent group instructions "+parentGroupInstructions.size());
                        }
                        isAnyInstructionNotDelivered = parentGroupInstructions.stream().anyMatch(ins -> !ins.getStatus().equals(deliveredStatus));
                        if (!isAnyInstructionNotDelivered) {
                            if(groupInstructions == null || (groupInstructions != null && !groupInstructions.isEmpty() &&!groupInstructions.get(0).getGroupId().equals(parentGroupId))) {
                                groupInstructions = instructionService.findAllByGroupId(parentGroupId);
                                groupInstructions.forEach(ins -> ins.setStatus(deliveredStatus));
                            }
                        }
                        weightToDeliver = instruction.getActualWeight();
                    } else if (parentInstruction != null) {
                        LOGGER.info("instruction has parent instruction id " + instruction.getParentInstruction().getInstructionId());
                            weightToDeliver = instruction.getActualWeight();
                            childrenInstructions = parentInstruction.getChildInstructions();
                            LOGGER.info("parent instruction has " + childrenInstructions.size() + " children");
                            isAnyInstructionNotDelivered = childrenInstructions.stream().anyMatch(ins -> !ins.getStatus().equals(deliveredStatus));
                            if (!isAnyInstructionNotDelivered) {
                                LOGGER.info("setting parent instruction status delivered as all children of parent instruction " + parentInstruction.getInstructionId() + " have status delivered");
                                parentInstruction.setStatus(deliveredStatus);
                            }
                            LOGGER.info("no status change for parent instruction " + parentInstruction.getInstructionId() + " as all children not delivered");


                    } else {
                        LOGGER.info("instruction has inward id " + inwardEntry.getInwardEntryId());
//                    parentWeight = inwardEntry.getInStockWeight();
                        weightToDeliver = instruction.getActualWeight();
                        childrenInstructions = instruction.getChildInstructions();
                        if (childrenInstructions != null && !childrenInstructions.isEmpty()) {
                            LOGGER.info("inward id" + inwardEntry.getInwardEntryId() + " is a parent instruction with " + childrenInstructions.size() + " children");
                            if (childrenInstructions.stream().anyMatch(ins -> !ins.getStatus().equals(deliveredStatus))) {
                                throw new RuntimeException("instruction with id " + instruction.getInstructionId() + " has undelivered children instructions");
                            }

                        }
                    }
//                    else {
//                        LOGGER.error("No inward id or parent instruction id found in instruction with id " + instruction.getInstructionId());
//                        throw new RuntimeException("No inward id or parent instruction id found in instruction with id " + instruction.getInstructionId());
//                    }
//                if(weightToDeliver > parentWeight){
//                    LOGGER.error("weight to deliver "+weightToDeliver+" exceeds parent weight "+parentWeight);
//                    throw new RuntimeException("weight to deliver "+weightToDeliver+" exceeds parent weight "+parentWeight);
//                }
                    if (weightToDeliver > inStockWeight) {
                        LOGGER.error("weight to deliver " + weightToDeliver + " exceeds in stock weight " + inStockWeight);
                        throw new RuntimeException("weight to deliver " + weightToDeliver + " exceeds in stock weight " + inStockWeight);
                    }
                    inwardEntry.setInStockWeight(inStockWeight - weightToDeliver);
                    if (Math.abs(inwardEntry.getInStockWeight()) < 1f) {
                        inwardEntry.setStatus(deliveredStatus);
                    }
                    inwardEntryList.add(inwardEntry);
                    instruction.setRemarks(instructionRemarksMap.get(instruction.getInstructionId()));

                }
            }catch (Exception e){
                e.printStackTrace();
                throw e;
            }
            delivery.addAllInstructions(instructions);
            float totalWeight = deliveryItemDetails.stream().reduce(0f,(sum,d) -> sum + d.getWeight().floatValue(),Float::sum);
            delivery.setTotalWeight(totalWeight);
            LOGGER.info("saving "+inwardEntryList.size()+" inward entries");
            inwardEntryService.saveAll(inwardEntryList);
            LOGGER.info("saving delivery details");
            deliveryDetailsRepo.save(delivery);
        return delivery;
    }

    @Override
    @Transactional
    public void deleteById(Integer deliveryId) {
        LOGGER.info("inside delete delivery api for delivery id "+deliveryId);
        List<Instruction> instructions = this.findInstructionsByDeliveryId(deliveryId);
        DeliveryDetails deliveryDetails = instructions.get(0).getDeliveryDetails();
        LOGGER.info("deleting delivery "+deliveryDetails.getDeliveryId());
        Integer deliveredStatusId = 4,readyToDeliverStatusId = 3;
        Status deliveredStatus = statusService.getStatusById(deliveredStatusId);
        Status readyToDeliverStatus = statusService.getStatusById(readyToDeliverStatusId);

        InwardEntry inwardEntry = null;
        Instruction parentInstruction = null;
        Integer parentGroupId;
        List<Instruction> groupInstructions = null;

        for(Instruction ins:instructions){
            LOGGER.info("deleting delivery for instruction id "+ins.getInstructionId());
            inwardEntry = ins.getInwardId();
            parentInstruction = ins.getParentInstruction();
            if(inwardEntry == null){
                LOGGER.info("setting inward from parent instruction "+parentInstruction.getInstructionId());
                inwardEntry = parentInstruction.getInwardId();
            }
            parentGroupId = ins.getParentGroupId();
            if(inwardEntry != null && parentGroupId != null){
                LOGGER.info("deleting delivery for parent group instruction with id, "+parentGroupId+" instruction id "+ins.getInstructionId());
                if(groupInstructions == null || (groupInstructions != null && !groupInstructions.isEmpty() &&!groupInstructions.get(0).getGroupId().equals(parentGroupId))){
                    LOGGER.info("fetching group instructions for parent group id "+parentGroupId);
                    groupInstructions = instructionService.findAllByGroupId(parentGroupId);
                    LOGGER.info("group instructions size "+groupInstructions.size());
                    if(groupInstructions.get(0).getStatus().equals(deliveredStatus)) {
                        LOGGER.info("setting group instructions status from delivered to readyToDeliver");
                        groupInstructions.forEach(groupIns -> groupIns.setStatus(readyToDeliverStatus));
                    }
                }
            }
            else if(parentInstruction != null){
                LOGGER.info("instruction has parent instruction id "+parentInstruction.getInstructionId());
                if(parentInstruction.getStatus().equals(deliveredStatus)){
                    parentInstruction.setStatus(readyToDeliverStatus);
                }
            }
            else if(inwardEntry != null){
                LOGGER.info("instruction has inward id " + inwardEntry.getInwardEntryId());
                inwardEntry.setStatus(readyToDeliverStatus);
            }
            else{
                LOGGER.error("No inward id or parent instruction id found in instruction with id " + ins.getInstructionId());
                throw new RuntimeException("No inward id or parent instruction id found in instruction with id " + ins.getInstructionId());
            }
            ins.setStatus(readyToDeliverStatus);
            Float inStockWeight = inwardEntry.getInStockWeight();
            inwardEntry.setInStockWeight(inStockWeight + ins.getActualWeight());
            LOGGER.info("adding instruction actual weight "+ins.getActualWeight()+" and setting inward inStock weight from "+inStockWeight+" to "+(inStockWeight + ins.getActualWeight()));
            if(inwardEntry.getStatus().equals(deliveredStatus)){
                LOGGER.info("setting inward " + inwardEntry.getInwardEntryId() + " status to ready to deliver");
                inwardEntry.setStatus(readyToDeliverStatus);
            }
            deliveryDetails.removeInstruction(ins);
        }
        deliveryDetailsRepo.deleteById(deliveryId);

    }

    @Override
    public List<DeliveryPacketsDto> deliveryList() {
        List<DeliveryDetails> inwardEntryList = deliveryDetailsRepo.findAllDeliveries();
        LOGGER.info("Delivery details list size "+inwardEntryList.size());
        return inwardEntryList.stream().map(inw -> new DeliveryPacketsDto(inw)).collect(Collectors.toList());

    }

    @Override
    public List<Instruction> findInstructionsByDeliveryId(Integer deliveryId) {
        return deliveryDetailsRepo.findInstructionsByDeliveryId(deliveryId);
    }

    @Override
    public Float findInstructionByInwardIdAndInstructionId(Integer inwardId,Integer instructionId) {
        return deliveryDetailsRepo.findInstructionByInwardIdAndInstructionId(inwardId,instructionId);
    }


}
