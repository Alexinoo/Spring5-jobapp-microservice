package com.embarkx.reviewms.messaging;

import com.embarkx.reviewms.dto.ReviewMessage;
import com.embarkx.reviewms.model.Review;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.stereotype.Service;

@Service
public class ReviewMessageProducer {

    private final RabbitTemplate rabbitTemplate;

    public ReviewMessageProducer(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(Review newReview){
        ReviewMessage reviewMessage = new ReviewMessage();
        reviewMessage.setId(newReview.getId());
        reviewMessage.setTitle(newReview.getTitle());
        reviewMessage.setDescription(newReview.getDescription());
        reviewMessage.setRating(newReview.getRating());
        reviewMessage.setCompanyId(newReview.getCompanyId());
        rabbitTemplate.convertAndSend("companyRatingQueue",reviewMessage);
    }
}
