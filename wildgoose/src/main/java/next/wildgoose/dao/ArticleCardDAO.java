package next.wildgoose.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import next.wildgoose.dto.ArticleCard;
import next.wildgoose.pool.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArticleCardDAO {
	private static final Logger LOGGER = LoggerFactory.getLogger(ArticleCardDAO.class.getName());
	
	public List<ArticleCard> findArticlesById(int reporterId) {
		Connection conn = DataSource.getInstance().getConnection();
		PreparedStatement psmt = null;
		ResultSet rs = null;
		List<ArticleCard> articleCards = new ArrayList<ArticleCard>();
		ArticleCard articleCard = null;
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT article.URL as url, article.title as title, ");
		query.append("article.section_id as section, article.content as content, article.datetime as datetime ");
		query.append("FROM article_author JOIN article ON article.URL = article_author.article_URL ");
		query.append("WHERE article_author.author_id = ? ORDER BY datetime DESC limit 5;");
		
		try {
			psmt = conn.prepareStatement(query.toString());
			psmt.setInt(1, reporterId);
			rs = psmt.executeQuery();
				
			while (rs.next()) {
				articleCard = new ArticleCard();
				articleCard.setUrl(rs.getString("url"));
				articleCard.setTitle(rs.getString("title"));
				articleCard.setSectionId(rs.getInt("section"));
				articleCard.setContent(rs.getString("content"));
				articleCard.setDatetime(rs.getTimestamp("datetime").toString());
				articleCards.add(articleCard);
			}
		} catch (SQLException sqle) {
			LOGGER.debug(sqle.getMessage(),sqle);
		} finally {
			SqlUtil.closePrepStatement(psmt);
			SqlUtil.closeResultSet(rs);
			SqlUtil.closeConnection(conn);
		}
		
		return articleCards;
	}
}
