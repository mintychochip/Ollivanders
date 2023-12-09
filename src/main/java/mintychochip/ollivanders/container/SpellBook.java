package mintychochip.ollivanders.container;

import mintychochip.ollivanders.Ollivanders;
import org.bukkit.Bukkit;
import org.bukkit.inventory.meta.BookMeta;

import java.io.IOException;
import java.util.*;

public class SpellBook {

    private Map<Integer,String> content;

    public SpellBook(BookMeta bookMeta) throws IOException {
        if(!bookMeta.hasPages()) {
            throw new IOException("This book is empty");
        }
        content = new HashMap<>();
        int count = 0;
        for (String page : bookMeta.getPages()) {
            content.put(count++,page);
        }
    }

    public Spell mainSpell(String page) {
        if(page == null) {
            return null;
        }
        List<Spell> spellList =new ArrayList<>();
        for (String s : page.split(",")) {
            Spell spell = Ollivanders.getTokenizer().defaultBuild(s);
            spell.getMechanic().setTransition(null);
            spellList.add(spell);
            Bukkit.broadcastMessage(spell.getMechanic().getShape().toString());
        }
        Collections.reverse(spellList);
        int index = spellList.size() - 1;
        for(int i = 0; i < index; i++) {
            Spell subSpell = spellList.get(i);
            spellList.get(i + 1).getMechanic().setTransition(subSpell);
        }
        return spellList.get(index);
    }

    public Spell mainSpell(int page) {
        return mainSpell(content.get(page));
    }
}
