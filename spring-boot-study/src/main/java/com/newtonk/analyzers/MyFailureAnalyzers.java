package com.newtonk.analyzers;

import com.newtonk.exception.MyException;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

/**
 * Created by newtonk on 2017/5/26.
 */
public class MyFailureAnalyzers extends AbstractFailureAnalyzer<Exception> {

    //故障分析器,当容器启动失败时会调用这个方法。 需要在spring.factories中注册
    @Override
    protected FailureAnalysis analyze(Throwable throwable, Exception e) {
        System.out.println("sa");
        return new FailureAnalysis(e.getMessage(),"how to do",new MyException(e.getMessage()));
    }
}
