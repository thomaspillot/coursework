package screen;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.MouseInputListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import stores.Cast;
import stores.Company;
import stores.Credits;
import stores.Crew;
import stores.Genre;
import stores.Movies;
import utils.Constants;
import utils.DisplayImage;
import utils.IsoSearch;

public class FilmScreen {
    public static void createPanel(JPanel panel, int filmID, Movies movies, Credits credits) {
        System.out.println("Film screen --> ID: " + filmID);
        panel.removeAll();

        //Create film title
        JTextPane title = new JTextPane();
        Document titleDoc = title.getStyledDocument();

        try{
            SimpleAttributeSet attributeSet = new SimpleAttributeSet();
            StyleConstants.setFontSize(attributeSet, 16);
            StyleConstants.setBold(attributeSet, true);
            titleDoc.insertString(titleDoc.getLength(), movies.getTitle(filmID) + " ", attributeSet);
            StyleConstants.setItalic(attributeSet, true);
            if (movies.getRelease(filmID) == null) {
                titleDoc.insertString(titleDoc.getLength(), "(N/A)", attributeSet);
            } else {
                titleDoc.insertString(titleDoc.getLength(), "(" + movies.getRelease(filmID).get(Calendar.YEAR) + ")", attributeSet);
            }
            StyleConstants.setItalic(attributeSet, false);
            titleDoc.insertString(titleDoc.getLength(), " - " + movies.getStatus(filmID), attributeSet);
            StyleConstants.setBold(attributeSet, false);
            titleDoc.insertString(titleDoc.getLength(), "\nOriginally called: \"" + movies.getOriginalTitle(filmID) + "\"", attributeSet);
        } catch(BadLocationException e) {

        } catch(NullPointerException e) {
            System.err.println("===== Either Title, Status and/or Originally called is NULL, please check! =====");
            e.printStackTrace();
        }

        title.setBounds(5,5,(int)(panel.getWidth()*0.8)-10, 40);
        title.setForeground(Constants.fontColor);
        title.setBackground(Constants.highlight);
        title.setEditable(false);
        System.out.println("\tTitle built");

        //Find and display film poster
        String filmPosterURL = movies.getPoster(filmID);
        if (filmPosterURL == null || filmPosterURL == "") {
            filmPosterURL = "src/main/resources/img/Movie-Unknown.png";
        } else {
            filmPosterURL = "https://image.tmdb.org/t/p/original" + filmPosterURL;
        }
        DisplayImage filmPoster = null;
        System.out.print("\tAttempted Poster URL: " + filmPosterURL);
        try {
            if (filmPosterURL == "src/main/resources/img/Movie-Unknown.png") {
                filmPoster = new DisplayImage(filmPosterURL, 10, false);
            } else {
                filmPoster = new DisplayImage(filmPosterURL, 0 , true);
            }
        } catch (IOException e) {
            System.err.print("\t...No file found");
            if (filmPosterURL != "src/main/resources/img/Movie-Unknown.png") {
                try {
                    System.err.print(", so putting in the default image");
                    filmPosterURL = "src/main/resources/img/Movie-Unknown.png";
                    filmPoster = new DisplayImage(filmPosterURL, 10, false);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        if (filmPoster != null) {
            filmPoster.setBounds((int) (panel.getWidth() * 0.8), 5, (int) (panel.getWidth() * 0.2)-5, (int) (panel.getHeight() * 0.5)-4);
            if (filmPosterURL == "src/main/resources/img/Movie-Unknown.png") {
                filmPoster.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            } else {
                filmPoster.addMouseListener(new MouseInputListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        try {
                            Desktop.getDesktop().browse(new URI("https://image.tmdb.org/t/p/original" + movies.getPoster(filmID)));
                        } catch (IOException | URISyntaxException e1) {
                            e1.printStackTrace();
                        }                        
                    }
                    @Override
                    public void mousePressed(MouseEvent e) {}
                    @Override
                    public void mouseReleased(MouseEvent e) {}
                    @Override
                    public void mouseEntered(MouseEvent e) {}
                    @Override
                    public void mouseExited(MouseEvent e) {}
                    @Override
                    public void mouseDragged(MouseEvent e) {}
                    @Override
                    public void mouseMoved(MouseEvent e) {}
                });
            }
            panel.add(filmPoster);
        }
        System.out.println("\tPoster built");

        //Add tagline
        TitledBorder taglineBorder;
        taglineBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Constants.fontColor), "Tagline");
        taglineBorder.setTitleJustification(TitledBorder.CENTER);
        taglineBorder.setTitleColor(Constants.fontColor);
        String taglineText = movies.getTagline(filmID);
        if (taglineText == null) {
            taglineText = "Tagline is NULL";
        } else if (taglineText == "") {
            taglineText = "No Tagline";
        }
        JTextArea tagline = new JTextArea(taglineText);
        tagline.setForeground(Constants.fontColor);
        tagline.setBackground(Constants.highlight);
        tagline.setLineWrap(true);
        tagline.setWrapStyleWord(true);
        tagline.setBounds((int) (panel.getWidth() * 0.8), (int) (panel.getHeight() * 0.5)+5, (int) (panel.getWidth() * 0.2)-5, (int) (panel.getHeight() * 0.2) - 5);
        tagline.setMinimumSize(new Dimension((int) (panel.getWidth() * 0.2)-5, (int) (panel.getHeight() * 0.2) - 5));
        tagline.setBorder(taglineBorder);
        tagline.setEditable(false);
        System.out.println("\tTagline built");

        //Add overview
        TitledBorder overviewBorder;
        overviewBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Constants.fontColor), "Overview");
        overviewBorder.setTitleJustification(TitledBorder.CENTER);
        overviewBorder.setTitleColor(Constants.fontColor);
        String overviewText = movies.getOverview(filmID);
        if (overviewText == null) {
            overviewText = "Overview is NULL";
        } else if (overviewText == "") {
            overviewText = "No Overview";
        }
        JTextArea overview = new JTextArea(overviewText);
        overview.setForeground(Constants.fontColor);
        overview.setBackground(Constants.highlight);
        overview.setLineWrap(true);
        overview.setWrapStyleWord(true);
        overview.setBounds(5, 55, (int) (panel.getWidth() * 0.8) - 10, (int) (panel.getHeight() * 0.2));
        overview.setBorder(overviewBorder);
        overview.setEditable(false);
        System.out.println("\tOverview built");

        //Add film stats
        TitledBorder statsBorder;
        statsBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Constants.fontColor),
                "Key Information");
        statsBorder.setTitleJustification(TitledBorder.CENTER);
        statsBorder.setTitleColor(Constants.fontColor);
        String statsText = "Film ID: " + filmID + "\n";
        String tmp = movies.getHomepage(filmID);
        if (tmp == null) {
            tmp = "Homepage is NULL\n";
        } else if (tmp == "") {
            tmp = "No Homepage given\n";
        } else {
            tmp = "Homepage: " + tmp + "\n";
        }
        statsText += tmp;
        double tmpDouble = movies.getRuntime(filmID);
        if (tmpDouble < 0) {
            tmp = "No Runtime given\n";
        } else {
            tmp = "Runtime: " + tmpDouble + " mins\n";
        }
        statsText += tmp;
        if (movies.getRelease(filmID) == null) {
            tmp = "Unknown Release Date";
        } else {
            tmp = "Full Release Date: " + movies.getRelease(filmID).get(Calendar.DAY_OF_MONTH) + " " + movies
                    .getRelease(filmID).getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) + " " + movies.getRelease(filmID).get(Calendar.YEAR) + "\n";
        }
        statsText += tmp;
        long tmpLong = movies.getBudget(filmID);
        if (tmpLong < 0) {
            tmp = "No Budget given\t\t";
        } else if (tmpLong < 1000L){
            tmp = "Budget: $" + tmpLong + "\t\t";
        } else if (tmpLong < 1000000L) {
            tmp = "Budget: $" + tmpLong/1000L + "k\t\t";
        } else if (tmpLong < 1000000000L) {
            tmp = "Budget: $" + tmpLong/1000000L + "M\t\t";
        } else if (tmpLong < 1000000000000L) {
            tmp = "Budget: $" + tmpLong/1000000000L + "B\t\t";
        } else {
            tmp = "Budget: $" + tmpLong + "\t\t";
        }
        statsText += tmp;
        tmpLong = movies.getRevenue(filmID);
        if (tmpLong < 0) {
            tmp = "No Revenue given\n";
        } else if (tmpLong < 1000L) {
            tmp = "Revenue: $" + tmpLong + "\n";
        } else if (tmpLong < 1000000L) {
            tmp = "Revenue: $" + tmpLong / 1000L + "k\n";
        } else if (tmpLong < 1000000000L) {
            tmp = "Revenue: $" + tmpLong / 1000000L + "M\n";
        } else if (tmpLong < 1000000000000L) {
            tmp = "Revenue: $" + tmpLong / 1000000000L + "B\n";
        } else {
            tmp = "Revenue: $" + tmpLong + "\n";
        }
        statsText += tmp;
        statsText += "Straight to Video: " + movies.getVideo(filmID)+"\n";
        statsText += "Adult film: " + movies.getAdult(filmID)+"\n\n";
        Company[] tmpCompanies = movies.getProductionCompanies(filmID);
        if (tmpCompanies == null) {
            tmp = "Unknown Production Companies";
        } else if (tmpCompanies.length == 0) {
            tmp = "No Production Companies known";
        } else {
            tmp = "Production Companies:";
            for (int i = 0; i < tmpCompanies.length; i++) {
                tmp += "\n\t* " + tmpCompanies[i].getName();
            }
        }
        statsText += tmp + "\n";
        String[] tmpCountries = movies.getProductionCountries(filmID);
        if (tmpCountries == null) {
            tmp = "Unknown Production Countries";
        } else if (tmpCountries.length == 0) {
            tmp = "No Production Countries known";
        } else {
            tmp = "Production Countries:";
            for (int i = 0; i < tmpCountries.length; i++) {
                tmp += "\n\t* ";
                String[] isoCountries = IsoSearch.iso3166SearchByKey(tmpCountries[i]);
                if (isoCountries.length < 1) {
                    tmp += "UNKNOWN ISO 3166 COUNTRY ";
                } else {
                    tmp += isoCountries[0];
                }
                for (int j = 1; j < isoCountries.length; j++) {
                    tmp += ", " + isoCountries[j];
                }
                tmp += " - ("+tmpCountries[i]+")";
            }
        }
        statsText += tmp;
        JPanel statsPanel = new JPanel();
        statsPanel.setBackground(Constants.highlight);
        JTextPane stats = new JTextPane();
        JScrollPane statsScroll = new JScrollPane(stats);
        Document statsDoc = stats.getStyledDocument();
        try {
            statsDoc.insertString(statsDoc.getLength(), statsText, null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        stats.setForeground(Constants.fontColor);
        stats.setBackground(Constants.highlight);
        statsPanel.setBounds(5, (int) (panel.getHeight() * 0.2) + 60, (int) (panel.getWidth() * 0.4) - 10, (int) (panel.getHeight() * 0.4) + 5);
        statsScroll.setMinimumSize(new Dimension((int) (panel.getWidth() * 0.4) - 25, (int) (panel.getHeight() * 0.4) - 30));
        statsScroll.setPreferredSize(new Dimension((int) (panel.getWidth() * 0.4) - 25, (int) (panel.getHeight() * 0.4) - 30));
        statsScroll.getViewport().setMinimumSize(new Dimension((int) (panel.getWidth() * 0.4) - 25, (int) (panel.getHeight() * 0.4) - 30));
        statsScroll.getViewport().setPreferredSize(new Dimension((int) (panel.getWidth() * 0.4) - 25, (int) (panel.getHeight() * 0.4) - 30));
        statsScroll.setBorder(null);
        stats.setSize(new Dimension((int) (panel.getWidth() * 0.4) - 25, (int) (panel.getHeight() * 0.4) - 10));
        stats.setEditable(false);
        statsPanel.setBorder(statsBorder);
        statsPanel.add(statsScroll);
        System.out.println("\tStats/Key Infomation built");

        //Add Genres section
        TitledBorder genresBorder;
        genresBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Constants.fontColor), "Genres");
        genresBorder.setTitleJustification(TitledBorder.CENTER);
        genresBorder.setTitleColor(Constants.fontColor);
        JPanel genres = new JPanel();
        genres.setForeground(Constants.fontColor);
        genres.setBackground(Constants.highlight);
        genres.setBounds((int) (panel.getWidth() * 0.4)+5, (int) (panel.getHeight() * 0.2) + 60, (int) (panel.getWidth() * 0.4) - 10, (int) (panel.getHeight() * 0.1));
        genres.setBorder(genresBorder);

        Genre[] genreObjs = movies.getGenres(filmID);
        if (genreObjs == null || genreObjs.length == 0) {
            genres.setLayout(new GridBagLayout());
            JPanel tmpPanel = new JPanel();
            JLabel tmpLabel = new JLabel("No Genres available", JLabel.CENTER);
            tmpLabel.setForeground(Constants.fontColor);
            tmpPanel.setBackground(Constants.highlight);
            tmpPanel.add(tmpLabel);
            genres.add(tmpPanel);
        } else {
            for (int i = 0; i < genreObjs.length; i++) {
                JPanel tmpPanel = new JPanel();
                JLabel tmpLabel = new JLabel(genreObjs[i].getName(), JLabel.CENTER);
                tmpLabel.setForeground(Constants.fontColor);
                tmpPanel.setBackground(Constants.background);
                tmpPanel.add(tmpLabel);
                genres.add(tmpPanel);
            }
        }
        System.out.println("\tGenres built");

        // Add Languages section
        TitledBorder languagesBorder;
        languagesBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Constants.fontColor), "Spoken Languages");
        languagesBorder.setTitleJustification(TitledBorder.CENTER);
        languagesBorder.setTitleColor(Constants.fontColor);
        JPanel languages = new JPanel();
        languages.setForeground(Constants.fontColor);
        languages.setBackground(Constants.highlight);
        languages.setBounds((int) (panel.getWidth() * 0.4) + 5, (int) (panel.getHeight() * 0.3) + 65, (int) (panel.getWidth() * 0.4) - 10, (int) (panel.getHeight() * 0.1));
        languages.setBorder(languagesBorder);

        String[] languageObjs = movies.getLanguages(filmID);
        if (languageObjs == null || languageObjs.length == 0) {
            languages.setLayout(new GridBagLayout());
            JPanel tmpPanel = new JPanel();
            JLabel tmpLabel = new JLabel("No Languages available", JLabel.CENTER);
            tmpLabel.setForeground(Constants.fontColor);
            tmpPanel.setBackground(Constants.highlight);
            tmpPanel.add(tmpLabel);
            languages.add(tmpPanel);
        } else {
            for (int i = 0; i < languageObjs.length; i++) {
                JPanel tmpPanel = new JPanel();
                JTextPane tmpLabel = new JTextPane();
                Document tmpDoc = tmpLabel.getStyledDocument();
                SimpleAttributeSet tmpAttributeSet = new SimpleAttributeSet();
                if (languageObjs[i].equals(movies.getOriginalLanguage(filmID))) {
                    StyleConstants.setBold(tmpAttributeSet, true);
                } else {
                    StyleConstants.setBold(tmpAttributeSet, false);
                }
                try{
                    tmpDoc.insertString(tmpDoc.getLength(), IsoSearch.iso639SearchByKey(languageObjs[i]) + " - (" + languageObjs[i] + ")", tmpAttributeSet);
                } catch (Exception e) {}
                tmpLabel.setForeground(Constants.fontColor);
                tmpLabel.setBackground(Constants.background);
                tmpPanel.setBackground(Constants.background);
                tmpPanel.add(tmpLabel);
                languages.add(tmpPanel);
            }
        }
        System.out.println("\tLanguages built");

        // Add collection
        TitledBorder collectionBorder;
        collectionBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Constants.fontColor),
                "Collection");
        collectionBorder.setTitleJustification(TitledBorder.CENTER);
        collectionBorder.setTitleColor(Constants.fontColor);
        String collectionText = movies.getCollectionName(movies.getCollectionID(filmID));
        if (collectionText == null) {
            collectionText = "Collection is NULL";
        } else if (collectionText == "") {
            collectionText = "No Collection";
        }
        JPanel collection = new JPanel();
        collection.setForeground(Constants.fontColor);
        collection.setBackground(Constants.highlight);
        collection.setBounds((int) (panel.getWidth() * 0.4) + 5, (int) (panel.getHeight() * 0.5) + 5, (int) (panel.getWidth() * 0.2) - 10, (int) (panel.getHeight() * 0.2)-5);
        collection.setBorder(collectionBorder);
        collection.setLayout(new GridBagLayout());
        JLabel collectionLabel = new JLabel(collectionText);
        collectionLabel.setForeground(Constants.fontColor);
        collectionLabel.setBackground(Constants.highlight);
        collection.add(collectionLabel);
        System.out.println("\tCollections built");

        // Add IMDb
        TitledBorder imdbBorder;
        imdbBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Constants.fontColor),
                "IMDb");
        imdbBorder.setTitleJustification(TitledBorder.CENTER);
        imdbBorder.setTitleColor(Constants.fontColor);
        String imdbVoteText = movies.getVoteAverage(filmID) + "/10 (" + movies.getVoteCount(filmID) + ")";
        String imdbID = movies.getIMDB(filmID);
        JPanel tmpIMDB = new JPanel();
        if (imdbID == null) {
            imdbID = "IMDb ID is NULL";
        } else if (imdbID == "") {
            imdbID = "No ImdbID ID";
        } else {
            tmpIMDB.addMouseListener(new MouseInputListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        Desktop.getDesktop().browse(new URI("https://www.imdb.com/title/"+movies.getIMDB(filmID)+"/"));
                    } catch (IOException | URISyntaxException e1) {
                        e1.printStackTrace();
                    }
                }
                @Override
                public void mousePressed(MouseEvent e) {}
                @Override
                public void mouseReleased(MouseEvent e) {}
                @Override
                public void mouseEntered(MouseEvent e) {}
                @Override
                public void mouseExited(MouseEvent e) {}
                @Override
                public void mouseDragged(MouseEvent e) {}
                @Override
                public void mouseMoved(MouseEvent e) {}
            });
        }
        imdbID = "IMDb ID: " + imdbID;
        JPanel imdb = new JPanel();
        imdb.setForeground(Constants.fontColor);
        imdb.setBackground(Constants.highlight);
        JPanel tmpVote = new JPanel();
        JLabel tmpVoteLabel = new JLabel(imdbVoteText, JLabel.CENTER);
        tmpVote.setForeground(Constants.fontColor);
        tmpVoteLabel.setForeground(Constants.fontColor);
        tmpVote.setBackground(Constants.highlight);
        tmpVote.add(tmpVoteLabel);
        imdb.add(tmpVote);
        JLabel tmpIMDBLabel = new JLabel(imdbID, JLabel.CENTER);
        tmpIMDB.setForeground(Constants.fontColor);
        tmpIMDB.setBackground(Constants.background);
        tmpIMDBLabel.setForeground(Constants.fontColor);
        tmpIMDB.add(tmpIMDBLabel);
        imdb.add(tmpIMDB);
        imdb.setBorder(imdbBorder);
        imdb.setBounds((int) (panel.getWidth() * 0.6), (int) (panel.getHeight() * 0.5)+5, (int) (panel.getWidth() * 0.2) - 5, (int) (panel.getHeight() * 0.2) - 5);
        System.out.println("\tIMDb built");

        TitledBorder castBorder;
        castBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Constants.fontColor), "Cast");
        castBorder.setTitleJustification(TitledBorder.LEFT);
        castBorder.setTitleColor(Constants.fontColor);
        JPanel castScrollOuterPanel = new JPanel();
        JPanel castScrollInnerPanel = new JPanel();
        JScrollPane castScroll = new JScrollPane(castScrollInnerPanel);

        castScrollOuterPanel.setBorder(castBorder);
        castScrollOuterPanel.setBounds(5, (int) (panel.getHeight() * 0.7), (int) (panel.getWidth()*0.5)-10, (int) (panel.getHeight()*0.25));
        castScrollOuterPanel.setBackground(Constants.highlight);
        castScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        castScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        castScroll.setPreferredSize(new Dimension((int) (panel.getWidth() * 0.475), (int) (panel.getHeight() * 0.2)));
        castScroll.setMinimumSize(new Dimension((int) (panel.getWidth() * 0.475), (int) (panel.getHeight() * 0.2)));
        castScroll.getViewport().setPreferredSize(new Dimension((int) (panel.getWidth() * 0.475), (int) (panel.getHeight() * 0.2)));
        castScroll.getViewport().setMinimumSize(new Dimension((int) (panel.getWidth() * 0.475), (int) (panel.getHeight() * 0.2)));
        castScrollInnerPanel.setSize(new Dimension((int) (panel.getWidth() * 0.475), (int) (panel.getHeight() * 0.2)));
        castScroll.setBackground(Constants.highlight);
        castScrollInnerPanel.setBackground(Constants.highlight);
        castScrollOuterPanel.setBackground(Constants.highlight);

        Cast[] cast = credits.getCast(filmID);
        if (cast == null) {
            castScrollOuterPanel.remove(castScroll);
            JPanel tmpPanel = new JPanel();
            tmpPanel.setBackground(Constants.background);
            JLabel tmpLabel = new JLabel("Cast is NULL");
            tmpLabel.setForeground(Constants.fontColor);
            tmpPanel.add(tmpLabel);
            castScrollOuterPanel.add(tmpPanel);
            castScrollOuterPanel.setLayout(new GridBagLayout());
        } else if (cast.length == 0) {
            castScrollOuterPanel.remove(castScroll);
            JPanel tmpPanel = new JPanel();
            tmpPanel.setBackground(Constants.background);
            JLabel tmpLabel = new JLabel("Cast is empty");
            tmpLabel.setForeground(Constants.fontColor);
            tmpPanel.add(tmpLabel);
            castScrollOuterPanel.add(tmpPanel);
            castScrollOuterPanel.setLayout(new GridBagLayout());
        } else {
            final int gapSize = 5;
            final int castPanelWidth = (int) (panel.getWidth() * 0.475)-30;
            int currentWidth = 0;
            int currentHeight = gapSize;

            JPanel horizontalPanel = new JPanel();
            horizontalPanel.setBackground(Constants.highlight);
            castScrollInnerPanel.setLayout(new BoxLayout(castScrollInnerPanel, BoxLayout.Y_AXIS));

            for (int i = 0; i < cast.length; i++) {
                JPanel tmpPanel = new JPanel();
                tmpPanel.setBackground(Constants.background);
                JLabel tmpLabel = new JLabel(cast[i].getName());
                tmpLabel.setForeground(Constants.fontColor);
                tmpPanel.add(tmpLabel);
                
                int stringWidth = panel.getGraphics().getFontMetrics().stringWidth(cast[i].getName())+10;
                if (currentWidth + (4*gapSize) + stringWidth > castPanelWidth) {
                    currentHeight += 30 + gapSize;
                    tmpPanel.setBounds(gapSize, currentHeight, stringWidth, 30);
                    currentWidth = gapSize + stringWidth;
                    castScrollInnerPanel.setSize(castPanelWidth, currentHeight + gapSize + 30);
                    castScrollInnerPanel.add(horizontalPanel);
                    horizontalPanel = new JPanel();
                    horizontalPanel.setBackground(Constants.highlight);
                    horizontalPanel.add(tmpPanel);
                } else {
                    tmpPanel.setBounds(currentWidth + gapSize, currentHeight, stringWidth, 30);
                    currentWidth += gapSize+ stringWidth;
                    horizontalPanel.add(tmpPanel);
                }
                
                castScrollInnerPanel.add(horizontalPanel);
            }
            castScrollOuterPanel.add(castScroll);
        }

        System.out.println("\tCast built");

        TitledBorder crewBorder;
        crewBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Constants.fontColor), "Crew");
        crewBorder.setTitleJustification(TitledBorder.RIGHT);
        crewBorder.setTitleColor(Constants.fontColor);
        JPanel crewScrollOuterPanel = new JPanel();
        JPanel crewScrollInnerPanel = new JPanel();
        JScrollPane crewScroll = new JScrollPane(crewScrollInnerPanel);
        crewScrollOuterPanel.setBorder(crewBorder);
        crewScrollOuterPanel.setBounds((int) (panel.getWidth() * 0.5)+5, (int) (panel.getHeight() * 0.7), (int) (panel.getWidth() * 0.5) - 10, (int) (panel.getHeight() * 0.25));
        crewScrollOuterPanel.setBackground(Constants.highlight);
        crewScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        crewScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        crewScroll.setPreferredSize(new Dimension((int) (panel.getWidth() * 0.475), (int) (panel.getHeight() * 0.2)));
        crewScroll.setMinimumSize(new Dimension((int) (panel.getWidth() * 0.475), (int) (panel.getHeight() * 0.2)));
        crewScroll.getViewport()
                .setPreferredSize(new Dimension((int) (panel.getWidth() * 0.475), (int) (panel.getHeight() * 0.2)));
        crewScroll.getViewport()
                .setMinimumSize(new Dimension((int) (panel.getWidth() * 0.475), (int) (panel.getHeight() * 0.2)));
        crewScrollInnerPanel.setSize(new Dimension((int) (panel.getWidth() * 0.475), (int) (panel.getHeight() * 0.2)));
        crewScroll.setBackground(Constants.highlight);
        crewScrollInnerPanel.setBackground(Constants.highlight);
        crewScrollOuterPanel.setBackground(Constants.highlight);

        Crew[] crew = credits.getCrew(filmID);
        if (crew == null) {
            crewScrollOuterPanel.remove(crewScroll);
            JPanel tmpPanel = new JPanel();
            tmpPanel.setBackground(Constants.background);
            JLabel tmpLabel = new JLabel("Crew is NULL");
            tmpLabel.setForeground(Constants.fontColor);
            tmpPanel.add(tmpLabel);
            crewScrollOuterPanel.add(tmpPanel);
            crewScrollOuterPanel.setLayout(new GridBagLayout());
        } else if (cast.length == 0) {
            crewScrollOuterPanel.remove(crewScroll);
            JPanel tmpPanel = new JPanel();
            tmpPanel.setBackground(Constants.background);
            JLabel tmpLabel = new JLabel("Crew is empty");
            tmpLabel.setForeground(Constants.fontColor);
            tmpPanel.add(tmpLabel);
            crewScrollOuterPanel.add(tmpPanel);
            crewScrollOuterPanel.setLayout(new GridBagLayout());
        } else {
            final int gapSize = 5;
            final int crewPanelWidth = (int) (panel.getWidth() * 0.475) - 30;
            int currentWidth = 0;
            int currentHeight = gapSize;

            JPanel horizontalPanel = new JPanel();
            horizontalPanel.setBackground(Constants.highlight);
            crewScrollInnerPanel.setLayout(new BoxLayout(crewScrollInnerPanel, BoxLayout.Y_AXIS));

            for (int i = 0; i < crew.length; i++) {
                JPanel tmpPanel = new JPanel();
                tmpPanel.setBackground(Constants.background);
                JLabel tmpLabel = new JLabel(crew[i].getName());
                tmpLabel.setForeground(Constants.fontColor);
                tmpPanel.add(tmpLabel);

                int stringWidth = panel.getGraphics().getFontMetrics().stringWidth(crew[i].getName()) + 10;
                if (currentWidth + (4 * gapSize) + stringWidth > crewPanelWidth) {
                    currentHeight += 30 + gapSize;
                    tmpPanel.setBounds(gapSize, currentHeight, stringWidth, 30);
                    currentWidth = gapSize + stringWidth;
                    crewScrollInnerPanel.setSize(crewPanelWidth, currentHeight + gapSize + 30);
                    crewScrollInnerPanel.add(horizontalPanel);
                    horizontalPanel = new JPanel();
                    horizontalPanel.setBackground(Constants.highlight);
                    horizontalPanel.add(tmpPanel);
                } else {
                    tmpPanel.setBounds(currentWidth + gapSize, currentHeight, stringWidth, 30);
                    currentWidth += gapSize + stringWidth;
                    horizontalPanel.add(tmpPanel);
                }

                crewScrollInnerPanel.add(horizontalPanel);
            }
            crewScrollOuterPanel.add(crewScroll);
        }
        System.out.println("\tCrew built");

        panel.add(title);
        panel.add(tagline);
        panel.add(overview);
        panel.add(statsPanel);
        panel.add(genres);
        panel.add(languages);
        panel.add(collection);
        panel.add(imdb);
        panel.add(castScrollOuterPanel);
        panel.add(crewScrollOuterPanel);

    }
}
