package com.newtonk.config;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.newtonk.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

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
		degradeRule();
	}

	/**
	 * 熔断降级规则
	 * 同一个资源可以有多个降级规则
	 * 超时时间： 平均每个请求响应时间 默认RT上限4900
	 * 	@see DegradeRuleManager#isValidRule(com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule)
	 *
	 * 异常比例: qps 必须大于5，比例范围[0,1]，异常总数占通过量比值超过阈值接下来时间窗口就会进入降级，
	 *
	 * 异常数:异常数字超过阈值 就会失败
	 *  具体实现
	 *  @see com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule#passCheck(com.alibaba.csp.sentinel.context.Context, com.alibaba.csp.sentinel.node.DefaultNode, int, java.lang.Object...)
	 *
	 *  异常仅针对业务异常，对于限流降级本身异常不生效
	 */
	void degradeRule(){
		List<DegradeRule> rules = new ArrayList<>();
		DegradeRule rule = new DegradeRule();
		rule.setResource(Constants.SentinelResourceKey.TIMEOUT);
		//熔断策略 (0: average RT, 1: exception ratio，2)  超时时间 和 异常比例 还有异常数
		rule.setGrade(RuleConstant.DEGRADE_GRADE_RT);
		//阈值
		rule.setCount(1000);
		//时间窗口 单位秒
		rule.setTimeWindow(60);
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
