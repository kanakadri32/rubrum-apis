package com.steel.product.application.service;

import com.lowagie.text.DocumentException;
import com.steel.product.application.dto.pdf.*;
import com.steel.product.application.entity.CompanyDetails;
import com.steel.product.application.entity.Instruction;
import com.steel.product.application.entity.InwardEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PdfService {

    private InwardEntryService inwardEntryService;
    private CompanyDetailsService companyDetailsService;
    private SpringTemplateEngine templateEngine;
    private InstructionService instructionService;

    @Autowired
    public PdfService(InwardEntryService inwardEntryService, CompanyDetailsService companyDetailsService, SpringTemplateEngine templateEngine, InstructionService instructionService) {
        this.inwardEntryService = inwardEntryService;
        this.companyDetailsService = companyDetailsService;
        this.templateEngine = templateEngine;
        this.instructionService = instructionService;
    }

    public File generatePdf(PdfDto pdfDto) throws IOException, org.dom4j.DocumentException, DocumentException {
        Context context = getContext(pdfDto);
        String html = loadAndFillTemplate(context, pdfDto.getProcessId());
        return renderPdf(html, "inward");
    }

    public File generatePdf(PartDto partDto) throws IOException, org.dom4j.DocumentException, DocumentException {
        Context context = getContext(partDto);
        InwardEntryPdfDto inwardEntryPdfDto = (InwardEntryPdfDto)context.getVariable("inward");
        String html = loadAndFillTemplate(context,Integer.parseInt(inwardEntryPdfDto.getVProcess()));
        return renderPdf(html, "inward");
    }

    public File generateDeliveryPdf(DeliveryPdfDto deliveryPdfDto) throws IOException, org.dom4j.DocumentException, DocumentException {
        Context context = getDeliveryContext(deliveryPdfDto);
        String html = loadAndFillDeliveryTemplate(context, deliveryPdfDto);
        return renderPdf(html, "delivery");
    }

    private String loadAndFillDeliveryTemplate(Context context, DeliveryPdfDto deliveryPdfDto) {
        return templateEngine.process("DC-slit", context);
    }

    private Context getDeliveryContext(DeliveryPdfDto deliveryPdfDto) {
        Context context = new Context();
        List<InwardEntry> inwardEntries = inwardEntryService.findDeliveryItemsByInstructionIds(deliveryPdfDto.getInstructionIds());
        CompanyDetails companyDetails = companyDetailsService.findById(1);
        DeliveryChallanPdfDto deliveryChallanPdfDto = new DeliveryChallanPdfDto(companyDetails,inwardEntries);
        context.setVariable("deliveryChallan",deliveryChallanPdfDto);
        return context;
    }


    private File renderPdf(String html,String filename) throws IOException, DocumentException {
        File file = File.createTempFile("aspen-steel-"+filename, ".pdf");
        OutputStream outputStream = new FileOutputStream(file);
        ITextRenderer renderer = new ITextRenderer(20f * 4f / 3f, 20);
        renderer.setDocumentFromString(html, new ClassPathResource("/").getURL().toExternalForm());
        renderer.layout();
        renderer.createPDF(outputStream);
        outputStream.close();
        file.deleteOnExit();
        return file;
    }

    private Context getContext(PdfDto pdfDto) {
        Context context = new Context();
        Integer slitAndCutProcessId = 3;
        InwardEntry inwardEntry;
        List<InstructionResponsePdfDto> instructionResponsePdfDtos;
        List<InstructionResponsePdfDto> instructionsSlit = null;
        List<InstructionResponsePdfDto> instructionsCut = null;
        InwardEntryPdfDto inwardEntryPdfDto;
        if (pdfDto.getProcessId() != null && pdfDto.getProcessId().equals(slitAndCutProcessId)) {
            List<Instruction> instructions = instructionService.findSlitAndCutInstructionByInwardId(pdfDto.getInwardId());
            inwardEntry = instructions.get(0).getInwardId();
            instructionsCut = instructions.stream()
                    .filter(ins -> ins.getProcess().getProcessId() == 3)
                    .map(ins -> Instruction.valueOfInstructionPdf(ins, null))
                    .collect(Collectors.toList());
            instructionsSlit = instructions.stream()
                    .filter(ins -> ins.getProcess().getProcessId() == 2)
                    .map(ins -> Instruction.valueOfInstructionPdf(ins, null))
                    .collect(Collectors.toList());
            instructionResponsePdfDtos = null;
            inwardEntryPdfDto = InwardEntry.valueOf(inwardEntry, instructionsCut, instructionsSlit);
        } else if (pdfDto.getProcessId() != null) {
            inwardEntry = inwardEntryService.getByEntryId(pdfDto.getInwardId());
            instructionResponsePdfDtos = inwardEntry.getInstructions()
                    .stream().filter(ins -> ins.getProcess().getProcessId() == pdfDto.getProcessId())
                    .map(i -> Instruction.valueOfInstructionPdf(i, null))
                    .collect(Collectors.toList());
            inwardEntryPdfDto = InwardEntry.valueOf(inwardEntry, instructionResponsePdfDtos);
        } else {
            inwardEntry = inwardEntryService.getByEntryId(pdfDto.getInwardId());
            instructionResponsePdfDtos = null;
            inwardEntryPdfDto = InwardEntry.valueOf(inwardEntry, instructionResponsePdfDtos);
        }
        context.setVariable("inward", inwardEntryPdfDto);
        return context;
    }

    private Context getContext(PartDto partDto) {
        Context context = new Context();
        InwardEntryPdfDto inwardEntryPdfDto = instructionService.findInwardJoinFetchInstructionsAndPartDetails(partDto.getPartDetailsId(), partDto.getGroupIds());
        context.setVariable("inward", inwardEntryPdfDto);
        return context;
    }

    private String loadAndFillTemplate(Context context, Integer processId) {
        if (processId != null && processId == 1) {
            return templateEngine.process("Cutting-slip", context);
        } else if (processId != null && processId == 2) {
            return templateEngine.process("Slitting-slip", context);
        } else if (processId != null && processId == 3) {
            return templateEngine.process("SlitAndCut-slip", context);
        } else {
            return templateEngine.process("Inward",context);
        }
    }

}
