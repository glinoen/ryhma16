package Lukuvinkisto;

import Lukuvinkisto.dao.TiedostoDAO;
import Lukuvinkisto.dao.TietokantaDAO;
import Lukuvinkisto.media.Article;
import Lukuvinkisto.media.Media;
import java.io.File;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ArticleDaoTest {
    TietokantaDAO db;
        Article article1 = new Article("Wikipedia: The Hitchhiker's Guide to the Galaxy", "https://en.wikipedia.org/wiki/The_Hitchhiker%27s_Guide_to_the_Galaxy");
        Article article2 = new Article("Linnunradan käsikirja liftareille", "https://fi.wikipedia.org/wiki/Linnunradan_k%C3%A4sikirja_liftareille");
    
    @Before
    public void setUp() {
        TiedostoDAO dbFile = new TiedostoDAO();
        dbFile.createFile("testi");
        db = new TietokantaDAO("testi");

    }
    
    @After
    public void tearDown() {
        File file = new File("testi.db"); 
        file.delete(); 
    }

    @Test
    public void testArticleAddOk() {
        boolean r = db.addVideo(article1.getTitle(), article1.getLink());
        assertTrue(r);
    } 

    @Test
    public void testArticleAddOkTitle() {
        db.addArticle(article1.getTitle(), article1.getLink());
        List<Media> list = db.listArticles(null);
        assertEquals(list.get(0).getTitle(),article1.getTitle());
    } 

    @Test
    public void testArticleAddOkLink() {
        db.addArticle(article1.getTitle(), article1.getLink());
        List<Media> list = db.listArticles(null);
        assertEquals(list.get(0).getLink(),article1.getLink());
    } 

    @Test
    public void testExistingArticlesAddReturnsFalse() {
        db.addArticle(article1.getTitle(), article1.getLink());
        boolean r = db.addArticle(article1.getTitle(), article1.getLink());
        assertFalse(r);
    } 

    @Test
    public void testRemovingNonExistingArticleReturnsFalse() {
        db.addArticle(article1.getTitle(), article1.getLink());
        boolean r = db.removeArticle(article2);
        assertFalse(r);
    } 

    @Test
    public void testTwoArticlesAdded() {
        db.addArticle(article1.getTitle(), article1.getLink());
        db.addArticle(article2.getTitle(), article2.getLink());
        List<Media> list = db.listArticles(null);
        assertEquals(list.size(),2);
    } 

    @Test
    public void testTwoArticlesAddedOneRemoved() {
        db.addArticle(article1.getTitle(), article1.getLink());
        db.addArticle(article2.getTitle(), article2.getLink());
        db.removeArticle(article1);
        
        List<Media> list = db.listArticles(null);
        assertEquals(list.size(),1);
    } 

    @Test
    public void testArticlesSearchOk() {
        db.addArticle(article1.getTitle(), article1.getLink());
        db.addArticle(article2.getTitle(), article2.getLink());
        List<Media> list = db.listArticles("RADAN");
        assertEquals(list.get(0).getTitle(),"Linnunradan käsikirja liftareille");
    } 
    
}