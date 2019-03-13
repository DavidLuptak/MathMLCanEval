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
