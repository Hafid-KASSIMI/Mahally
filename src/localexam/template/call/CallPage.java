/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package localexam.template.call;


import org.sicut.util.pdf.AvailableFonts;
import org.sicut.util.pdf.LARectangle;

/**
 *
 * @author Sicut
 */
public class CallPage {
    protected final LARectangle[] schoolInfos, ministeries, codes, names, rooms, years;
    private final int SIZE = 2;

    public CallPage() {
        float vGap;
        
        schoolInfos = new LARectangle[SIZE];
        ministeries = new LARectangle[SIZE];
        codes = new LARectangle[SIZE];
        names = new LARectangle[SIZE];
        rooms = new LARectangle[SIZE];
        years = new LARectangle[SIZE];
        vGap = 420.57f;
        for ( int i = 0; i < SIZE; i++ ) {
            ministeries[i] = new LARectangle();
            ministeries[i].setFormat(9f, AvailableFonts.MAGHRIBI);
            ministeries[i].reset(329.080f, 29.453f + vGap * i, 208.335f, 54.786f);
            
            schoolInfos[i] = new LARectangle();
            schoolInfos[i].setFormat(9f, AvailableFonts.MAGHRIBI);
            schoolInfos[i].reset(57.861f, 29.453f + vGap * i, 208.335f, 54.786f);
            
            codes[i] = new LARectangle();
            codes[i].setFormat(20f, AvailableFonts.TRAD_AR, true);
            codes[i].reset(177.189f, 151.182f + vGap * i, 176.625f, 18.653f);
            
            names[i] = new LARectangle();
            names[i].setFormat(20f, AvailableFonts.TRAD_AR, true);
            names[i].reset(177.501f, 124.626f + vGap * i, 176.625f, 18.653f);
            
            rooms[i] = new LARectangle();
            rooms[i].setFormat(14f, AvailableFonts.TIMES, true);
            rooms[i].reset(222.169f, 177.974f + vGap * i, 118.798f, 18.653f);
            
            years[i] = new LARectangle();
            years[i].setFormat(14f, AvailableFonts.TIMES, true);
            years[i].reset(50.020f, 94.118f + vGap * i, 84.898f, 15.341f);
        }
    }

    public LARectangle[] getSchoolInfos() {
        return schoolInfos;
    }

    public LARectangle[] getMinisteries() {
        return ministeries;
    }

    public int getSIZE() {
        return SIZE;
    }

    public LARectangle[] getCodes() {
        return codes;
    }

    public LARectangle[] getNames() {
        return names;
    }

    public LARectangle[] getRooms() {
        return rooms;
    }

    public LARectangle[] getYears() {
        return years;
    }
    
}
