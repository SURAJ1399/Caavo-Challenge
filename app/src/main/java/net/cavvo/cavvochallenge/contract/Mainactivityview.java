package net.cavvo.cavvochallenge.contract;

import net.cavvo.cavvochallenge.model.RecpieModel;

import java.util.List;

public interface Mainactivityview {

    void showProgress();
    void hideProgress();
    void requestdatafromapi(String url);
    void setrecycleradapter(List<RecpieModel> recpieModels);

}
