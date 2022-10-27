package jpaStudy.ex.entity.userfuc;

import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;

public class CustomDialect extends H2Dialect {
    public CustomDialect(){
        registerFunction("add_char", new StandardSQLFunction("concat"));
    }
}
