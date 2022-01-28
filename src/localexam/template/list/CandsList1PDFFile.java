/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package localexam.template.list;

import com.ibm.icu.text.ArabicShapingException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.sicut.util.ALIGNMENT;
import org.sicut.util.pdf.BasePDFFile;
import static localexam.Settings.PREF_BUNDLE;
import static localexam.Settings.SCHOOL;
import static localexam.Settings.SORTED_STUDENTS;

/**
 *
 * @author Sicut
 */
public class CandsList1PDFFile extends BasePDFFile {
    
    private CandsList1Page metrics;
    private final DecimalFormat INTEGER;
    private int room, roomCands, i;
    private PDPage pg;
    
    public CandsList1PDFFile() {
        super();
        metrics = null;
        INTEGER = new DecimalFormat("00");
        INTEGER.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.FRANCE));
        tpl = "/localexam/template/resources/LIST1_TEMPLATE.pdf";
    }
    
    public Boolean generate() {
        reset();
        if ( metrics == null )
            metrics = new CandsList1Page();    
        room = -1;
        i = 0;
        SORTED_STUDENTS.forEach(stu -> {
            if ( stu.getRoom() != room ) {
                pg = clonePage(0);
                room = stu.getRoom();
                roomCands = (int) SORTED_STUDENTS.stream().filter(s -> s.getRoom() == room).count();
                metrics.prepareRows(roomCands);
                i = 0;
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
                } catch (IOException | ArabicShapingException | CloneNotSupportedException ex) { }
            }
            try (PDPageContentStream pcs = new PDPageContentStream(doc, pg, PDPageContentStream.AppendMode.APPEND, true, true)) {
                metrics.prepareRow(i++);
                placeString(metrics.getCode(), pcs, stu.getCode());
                placeString(metrics.getName(), pcs, stu.getName(), ALIGNMENT.RIGHT);
                placeString(metrics.getGender(), pcs, stu.isFemale() ? "\ue9dd" : "\ue9dc");
                placeString(metrics.getGroup(), pcs, stu.getGroup());
                placeString(metrics.getExamCode(), pcs, INTEGER.format(stu.getExamCode()));
                placeString(metrics.getBirthDate(), pcs, stu.getBirthDate());
            } catch (IOException | ArabicShapingException ex) { }
        });
        removePage(0);
        return save(PREF_BUNDLE.get("OUTPUT_DIR") + generateFileName("List1-", "pdf"));
    }
    
}
