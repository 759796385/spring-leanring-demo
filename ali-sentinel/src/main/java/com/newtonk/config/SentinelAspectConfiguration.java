package com.newtonk.config;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.newtonk.Constants;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 类名称：注解配置
 * 类描述：
 * @author：qiang.tang
 * 创建日期：2019/7/25
 */
@Configuration
public class SentinelAspectConfiguration {
	@PostConstruct
	public void configRule(){
		initFlowRule();

	}

	/**
	 * 熔断降级规则
	 * 同一个资源可以有多个降级规则
	 */
	void DegradeRule(){


	}

	/**
	 * 限流规则
	 * 同一个资源可以有多个限流规则
	 */
	void initFlowRule(){
		List<FlowRule> rules = new ArrayList<>();
		FlowRule rule = new FlowRule();
		//resource 限流对象
		rule.setResource(Constants.SentinelResourceKey.HELLOWORLD);
		//限流阈值类型 qps 或 线程数
		rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
		// count 限流阈值
		rule.setCount(10);
		// controlBehavior 流控效果（直接拒绝 / 排队等待 / 慢启动模式） 默认直接拒绝
		rules.add(rule);

		FlowRuleManager.loadRules(rules);
	}

	@Bean
	public SentinelResourceAspect sentinelResourceAspect() {
		return new SentinelResourceAspect();
	}
}
