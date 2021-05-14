package ch.heigvd.statique.config;

public class Article {

    private String siteTitle;
    private String pageTitle;
    private String content;

    public Article(String siteTitle, String pageTitle, String content) {
        this.siteTitle = siteTitle;
        this.pageTitle = pageTitle;
        this.content = content;
    }

    public String getSiteTitle() {
        return siteTitle;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public String getContent() {
        return content;
    }
}
