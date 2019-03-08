/**
 * 
 */
package com.ido85.frame.hibernate.model.naming;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

import com.ido85.frame.common.utils.StringUtils;

/**
 * 驼峰式命名
 * @author rongxj
 *
 */
public class CamelPhysicalNamingStrategyImpl implements PhysicalNamingStrategy {

	@Override
	public Identifier toPhysicalCatalogName(Identifier name,
			JdbcEnvironment jdbcEnvironment) {
		return convert(name);
	}

	@Override
	public Identifier toPhysicalSchemaName(Identifier name,
			JdbcEnvironment jdbcEnvironment) {
		return convert(name);
	}

	@Override
	public Identifier toPhysicalTableName(Identifier name,
			JdbcEnvironment jdbcEnvironment) {
		return convert(name);
	}

	@Override
	public Identifier toPhysicalSequenceName(Identifier name,
			JdbcEnvironment jdbcEnvironment) {
		return convert(name);
	}

	@Override
	public Identifier toPhysicalColumnName(Identifier name,
			JdbcEnvironment jdbcEnvironment) {
		return convert(name);
	}

	private Identifier convert(Identifier name) {
        if (name == null || StringUtils.isEmpty(name.getText())) {
            return name;
        }

        String regex = "([a-z])([A-Z])";
        String replacement = "$1_$2";
        String newName = name.getText().replaceAll(regex, replacement).toLowerCase();
        return Identifier.toIdentifier(newName);
    }
}
