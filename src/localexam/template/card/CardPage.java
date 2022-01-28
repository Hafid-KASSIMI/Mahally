/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package localexam.template.card;

import java.awt.Color;
import java.util.ArrayList;
import org.sicut.util.pdf.AvailableFonts;
import org.sicut.util.pdf.LARectangle;

/**
 *
 * @author Sicut
 */
public class CardPage {
    protected final LARectangle MINISTERY, SCHOOL_INFOS, YEAR, ALL_MALES, ALL_FEMALES, ALL_CANDS;
    protected LARectangle room, firstCode, lastCode, maleCount, femaleCount, candsCount;
    protected float MAX_HEIGHT, FIRST_Y, LAST_Y, HEIGHT;
    protected final LARectangle DIVIDER;
    private float rowHeight, size;

    public CardPage() {
        MINISTERY = new LARectangle();
        MINISTERY.setFormat(9f, AvailableFonts.MAGHRIBI);
        MINISTERY.reset(329.080f, 29.453f, 208.335f, 54.786f);
        
        SCHOOL_INFOS = new LARectangle();
        SCHOOL_INFOS.setFormat(9f, AvailableFonts.MAGHRIBI);
        SCHOOL_INFOS.reset(57.861f, 29.453f, 208.335f, 54.786f);
        
        YEAR = new LARectangle();
        YEAR.setFormat(12f, AvailableFonts.TIMES);
        YEAR.reset(50.020f, 94.118f, 84.898f, 15.341f);
        
        ALL_MALES = new LARectangle();
        ALL_MALES.setFormat(14f, AvailableFonts.TIMES);
        ALL_MALES.reset(357.997f, 194.123f, 118.797f, 29.790f);
        ALL_FEMALES = new LARectangle();
        ALL_FEMALES.setFormat(14f, AvailableFonts.TIMES);
        ALL_FEMALES.reset(238.239f, 194.123f, 118.797f, 29.790f);
        ALL_CANDS = new LARectangle();
        ALL_CANDS.setFormat(14f, AvailableFonts.TIMES);
        ALL_CANDS.reset(118.481f, 194.123f, 118.797f, 29.790f);
        
        maleCount = new LARectangle();
        maleCount.setFormat(12f, AvailableFonts.TIMES);
        maleCount.setXW(174.820f, 60.959f);
        
        femaleCount = new LARectangle();
        femaleCount.setFormat(12f, AvailableFonts.TIMES);
        femaleCount.setXW(112.9f, 60.959f);
        
        candsCount = new LARectangle();
        candsCount.setFormat(12f, AvailableFonts.TIMES);
        candsCount.setXW(50.981f, 60.959f);
        
        firstCode = new LARectangle();
        firstCode.setFormat(12f, AvailableFonts.TIMES);
        firstCode.setXW(349.877f, 112.039f);
        lastCode = new LARectangle();
        lastCode.setFormat(12f, AvailableFonts.TIMES);
        lastCode.setXW(236.739f, 112.177f);
        room = new LARectangle();
        room.setFormat(12f, AvailableFonts.TIMES);
        room.setXW(462.877f, 81.418f);
        
        DIVIDER = new LARectangle();
        DIVIDER.setColor(new Color(0x7F, 0x7F, 0x7F));
        DIVIDER.reset(50.490f, 0, 494.766f, 0.961f);
        MAX_HEIGHT = 24.909f;
        FIRST_Y = 295.067f;
        LAST_Y = 812.582f;
        HEIGHT = LAST_Y - FIRST_Y;
    }
    
    
    public void prepareRows(long size) {
        float h = ( HEIGHT - DIVIDER.getHeight() * ( size - 1 ) ) / size;
        if ( h > MAX_HEIGHT )
            h = MAX_HEIGHT;
        room.setHeight(h);
        firstCode.setHeight(h);
        lastCode.setHeight(h);
        maleCount.setHeight(h);
        femaleCount.setHeight(h);
        candsCount.setHeight(h);
        rowHeight = h + DIVIDER.getHeight();
        this.size = size;
    }
    
    public void prepareRow(int index) {
        float y = FIRST_Y + rowHeight * index;
        room.setY(y);
        firstCode.setY(y);
        lastCode.setY(y);
        maleCount.setY(y);
        femaleCount.setY(y);
        candsCount.setY(y);
        DIVIDER.setY(y - DIVIDER.getHeight());
    }
    
    public ArrayList<LARectangle> prepareDividers() throws CloneNotSupportedException {
        ArrayList<LARectangle> arr = new ArrayList();
        LARectangle tmp;
        if ( size < 20 )
            size = 20;
        for ( int i = 1; i < size; i++ ) {
            tmp = (LARectangle) DIVIDER.clone();
            tmp.setY(FIRST_Y + rowHeight * i - DIVIDER.getHeight());
            arr.add(tmp);
        }
        return arr;
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

    public LARectangle getALL_MALES() {
        return ALL_MALES;
    }

    public LARectangle getALL_FEMALES() {
        return ALL_FEMALES;
    }

    public LARectangle getALL_CANDS() {
        return ALL_CANDS;
    }

    public LARectangle getRoom() {
        return room;
    }

    public LARectangle getFirstCode() {
        return firstCode;
    }

    public LARectangle getLastCode() {
        return lastCode;
    }

    public LARectangle getMaleCount() {
        return maleCount;
    }

    public LARectangle getFemaleCount() {
        return femaleCount;
    }

    public LARectangle getCandsCount() {
        return candsCount;
    }

    public float getRowHeight() {
        return rowHeight;
    }
}
