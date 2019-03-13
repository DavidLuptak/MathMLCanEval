/*
 * Copyright © 2013 the original author or authors (webmias@fi.muni.cz)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cz.muni.fi.mir.mathmlcaneval.support;

import java.sql.Types;
import org.hibernate.dialect.PostgreSQL95Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;

public class ExtendedPostgreSQLDialect extends PostgreSQL95Dialect {
  public ExtendedPostgreSQLDialect() {
    super();


    registerFunction("vector_similarity", new StandardSQLFunction("vector_similarity"));

    registerHibernateType(Types.ARRAY, "array");

    registerColumnType(Types.ARRAY, "numeric(15,10)[]");
  }
}
