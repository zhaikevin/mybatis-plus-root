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
package com.baomidou.mybatisplus.extension.plugins.pagination;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.ClassUtils;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.dialects.IDialect;
import org.apache.ibatis.session.RowBounds;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 分页方言工厂类
 *
 * @author hubin
 * @since 2016-01-23
 */
public class DialectFactory {

    /**
     * 方言缓存
     */
    private static final Map<String, IDialect> DIALECT_CACHE = new ConcurrentHashMap<>();

    /**
     * Physical Page Interceptor for all the queries with parameter
     * {@link RowBounds}
     *
     * @param page         翻页对象
     * @param buildSql     编译 SQL
     * @param dbType       数据类型
     * @param dialectClazz 数据库方言
     * @return 分页模型
     */
    public static DialectModel buildPaginationSql(IPage<?> page, String buildSql, DbType dbType, String dialectClazz) {
        // fix #196
        return getDialect(dbType, dialectClazz).buildPaginationSql(buildSql, page.offset(), page.getSize());
    }

    /**
     * 获取数据库方言
     *
     * @param dbType       数据库类型
     * @param dialectClazz 自定义方言实现类
     * @return ignore
     */
    private static IDialect getDialect(DbType dbType, String dialectClazz) {
        return DIALECT_CACHE.computeIfAbsent(dbType.getDb(), key -> {
            IDialect dialect = null;
            String dialectClassName = StringUtils.isEmpty(dialectClazz) ? dbType.getDialect() : dialectClazz;
            try {
                Class<?> clazz = Class.forName(dialectClassName);
                if (IDialect.class.isAssignableFrom(clazz)) {
                    dialect = (IDialect) ClassUtils.newInstance(clazz);
                }
            } catch (ClassNotFoundException e) {
                throw ExceptionUtils.mpe("Class : %s is not found", dialectClazz);
            }
            /* 未配置方言则抛出异常 */
            Assert.notNull(dialect, "The value of the dialect property in mybatis configuration.xml is not defined.");
            return dialect;
        });
    }
}
