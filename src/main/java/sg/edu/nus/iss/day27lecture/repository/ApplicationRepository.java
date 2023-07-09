package sg.edu.nus.iss.day27lecture.repository;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.day27lecture.model.Comment;
import sg.edu.nus.iss.day27lecture.model.Game;

@Repository
public class ApplicationRepository {

    @Autowired
    private MongoTemplate template;

    public static final String C_GAME = "game";
    public static final String F_GAME_NAME = "name";
    public static final String F_GAME_ID = "gid";
    public static final String C_COMMENT = "comment";

    public Game getGameByID(Integer id) {
        Query query = Query.query(Criteria.where(F_GAME_ID).is(id));
        return template.findOne(query, Game.class, C_GAME);
    }

    public List<Comment> getCommentsByGameID(Integer gameID) {
        Query query = Query.query(Criteria.where(F_GAME_ID).is(gameID)).limit(5);
        return template.find(query, Comment.class, C_COMMENT);
    }

    public Boolean checkIfBoardGameExistsByID(Integer id) {
        Query query = Query.query(Criteria.where(F_GAME_ID).is(id));
        Game game = template.findOne(query, Game.class, C_GAME);
        return game != null;
    }

    public List<Document> retrieveSearchResultsByName(String name) {
        TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingPhrase(name);
        TextQuery query = TextQuery.queryText(criteria).sortByScore();
        query.setScoreFieldName("score");
        return template.find(query, Document.class, "game");
    }

    public Boolean insertComment(Comment comment) {
        Document docToInsert = new Document();
        docToInsert.put("c_id", comment.getId());
        docToInsert.put("user", comment.getUser());
        docToInsert.put("rating", comment.getRating());
        docToInsert.put("c_text", comment.getC_text());
        docToInsert.put("gid", comment.getgID());

        docToInsert = template.insert(docToInsert, C_COMMENT);
        return true;
    }

}
