package gkfire.hibernate;

import gkfire.hibernate.AliasList.AliasItem;
import java.io.Serializable;
import java.util.HashMap;
import org.hibernate.sql.JoinType;

public class AliasList extends HashMap<String, AliasItem> {

    public void add(String property, String alias) {
        super.put(property, new AliasItem(alias, JoinType.INNER_JOIN));
    }

    public void add(String property, String alias, JoinType joinType) {
        super.put(property, new AliasItem(alias, joinType));
    }

    public class AliasItem implements Serializable {

        private String alias;
        private JoinType joinType;

        private AliasItem(String alias, JoinType joinType) {
            this.alias = alias;
            this.joinType = joinType;
        }

        public String getAlias() {
            return this.alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public JoinType getJoinType() {
            return this.joinType;
        }

        public void setJoinType(JoinType joinType) {
            this.joinType = joinType;
        }
    }
}
