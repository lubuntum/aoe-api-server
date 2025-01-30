package com.englishaoe.lesson.controllers;

import com.englishaoe.lesson.database.services.PartnerService;
import com.englishaoe.lesson.database.services.PartnershipService;
import com.englishaoe.lesson.dto.partner.PartnerProposalDTO;
import com.englishaoe.lesson.dto.partner.PartnerProposalDTOMapper;
import com.englishaoe.lesson.exceptions.RegularException;
import com.englishaoe.lesson.services.AuthorizationService;
import com.englishaoe.lesson.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/partner")
public class PartnerController {
    @Autowired
    PartnerService partnerService;
    @Autowired
    PartnershipService partnershipService;
    @Autowired
    AuthorizationService authorizationService;
    @Autowired
    private JwtUtil jwtUtil;
    //TODO
    // - write API on react side and make a call
    // - create a method for change partner approval (if false then delete partner data,
    // else create partnership with generated promocode and add role "partner" to his account save all)
    // - create a method for partner account (all his data included how many entered his promocode)
    @GetMapping("/by-approving")
    public ResponseEntity<List<PartnerProposalDTO>> getAllPartnersByApproving(@RequestHeader("Authorization")String token,
                                                                              @RequestParam("isApproved")Boolean isApproved){
        if (!authorizationService.isCustomerAdmin(token))
            throw new RegularException("Access denied", HttpStatus.FORBIDDEN.value());
        return ResponseEntity.ok(partnerService.getPartnersByApproving(isApproved));
    }
    @GetMapping("/get-all")
    public ResponseEntity<List<PartnerProposalDTO>> getAllPartners(@RequestHeader("Authorization")String token){
        if (!authorizationService.isCustomerAdmin(token))
            throw new RegularException("Access denied", HttpStatus.FORBIDDEN.value());
        return ResponseEntity.ok(partnerService.getAllPartnersDTO());
    }
    @PostMapping("/partnership-procedure")
    public ResponseEntity<String> approvePartner(@RequestHeader("Authorization")String token,
                                                 @RequestBody PartnerProposalDTO partner){
        if (!authorizationService.isCustomerAdmin(token))
            throw new RegularException("Access denied", HttpStatus.FORBIDDEN.value());
        if(!partner.getIsApproved()) {
            partnerService.deletePartnerById(partner.getId());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        partnershipService.createPartnershipForPartner(PartnerProposalDTOMapper.parse(partner),0.0, 0.1);
        return ResponseEntity.ok(String.format("partner %s approved",partner.getPartnerName()));
    }
    @PostMapping("/pay")
    public ResponseEntity<Boolean> payToPartnerAmount(@RequestHeader("Authorization")String token,
                                                    @RequestParam("partnerId") Long partnerId,
                                                    @RequestParam("amount") BigDecimal amount){
        if (!authorizationService.isCustomerAdmin(token))
            throw new RegularException("Access denied", HttpStatus.FORBIDDEN.value());
        return ResponseEntity.ok(partnerService.subAmountFromPartnerRevenueById(partnerId, amount));
    }
    @PostMapping("/apply-promocode")
    public ResponseEntity<Boolean> applyPromocodeForCustomer(@RequestHeader("Authorization")String token,
                                                             @RequestParam("promocode") String promocode){
        return ResponseEntity.ok(partnershipService.applyPromocode(promocode, Long.valueOf(jwtUtil.extractSubject(token))));
    }
}
