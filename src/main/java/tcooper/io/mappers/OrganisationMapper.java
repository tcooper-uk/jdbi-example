package tcooper.io.mappers;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import tcooper.io.model.Organisation;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Jdbi mapper for organisation
 */
public class OrganisationMapper implements RowMapper<Organisation> {

    /**
     * Map the jdbi result set to a new Organisation POJO
     * @param rs
     * @param ctx
     * @return
     * @throws SQLException
     */
    @Override
    public Organisation map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new Organisation(rs.getLong(1), rs.getString(2));
    }
}
