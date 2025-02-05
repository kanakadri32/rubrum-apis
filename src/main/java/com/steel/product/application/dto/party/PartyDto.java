package com.steel.product.application.dto.party;

import com.steel.product.application.dto.address.AddressDto;
import com.steel.product.application.dto.packetClassification.PacketClassificationRequest;
import com.steel.product.application.dto.packetClassification.PacketClassificationResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PartyDto {

    private String partyName;
    private String partyNickname;
    private String contactName;
    private String contactNumber;
    private String gstNumber;
    private String panNumber;
    private String tanNumber;
    private String email1;
    private String email2;
    private String phone1;
    private String phone2;
    private AddressDto address1;
    private AddressDto address2;
    private List<PacketClassificationRequest> tags;

}
