package com.newtonk.app.listener;

import com.newtonk.app.event.EmailEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;


public class EmailNotifier implements ApplicationListener {

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof EmailEvent) {
			EmailEvent email = (EmailEvent) event;
			System.out.println("收件地址：" + email.getAddress());
			System.out.println("邮件内容" + email.getText());
		} else {
			System.out.println("其他事件" + event);
		}
	}

}
