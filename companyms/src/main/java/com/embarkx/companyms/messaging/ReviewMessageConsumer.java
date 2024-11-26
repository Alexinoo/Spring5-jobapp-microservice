package com.embarkx.companyms.messaging;

import com.embarkx.companyms.dto.ReviewMessage;
import com.embarkx.companyms.service.CompanyService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class ReviewMessageConsumer {

    private final CompanyService companyService;

    public ReviewMessageConsumer(CompanyService companyService) {
        this.companyService = companyService;
    }

    @RabbitListener(queues = "companyRatingQueue")
    public void consumeMessage(ReviewMessage reviewMessage){
        companyService.updateCompanyRating(reviewMessage);
    }
}
