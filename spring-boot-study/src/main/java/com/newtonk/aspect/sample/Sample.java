package com.newtonk.aspect.sample;

import java.util.Collection;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/7/12 0012
 */
public interface Sample<T> {
    void sampleGenericMethod(T param);
    void sampleGenericCollectionMethod(Collection<T> param);
}
