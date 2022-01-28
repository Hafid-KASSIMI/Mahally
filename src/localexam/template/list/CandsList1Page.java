/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package localexam.template.list;

import java.awt.Color;
import java.util.ArrayList;
import org.sicut.util.pdf.AvailableFonts;
import org.sicut.util.pdf.LARectangle;

/**
 *
 * @author Sicut
 */
public class CandsList1Page {
    protected final LARectangle MINISTERY, SCHOOL_INFOS, YEAR, LEVEL, ROOM, CANDS;
    protected LARectangle code, name, gender, examCode, birthDate, group;
    protected float MAX_HEIGHT, FIRST_Y, LAST_Y, HEIGHT;
    protected final LARectangle DIVIDER;
    private float rowHeight, size;

    public CandsList1Page() {
        MINISTERY = new LARectangle();
        MINISTERY.setFormat(9f, AvailableFonts.MAGHRIBI);
        MINISTERY.reset(329.080f, 43.627f, 208.335f, 54.786f);
        
        SCHOOL_INFOS = new LARectangle();
        SCHOOL_INFOS.setFormat(9f, AvailableFonts.MAGHRIBI);
        SCHOOL_INFOS.reset(57.861f, 43.627f, 208.335f, 54.786f);
        
        YEAR = new LARectangle();
        YEAR.setFormat(12f, AvailableFonts.TIMES);
        YEAR.reset(42.520f, 106.118f, 84.898f, 15.341f);
        
        LEVEL = new LARectangle();
        LEVEL.setFormat(12f, AvailableFonts.TIMES);
        LEVEL.reset(226.970f, 132.541f, 283.189f, 15.341f);
        ROOM = new LARectangle();
        ROOM.setFormat(12f, AvailableFonts.TIMES, true);
        ROOM.reset(148.118f, 132.541f, 39.886f, 15.341f);
        CANDS = new LARectangle();
        CANDS.setFormat(12f, AvailableFonts.TIMES);
        CANDS.reset(42.520f, 132.541f, 39.886f, 15.341f);
        
        examCode = new LARectangle();
        examCode.setFormat(12f, AvailableFonts.TIMES);
        examCode.setXW(497.799f, 53.996f);
        code = new LARectangle();
        code.setFormat(12f, AvailableFonts.TIMES);
        code.setXW(415.842f, 80.996f);
        name = new LARectangle();
        name.setFormat(18f, AvailableFonts.TRAD_AR);
        name.setXW(221.438f, 193.443f);
        gender = new LARectangle();
        gender.setFormat(11f, AvailableFonts.ICOMOON);
        gender.setXW(191.438f, 29.039f);
        birthDate = new LARectangle();
        birthDate.setFormat(12f, AvailableFonts.TIMES);
        birthDate.setXW(116.438f, 74.039f);
        group = new LARectangle();
        group.setFormat(12f, AvailableFonts.TIMES);
        group.setXW(43.481f, 71.996f);
        
        DIVIDER = new LARectangle();
        DIVIDER.setColor(new Color(0x7F, 0x7F, 0x7F));
        DIVIDER.reset(42.520f, 0, 510.236f, 0.961f);
        MAX_HEIGHT = 29.156f;
        FIRST_Y = 196.067f;
        LAST_Y = 798.409f;
        HEIGHT = LAST_Y - FIRST_Y;
    }
    
    
    public void prepareRows(long size) {
        float h = ( HEIGHT - DIVIDER.getHeight() * ( size - 1 ) ) / size;
        if ( h > MAX_HEIGHT )
            h = MAX_HEIGHT;
        code.setHeight(h);
        name.setHeight(h);
        gender.setHeight(h);
        examCode.setHeight(h);
        group.setHeight(h);
        birthDate.setHeight(h);
        rowHeight = h + DIVIDER.getHeight();
        this.size = size;
    }
    
    public void prepareRow(int index) {
        float y = FIRST_Y + rowHeight * index;
        code.setY(y);
        name.setY(y);
        gender.setY(y);
        examCode.setY(y);
        group.setY(y);
        birthDate.setY(y);
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

    public LARectangle getROOM() {
        return ROOM;
    }

    public LARectangle getCANDS() {
        return CANDS;
    }

    public LARectangle getGroup() {
        return group;
    }

    public LARectangle getLEVEL() {
        return LEVEL;
    }

    public LARectangle getCode() {
        return code;
    }

    public LARectangle getName() {
        return name;
    }

    public LARectangle getGender() {
        return gender;
    }

    public LARectangle getExamCode() {
        return examCode;
    }

    public float getRowHeight() {
        return rowHeight;
    }

    public LARectangle getBirthDate() {
        return birthDate;
    }
}
