package com.newtonk.aspect.sample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/7/12 0012
 */
@Slf4j
@Component
public class SampleImpl implements Sample<Integer> {
    @Override
    public void sampleGenericMethod(Integer param) {
        log.info(""+param);
    }

    @Override
    public void sampleGenericCollectionMethod(Collection<Integer> param) {
        log.info(param.toString());
    }
}
