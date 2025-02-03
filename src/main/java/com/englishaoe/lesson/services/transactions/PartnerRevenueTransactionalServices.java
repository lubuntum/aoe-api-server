package com.englishaoe.lesson.services.transactions;

import com.englishaoe.lesson.database.entity.Customer;
import com.englishaoe.lesson.database.entity.partnership.Partnership;
import com.englishaoe.lesson.database.entity.partnership.PromocodeUsage;
import com.englishaoe.lesson.database.services.CustomerServices;
import com.englishaoe.lesson.database.services.PartnerService;
import com.englishaoe.lesson.database.services.PartnershipService;
import com.englishaoe.lesson.database.services.TaskTypeService;
import com.englishaoe.lesson.dto.partner.PartnerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class PartnerRevenueTransactionalServices {
    @Autowired
    PartnerService partnerService;
    @Autowired
    TaskTypeService taskTypeService;
    @Autowired
    CustomerServices customerServices;
    @Autowired
    PartnershipService partnershipService;
    @Transactional
    public void addRevenueForPartnerByCustomer(Long customerId, Integer taskType){
        Customer currentCustomer = customerServices.getCustomerById(customerId);
        if (currentCustomer.getPromocodeUsageList() == null ||
                currentCustomer.getPromocodeUsageList().isEmpty()) return;
        PromocodeUsage promocodeUsage = currentCustomer.getPromocodeUsageList().get(0);
        //TODO if promocodeUsage already has parthershipId not search for partner just use it
        //PartnerDTO partnerDTO = partnerService.getPartnerDTOById(currentCustomer.getPromocodeUsageList().get(0).getPartnerId());
        //Partnership partnership = partnershipService.getPartnershipByPartnerId(partnerDTO.getId());
        Partnership partnership = partnershipService.getPartnershipById(promocodeUsage.getPartnershipId());
        BigDecimal price = (taskType != null) ? taskTypeService.getPriceByTaskType(taskType) : taskTypeService.getTotalPrice();
        BigDecimal partnerRate = BigDecimal.valueOf(partnership.getPartnerRate());

        BigDecimal revenueFromPrice = price.multiply(partnerRate);
        partnerService.addAmountToPartnerRevenueById(promocodeUsage.getPartnerId(), revenueFromPrice);
    }

}
