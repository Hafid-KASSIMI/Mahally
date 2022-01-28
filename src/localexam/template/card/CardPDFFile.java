/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package localexam.template.card;

import com.ibm.icu.text.ArabicShapingException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;
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
public class CardPDFFile extends BasePDFFile {
    
    private CardPage metrics;
    private final DecimalFormat INTEGER;
    private int i;
    private PDPage pg;
    private long all, fem;
    
    public CardPDFFile() {
        super();
        metrics = null;
        INTEGER = new DecimalFormat("00");
        INTEGER.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.FRANCE));
        tpl = "/localexam/template/resources/CARD_TEMPLATE.pdf";
    }
    
    public Boolean generate() {
        ArrayList<Integer> rooms;
        reset();
        if ( metrics == null )
            metrics = new CardPage();
        i = 0;
        pg = clonePage(0);
        rooms = SORTED_STUDENTS.stream().map(stu -> stu.getRoom()).distinct().collect(Collectors.toCollection(ArrayList::new));
        metrics.prepareRows(rooms.size());
        rooms.forEach(r -> {
            metrics.prepareRow(i++);
            try (PDPageContentStream pcs = new PDPageContentStream(doc, pg, PDPageContentStream.AppendMode.APPEND, true, true)) {
                placeString(metrics.getRoom(), pcs, INTEGER.format(r));
                placeString(metrics.getFirstCode(), pcs, SORTED_STUDENTS.stream().filter(s -> Objects.equals(s.getRoom(), r)).min((s1, s2) -> s1.getExamCode().compareTo(s2.getExamCode())).get().getExamCode() + "");
                placeString(metrics.getLastCode(), pcs, SORTED_STUDENTS.stream().filter(s -> Objects.equals(s.getRoom(), r)).max((s1, s2) -> s1.getExamCode().compareTo(s2.getExamCode())).get().getExamCode() + "");
                fem = SORTED_STUDENTS.stream().filter(s -> Objects.equals(s.getRoom(), r) && s.isFemale()).count();
                all = SORTED_STUDENTS.stream().filter(s -> Objects.equals(s.getRoom(), r)).count();
                placeString(metrics.getCandsCount(), pcs, all + "");
                placeString(metrics.getFemaleCount(), pcs, fem + "");
                placeString(metrics.getMaleCount(), pcs, (all - fem) + "");
            } catch (IOException | ArabicShapingException ex) { }
        });
        all = SORTED_STUDENTS.size();
        fem = SORTED_STUDENTS.stream().filter(stu -> stu.isFemale()).count();
        try (PDPageContentStream pcs = new PDPageContentStream(doc, pg, PDPageContentStream.AppendMode.APPEND, true, true)) {
            placeMultilineString(metrics.getMINISTERY(), pcs, PREF_BUNDLE.get("KINGDOM-AR").replaceAll("%new_line%", "\n"), ALIGNMENT.LEFT);
            placeMultilineString(metrics.getSCHOOL_INFOS(), pcs, SCHOOL.getAcademy() + "\n" + SCHOOL.getDirection() + "\n" + SCHOOL.getSchool(), ALIGNMENT.RIGHT);
            placeString(metrics.getYEAR(), pcs, SCHOOL.getYear());
            placeString(metrics.getALL_CANDS(), pcs, all + "");
            placeString(metrics.getALL_FEMALES(), pcs, fem + "");
            placeString(metrics.getALL_MALES(), pcs, (all - fem) + "");
            metrics.prepareDividers().forEach(div -> {
                try {
                    drawNFill(div, pcs);
                } catch (IOException ex) { }
            });
        } catch (IOException | ArabicShapingException | CloneNotSupportedException ex) { }
        removePage(0);
        return ( doc.getPages().getCount() > 0 ) ? 
            save(PREF_BUNDLE.get("OUTPUT_DIR") + generateFileName("CARD-", "pdf")) : false;
    }
    
}
