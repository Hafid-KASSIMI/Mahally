/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package localexam.template.pv;

import java.awt.Color;
import java.util.ArrayList;
import org.sicut.util.pdf.AvailableFonts;
import org.sicut.util.pdf.LARectangle;

/**
 *
 * @author Sicut
 */
public class CandsPVPage {
    protected final LARectangle MINISTERY, SCHOOL_INFOS, YEAR, LEVEL, ROOM, CANDS;
    protected LARectangle name, examCode, matter;
    protected float MAX_HEIGHT, FIRST_Y, LAST_Y, HEIGHT;
    protected final LARectangle HOR_DIVIDER, VER_DIVIDER1, VER_DIVIDER2;
    private float rowHeight, size;

    public CandsPVPage() {
        MINISTERY = new LARectangle();
        MINISTERY.setFormat(9f, AvailableFonts.MAGHRIBI);
        MINISTERY.reset(329.080f, 15.280f, 208.335f, 54.786f);
        
        SCHOOL_INFOS = new LARectangle();
        SCHOOL_INFOS.setFormat(9f, AvailableFonts.MAGHRIBI);
        SCHOOL_INFOS.reset(57.861f, 15.280f, 208.335f, 54.786f);
        
        YEAR = new LARectangle();
        YEAR.setFormat(12f, AvailableFonts.TIMES);
        YEAR.reset(15.128f, 83.608f, 84.898f, 15.341f);
        
        LEVEL = new LARectangle();
        LEVEL.setFormat(12f, AvailableFonts.TIMES);
        LEVEL.reset(239.532f, 134.110f, 299.933f, 15.341f);
        
        ROOM = new LARectangle();
        ROOM.setFormat(12f, AvailableFonts.TIMES, true);
        ROOM.reset(152.033f, 134.110f, 35.971f, 15.341f);
        
        CANDS = new LARectangle();
        CANDS.setFormat(12f, AvailableFonts.TIMES);
        CANDS.reset(46.435f, 134.110f, 35.971f, 15.341f);
        
        examCode = new LARectangle();
        examCode.setFormat(12f, AvailableFonts.TIMES);
        examCode.setXW(526.299f, 55.764f);
        
        name = new LARectangle();
        name.setFormat(14f, AvailableFonts.TRAD_AR);
        name.setXW(390.395f, 134.943f);
        
        matter = new LARectangle();
        matter.setFormat(12f, AvailableFonts.TIMES, true);
        matter.reset(15.128f, 153.407f, 374.306f, 39.238f);
        
        HOR_DIVIDER = new LARectangle();
        HOR_DIVIDER.setColor(new Color(0x7F, 0x7F, 0x7F));
        HOR_DIVIDER.reset(14.173f, 0, 567.890f, 0.961f);
        
        VER_DIVIDER1 = new LARectangle();
        VER_DIVIDER1.setColor(new Color(0x7F, 0x7F, 0x7F));
        VER_DIVIDER1.reset(0, 677.447f, 0.961f, 524.866f);
        
        VER_DIVIDER2 = new LARectangle();
        VER_DIVIDER2.setColor(new Color(0x7F, 0x7F, 0x7F));
        VER_DIVIDER2.reset(0, 804.116f, 0.961f, 117.048f);
        
        MAX_HEIGHT = 23.183f;
        FIRST_Y = 194.567f;
        LAST_Y = 677.447f;
        HEIGHT = LAST_Y - FIRST_Y;
    }
    
    
    public void prepareRows(long size) {
        float h = ( HEIGHT - HOR_DIVIDER.getHeight() * ( size - 1 ) ) / size;
        if ( h > MAX_HEIGHT )
            h = MAX_HEIGHT;
        name.setHeight(h);
        examCode.setHeight(h);
        rowHeight = h + HOR_DIVIDER.getHeight();
        this.size = size;
    }
    
    public void prepareRow(int index) {
        float y = FIRST_Y + rowHeight * index;
        name.setY(y);
        examCode.setY(y);
        HOR_DIVIDER.setY(y - HOR_DIVIDER.getHeight());
    }
    
    public ArrayList<LARectangle> prepareDividers() throws CloneNotSupportedException {
        ArrayList<LARectangle> arr = new ArrayList();
        LARectangle tmp;
        if ( size < 20 )
            size = 20;
        for ( int i = 1; i < size; i++ ) {
            tmp = (LARectangle) HOR_DIVIDER.clone();
            tmp.setY(FIRST_Y + rowHeight * i - HOR_DIVIDER.getHeight());
            arr.add(tmp);
        }
        return arr;
    }
    
    public ArrayList<LARectangle> prepareV1N2Dividers(int n) throws CloneNotSupportedException {
        ArrayList<LARectangle> arr = new ArrayList();
        LARectangle v1, v2;
        float w = matter.getWidth() / n;
        for ( int i = 1; i < n; i++ ) {
            v1 = (LARectangle) VER_DIVIDER1.clone();
            v1.setX(matter.getX() + matter.getWidth() - w * i - v1.getWidth());
            arr.add(v1);
            v2 = (LARectangle) VER_DIVIDER2.clone();
            v2.setX(matter.getX() + matter.getWidth() - w * i - v2.getWidth());
            arr.add(v2);
        }
        return arr;
    }
    
    public LARectangle getMatter(int index, int size) throws CloneNotSupportedException {
        LARectangle mat = (LARectangle) matter.clone();
        float w = matter.getWidth() / size;
        index++;
        mat.setXW(matter.getX() + matter.getWidth() - w * index, w);
        return mat;
    }

    public LARectangle getMINISTERY() {
        return MINISTERY;
    }

    public LARectangle getSCHOOL_INFOS() {
        return SCHOOL_INFOS;
    }

    public LARectangle getYEAR() {
        return YEAR;
    }

    public LARectangle getROOM() {
        return ROOM;
    }

    public LARectangle getCANDS() {
        return CANDS;
    }

    public LARectangle getLEVEL() {
        return LEVEL;
    }

    public LARectangle getName() {
        return name;
    }

    public LARectangle getExamCode() {
        return examCode;
    }

    public float getRowHeight() {
        return rowHeight;
    }
}
