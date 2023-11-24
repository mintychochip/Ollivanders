package mintychochip.ollivanders.container;

import mintychochip.ollivanders.Ollivanders;
import org.bukkit.inventory.meta.BookMeta;

import java.io.IOException;

public class WizardBook {

    private final BookMeta bookMeta;

    public WizardBook(BookMeta bookMeta) {
        this.bookMeta = bookMeta;
    }


    public WizardSpell getSpell(int page) {
        try {
            return Ollivanders.getReader().setBookMeta(bookMeta).getMainSpell(page);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
