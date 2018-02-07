/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cart;

import wyp.Ksiazki;

/**
 *
 * @author tgiunipero
 */
public class ShoppingCartItem {
    Ksiazki product;
    short quantity;

    public ShoppingCartItem(Ksiazki product) {
        this.product = product;
        quantity = 1;
    }

    public Ksiazki getProduct() {
        return product;
    }

    public short getQuantity() {
        return quantity;
    }

    public void setQuantity(short quantity) {
        this.quantity = quantity;
    }

    public void incrementQuantity() {
        quantity++;
    }

    public void decrementQuantity() {
        quantity--;
    }


}