package next.wildgoose.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import next.wildgoose.framework.dao.template.JdbcTemplate;
import next.wildgoose.framework.dao.template.PreparedStatementSetter;

@Component
public class SearchKeywordDAO {
	
	public boolean addKeywordRecord (final String keyword) {
		JdbcTemplate t = new JdbcTemplate();
		PreparedStatementSetter pss = new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement psmt) throws SQLException {
				psmt.setString(1, keyword);
				
			}
			
		};
		
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO search_keyword (keyword) VALUES (?) ");
		query.append("ON DUPLICATE KEY UPDATE count = count + 1");
		
		return (Boolean) t.execute(query.toString(), pss); 
	}
}	
