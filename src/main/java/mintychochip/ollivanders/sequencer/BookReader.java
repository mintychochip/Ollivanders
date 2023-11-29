package mintychochip.ollivanders.sequencer;

import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.container.WizardSpell;
import mintychochip.ollivanders.registry.MechanicRegistry;
import org.bukkit.Bukkit;
import org.bukkit.inventory.meta.BookMeta;

import java.io.IOException;
import java.util.*;

public class BookReader { //avoid creating more instances of objects we do not need


    private BookMeta bookMeta;

    private final Map<Integer,String> content = new HashMap<>();

    public BookReader setBookMeta(BookMeta bookMeta) throws IOException {
        content.clear();
        if(!bookMeta.hasPages()) {
            throw new IOException("Book does not have any pages");
        }
        this.bookMeta = bookMeta;
        int count = 0;
        for (String page : bookMeta.getPages()) {
            content.put(count++,page);
        }
        return this;
    }
    public WizardSpell getMainSpell(String page) { //write this method better
        List<WizardSpell> spells = null;
        for (String s : page.split(",")) {
            WizardSpell build = Ollivanders.getSequencer().setSpell(s).build();
            if(spells == null) {
                spells = new ArrayList<>();
            }
            spells.add(build);
        }
        if (spells != null) {
            Collections.reverse(spells);
            for (int i = 0; i < spells.size() - 1; i++) {
                WizardSpell WizardSpell = spells.get(i + 1);
                WizardSpell.getMechanic().setTransition(spells.get(i));
            }
            return spells.get(spells.size() - 1);
        }
        return null;
    }

    public WizardSpell getMainSpell(int page) {
        return getMainSpell(content.get(page));
    }

}
