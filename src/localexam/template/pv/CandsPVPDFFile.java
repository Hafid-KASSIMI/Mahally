/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package localexam.template.pv;

import com.ibm.icu.text.ArabicShapingException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;
import static localexam.Settings.PREF_BUNDLE;
import static localexam.Settings.SCHOOL;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.sicut.util.ALIGNMENT;
import org.sicut.util.pdf.BasePDFFile;
import static localexam.Settings.SORTED_STUDENTS;

/**
 *
 * @author Sicut
 */
public class CandsPVPDFFile extends BasePDFFile {
    
    private CandsPVPage metrics;
    private final DecimalFormat INTEGER;
    private int room, roomCands, i;
    private PDPage pg;
    private final ArrayList<String> matters = new ArrayList();
    
    
    public CandsPVPDFFile() {
        super();
        metrics = null;
        INTEGER = new DecimalFormat("00");
        INTEGER.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.FRANCE));
        tpl = "/localexam/template/resources/PV_TEMPLATE.pdf";
    }
    
    public Boolean generate() {
        String matter;
        reset();
        if ( metrics == null )
            metrics = new CandsPVPage();    
        for ( int days = 0; days < 5; days++ ) {
            matters.clear();
            for ( int j = 0; j < 14; j++ ) {
                matter = PREF_BUNDLE.get("DAY_" + days + "_MAT_" + j);
                if ( !"".equals(matter) ) {
                    matters.add(matter);
                }
            }
            room = -1;
            if ( !matters.isEmpty() ) {
                SORTED_STUDENTS.forEach(stu -> {
                    if ( stu.getRoom() != room ) {
                        pg = clonePage(0);
                        room = stu.getRoom();
                        roomCands = (int) SORTED_STUDENTS.stream().filter(s -> s.getRoom() == room).count();
                        metrics.prepareRows(roomCands);
                        try (PDPageContentStream pcs = new PDPageContentStream(doc, pg, PDPageContentStream.AppendMode.APPEND, true, true)) {
                            placeMultilineString(metrics.getMINISTERY(), pcs, PREF_BUNDLE.get("KINGDOM-AR").replaceAll("%new_line%", "\n"), ALIGNMENT.LEFT);
                            placeMultilineString(metrics.getSCHOOL_INFOS(), pcs, SCHOOL.getAcademy() + "\n" + SCHOOL.getDirection() + "\n" + SCHOOL.getSchool(), ALIGNMENT.RIGHT);
                            placeString(metrics.getLEVEL(), pcs, PREF_BUNDLE.get("SELECTED_LEVEL"), ALIGNMENT.RIGHT);
                            placeString(metrics.getYEAR(), pcs, SCHOOL.getYear());
                            placeString(metrics.getCANDS(), pcs, INTEGER.format(roomCands));
                            placeString(metrics.getROOM(), pcs, INTEGER.format(room));
                            metrics.prepareDividers().forEach(div -> {
                                try {
                                    drawNFill(div, pcs);
                                } catch (IOException ex) { }
                            });
                            metrics.prepareV1N2Dividers(matters.size()).forEach(div -> {
                                try {
                                    drawNFill(div, pcs);
                                } catch (IOException ex) { }
                            });
                            i = 0;
                            matters.forEach(mat -> {
                                try {
                                    resizeNplaceString(metrics.getMatter(i++, matters.size()), pcs, mat);
                                } catch (IOException | ArabicShapingException | CloneNotSupportedException ex) { }
                            });
                        } catch (IOException | ArabicShapingException | CloneNotSupportedException ex) { }
                        i = 0;
                    }
                    try (PDPageContentStream pcs = new PDPageContentStream(doc, pg, PDPageContentStream.AppendMode.APPEND, true, true)) {
                        metrics.prepareRow(i++);
                        placeString(metrics.getName(), pcs, stu.getName(), ALIGNMENT.RIGHT);
                        placeString(metrics.getExamCode(), pcs, INTEGER.format(stu.getExamCode()));
                    } catch (IOException | ArabicShapingException ex) { }
                });
            }
        }
        removePage(0);
        return ( doc.getPages().getCount() > 0 ) ? 
            save(PREF_BUNDLE.get("OUTPUT_DIR") + generateFileName("Pv-", "pdf")) : false;
    }    
}
