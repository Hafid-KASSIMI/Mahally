/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package localexam.template.label;


import org.sicut.util.pdf.AvailableFonts;
import org.sicut.util.pdf.LARectangle;

/**
 *
 * @author Sicut
 */
public class LabelPage {
    protected final LARectangle[] schoolInfos, ministeries, codes, names, rooms;
    private final int SIZE = 20;

    public LabelPage() {
        float vGap, hGap;
        
        schoolInfos = new LARectangle[SIZE];
        ministeries = new LARectangle[SIZE];
        codes = new LARectangle[SIZE];
        names = new LARectangle[SIZE];
        rooms = new LARectangle[SIZE];
        vGap = 81.229f;
        hGap = 283.465f;
        for ( int i = 0, div = 0, mod = 0; i < SIZE; i++, div = i / 2, mod = i % 2 ) {
            ministeries[i] = new LARectangle();
            ministeries[i].setFormat(6f, AvailableFonts.MAGHRIBI);
            ministeries[i].reset(454.579f - hGap * mod, 16.687f + vGap * div, 104.167f, 27.393f);
            
            schoolInfos[i] = new LARectangle();
            schoolInfos[i].setFormat(6f, AvailableFonts.MAGHRIBI);
            schoolInfos[i].reset(322.579f - hGap * mod, 16.687f + vGap * div, 104.167f, 27.393f);
            
            codes[i] = new LARectangle();
            codes[i].setFormat(20f, AvailableFonts.TRAD_AR, true);
            codes[i].reset(469.430f - hGap * mod, 80.641f + vGap * div, 107.996f, 15.341f);
            
            names[i] = new LARectangle();
            names[i].setFormat(20f, AvailableFonts.TRAD_AR, true);
            names[i].reset(322.701f - hGap * mod, 49.247f + vGap * div, 235.922f, 27.393f);
            
            rooms[i] = new LARectangle();
            rooms[i].setFormat(14f, AvailableFonts.TIMES, true);
            rooms[i].reset(322.579f - hGap * mod, 80.641f + vGap * div, 35.971f, 15.341f);
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
    
}
