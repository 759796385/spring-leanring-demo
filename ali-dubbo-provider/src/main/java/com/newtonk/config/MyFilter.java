package com.newtonk.config;

import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcException;

/**
 * 类名称：
 * 类描述：
 * @author：qiang.tang
 * 创建日期：2019/10/16
 */
public class MyFilter implements Filter {
	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		before(invoker,invocation);
		Result result = invoker.invoke(invocation);
		after(invoker,invocation);
		return result;
	}

	private void after(Invoker<?> invoker, Invocation invocation) {
		System.out.println("之后");
	}

	private void before(Invoker<?> invoker, Invocation invocation) {
		System.out.println("before");
	}
}
