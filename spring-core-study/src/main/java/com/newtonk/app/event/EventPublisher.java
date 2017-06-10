package com.newtonk.app.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

/**
 * Created by newtonk on 2017/4/4.
 */
public class EventPublisher implements ApplicationEventPublisherAware{
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public  void sendMessge(){
        EmailEvent emailEvent = new EmailEvent("ctx","地址","文本");
        applicationEventPublisher.publishEvent(emailEvent);
    }


}
