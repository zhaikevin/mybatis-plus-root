/*
 * Copyright (c) 2011-2020, baomidou (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.baomidou.mybatisplus.core.incrementer;

/**
 * Id 生成器接口
 * 可以自定义id生成方式，如 baidu 的UidGenerator,美团的leaf 等
 *
 * @author sd-wangtaicheng@sdcncsi.com.cn
 * @date 2019/10/15
 */
public interface IdGenerator {
    /**
     * 生成Id
     *
     * @return id
     */
    long nextId();
}
