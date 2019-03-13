package cz.muni.fi.mir.mathmlcaneval.support;

import java.io.Serializable;
import java.sql.Array;
import java.sql.Connection;
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
    Connection c = st.getConnection();
    double[] orig = (double[]) value;

    Object[] copy = new Object[orig.length];

    for(int i =0 ; i < orig.length; i++) {
      copy[i] = Double.valueOf(orig[i]);
    }
    Array array = c.createArrayOf("numeric", copy);

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
