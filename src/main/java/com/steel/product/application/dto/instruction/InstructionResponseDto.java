package com.steel.product.application.dto.instruction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.steel.product.application.dto.delivery.DeliveryResponseDto;
import com.steel.product.application.dto.process.ProcessDto;
import com.steel.product.application.dto.status.StatusDto;
import com.steel.product.application.entity.PacketClassification;

import java.util.Date;
import java.util.List;

public class InstructionResponseDto {
    private Integer instructionId ;

    private Integer inwardId;

    private ProcessDto process;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date instructionDate;

    private Float plannedLength;

    private Float plannedWidth;

    private Float plannedWeight;

    private Integer plannedNoOfPieces;

    private Float actualLength;

    private Float actualWidth;

    private Float actualWeight;

    private Integer actualNoOfPieces;

    private StatusDto status;

    private PacketClassification packetClassification;

    private Integer groupId ;

    private Integer parentGroupId ;

    private Integer parentInstructionId;

    private Float wastage;

    private Float damage;

    private Float packingWeight;

    private Integer createdBy;

    private Integer updatedBy;

    private Date createdOn;

    private Date updatedOn;

    private Boolean isDeleted;

    private String remarks;

    private List<InstructionResponseDto> childInstructions;

    private DeliveryResponseDto deliveryDetails;

    public Integer getInstructionId() {
        return instructionId;
    }

    public void setInstructionId(Integer instructionId) {
        this.instructionId = instructionId;
    }

    public Integer getInwardId() {
        return inwardId;
    }

    public void setInwardId(Integer inwardId) {
        this.inwardId = inwardId;
    }

    public ProcessDto getProcess() {
        return process;
    }

    public void setProcess(ProcessDto process) {
        this.process = process;
    }

    public Date getInstructionDate() {
        return instructionDate;
    }

    public void setInstructionDate(Date instructionDate) {
        this.instructionDate = instructionDate;
    }

    public Float getPlannedLength() {
        return plannedLength;
    }

    public void setPlannedLength(Float plannedLength) {
        this.plannedLength = plannedLength;
    }

    public Float getPlannedWidth() {
        return plannedWidth;
    }

    public void setPlannedWidth(Float plannedWidth) {
        this.plannedWidth = plannedWidth;
    }

    public Float getPlannedWeight() {
        return plannedWeight;
    }

    public void setPlannedWeight(Float plannedWeight) {
        this.plannedWeight = plannedWeight;
    }

    public Integer getPlannedNoOfPieces() {
        return plannedNoOfPieces;
    }

    public void setPlannedNoOfPieces(Integer plannedNoOfPieces) {
        this.plannedNoOfPieces = plannedNoOfPieces;
    }

    public Float getActualLength() {
        return actualLength;
    }

    public void setActualLength(Float actualLength) {
        this.actualLength = actualLength;
    }

    public Float getActualWidth() {
        return actualWidth;
    }

    public void setActualWidth(Float actualWidth) {
        this.actualWidth = actualWidth;
    }

    public Float getActualWeight() {
        return actualWeight;
    }

    public void setActualWeight(Float actualWeight) {
        this.actualWeight = actualWeight;
    }

    public Integer getActualNoOfPieces() {
        return actualNoOfPieces;
    }

    public void setActualNoOfPieces(Integer actualNoOfPieces) {
        this.actualNoOfPieces = actualNoOfPieces;
    }

    public StatusDto getStatus() {
        return status;
    }

    public void setStatus(StatusDto status) {
        this.status = status;
    }

    public PacketClassification getPacketClassification() {
        return packetClassification;
    }

    public void setPacketClassification(PacketClassification packetClassification) {
        this.packetClassification = packetClassification;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getParentGroupId() {
        return parentGroupId;
    }

    public void setParentGroupId(Integer parentGroupId) {
        this.parentGroupId = parentGroupId;
    }

    public Integer getParentInstructionId() {
        return parentInstructionId;
    }

    public void setParentInstructionId(Integer parentInstructionId) {
        this.parentInstructionId = parentInstructionId;
    }

    public Float getWastage() {
        return wastage;
    }

    public void setWastage(Float wastage) {
        this.wastage = wastage;
    }

    public Float getDamage() {
        return damage;
    }

    public void setDamage(Float damage) {
        this.damage = damage;
    }

    public Float getPackingWeight() {
        return packingWeight;
    }

    public void setPackingWeight(Float packingWeight) {
        this.packingWeight = packingWeight;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public DeliveryResponseDto getDeliveryDetails() {
        return deliveryDetails;
    }

    public void setDeliveryDetails(DeliveryResponseDto deliveryDetails) {
        this.deliveryDetails = deliveryDetails;
    }

    public List<InstructionResponseDto> getChildInstructions() {
        return childInstructions;
    }

    public void setChildInstructions(List<InstructionResponseDto> childInstructions) {
        this.childInstructions = childInstructions;
    }
}
