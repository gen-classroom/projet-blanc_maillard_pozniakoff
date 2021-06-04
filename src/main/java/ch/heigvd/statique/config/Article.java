/**
 * Class to represent articles
 * @authors Jean-Luc Blanc, Lev Pozniakoff, Mathias Maillard
 */

package ch.heigvd.statique.config;

public class Article {

    /**
     * Title of the site
     */
    private String siteTitle;
    /**
     * Title of the page
     */
    private String pageTitle;
    /**
     * Content of the article
     */
    private String content;

    /**
     * Constructor
     * @param siteTitle title of the site
     * @param pageTitle title of the page
     * @param content content of the article
     */
    public Article(String siteTitle, String pageTitle, String content) {
        this.siteTitle = siteTitle;
        this.pageTitle = pageTitle;
        this.content = content;
    }

    /**
     * Getter
     * @return title of the site
     */
    public String getSiteTitle() {
        return siteTitle;
    }

    /**
     * Getter
     * @return title of the page
     */
    public String getPageTitle() {
        return pageTitle;
    }

    /**
     * Getter
     * @return content of the page
     */
    public String getContent() {
        return content;
    }
}
