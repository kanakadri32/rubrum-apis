package com.steel.product.application.dto.pdf;

import com.steel.product.application.dto.instruction.InstructionResponseDto;
import com.steel.product.application.entity.*;

import java.util.Date;
import java.util.List;

public class InwardEntryPdfDto {

        private Integer inwardEntryId;
        
        private String partyName;

        private String partyCgst;

        private String coilNumber;

        private String batchNumber;

        private Date dReceivedDate;

        private Date dBillDate;

        private String vLorryNo;

        private Date dInvoiceDate;

        private String customerCoilId;

        private String customerInvoiceNo;

        private String customerBatchId;

        private String purposeType;

        private String testCertificateNumber;

        private String testCertificateFileUrl;

        private String vInvoiceNo;

        private String matDescription;

        private String materialGradeName;

        private float fWidth;

        private float fThickness;

        private float fLength;

        private float fQuantity;

        private float grossWeight;

        private Status status;

        private String vProcess;

        private Float fpresent;

        private Float valueOfGoods;

        private float billedweight;

        private String parentCoilNumber;

        private int vParentBundleNumber;

        private String remarks;

        private User createdBy;

        private User updatedBy;

        private Date createdOn;

        private Date updatedOn;

        private Boolean isDeleted;

        private List<InwardDoc> docs;

        private List<InstructionResponseDto> instruction;

        private Float totalWeight;

        private Float inStockWeight;

    public Integer getInwardEntryId() {
        return inwardEntryId;
    }

    public void setInwardEntryId(Integer inwardEntryId) {
        this.inwardEntryId = inwardEntryId;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getCoilNumber() {
        return coilNumber;
    }

    public void setCoilNumber(String coilNumber) {
        this.coilNumber = coilNumber;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public Date getdReceivedDate() {
        return dReceivedDate;
    }

    public void setdReceivedDate(Date dReceivedDate) {
        this.dReceivedDate = dReceivedDate;
    }

    public Date getdBillDate() {
        return dBillDate;
    }

    public void setdBillDate(Date dBillDate) {
        this.dBillDate = dBillDate;
    }

    public String getvLorryNo() {
        return vLorryNo;
    }

    public void setvLorryNo(String vLorryNo) {
        this.vLorryNo = vLorryNo;
    }

    public Date getdInvoiceDate() {
        return dInvoiceDate;
    }

    public void setdInvoiceDate(Date dInvoiceDate) {
        this.dInvoiceDate = dInvoiceDate;
    }

    public String getCustomerCoilId() {
        return customerCoilId;
    }

    public void setCustomerCoilId(String customerCoilId) {
        this.customerCoilId = customerCoilId;
    }

    public String getCustomerInvoiceNo() {
        return customerInvoiceNo;
    }

    public void setCustomerInvoiceNo(String customerInvoiceNo) {
        this.customerInvoiceNo = customerInvoiceNo;
    }

    public String getCustomerBatchId() {
        return customerBatchId;
    }

    public void setCustomerBatchId(String customerBatchId) {
        this.customerBatchId = customerBatchId;
    }

    public String getPurposeType() {
        return purposeType;
    }

    public void setPurposeType(String purposeType) {
        this.purposeType = purposeType;
    }

    public String getTestCertificateNumber() {
        return testCertificateNumber;
    }

    public void setTestCertificateNumber(String testCertificateNumber) {
        this.testCertificateNumber = testCertificateNumber;
    }

    public String getTestCertificateFileUrl() {
        return testCertificateFileUrl;
    }

    public void setTestCertificateFileUrl(String testCertificateFileUrl) {
        this.testCertificateFileUrl = testCertificateFileUrl;
    }

    public String getvInvoiceNo() {
        return vInvoiceNo;
    }

    public void setvInvoiceNo(String vInvoiceNo) {
        this.vInvoiceNo = vInvoiceNo;
    }

    public String getMatDescription() {
        return matDescription;
    }

    public void setMatDescription(String matDescription) {
        this.matDescription = matDescription;
    }

    public String getMaterialGradeName() {
        return materialGradeName;
    }

    public void setMaterialGradeName(String materialGradeName) {
        this.materialGradeName = materialGradeName;
    }

    public float getfWidth() {
        return fWidth;
    }

    public void setfWidth(float fWidth) {
        this.fWidth = fWidth;
    }

    public float getfThickness() {
        return fThickness;
    }

    public void setfThickness(float fThickness) {
        this.fThickness = fThickness;
    }

    public float getfLength() {
        return fLength;
    }

    public void setfLength(float fLength) {
        this.fLength = fLength;
    }

    public float getfQuantity() {
        return fQuantity;
    }

    public void setfQuantity(float fQuantity) {
        this.fQuantity = fQuantity;
    }

    public float getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(float grossWeight) {
        this.grossWeight = grossWeight;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getvProcess() {
        return vProcess;
    }

    public void setvProcess(String vProcess) {
        this.vProcess = vProcess;
    }

    public Float getFpresent() {
        return fpresent;
    }

    public void setFpresent(Float fpresent) {
        this.fpresent = fpresent;
    }

    public Float getValueOfGoods() {
        return valueOfGoods;
    }

    public void setValueOfGoods(Float valueOfGoods) {
        this.valueOfGoods = valueOfGoods;
    }

    public float getBilledweight() {
        return billedweight;
    }

    public void setBilledweight(float billedweight) {
        this.billedweight = billedweight;
    }

    public String getParentCoilNumber() {
        return parentCoilNumber;
    }

    public void setParentCoilNumber(String parentCoilNumber) {
        this.parentCoilNumber = parentCoilNumber;
    }

    public int getvParentBundleNumber() {
        return vParentBundleNumber;
    }

    public void setvParentBundleNumber(int vParentBundleNumber) {
        this.vParentBundleNumber = vParentBundleNumber;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(User updatedBy) {
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

    public List<InwardDoc> getDocs() {
        return docs;
    }

    public void setDocs(List<InwardDoc> docs) {
        this.docs = docs;
    }

    public List<InstructionResponseDto> getInstruction() {
        return instruction;
    }

    public void setInstruction(List<InstructionResponseDto> instruction) {
        this.instruction = instruction;
    }

    public Float getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(Float totalWeight) {
        this.totalWeight = totalWeight;
    }

    public Float getInStockWeight() {
        return inStockWeight;
    }

    public void setInStockWeight(Float inStockWeight) {
        this.inStockWeight = inStockWeight;
    }

    public String getPartyCgst() {
        return partyCgst;
    }

    public void setPartyCgst(String partyCgst) {
        this.partyCgst = partyCgst;
    }
}
