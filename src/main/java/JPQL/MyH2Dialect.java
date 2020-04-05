package JPQL;

import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;

public class MyH2Dialect extends H2Dialect {
    public MyH2Dialect() {
        this.registerFunction("group_concat",new StandardSQLFunction("group_concat"));
    }
}
