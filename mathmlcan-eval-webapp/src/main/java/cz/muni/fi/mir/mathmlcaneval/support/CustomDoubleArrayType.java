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

import java.io.Serializable;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

public class CustomDoubleArrayType  implements UserType {

  @Override
  public int[] sqlTypes() {
    return new int[]{Types.ARRAY};
  }

  @Override
  public Class returnedClass() {
    return double[].class;
  }

  @Override
  public boolean equals(Object x, Object y) throws HibernateException {
    if(x == y) {
      return true;
    }
    if(x == null || y == null) {
      return false;
    }

    return Arrays.equals((double[]) x, (double[]) y);
  }

  @Override
  public int hashCode(Object x) throws HibernateException {
    return Arrays.hashCode((double[]) x);
  }

  @Override
  public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session,
    Object owner) throws HibernateException, SQLException {
    Array array = rs.getArray(names[0]);
    return array.getArray();
  }

  @Override
  public void nullSafeSet(PreparedStatement st, Object value, int index,
    SharedSessionContractImplementor session) throws HibernateException, SQLException {
    final var c = st.getConnection();
    final var orig = (double[]) value;

    final var copy = new Object[orig.length];

    for(int i =0 ; i < orig.length; i++) {
      copy[i] = Double.valueOf(orig[i]);
    }
    final var array = c.createArrayOf("numeric", copy);

    st.setArray(index, array);
  }

  @Override
  public Object deepCopy(Object value) throws HibernateException {
    double[] original = (double[]) value;
    double[] copy = new double[original.length];

    System.arraycopy(original, 0, copy, 0, original.length);

    return copy;
  }

  @Override
  public boolean isMutable() {
    return false;
  }

  @Override
  public Serializable disassemble(Object value) throws HibernateException {
    return (Serializable) value;
  }

  @Override
  public Object assemble(Serializable cached, Object owner) throws HibernateException {
    return cached;
  }

  @Override
  public Object replace(Object original, Object target, Object owner) throws HibernateException {
    return original;
  }
}
