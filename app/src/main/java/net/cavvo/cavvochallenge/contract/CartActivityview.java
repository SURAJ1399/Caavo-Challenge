package net.cavvo.cavvochallenge.contract;

import net.cavvo.cavvochallenge.room.CartEntity;

import java.util.List;

public interface CartActivityview {

    void showProgress();
    void hideProgress();
    void requestdatafromroom();
    void setrecycleradapter(List<CartEntity> cartEntities);

}
