package com.newtonk.app.service.impl;

import com.newtonk.app.event.EmailEvent;
import com.newtonk.app.service.Axe;
import com.newtonk.app.service.Person;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

public class America implements Person,ApplicationEventPublisherAware {
	private Axe axe;
	private ApplicationEventPublisher applicationEventPublisher;
//	@Autowired
	public America(@Qualifier("steelaxe") Axe axe) {
		this.axe = axe;
		System.out.println(axe);
	}

	@Override
	public void useAxe() {
		System.out.println(axe.chop());
//		sendMessge();
	}
	public  void sendMessge(){
		EmailEvent emailEvent = new EmailEvent("ctx","地址","文本");
		applicationEventPublisher.publishEvent(emailEvent);
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

}
