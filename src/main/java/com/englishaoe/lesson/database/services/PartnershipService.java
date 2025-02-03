package com.englishaoe.lesson.database.services;

import com.englishaoe.lesson.database.entity.Customer;
import com.englishaoe.lesson.database.entity.partnership.Partner;
import com.englishaoe.lesson.database.entity.partnership.Partnership;
import com.englishaoe.lesson.database.entity.partnership.PromocodeUsage;
import com.englishaoe.lesson.database.entity.role.RoleEnum;
import com.englishaoe.lesson.database.repository.PartnerRepository;
import com.englishaoe.lesson.database.repository.PartnershipRepository;
import com.englishaoe.lesson.exceptions.RegularException;
import com.englishaoe.lesson.utility.DateUtil;
import com.englishaoe.lesson.utility.PromocodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.Objects;

@Service
public class PartnershipService {
    @Autowired
    private PartnershipRepository partnershipRepository;
    @Autowired
    private CustomerServices customerServices;
    @Autowired
    private PartnerService partnerService;
    @Transactional
    public void createPartnershipForPartner(Partner partner, Double discount, Double partnerRate){
        Partnership partnership = new Partnership();
        partnership.setPartnerId(partner.getId());
        partnership.setDiscount(discount);
        partnership.setPartnerRate(partnerRate);
        partnership.setContractDate(DateUtil.getCurrentDate());
        partnership.setPromocode(PromocodeUtil.generatePromocode(10));
        partnershipRepository.save(partnership);
        customerServices.addRoleToCustomer(partner.getCustomerId(), RoleEnum.PARTNER.getRole());
        partnerService.approvePartner(partner.getId());
    }
    public Boolean applyPromocode(String promocode, Long customerId){
        Partnership partnership = partnershipRepository.findPartnershipByPromocode(promocode);
        if (partnership == null || partnership.getPartner() == null) return false;
        Customer customer = customerServices.getCustomerById(customerId);
        if (customer.getPromocodeUsageList() != null && customer.getPromocodeUsageList().size() > 0)
            customer.getPromocodeUsageList().clear();
        if (customer.getPromocodeUsageList() == null) customer.setPromocodeUsageList(new LinkedList<>());
        PromocodeUsage promocodeUsage = new PromocodeUsage();
        promocodeUsage.setPartnerId(partnership.getPartner().getId());
        promocodeUsage.setCustomerId(customer.getId());
        promocodeUsage.setPartnershipId(partnership.getId());
        //add partnershipId to promocodeUsage (and in db scheme)
        customer.getPromocodeUsageList().add(promocodeUsage);
        customerServices.saveCustomer(customer);
        return true;
    }
    public Partnership getPartnershipById(Long id) {
        return partnershipRepository.findById(id).orElseThrow(NullPointerException::new);
    }
}
