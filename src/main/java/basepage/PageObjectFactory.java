package basepage;

import org.openqa.selenium.WebDriver;


/* Before PageObjectFactory every test was responsible for creating its own page.
After this implementation the factory creates pages for the tests.
It doesn't change what the tests do. It just moves the object-creation responsibility into one place.
That's the whole purpose of PageObjectFactory.

PageObjectFactory is with generic method so T in there can be any class that extends / inherits from BasePage.

The factory doesn't adapt by itself. It only works because every page follows one rule:
 **the constructor takes exactly one `WebDriver`**. If `LoginPage` suddenly needed `WebDriver` + `String`, `create()`
 would fail with the "must have a constructor with one WebDriver" error.

You have three options:

1.
 **Keep the rule (best for you now). - ** Every page takes only `WebDriver`.
If a page needs extra data, pass it to a method, not the constructor.
For example, `open(String url)` instead of `new LoginPage(driver, url)`. Or read it from config later.

2.
**Make the factory accept extra arguments.** - It's possible,
but it needs more reflection code and gets harder to read. That's over-engineering.

3.
 **Create odd pages with `new` directly.** Use the factory for the normal pages, and write `new SpecialPage(driver, "text")` for the exception.
 It's simple, but you lose the one-style benefit for that page.

The rule: all pages are built the same way, so one factory can build them all.
Real frameworks work like this too. Carina's `initPage` also expects pages to follow a fixed constructor shape.
*/

public final class PageObjectFactory {

    private PageObjectFactory() {
    }

    public static <T extends BasePage> T create(Class<T> pageClass, WebDriver driver) {
        try {
            return pageClass.getConstructor(WebDriver.class).newInstance(driver);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException(
                    pageClass.getSimpleName() + " must have a public constructor with one WebDriver parameter", e);
        }
    }

}