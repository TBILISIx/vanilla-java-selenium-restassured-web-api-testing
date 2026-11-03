package saucedemo.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ProductCard {

    private final WebElement root;

    private final By nameLocator = By.className("inventory_item_name");
    private final By addButton = By.xpath(".//button[contains(@data-test,'add-to-cart')]");

    // ProductCard product = new ProductCard(productElement);
    // Whatever WebElement you give me, I will store it in my variable called root
    // this.root = productElement

    /*
     * HOW ROOT WORKS IN VANILLA SELENIUM:
     *
     * 1. FIND THE PRODUCT CARD
     *    driver.findElement() searches the entire webpage
     *    and returns a WebElement representing the product card.
     *
     *    WebElement element = driver.findElement(
     *        By.className("inventory_item")
     *    );
     *
     * 2. PASS THE ELEMENT TO PRODUCT CARD
     *    We pass the previously found WebElement to the
     *    ProductCard constructor.
     *
     *    ProductCard product = new ProductCard(element);
     *
     * 3. STORE THE ELEMENT IN ROOT
     *    The constructor receives the WebElement and stores
     *    it in the class-level variable called root.
     *
     *    public ProductCard(WebElement root) {
     *        this.root = root;
     *    }
     *
     * 4. USE ROOT TO INTERACT WITH THE PRODUCT CARD
     *    root.findElement() searches ONLY inside the specific
     *    product card represented by root, not the whole page.
     *
     *    product.getName();
     *    product.addToCart();
     *
     * KEY DIFFERENCE:
     *    driver.findElement() -> searches the entire webpage.
     *    root.findElement()   -> searches inside a specific element.
     *
     * Think of driver as the entire house and root as one room.
     * driver searches the whole house; root searches only that room.
     */

    public ProductCard(WebElement root) {
        this.root = root;
    }

    public String getName() {
        return root.findElement(nameLocator).getText();
    }

    public void addToCart() {
        root.findElement(addButton).click();
    }

}
